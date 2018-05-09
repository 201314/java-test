package com.gitee.linzl.memento.pattern;

/**
 * 角色状态
 * 
 * @author GDCC i心灵鸡汤you email:2225010489@qq.com 2013-4-22 下午08:40:46
 */
public class RoleStateMemento {
	// 生命力
	private int vitality;
	// 攻击力
	private int attack;
	// 防御力
	private int defense;

	public RoleStateMemento(int vitality, int attack, int defense) {
		this.vitality = vitality;
		this.attack = attack;
		this.defense = defense;
	}

	public int getVitality() {
		return vitality;
	}

	public int getAttack() {
		return attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setVitality(int vitality) {
		this.vitality = vitality;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

}
