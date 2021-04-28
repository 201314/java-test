package com.gitee.linzl.file;

import com.gitee.linzl.file.event.ProgressListener;
import com.gitee.linzl.file.model.SplitFileRequest;
import com.gitee.linzl.file.progress.MergeRunnable;
import com.gitee.linzl.file.progress.SplitRunnable;

import javax.activation.MimetypesFileTypeMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
     * 获取jar包或war中 java.util.jar.JarFile#MANIFEST_NAME 的信息
     *
     * @param jarFile
     * @return
     * @throws IOException
     */
    public static Manifest getManifest(File jarFile) throws IOException {
        Manifest manifest;
        try (InputStream is = new FileInputStream(jarFile);
             JarInputStream jin = new JarInputStream(is)) {
            manifest = jin.getManifest();
        }
        return manifest;
    }

    /**
     * 按字符数读取文件,常用于读文本，数字等类型的文本文件 Reader/Writer 如读word\pdf等使用ISO-8859-1
     *
     * @param file    需要读取的文件对象
     * @param charset 指定文件读取的编码,不指定默认为GBK
     * @return
     * @throws IOException
     */
    public static String read(File file, String charset) throws Exception {
        if (!file.exists()) {
            throw new Exception(file.getPath() + "不存在该文件");
        }

        String realCharset = (charset == null || charset.trim().length() <= 0) ? DEFAULT_CHARSET : charset;
        StringBuilder sb = new StringBuilder();
        try (InputStream is = new FileInputStream(file);
             Reader reader = new InputStreamReader(is, realCharset);
             BufferedReader breader = new BufferedReader(reader)) {
            char[] cbuf = new char[1024];
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

    /**
     * 读取文件内容
     *
     * @param file 待读取的文件
     * @return 文件内容
     * @throws IOException
     */
    public static byte[] read(File file) throws Exception {
        if (!file.isFile()) {
            throw new Exception("file不是文件");
        }
        return Files.readAllBytes(file.toPath());
    }

    /**
     * 获取指定目录下的直接文件
     *
     * @param dir
     * @return
     */
    public static List<File> list(File dir) {
        List<File> files = null;
        Stream<Path> stream;
        try {
            stream = Files.list(dir.toPath());
            files = stream.filter(path -> path.toFile().isFile())

                    .map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(files).orElseGet(ArrayList::new);
    }


    /**
     * 获取指定目录下特定文件后缀名的文件列表(不包括子文件夹)
     *
     * @param dir    目录路径
     * @param suffix 文件后缀
     * @return
     */
    public static List<File> list(File dir, String suffix) {
        List<File> files = null;
        try {
            Stream<Path> stream = Files.list(dir.toPath());
            files = stream.filter(path -> path.toFile().isFile() && path.toFile().getName().endsWith(suffix))

                    .map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(files).orElseGet(ArrayList::new);
    }

    /***
     * 递归获取指定目录下的所有的文件(不包括文件夹)
     *
     * @param dir
     * @return
     */
    public static List<File> recursiveList(File dir) {
        return recursiveList(dir, null);
    }

    public static List<File> recursiveList(File dir, String suffix) {
        List<File> files = new ArrayList<>();

        try {
            Stream<Path> stream = Files.list(dir.toPath());
            stream.forEach((path) -> {
                if (path.toFile().isFile()) {
                    if (Objects.isNull(suffix) || path.toFile().getName().endsWith(suffix)) {
                        files.add(path.toFile());
                    }
                } else {
                    files.addAll(recursiveList(path.toFile()));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(files).orElseGet(ArrayList::new);
    }

    /**
     * 读取文件的行数
     *
     * @param file
     * @return
     */
    public static int lineNumber(File file) {
        try (LineNumberReader reader = new LineNumberReader(new FileReader(file))) {
            long fileLength = file.length();
            reader.skip(fileLength);
            return reader.getLineNumber() + 1;
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
                if (raf.read() == '\n') { // \r的是否也需要判断，操作系统不同换行不同
                    count--;
                }
            }
            if (pos == 0) {
                raf.seek(0);
            }
            // 文本的最后一位为结束符，所以要减去1
            byte[] bytes = new byte[(int) (len - 1 - pos)];
            raf.read(bytes);
            return Objects.isNull(charset) ? new String(bytes) : new String(bytes, charset);
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
        Path path;
        if (append) {
            path = Files.write(file.toPath(), fileContent, StandardOpenOption.APPEND);
        } else {
            path = Files.write(file.toPath(), fileContent);
        }
        return Objects.nonNull(path);
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
        // 分割后文件的数目
        int number = (int) ((fileSize + byteSize - 1) / byteSize);

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
     * @param dir            需要合并的文件所在目录名
     * @param partFileSuffix 需要合并的文件后缀名
     * @param partFileSize   需要合并文件的字节数大小,必须每个分片固定大小，除了最后一个分片
     * @param mergeFile      合并后的文件
     */
    public static void asynMergeFiles(File dir, String partFileSuffix, int partFileSize, File mergeFile) {
        List<File> partFiles = list(dir, partFileSuffix);
        /**
         * 需要合并的分片进行排序
         */
        Collections.sort(partFiles, new FileComparator());

        ExecutorService executor = Executors.newCachedThreadPool();

        Map<Integer, Future<Boolean>> completeMap = new HashMap<>(partFiles.size());
        for (int index = 0, length = partFiles.size(); index < length; index++) {
            Future<Boolean> future = executor
                    .submit(new MergeRunnable(index * partFileSize, mergeFile, partFiles.get(index)));
            completeMap.put(index, future);
        }

        Set<Integer> set = completeMap.keySet();
        Iterator<Integer> iter = set.iterator();
        Integer index;
        try {
            while (iter.hasNext()) {
                index = iter.next();
                // 表示已经合并成功，即可删除
                if (completeMap.get(index).get()) {
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

    public static long size(File file) {
        try {
            return Files.size(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long size(FileInputStream file) {
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
    public static void delete(File dir) {
        List<File> fileList = list(dir);
        fileList.stream().forEach(file -> file.deleteOnExit());
    }

    /**
     * 删除该路径下所有名称匹配的文件，包括路径下文件夹中的文件
     *
     * @param dir    删除路径
     * @param suffix 匹配文件名的正则表达式
     * @throws Exception
     */
    public static void delete(File dir, String suffix) {
        List<File> fileList = list(dir, suffix);
        fileList.stream().forEach(file -> file.deleteOnExit());
    }

    /**
     * 删除目录下的文件及其子目录的文件,不删除目录
     */
    public static void recursionDelete(File dir) {
        List<File> fileList = recursiveList(dir);
        fileList.stream().forEach(file -> file.deleteOnExit());
    }

    public static void recursionDelete(File dir, String suffix) {
        List<File> fileList = recursiveList(dir, suffix);
        fileList.stream().forEach(file -> file.deleteOnExit());
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
     * 复制文件
     *
     * @param source 源文件
     * @param target 复制文件
     * @throws IOException
     */
    public static void copy(File source, File target) throws IOException {
        Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public String getContentType(File file) {
        //利用nio提供的类判断文件ContentType
        Path path = file.toPath();
        String contentType = null;
        try {
            contentType = Files.probeContentType(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //若失败则调用另一个方法进行判断
        if (Objects.isNull(contentType)) {
            contentType = new MimetypesFileTypeMap().getContentType(file);
        }
        return contentType;
    }
}