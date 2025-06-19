package com.gitee.linzl;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class TestMain {
    // 这个是我们经常写的
    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        //获取当前系统中所有 运行中的 虚拟机
        System.out.println("running JVM start ");
        /**
         * 注意：在mac上安装了的jdk是能直接找到 VirtualMachine 类的，但是在windows中安装的jdk无法找到，如果你遇到这种情况，
         * 请手动将你jdk安装目录下：lib目录中的tools.jar添加进当前工程的Libraries中。
         */
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : list) {
            //如果虚拟机的名称为 xxx 则 该虚拟机为目标虚拟机，获取该虚拟机的 pid
            //看到当前系统都有哪些JVM进程在运行,然后加载 agent.jar 发送给该虚拟机
            System.out.println(vmd.displayName());
            if (vmd.displayName().endsWith("com.gitee.linzl.TestMain")) {
                VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
                // 等同于在 vm 中配置 -javaagent:C:\Users\linzhenlie-jk\Desktop\test\javaagent.jar
                virtualMachine.loadAgent("C:\\Users\\linzhenlie-jk\\Desktop\\test\\javaagent.jar");
                System.out.println("===START====");
                System.out.println(new Date().toString());
                System.out.println("===END====");
                virtualMachine.detach();
            }
        }
    }
}