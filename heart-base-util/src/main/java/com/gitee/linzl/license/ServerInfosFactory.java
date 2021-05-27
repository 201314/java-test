package com.gitee.linzl.license;

/**
 * @author linzhenlie-jk
 * @date 2021/5/27
 */
public class ServerInfosFactory {
    /**
     * 获取当前服务器需要额外校验的License参数
     */
    public static AbstractServerInfos getObject() {
        //操作系统类型
        String osName = System.getProperty("os.name").toLowerCase();
        AbstractServerInfos abstractServerInfos;

        //根据不同操作系统类型选择不同的数据获取方法
        if (osName.startsWith("windows")) {
            abstractServerInfos = new WindowsServerInfos();
        } else if (osName.startsWith("linux")) {
            abstractServerInfos = new LinuxServerInfos();
        } else {//其他服务器类型
            abstractServerInfos = new LinuxServerInfos();
        }

        return abstractServerInfos;
    }
}
