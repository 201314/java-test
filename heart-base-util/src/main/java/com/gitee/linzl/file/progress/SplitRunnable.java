package com.gitee.linzl.file.progress;

import com.gitee.linzl.file.event.ProgressEvent;
import com.gitee.linzl.file.event.ProgressEventType;
import com.gitee.linzl.file.event.ProgressPublisher;
import com.gitee.linzl.file.model.FileProgressPart;
import com.gitee.linzl.file.model.SplitFileRequest;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Base64;
import java.util.concurrent.Callable;

/**
 * 分割处理Runnable
 *
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年6月22日
 */
public class SplitRunnable implements Callable<Boolean> {
    private SplitFileRequest model;

    public SplitRunnable(SplitFileRequest model) {
        this.model = model;
    }

    @Override
    public Boolean call() {
        boolean flag;
        try (RandomAccessFile rFile = new RandomAccessFile(model.getFile(), "r")) {
            int index = model.getPartNum();
            int byteSize = model.getPartSize();
            long fileSize = model.getFile().length();

            int offset = index * byteSize;
            byte[] bytes;
            if (fileSize - offset >= byteSize) {
                bytes = new byte[byteSize];
            } else {// 最后一片可能小于byteSize
                bytes = new byte[(int) (fileSize - offset)];
            }
            // 移动指针到每“段”开头
            rFile.seek(index * byteSize);
            int s = rFile.read(bytes);

            flag = true;

            FileProgressPart part = new FileProgressPart();
            part.setBase64(Base64.getEncoder().encodeToString(bytes));
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
