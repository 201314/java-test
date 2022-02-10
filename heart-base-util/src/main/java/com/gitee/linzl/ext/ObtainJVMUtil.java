package com.gitee.linzl.ext;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.net.NetworkInterface;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ObtainJVMUtil {
    // ---------- Java Runtime Info Begin
    RuntimeMXBean rt = ManagementFactory.getRuntimeMXBean();

    /**
     * jvm名称
     *
     * @return
     */
    public String getVMName() {
        return rt.getVmName();
    }

    /**
     * jvm版本
     *
     * @return
     */
    public String getVMVersion() {
        return rt.getVmVersion();
    }

    /**
     * jvm厂商
     *
     * @return
     */
    public String getVmVendor() {
        return rt.getVmVendor();
    }

    /**
     * jvm 正常运行时间
     *
     * @return
     */
    public double getUpTime() {
        return ((double) rt.getUptime()) / (1000 * 60 * 60);
    }

    /**
     * jvm参数信息
     *
     * @return
     */
    public List<String> getInputArguments() {
        return rt.getInputArguments();
    }

    /**
     * Java library path
     * <p>
     * 标准库路径
     *
     * @return
     */
    public String getLibraryPath() {
        return rt.getLibraryPath();
    }

    /**
     * java class path
     * <p>
     * 类路径
     *
     * @return
     */
    public String getClassPath() {
        return rt.getClassPath();
    }

    /**
     * jvm 启动时间
     *
     * @return
     */
    public String getStartTime() {
        DateTime dt = new DateTime(rt.getStartTime());
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        return fmt.print(dt);
    }

    // ---------- Java Runtime Info End

    // ---------- JVM OS Info Begin
    OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();

    /**
     * 服务器操作信息名称
     *
     * @return
     */
    public String getOSName() {
        return os.getName();
    }

    /**
     * 服务器操作系统版本
     *
     * @return
     */
    public String getVersion() {
        return os.getVersion();
    }

    /**
     * 处理器个数
     *
     * @return
     */
    public int getAvailableProcessors() {
        return os.getAvailableProcessors();
    }

    /**
     * 操作系统架构
     *
     * @return
     */
    public String get() {
        return os.getArch();
    }
    // ---------- JVM OS Info End

    MemoryMXBean memory = ManagementFactory.getMemoryMXBean();

    /**
     * 初始化内存大小
     *
     * @return
     */
    public long getInitMemory() {
        return memory.getHeapMemoryUsage().getInit() / 1000000;
    }

    /**
     * 最大内存
     *
     * @return
     */
    public long getMaxMemory() {
        return memory.getHeapMemoryUsage().getMax() / 1000000;
    }

    /**
     * 已经使用了多少内存
     *
     * @return
     */
    public long getUsedMemory() {
        return memory.getHeapMemoryUsage().getUsed() / 1000000;
    }

    private static final int LOW_ORDER_THREE_BYTES = 0x00ffffff;

    /**
     * 获取机器标识
     *
     * @return
     */
    public static int getMachineIdentifier() {
        // build a 2-byte machine piece based on NICs info
        int machinePiece;
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface ni = e.nextElement();
                sb.append(ni.toString());
                byte[] mac = ni.getHardwareAddress();
                if (mac != null) {
                    ByteBuffer bb = ByteBuffer.wrap(mac);
                    try {
                        sb.append(bb.getChar());
                        sb.append(bb.getChar());
                        sb.append(bb.getChar());
                    } catch (BufferUnderflowException shortHardwareAddressException) { //NOPMD
                        // mac with less than 6 bytes. continue
                    }
                }
            }
            machinePiece = sb.toString().hashCode();
        } catch (Throwable t) {
            // exception sometimes happens with IBM JVM, use SecureRandom instead
            machinePiece = (new SecureRandom().nextInt());
        }
        machinePiece = machinePiece & LOW_ORDER_THREE_BYTES;
        return machinePiece;
    }

    public static void main(String[] args) {
        MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
        System.out.println(memory.getHeapMemoryUsage().getInit() / 1000000);
        System.out.println(memory.getHeapMemoryUsage().getMax() / 1000000);
        System.out.println(memory.getHeapMemoryUsage().getUsed() / 1000000);
    }
}
