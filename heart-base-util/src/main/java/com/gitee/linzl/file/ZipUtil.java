package com.gitee.linzl.file;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * zip4j仅支持zip,其他压缩包方式使用
 *
 * @author linzl
 * @description
 * @email 2225010489@qq.com
 * @date 2018年8月26日
 * @see com.gitee.linzl.file.IOUtil
 */
public class ZipUtil {
    /**
     * 使用给定密码解压指定的ZIP压缩文件到当前目录
     *
     * @param zip    指定的ZIP压缩文件
     * @param passwd ZIP文件的密码
     * @return 解压后文件数组
     * @throws ZipException 压缩文件有损坏或者解压缩失败抛出
     */
    public static List<File> unzip(File zip, String passwd) throws ZipException {
        File parentDir = zip.getParentFile();
        return unzip(zip, parentDir.getParentFile(), passwd);
    }

    /**
     * 使用给定密码解压指定的ZIP压缩文件到指定目录
     * <p>
     * 如果指定目录不存在,可以自动创建,不合法的路径将导致异常被抛出
     *
     * @param zipFile 指定的ZIP压缩文件
     * @param dest    解压目录
     * @param passwd  ZIP文件的密码
     * @return 解压后文件数组
     * @throws ZipException 压缩文件有损坏或者解压缩失败抛出
     */
    @SuppressWarnings("unchecked")
    public static List<File> unzip(File zipFile, File dest, String passwd) throws ZipException {
        ZipFile zFile = new ZipFile(zipFile);
        zFile.setFileNameCharset("UTF-8");
        if (!zFile.isValidZipFile()) {
            throw new ZipException("压缩文件不合法,可能被损坏.");
        }

        if (zFile.isEncrypted()) {
            zFile.setPassword(passwd.toCharArray());
        }
        zFile.extractAll(dest.getPath());

        List<FileHeader> headerList = zFile.getFileHeaders();
        List<File> extractedFileList = headerList.stream().filter(fileHeader -> !fileHeader.isDirectory()).map(fileHeader -> new File(dest,
                fileHeader.getFileName())).collect(Collectors.toList());
        return extractedFileList;
    }

    /**
     * 压缩指定文件到当前文件夹
     *
     * @param src 要压缩的指定文件
     * @return 最终的压缩文件存放的绝对路径, 如果为null则说明压缩失败.
     */
    public static File zip(File src) {
        return zip(src, new File(src.getParent(), src.getName() + ".zip"), null);
    }

    /**
     * 使用给定密码压缩指定文件或文件夹到当前目录
     *
     * @param src    要压缩的文件
     * @param passwd 压缩使用的密码
     * @return 最终的压缩文件存放的绝对路径, 如果为null则说明压缩失败.
     */
    public static File zip(File src, String passwd) {
        return zip(src, new File(src.getParent(), src.getName() + ".zip"), passwd);
    }

    /**
     * 使用给定密码压缩指定文件或文件夹到当前目录
     *
     * @param src    要压缩的文件
     * @param dest   压缩文件存放路径
     * @param passwd 压缩使用的密码
     * @return 最终的压缩文件存放的绝对路径, 如果为null则说明压缩失败.
     */
    public static File zip(File src, File dest, String passwd) {
        return zip(src, dest, true, passwd);
    }

    /**
     * 使用给定密码压缩指定文件或文件夹到指定位置.
     * <p>
     * dest可传最终压缩文件存放的绝对路径,也可以传存放目录,也可以传null或者"".<br />
     * 如果传null或者""则将压缩文件存放在当前目录,即跟源文件同目录,压缩文件名取源文件名,以.zip为后缀;<br />
     * 如果以路径分隔符(File.separator)结尾,则视为目录,压缩文件名取源文件名,以.zip为后缀,否则视为文件名.
     *
     * @param src         要压缩的文件或文件夹路径
     * @param dest        压缩文件存放路径
     * @param isCreateDir 是否在压缩文件里创建目录,仅在压缩文件为目录时有效.<br />
     *                    如果为false,将直接压缩目录下文件到压缩文件.
     * @param passwd      压缩使用的密码
     * @return 最终的压缩文件存放的绝对路径, 如果为null则说明压缩失败.
     */
    public static File zip(File src, File dest, boolean isCreateDir, String passwd) {
        ZipParameters parameters = new ZipParameters();
        // 压缩方式
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        // 压缩级别
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_FASTEST);
        if (StringUtils.isNotEmpty(passwd)) {
            parameters.setEncryptFiles(true);
            // 加密方式
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            parameters.setPassword(passwd.toCharArray());
        }
        try {
            ZipFile zipFile = new ZipFile(dest);
            if (src.isFile()) {
                zipFile.addFile(src, parameters);
            } else if (src.isDirectory()) {
                // 如果不创建目录的话,将直接把给定目录下的文件压缩到压缩文件,即没有目录结构
                if (!isCreateDir) {
                    File[] subFiles = src.listFiles();
                    ArrayList<File> temp = new ArrayList<>();
                    Collections.addAll(temp, subFiles);
                    zipFile.addFiles(temp, parameters);
                    return dest;
                }
                zipFile.addFolder(src, parameters);
            }
            return dest;
        } catch (ZipException e) {
            e.printStackTrace();
        }
        return null;
    }
}
