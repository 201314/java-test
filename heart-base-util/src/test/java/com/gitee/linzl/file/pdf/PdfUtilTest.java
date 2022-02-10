package com.gitee.linzl.file.pdf;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

/**
 * @author linzhenlie-jk
 * @date 2022/2/10
 */
public class PdfUtilTest {
    @Test
    public void merge() {
        //将两个pdf合并成一个
        List<File> list = new ArrayList<>();
        list.add(new File("D:\\BaiduNetdiskDownload\\Spring\\Spring笔记.pdf"));
        list.add(new File("D:\\BaiduNetdiskDownload\\Spring高手系列\\Spring系列【公众号：路人甲Java】(下).pdf"));
        File targetFile = new File("D:\\Spring笔记.pdf");
        PdfUtil.merge(list, targetFile);
    }

    @Test
    public void generate() throws IOException {
        File tempalte = new File("C:\\Users\\linzhenlie-jk\\Desktop", "template模版.pdf");
        Map<String, String> data = new HashMap<>();
        data.put("name_1", "第一个姓名");
        data.put("age_1", "第一个年龄");
        data.put("birthday_1", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        data.put("base64_1", "C:\\Users\\linzhenlie-jk\\Desktop\\图片1.jpg");
        PdfUtil.generate(data, tempalte, new File("D:\\", System.currentTimeMillis() + ".pdf"));
    }
}
