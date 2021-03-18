package com.gitee.linzl.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * 单文件/多文件 压缩、解压功能
 * <p>
 * 使用了ant.jar包
 *
 * @author linzl
 */
public class IOUtil {
    private static byte[] buf = new byte[1024];

    /**
     * 压缩单个文件 pck ==> package
     *
     * @param srcFile 需要压缩的文件,默认压缩到同目录下,后缀为zip
     * @throws IOException
     */
    public static void pck(File srcFile) throws IOException {
        String compressionFilesName = srcFile.isDirectory() ? srcFile.getName() : srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
        compressionFilesName += ".zip";
        pck(srcFile, new File(srcFile.getParentFile(), compressionFilesName));
    }

    /**
     * 压缩文件
     *
     * @param srcFile    需要压缩的文件
     * @param targetFile 压缩后目标位置,如 D:/测试中文.zip
     * @throws IOException
     */
    public static void pck(File srcFile, File targetFile) throws IOException {
        File[] srcFiles = new File[]{srcFile};
        pck(srcFiles, targetFile);
    }

    /**
     * 压缩文件
     *
     * @param srcFiles   需要压缩的文件
     * @param targetFile 压缩后目标位置,如 D:/测试中文.zip
     * @throws IOException
     */
    public static void pck(File[] srcFiles, File targetFile) throws IOException {
        pck(srcFiles, targetFile, null);
    }

    /**
     * 压缩文件
     *
     * @param srcFiles   需要压缩的文件
     * @param targetFile 压缩后目标位置,如 D:/测试中文.zip
     * @param rootName   压缩成功后，根文件夹的全路径,如windows环境下 D:/test,linux环境下 /var/local
     * @throws IOException
     */
    public static void pck(File[] srcFiles, File targetFile, String rootName) throws IOException {
        pck(srcFiles, targetFile, rootName, null);
    }

    /**
     * 压缩文件
     *
     * @param srcFiles   需要压缩的文件
     * @param targetFile 压缩后目标位置,如 D:/测试中文.zip
     * @param rootName   压缩成功后，根文件夹的全路径,如windows环境下 D:/test,linux环境下 /var/local
     * @param comment    压缩包说明
     * @throws IOException
     */
    public static void pck(File[] srcFiles, File targetFile, String rootName, String comment)
            throws IOException {
        rootName = (rootName == null ? "/" : rootName.trim().replaceAll("\\\\", "/").replaceAll("//*", "/"));
        ZipOutputStream out = new ZipOutputStream(targetFile);
        pck(out, srcFiles, rootName);
        comment = (comment == null ? "" : comment);
        out.setComment(comment);
        out.close();
    }

    /**
     * 压缩文件
     *
     * @param srcFiles 被压缩源文件
     * @param rootName 压缩成功后，根文件夹的全路径,如windows环境下 D:/test,linux环境下 /var/local
     */
    private static void pck(ZipOutputStream out, File[] srcFiles, String rootName) {
        if (!"".equalsIgnoreCase(rootName) && !rootName.endsWith("/")) {
            rootName += "/";
        }

        try {
            for (int i = 0; i < srcFiles.length; i++) {
                String fileName = srcFiles[i].getName();
                fileName = rootName + fileName;
                if (srcFiles[i].isDirectory()) {
                    // 标记为目录
                    fileName += "/";
                    out.putNextEntry(new ZipEntry(fileName));
                    pck(out, srcFiles[i].listFiles(), fileName);
                } else {
                    out.putNextEntry(new ZipEntry(fileName));
                    InputStream in = Files.newInputStream(srcFiles[i].toPath());
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }

                    IOUtils.closeQuietly(in);
                    out.closeEntry();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压文件到指定目录
     *
     * @param srcFile 需要解压的压缩文件,默认解压到srcFile父目录下
     * @return 得到解压后的文件
     * @throws IOException
     */
    public static List<File> unpck(File srcFile) throws IOException {
        return unpck(srcFile, srcFile.getParentFile());
    }

    /**
     * 解压文件到指定目录
     *
     * @param srcFile  需要解压的压缩文件
     * @param destFile 解压到指定目录
     * @return 得到解压后的文件
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    public static List<File> unpck(File srcFile, File destFile) throws IOException {
        FileUtils.forceMkdir(destFile);

        List<File> fileList = Collections.emptyList();

        try (ZipFile zip = new ZipFile(srcFile)) {
            Enumeration entries = zip.getEntries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();

                // 如果是文件夹，则创建完提前结束
                File file = new File(destFile.getPath(), entry.getName());
                if (entry.isDirectory()) {
                    FileUtils.forceMkdir(file);
                    continue;
                }

                try (InputStream in = zip.getInputStream(entry);
                     OutputStream out = Files.newOutputStream(file.toPath())) {
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    fileList.add(file);
                }
            }
        }
        return fileList;
    }
}
