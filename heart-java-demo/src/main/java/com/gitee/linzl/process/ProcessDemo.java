package com.gitee.linzl.process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 执行linux命令，如有需要执行其他系统(如windows)命令，也是类似的
 * 
 * @description
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2018年12月14日
 */
public class ProcessDemo {
	public static void main(String[] args) {
		List<String> commands = new ArrayList<>();
		commands.add("ls -l");
		commands.add("netstat -s");
		executeNewFlow(commands);
	}

	public static List<String> executeNewFlow(List<String> commands) {
		List<String> rspList = new ArrayList<String>();
		Runtime run = Runtime.getRuntime();
		try {
			Process proc = run.exec("/bin/bash", null, null);
			BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true);
			for (String line : commands) {
				out.println(line);
			}
			out.println("exit");// 这个命令必须执行，否则in流不结束。
			String rspLine = "";
			while ((rspLine = in.readLine()) != null) {
				System.out.println(rspLine);
				rspList.add(rspLine);
			}
			proc.waitFor();
			in.close();
			out.close();
			proc.destroy();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return rspList;
	}
}