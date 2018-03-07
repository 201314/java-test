package day06.reflect.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
@Documented
public @interface Column {
	/**
	 * 对应数据库中列的名字
	 * 
	 * @return
	 */
	public String name();

	/**
	 * 主键唯一性
	 * 
	 * @return
	 */
	public boolean unique() default false;

	/**
	 * 是否支持模糊查询,只对string,number类型生效
	 * 
	 * @return
	 */
	public boolean fuzzy() default false;
}
