package com.gitee.linzl.file.progress;

import org.bouncycastle.util.encoders.Base64;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.concurrent.Callable;

/**
 * @author linzl
 * @description 合并文件
 * @email 2225010489@qq.com
 * @date 2018年4月24日
 */
public class MergeRunnable implements Callable<Boolean> {
    long startPos;
    File mergeFile;
    byte[] partFileByte;

    public MergeRunnable(long startPos, File mergeFile, File partFile) {
        this.startPos = startPos;
        this.mergeFile = mergeFile;
        try {
            this.partFileByte = Base64.decode(Files.readAllBytes(partFile.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MergeRunnable(long startPos, File mergeFile, byte[] partFileByte) {
        this.startPos = startPos;
        this.mergeFile = mergeFile;
        this.partFileByte = partFileByte;
    }

    @Override
    public Boolean call() {
        try (RandomAccessFile rFile = new RandomAccessFile(mergeFile, "rw")) {
            rFile.seek(startPos);
            rFile.write(partFileByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
