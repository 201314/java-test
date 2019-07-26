package com.gitee.linzl.linked;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OneWayNode {
	private String data;
	private int num;

	private OneWayNode next;
}
