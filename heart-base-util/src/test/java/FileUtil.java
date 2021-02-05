import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author linzhenlie-jk
 * @date 2021/1/23
 */
public class FileUtil {
    public static void main(String[] args) throws IOException {
        List<String> strings = FileUtils.readLines(new File("D://", "CD6071051636602593280.txt"), StandardCharsets.UTF_8);
        Map<String, String> map = strings.stream().collect(Collectors.toMap(s -> s.substring(0, 23), c -> c, (k1, k2) -> k2));

        TreeMap<String, String> treeMap = new TreeMap<>(String::compareTo);
        treeMap.putAll(map);
        treeMap.forEach((s, s2) -> {
            try {
                FileUtils.writeLines(new File("D://", "CD6071051636602593280-order.txt"), "UTF-8",
                        Arrays.asList(s2), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
