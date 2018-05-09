package com.gitee.linzl.memento.pattern;

/**
 * 角色
 * 
 * @author GDCC i心灵鸡汤you email:2225010489@qq.com 2013-4-22 下午08:47:54
 */
public class GameBoy {
	// 生命力
	private int vit; // vitality
	// 攻击力
	private int att; // attack
	// 防御力
	private int def; // defense

	// 状态显示
	public void stateDisplay() {
		System.out.println("生命力：" + this.vit);
		System.out.println("攻击力：" + this.att);
		System.out.println("防御力：" + this.def);
	}

	// 获得初始状态
	public void getInitState() {
		this.vit = 100;
		this.att = 100;
		this.def = 100;
	}

	// 战斗
	public void fight() {
		this.vit = 0;
		this.att = 0;
		this.def = 0;
	}

	// 保存角色状态
	public RoleStateMemento saveState() {
		return (new RoleStateMemento(vit, att, def));
	}

	// 恢复角色状态
	public void recoverState(RoleStateMemento roleStateMemento) {
		this.vit = roleStateMemento.getVitality();
		this.att = roleStateMemento.getAttack();
		this.def = roleStateMemento.getDefense();
	}

	public int getVit() {
		return vit;
	}

	public int getAtt() {
		return att;
	}

	public int getDef() {
		return def;
	}

	public void setVit(int vit) {
		this.vit = vit;
	}

	public void setAtt(int att) {
		this.att = att;
	}

	public void setDef(int def) {
		this.def = def;
	}

}
