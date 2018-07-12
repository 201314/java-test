package com.gitee.linzl.file.progress;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.Callable;

import org.bouncycastle.util.encoders.Base64;

import com.gitee.linzl.file.event.ProgressEvent;
import com.gitee.linzl.file.event.ProgressEventType;
import com.gitee.linzl.file.event.ProgressPublisher;
import com.gitee.linzl.file.model.FileProgressPart;
import com.gitee.linzl.file.model.SplitFileRequest;

/**
 * 分割处理Runnable
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年6月22日
 */
public class SplitRunnable implements Callable<Boolean> {

	private SplitFileRequest model;

	public SplitRunnable(SplitFileRequest model) {
		this.model = model;
	}

	public Boolean call() {
		boolean flag = false;
		try (RandomAccessFile rFile = new RandomAccessFile(model.getFile(), "r")) {
			int index = model.getPartNum();
			int byteSize = model.getPartSize();
			long fileSize = model.getFile().length();

			int offset = index * byteSize;
			byte[] bytes = null;
			if (fileSize - offset >= byteSize) {
				bytes = new byte[byteSize];
			} else {// 最后一片可能小于byteSize
				bytes = new byte[(int) (fileSize - offset)];
			}

			rFile.seek(index * byteSize);// 移动指针到每“段”开头
			int s = rFile.read(bytes);

			flag = true;

			FileProgressPart part = new FileProgressPart();
			part.setBase64(Base64.toBase64String(bytes));
			part.setIndex(index);
			part.setOffset(offset);
			ProgressEvent event = new ProgressEvent(ProgressEventType.SPLIT_PART_COMPLETED_EVENT, part);
			ProgressPublisher.publishProgress(model.getListener(), event);
		} catch (IOException e) {
			flag = false;
			ProgressEvent event = new ProgressEvent(ProgressEventType.SPLIT_PART_FAILED_EVENT, null);
			ProgressPublisher.publishProgress(model.getListener(), event);
		}
		return flag;
	}
}
