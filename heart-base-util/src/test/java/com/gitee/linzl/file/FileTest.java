package com.gitee.linzl.file;

import com.gitee.linzl.cipher.message.DigestUtilsExt;
import com.gitee.linzl.file.event.ProgressListener;
import com.gitee.linzl.file.model.FileProgressPart;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class FileTest {
    @Test
    public void readFileByChars() throws Exception {
        System.out.println(FileUtil.read(new File("D:\\副本\\333.txt"), "UTF-8"));
    }

    @Test
    public void readAllLines() throws Exception {
        System.out.println(FileUtil.readAllLines(new File("D:\\副本\\333.txt"), "UTF-8"));
    }

    @Test
    public void read() throws Exception {
        System.out.println(FileUtil.read(new File("D:\\副本\\333.txt")));
    }

    @Test
    public void list() {
        System.out.println(FileUtil.list(new File("D:\\副本")));
    }

    @Test
    public void listSuffix() {
        System.out.println(FileUtil.list(new File("D:\\副本"), "docx"));
    }

    @Test
    public void recursiveList() {
        System.out.println(FileUtil.recursiveList(new File("D:\\副本")));
    }

    @Test
    public void recursiveListSuffix() {
        System.out.println(FileUtil.recursiveList(new File("D:\\副本"), "docx"));
    }

    @Test
    public void lineNumber() {
        System.out.println(FileUtil.lineNumber(new File("D:\\副本\\333.txt")));
    }

    @Test
    public void readLastLine() throws Exception {
        System.out.println(FileUtil.readLastLine(new File("D:\\副本\\333.txt"), Charset.defaultCharset(), 3));
    }

    @Test
    public void write() throws Exception {
        String content = "我是中国人";
        FileUtil.write(new File("D:\\副本\\333.txt"), content.getBytes());
    }

    @Test
    public void writeAppend() throws Exception {
        String content = "我是中国人";
        FileUtil.write(new File("D:\\副本\\333.txt"), content.getBytes(), true);
    }

    @Test
    public void writeHead() throws IOException {
        String content = "头部新闻，我是中国\n";
        FileUtil.writeHead(new File("D:\\副本\\333.txt"), content.getBytes());
    }

    @Test
    public void splitFile() {
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
            FileUtil.asynSplitFile(file, 40 * 1024, listener);
            System.out.println("文件MD5：" + DigestUtilsExt.md5Hex(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void mergeFile() throws IOException, InterruptedException {
        // 合并成新文件
        FileUtil.asynMergeFiles(new File("D:\\trawe_store\\split"), ".txt", 40 * 1024,
                new File("D:\\\\trawe_store\\\\split\\new.zip"));
        Thread.sleep(10000);// 稍等10秒，等前面的小文件全都写完
    }

    @Test
    public void size() throws IOException {
        File file = new File("D:\\副本\\333.txt");
        System.out.println("1.文件大小:" + FileUtil.size(file));

        System.out.println("2.文件大小:" + FileUtil.size(new FileInputStream(file)));
    }

    @Test
    public void delete() {
        FileUtil.delete(new File("D:\\副本"));
    }

    @Test
    public void deleteSuffix() {
        FileUtil.delete(new File("D:\\副本"), "docx");
    }

    @Test
    public void recursionDelete() {
        FileUtil.recursionDelete(new File("D:\\副本"));
    }

    @Test
    public void recursionDeleteSuffix() {
        FileUtil.recursionDelete(new File("D:\\副本"), "docx");
    }

    @Test
    public void reName() throws Exception {
        FileUtil.reName(new File("D:\\副本\\333.txt"), "444改名字");
    }

    @Test
    public void copy() throws IOException {
        FileUtil.copy(new File("D:\\副本\\333.txt"), new File("D:\\副本\\hello.txt"));
    }
}