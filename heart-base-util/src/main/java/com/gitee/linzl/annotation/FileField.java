package com.gitee.linzl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface FileField {
	/** 生成文件中内容顺序 */
	int order() default 0;

	/** 加密前,需求中规定的总长度 */
	int length() default 0;

	/** 默认使用空填充 */
	String padding() default "";

	/** 默认使用右填充 */
	PaddingDirection direct() default PaddingDirection.RIGHT;

	/** 如果字段是时间,所需要的时间格式 */
	String format() default "yyyyMMddHHmmss";

	/** 加密方式 */
	Class<? extends Encrypt> encrypt() default NoneEncrypt.class;

	/** 解密方式 */
	Class<? extends Decrypt> decrypt() default NoneDecrypt.class;
}
