package com.gitee.linzl.file;

import com.gitee.linzl.file.event.ProgressListener;
import com.gitee.linzl.file.model.SplitFileRequest;
import com.gitee.linzl.file.progress.MergeRunnable;
import com.gitee.linzl.file.progress.SplitRunnable;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * 文件处理辅助类
 * <p>
 * 认识文本和文本文件 java的文本(char)是16位无符号整数，是字符的unicode编码(双字节编码) 文件是byte byte
 * byte……的数据序列 文本文件是文本(char)序列按照某种编码(gbk,utf-8,utf-16be)序列化为byte的存储结果
 *
 * @author linzl
 * @description
 * @email 2225010489@qq.com
 * @date 2018年5月4日
 */
public class FileUtil {
    private static final String DEFAULT_CHARSET = "GBK";

    /**
     * 按字符数读取文件,常用于读文本，数字等类型的文本文件 Reader/Writer 如读word\pdf等使用ISO-8859-1
     *
     * @param file    需要读取的文件对象
     * @param charset 指定文件读取的编码,不指定默认为GBK
     * @return
     * @throws IOException
     */
    public static String readFileByChars(File file, String charset) throws Exception {
        if (!file.exists()) {
            throw new Exception(file.getPath() + "不存在该文件");
        }

        String realCharset = (charset == null || charset.trim().length() <= 0) ? DEFAULT_CHARSET : charset;
        StringBuilder sb = new StringBuilder();
        try (InputStream is = new FileInputStream(file);
             Reader reader = new InputStreamReader(is, realCharset);
             BufferedReader breader = new BufferedReader(reader)) {
            char[] cbuf = new char[is.available()];
            while (breader.read(cbuf) > -1) {
                sb.append(cbuf);
            }
        } catch (Exception e) {

        }
        return sb.toString();
    }

    /**
     * 按行读取
     *
     * @param file    需要读取的文件对象
     * @param charset 指定文件读取的编码,不指定默认为GBK
     * @return
     * @throws Exception
     */
    public static List<String> readAllLines(File file, String charset) throws Exception {
        if (!file.exists()) {
            throw new Exception(file.getPath() + "不存在该文件");
        }
        String realCharset = (charset == null || charset.trim().length() <= 0) ? DEFAULT_CHARSET : charset;
        return Files.readAllLines(file.toPath(), Charset.forName(realCharset));
    }

    /***
     * 递归获取指定目录下的所有的文件(不包括文件夹)
     *
     * @param dir
     * @return
     */
    public static List<File> getFiles(File dir) {
        List<File> files = new ArrayList<>();

        if (dir.isDirectory()) {
            File[] fileArr = dir.listFiles();
            if (Objects.nonNull(fileArr)) {
                Arrays.stream(fileArr).forEach((file) -> {
                    if (file.isFile()) {
                        files.add(file);
                    } else {
                        files.addAll(getFiles(file));
                    }
                });
            }
        }
        return Optional.ofNullable(files).orElseGet(ArrayList::new);
    }

    /**
     * 获取指定目录下的直接文件
     *
     * @param dir
     * @return
     */
    public static List<File> getDirFiles(File dir) {
        List<File> files = null;
        if (dir.isDirectory()) {
            File[] fileArr = dir.listFiles();
            if (Objects.nonNull(fileArr)) {
                files = Arrays.stream(fileArr).filter(file -> file.isFile()).collect(Collectors.toList());
            }
        }
        return Optional.ofNullable(files).orElseGet(ArrayList::new);
    }

    /**
     * 获取指定目录下特定文件后缀名的文件列表(不包括子文件夹)
     *
     * @param dirPath 目录路径
     * @param suffix  文件后缀
     * @return
     */
    public static List<File> getDirFiles(String dirPath, final String suffix) {
        File path = new File(dirPath);
        File[] fileArr = path.listFiles((File dir, String name) -> {
            String lowerName = name.toLowerCase();
            String lowerSuffix = suffix.toLowerCase();
            if (lowerName.endsWith(lowerSuffix)) {
                return true;
            }
            return false;
        });

        List<File> files = null;
        if (Objects.nonNull(fileArr)) {
            files = Arrays.stream(fileArr).filter(file -> file.isFile()).collect(Collectors.toList());
        }
        return Optional.ofNullable(files).orElseGet(ArrayList::new);
    }

    /**
     * 读取文件内容
     *
     * @param file 待读取的文件
     * @return 文件内容
     * @throws IOException
     */
    public byte[] read(File file) throws Exception {
        if (!file.isFile()) {
            throw new Exception("file不是文件");
        }

        /*byte[] bytes;
        try (InputStream fs = new BufferedInputStream(new FileInputStream(file))) {
            bytes = new byte[fs.available()];
            fs.read(bytes);
        }
        return bytes;*/
        return Files.readAllBytes(file.toPath());
    }

    /**
     * 读取文件的行数
     *
     * @param file
     * @return
     */
    public static int readLineNumber(File file) {
        try (LineNumberReader reader = new LineNumberReader(new FileReader(file))) {
            if (Objects.nonNull(file) && file.exists()) {
                long fileLength = file.length();
                reader.skip(fileLength);
                return reader.getLineNumber();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 读取文件最后多少行
     *
     * @param file
     * @param charset
     * @param count
     * @return
     * @throws Exception
     */
    public static String readLastLine(File file, Charset charset, int count) throws Exception {
        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            throw new Exception("文件不存在or目标为文件夹or文件不可读");
        }

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            long len = raf.length();
            if (len == 0L) {
                throw new Exception("文件为空");
            }
            // 文本的最后一位为结束符，所以要减去1
            long pos = len - 1;
            while (count > 0 && pos > 0) {
                pos--;
                raf.seek(pos);
                // 得到 count 行数据
                if (raf.read() == '\n' || raf.read() == '\r') {
                    count--;
                }
            }
            if (pos == 0) {
                raf.seek(0);
            }
            // 文本的最后一位为结束符，所以要减去1
            byte[] bytes = new byte[(int) (len - 1 - pos)];
            raf.read(bytes);

            if (Objects.isNull(charset)) {
                return new String(bytes);
            }
            return new String(bytes, charset);
        }
    }

    /**
     * 写文件
     *
     * @param file        目标文件
     * @param fileContent 写入的内容
     * @return
     * @throws Exception
     */
    public static boolean write(File file, byte[] fileContent) throws Exception {
        return write(file, fileContent, false);
    }

    /**
     * 写文件
     *
     * @param file        目标文件
     * @param fileContent 写入的内容
     * @param append      是否追加在文件末尾
     * @return
     * @throws Exception
     */
    public static boolean write(File file, byte[] fileContent, boolean append) throws Exception {
        if (!file.exists() || !file.isFile()) {
            throw new Exception("file不是文件");
        }

        boolean result = false;
        try (OutputStream fs = new BufferedOutputStream(new FileOutputStream(file, append))) {
            fs.write(fileContent);
            result = true;
        }
        return result;
    }

    /**
     * 将内容写进文件头,文件内容大的时候不建议这么做
     *
     * @param file
     * @param fileContent
     * @return
     */
    public static boolean writeHead(File file, byte[] fileContent) throws IOException {
        boolean result = false;
        if (file.exists() && file.isFile()) {
            long originLen = file.length();
            byte[] readFile = new byte[(int) originLen];
            try (RandomAccessFile rFile = new RandomAccessFile(file, "rw")) {
                // 读取所有文件内容
                rFile.read(readFile);
                rFile.seek(0);
                rFile.write(fileContent);
                rFile.write(readFile);
                result = true;
            }
        }
        return result;
    }

    /**
     * @param file     要分片的文件
     * @param byteSize 分片大小
     * @throws IOException
     */
    public static void asynSplitFile(File file, int byteSize, ProgressListener listener) throws IOException {
        long fileSize = file.length();
        int number = (int) (fileSize / byteSize);
        number = (fileSize % byteSize == 0 ? number : number + 1);// 分割后文件的数目

        ExecutorService executor = (ExecutorService) Executors.newCachedThreadPool();
        SplitFileRequest request;
        List<Future<Boolean>> completeList = new ArrayList<>();
        for (int index = 0; index < number; index++) {
            request = new SplitFileRequest();
            request.setFile(file);
            request.setPartNum(index);
            request.setPartSize(byteSize);
            request.setListener(listener);
            Future<Boolean> future = executor.submit(new SplitRunnable(request));
            completeList.add(future);
        }

        int totalCompleteNumber = 0;
        Iterator<Future<Boolean>> iter = completeList.iterator();
        while (iter.hasNext()) {
            boolean flag = false;
            try {
                flag = iter.next().get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            if (flag) {
                ++totalCompleteNumber;
            }
        }
        completeList.clear();
        if (totalCompleteNumber == number) {
            // 发布分片结束事件
        }
    }

    /**
     * 异步合并文件,合并完成会删除分片文件
     *
     * @param dirPath        拆分文件所在目录名
     * @param partFileSuffix 拆分文件后缀名
     * @param partFileSize   拆分文件的字节数大小
     * @param mergeFile      合并后的文件
     * @throws IOException
     */
    public static void asynMergeFiles(String dirPath, String partFileSuffix, int partFileSize, File mergeFile)
            throws IOException {
        List<File> partFiles = getDirFiles(dirPath, partFileSuffix);
        Collections.sort(partFiles, new FileComparator());
        ExecutorService executor = (ExecutorService) Executors.newCachedThreadPool();

        Map<Integer, Future<Boolean>> completeMap = new HashMap<>();
        for (int index = 0, length = partFiles.size(); index < length; index++) {
            Future<Boolean> future = executor
                    .submit(new MergeRunnable(index * partFileSize, mergeFile, partFiles.get(index)));
            completeMap.put(index, future);
        }
        Set<Integer> set = completeMap.keySet();
        Iterator<Integer> iter = set.iterator();
        Integer index = 0;
        try {
            while (iter.hasNext()) {
                index = iter.next();
                if (completeMap.get(index).get()) {// 表示已经合并成功，即可删除
                    partFiles.get(index).deleteOnExit();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            partFiles.clear();
            completeMap.clear();
        }
    }

    /**
     * 根据文件名，比较文件
     *
     * @author linzl
     * @description
     * @email 2225010489@qq.com
     * @date 2018年6月26日
     */
    private static class FileComparator implements Comparator<File> {
        @Override
        public int compare(File o1, File o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    }

    public static long fileSize(File file) {
        try {
            return fileSize(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long fileSize(FileInputStream file) {
        try {
            FileChannel fc = file.getChannel();
            return fc.size();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 删除目录下所有直接子文件
     *
     * @param dir
     */
    public static void deleteDoc(File dir) {
        if (dir == null || !dir.isDirectory()) {
            return;
        }
        File[] childFiles = dir.listFiles();
        for (int i = 0; i < childFiles.length; i++) {
            childFiles[i].delete();
        }
    }

    /**
     * 删除目录下的文件及其子目录的文件
     */
    public static void recursionDeleteDoc(File dir) {
        if (dir == null || !dir.isDirectory()) {
            return;
        }
        // 列出目录下所有文件夹和文件的名称
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File param : files) {
            if (param.isDirectory()) {
                recursionDeleteDoc(param);
            }
            param.delete();
        }
    }

    /**
     * 删除目录下所有文件,包括该目录
     */
    public static void recursionDeleteFile(File file) {
        // 列出目录下所有文件夹和文件的名称
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File param : files) {
            if (param.isDirectory()) {
                recursionDeleteFile(param);
            }
            param.delete();
        }
        file.delete();
    }

    /**
     * 删除该路径下所有名称匹配的文件，包括路径下文件夹中的文件
     *
     * @param dir     删除路径
     * @param pattern 匹配文件名的正则表达式
     * @throws Exception
     */
    public void deleteDoc(File dir, String pattern) throws Exception {
        if (dir.exists()) {
            List<File> list = findMatchFileName(dir, pattern);
            for (File dest : list) {
                deleteDoc(dest);
            }
        } else {
            throw new Exception(dir + "路径不存在");
        }
    }

    /**
     * 重命名文件名称
     *
     * @param file    需要重命名的文件
     * @param newName 新的文件名称(不用带后缀)
     * @throws Exception
     */
    public static void reName(File file, String newName) throws Exception {
        if (file.exists()) {
            // 文件的后缀
            String filePath = file.getPath();
            String suffix = filePath.substring(filePath.lastIndexOf("."));
            file.renameTo(new File(file.getParentFile().getPath(), newName + suffix));
        } else {
            throw new Exception("该" + file.getPath() + "路径不存在");
        }
    }


    /**
     * 递归搜索目录下文件名与pattern匹配的所有文件
     *
     * @param dir     搜索路径
     * @param pattern 匹配文件名的正则表达式
     * @return
     * @throws Exception
     */
    public List<File> findMatchFileName(File dir, String pattern) {
        List<File> docList = getFiles(dir);
//		for (File file : new ArrayList<File>(docList)) {// 复制一份，List不能安全删除，除非使用Iterator
//			if (file.getName().indexOf(pattern) < 0) {
//				docList.remove(file);
//			}
//		}

        // 或者
        Iterator<File> iter = docList.iterator();
        if (iter != null) {
            while (iter.hasNext()) {
                File file = iter.next();
                if (file.getName().indexOf(pattern) < 0) {
                    iter.remove();
                }
            }
        }
        return docList;
    }


    /**
     * 复制文件
     *
     * @param source 源文件
     * @param target 复制文件
     * @throws IOException
     */
    public static void copyFile(File source, File target) throws IOException {
        byte[] sourceB = new byte[1024 * 5];
        int length;

        try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(source));
             BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(target))) {
            while ((length = is.read(sourceB)) > -1) {
                fos.write(sourceB, 0, length);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //writeHead(new File("D://CZ9700000630000020190117103159.txt"), "我是中国人" .getBytes());
        List<String> list = Files.readAllLines(Paths.get("D:\\333.txt"));
        System.out.println(list);
    }
}