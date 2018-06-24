package com.gitee.linzl.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/**
 * 分割处理Runnable
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年6月22日
 */
public class SplitRunnable implements Runnable {
	int byteSize;
	String partFileName;
	File originFile;
	int startPos;

	public SplitRunnable(int byteSize, int startPos, String partFileName, File originFile) {
		this.startPos = startPos;
		this.byteSize = byteSize;
		this.partFileName = partFileName;
		this.originFile = originFile;
	}

	public void run() {
		try (RandomAccessFile rFile = new RandomAccessFile(originFile, "r");
				OutputStream os = new FileOutputStream(new File(originFile.getParentFile(), partFileName));) {
			byte[] b = new byte[byteSize];
			rFile.seek(startPos);// 移动指针到每“段”开头
			int s = rFile.read(b);
			os.write(b, 0, s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
