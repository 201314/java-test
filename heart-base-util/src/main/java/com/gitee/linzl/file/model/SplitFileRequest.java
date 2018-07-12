package com.gitee.linzl.file.model;

import java.io.File;

import com.gitee.linzl.file.event.ProgressListener;

public class SplitFileRequest {
	/**
	 * 需要分片的文件
	 */
	private File file;
	/**
	 * 分片大小
	 */
	private int partSize;
	/**
	 * 分片序号
	 */
	private int partNum;

	private ProgressListener listener;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getPartSize() {
		return partSize;
	}

	public void setPartSize(int partSize) {
		this.partSize = partSize;
	}

	public int getPartNum() {
		return partNum;
	}

	public void setPartNum(int partNum) {
		this.partNum = partNum;
	}

	public ProgressListener getListener() {
		return listener;
	}

	public void setListener(ProgressListener listener) {
		this.listener = listener;
	}

}
