package com.gitee.linzl.github;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linzhenlie-jk
 * @date 2021/8/2
 */
public class FileTransferMergeTest {
    public static void main(String[] args) throws IOException {
        String workdir = "D:\\workspaces-ext\\新建文件夹";
        File all = new File(workdir);
        File[] lines = all.listFiles();
        Arrays.stream(lines).forEach(file -> {
            try {
                perFile(file.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }



    public static void perFile(String workdir) throws IOException {
        /**
         * md文件名 == 实际章节名称
         */
        Map<String, String> map = new LinkedHashMap<>();
        Map<String, File> mergeMap = new LinkedHashMap<>();
        List<String> lines = FileUtils.readLines(new File(workdir, "SUMMARY.md"), "UTF-8");

        final File[] readMeFile = {null};
        lines.stream().filter(s -> s.startsWith("* [")).forEach(s -> {
            int titleStart = s.indexOf("* [");
            int titleEnd = s.indexOf("](");
            String title = s.substring(titleStart + 3, titleEnd);
            int fileNameEnd = s.indexOf(".md)");
            String fileName = s.substring(titleEnd + 2, fileNameEnd + 3);
            if ("README.md".equals(fileName)) {
                readMeFile[0] = new File(workdir, fileName);
                return;
            }
            System.out.println(title + "===" + fileName);
            map.put(fileName, title);
            mergeMap.put(fileName, null);
        });

        List<String> readMeList = FileUtils.readLines(readMeFile[0],"UTF-8");
        StringBuilder sb = new StringBuilder();
        readMeList.stream().forEach(line -> {
            if (line.startsWith("# ")){
                sb.append(line.substring(2)).append("-");
            }
            if (line.startsWith("作者：")){
                sb.append(line.substring(3)).append(".md");
            }
        });
        String mergeFile = sb.toString();
        List<File> files = (List) FileUtils.listFiles(new File(workdir), new String[]{"md"}, false);

        files.stream().forEach(file -> {
            if (map.containsKey(file.getName())) {
                try {
                    mergeMap.put(file.getName(), file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        File merge = new File(new File(workdir).getParentFile(), mergeFile);
        mergeMap.forEach((s, file) -> {
            String title = map.get(file.getName());
            String firstTitle = "# " + title + System.lineSeparator();
            try {
                FileUtils.writeByteArrayToFile(merge, firstTitle.getBytes(StandardCharsets.UTF_8), true);
                FileUtils.writeByteArrayToFile(merge, FileUtils.readFileToByteArray(file), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
