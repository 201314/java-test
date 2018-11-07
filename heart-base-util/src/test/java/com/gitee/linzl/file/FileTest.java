package com.gitee.linzl.file;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.gitee.linzl.cipher.message.DigestUtilsExt;
import com.gitee.linzl.file.event.ProgressListener;
import com.gitee.linzl.file.model.FileProgressPart;

public class FileTest {

	@Test
	public void splitFile() {
		FileUtil fileUtil = new FileUtil();

		ProgressListener listener = (progressEvent) -> {
			FileProgressPart part = progressEvent.getPart();
			try {
				System.out.println(part.getIndex() + "=" + DigestUtils.md5Hex(part.getBase64()));
				FileUtils.writeStringToFile(new File("D://trawe_store//split//" + part.getIndex() + ".txt"),
						part.getBase64(), Charset.forName("UTF-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
		try {
			File file = new File("D:\\trawe_store\\trawe_store1.zip");
			fileUtil.asynSplitFile(file, 40 * 1024, listener);
			System.out.println("文件MD5：" + DigestUtilsExt.md5Hex(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void mergeFile() throws IOException, InterruptedException {
		FileUtil fileUtil = new FileUtil();

		// 合并成新文件
		fileUtil.asynMergeFiles("D:\\trawe_store\\split", ".txt", 40 * 1024,
				new File("D:\\\\trawe_store\\\\split\\new.zip"));
		Thread.sleep(10000);// 稍等10秒，等前面的小文件全都写完
	}
}