package com.gitee.linzl.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;

/**
 * @description 合并文件
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年4月24日
 */
public class MergeRunnable implements Callable<Boolean> {
	long startPos;
	String mergeFileName;
	byte[] partFileByte;

	public MergeRunnable(long startPos, String fullMergeFileName, File partFile) {
		this.startPos = startPos;
		this.mergeFileName = fullMergeFileName;
		try {
			this.partFileByte = FileUtils.readFileToByteArray(partFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public MergeRunnable(long startPos, String fullMergeFileName, byte[] partFileByte) {
		this.startPos = startPos;
		this.mergeFileName = fullMergeFileName;
		this.partFileByte = partFileByte;
	}

	public Boolean call() {
		try (RandomAccessFile rFile = new RandomAccessFile(mergeFileName, "rw")) {
			rFile.seek(startPos);
			rFile.write(partFileByte);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
