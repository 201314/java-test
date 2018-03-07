package com.linzl.memento.pattern;

/**
 * @description 备忘录模式
 * @author linzl
 * @email 2225010489@qq.com
 * @date 2017年11月26日
 */
public class Test {
	public static void main(String[] args) {
		// 打BOSS前，满血状态
		System.out.println("----打BOSS前，满血状态----");
		GameBoy gb = new GameBoy();
		gb.getInitState();
		gb.stateDisplay();

		// 保存进度
		System.out.println("----保存进度----");
		RoleStateCaretaker rsc = new RoleStateCaretaker();
		rsc.setRoleStateMemento(gb.saveState());

		// 打BOSS后，Game Over了……
		System.out.println("----打BOSS后，Game Over了……----");
		gb.fight();
		gb.stateDisplay();

		// 恢复进度
		System.out.println("----恢复进度----");
		gb.recoverState(rsc.getRoleStateMemento());
		gb.stateDisplay();

	}
}
