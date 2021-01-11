package com.gitee.linzl.properties;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author linzhenlie-jk
 * @date 2021/1/11
 */
public class ReadResourceTest {
    public static void main(String[] args) {
        try {
            // 编译后要注意资源文件是否在该路径下，不存在则不会成功
            Reader reader = Resources.getResourceAsReader("com/gitee/linzl/properties/jdbc.properties");
            System.out.println(reader);
            URL url = Resources.getResourceURL("com/gitee/linzl/properties/jdbc.properties");
            System.out.println(url.toURI().toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
