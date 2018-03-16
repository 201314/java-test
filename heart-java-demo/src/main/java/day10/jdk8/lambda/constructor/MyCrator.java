package day10.jdk8.lambda.constructor;

import java.util.List;

interface MyCrator<T extends List<?>> {
	T create();
}