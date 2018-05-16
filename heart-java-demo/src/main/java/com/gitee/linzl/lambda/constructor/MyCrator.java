package com.gitee.linzl.lambda.constructor;

import java.util.List;

interface MyCrator<T extends List<?>> {
	T create();
}