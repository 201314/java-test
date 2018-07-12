package day08.reflex;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface FieldMap {
	/**
	 * 对应数据库中列的名字
	 * 
	 * @return
	 */
	public String columnName();

	/**
	 * 属性的类型 如:java.lang.String
	 * 
	 * @return
	 */
	public Class fieldClass();

}
