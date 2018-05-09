package com.gitee.linzl.memento.pattern;

/**
 * 角色状态管理者
 * 
 * @author GDCC i心灵鸡汤you email:2225010489@qq.com 2013-4-22 下午08:47:34
 */
public class RoleStateCaretaker {
	private RoleStateMemento roleStateMemento;

	public RoleStateMemento getRoleStateMemento() {
		return roleStateMemento;
	}

	public void setRoleStateMemento(RoleStateMemento roleStateMemento) {
		this.roleStateMemento = roleStateMemento;
	}

}
