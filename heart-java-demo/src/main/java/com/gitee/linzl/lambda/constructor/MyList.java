package com.gitee.linzl.lambda.constructor;

import java.util.List;

interface MyList<T extends List<?>> {
	T create();
}