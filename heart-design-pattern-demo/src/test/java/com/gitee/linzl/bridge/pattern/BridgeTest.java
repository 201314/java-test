package com.gitee.linzl.bridge.pattern;

import com.gitee.linzl.bridge.pattern.*;

public class BridgeTest {
	public static void main(String[] args) {
		System.out.println("华为手机运行软件：");
		// 华为手机 运行游戏
		CellphoneBrand huaWei = new HuaWei();
		huaWei.setCps(new Game());
		huaWei.operation();

		// 华为手机 运行通讯录
		huaWei.setCps(new AddressBook());
		huaWei.operation();

		System.out.println("中兴手机运行软件：");
		// 中兴手机运行游戏
		CellphoneBrand htc = new HTC();
		htc.setCps(new Game());
		htc.operation();

		// 中兴手机运行通讯录
		htc.setCps(new AddressBook());
		htc.operation();
	}

}
