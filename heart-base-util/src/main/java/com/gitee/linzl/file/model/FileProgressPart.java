package com.gitee.linzl.file.model;

import org.apache.commons.codec.digest.DigestUtils;

public class FileProgressPart {
	private String base64;

	/**
	 * base64的md5码
	 */
	private String md5;

	private int offset;

	private int index;

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
		this.md5 = DigestUtils.md5Hex(base64);
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
