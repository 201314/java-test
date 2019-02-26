package com.gitee.linzl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface FieldDecrypt {
	/** 在文件内容中的起始位置 */
	int start() default 0;

	/** 解密前,需求中规定需要截取的长度 */
	int length() default 0;

	/** 默认使用空格填充 */
	Padding padding() default Padding.SPACE;

	/** 默认使用右填充 */
	PaddingDirection direct() default PaddingDirection.RIGHT;

	/** 如果字段是时间,所需要的时间格式 */
	String format() default "yyyyMMddHHmmss";

	/** 解密方式 */
	Class<? extends Decrypt> decrypt() default NoneDecrypt.class;
}
