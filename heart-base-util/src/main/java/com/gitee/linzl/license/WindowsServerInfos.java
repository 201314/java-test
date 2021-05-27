package com.gitee.linzl.license;

import java.util.Scanner;

/**
 * 用于获取客户Windows服务器的基本信息
 */
public class WindowsServerInfos extends AbstractServerInfos {
    @Override
    protected String getCPUSerial() throws Exception {
        //序列号
        String serialNumber = "";

        //使用WMIC获取CPU序列号
        Process process = Runtime.getRuntime().exec("wmic cpu get processorid");
        process.getOutputStream().close();
        Scanner scanner = new Scanner(process.getInputStream());

        if (scanner.hasNext()) {
            scanner.next();
        }
        if (scanner.hasNext()) {
            serialNumber = scanner.next().trim();
        }
        scanner.close();
        return serialNumber;
    }

    @Override
    protected String getMainBoardSerial() throws Exception {
        //序列号
        String serialNumber = "";
        //使用WMIC获取主板序列号
        Process process = Runtime.getRuntime().exec("wmic baseboard get serialnumber");
        process.getOutputStream().close();
        Scanner scanner = new Scanner(process.getInputStream());
        if (scanner.hasNext()) {
            scanner.next();
        }
        if (scanner.hasNext()) {
            serialNumber = scanner.next().trim();
        }
        scanner.close();
        return serialNumber;
    }
}