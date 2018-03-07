package day06.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import day06.reflect.annotation.Column;
import day06.reflect.annotation.Id;
import day06.reflect.annotation.Table;

/**
 * 
 * @author linzl
 * 
 */

public class ReflectUtil {
	/**
	 * 得到VO属性的set方法
	 * 
	 * @param fieldName
	 * @return
	 */
	public static String obtainSetMethodName(String fieldName) {
		StringBuilder sb = new StringBuilder(fieldName);
		sb.setCharAt(0, fieldName.toUpperCase().charAt(0));
		sb.insert(0, "set");
		return sb.toString();
	}

	/**
	 * 得到VO属性的get方法
	 * 
	 * @param fieldName
	 * @return
	 */
	public static String obtainGetMethodName(String fieldName) {
		StringBuilder sb = new StringBuilder(fieldName);
		sb.setCharAt(0, fieldName.toUpperCase().charAt(0));
		sb.insert(0, "get");
		return sb.toString();
	}

	/**
	 * 获取VO对应的表名
	 * 
	 * @param voClass
	 * @return
	 */
	public static String obtainTableName(Class voClass) {
		Table table = (Table) voClass.getAnnotation(Table.class);
		if (table != null) {
			return table.name();
		}
		return null;
	}

	/**
	 * 获取VO主键域对象
	 * 
	 * @param voClass
	 * @return
	 */
	public static Field obtainPKField(Class voClass) {
		return obtainField(Id.class, voClass);
	}

	/**
	 * 获取VO 某个注解对应的域对象
	 * 
	 * @param colClass
	 *            某注解
	 * @param voClass
	 * @return
	 */
	private static Field obtainField(Class colClass, Class voClass) {
		for (Field field : voClass.getDeclaredFields()) {
			Annotation annotation = field.getAnnotation(colClass);
			if (annotation != null) {
				return field;
			}
		}
		return null;
	}

	/**
	 * 获取数据库列对应的域对象
	 * 
	 * @param colName
	 * @param voClass
	 * @return
	 */
	public static Field obtainFieldByColName(String colName, Class voClass) {
		for (Field field : voClass.getDeclaredFields()) {
			Column column = field.getAnnotation(Column.class);
			if (column != null && column.name().equals(colName)) {
				return field;
			}
		}
		return null;
	}

	/**
	 * 获取VO 主键对应的数据库列名
	 * 
	 * @param filedName
	 * @param voClass
	 * @return
	 */
	public static String obtainPKColName(Class voClass) {
		Field field = obtainPKField(voClass);
		if (field != null) {
			Column column = field.getAnnotation(Column.class);
			if (column != null) {
				return column.name();
			}
		}
		return null;
	}

	/**
	 * 获取VO属性对应的数据库列名
	 * 
	 * @param filedName
	 * @param voClass
	 * @return
	 */
	public static String obtainColName(String filedName, Class voClass) {
		for (Field field : voClass.getDeclaredFields()) {
			if (field.getName().equals(filedName)) {
				Column column = field.getAnnotation(Column.class);
				if (column != null) {
					return column.name();
				}
			}
		}
		return null;
	}

	// 该方法主要传入的参数有两个，第一个是Map接口，第二个就是要绑定的VO。
	public static void mapBind(Map map, Class voClass) throws Exception {
		// 得到vo中所有的成员变量
		Field[] fs = voClass.getDeclaredFields();
		// 方法变量
		String methodName = null;
		// map的value值
		Object mapValue = null;
		// 参数类型
		String parameterType = null;
		// 查找方法时需要传入的参数
		Class[] parameterTypes = new Class[1];
		// 执行invoke方法时需要传入的参数
		Object[] args = new Object[1];
		// 取得Map的迭代器,取出数据库列名
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			// 取出map的key值
			String key = (String) it.next();
			if (key != null) {
				for (int i = 0; i < fs.length; i++) {

					if (key.equals(fs[i].getName())) {
						// 拼set方法名
						methodName = obtainSetMethodName(key);
						try {
							// 得到vo中成员变量的类型
							parameterTypes[0] = fs[i].getType();
							parameterType = parameterTypes[0].toString();
							// 找到vo中的方法
							Method method = voClass.getDeclaredMethod(methodName, parameterTypes);
							mapValue = map.get(key);
							// 下面代码都是参数类型是什么，如果有需求可以自行增加
							// 当set方法中的参数为int或者Integer
							if (parameterTypes[0] == Integer.class || parameterTypes[0] == int.class) {
								if (mapValue instanceof Integer) {
									args[0] = mapValue;
								} else {
									args[0] = Integer.parseInt((String) mapValue);
								}
								// 当set方法中的参数为Date
							} else if (parameterTypes[0] == Date.class) {
								if (mapValue instanceof Date) {
									args[0] = mapValue;
								} else {
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
									args[0] = sdf.parse((String) mapValue);
								}
								// 当set方法中的参数为Double
							} else if (parameterTypes[0] == double.class || parameterTypes[0] == Double.class) {
								if (mapValue instanceof Double) {
									args[0] = mapValue;
								} else {
									args[0] = Double.parseDouble((String) mapValue);
								}
								// 当set方法中的参数为其他
							} else if (parameterTypes[0] == float.class || parameterTypes[0] == Float.class) {
								if (mapValue instanceof Float) {
									args[0] = mapValue;
								} else {
									args[0] = Float.parseFloat((String) mapValue);
								}
								// 当set方法中的参数为其他
							} else if (parameterTypes[0] == String.class) {
								if (mapValue instanceof String[]) {
									String[] tempArray = (String[]) mapValue;
									String result = "";
									for (int m = 0; m < tempArray.length; m++) {
										result = result + tempArray[m] + ",";
									}
									result = result.substring(0, result.length() - 1);
									args[0] = result;
								} else {
									args[0] = (String) mapValue;
								}
							} else {
								args[0] = mapValue;
							}
							// 执行set方法存储数据
							method.invoke(voClass, args);

						} catch (SecurityException e) {
							throw new SecurityException("[mapBind]安全异常：" + e);
						} catch (NoSuchMethodException e) {
							throw new NoSuchMethodException("[mapBind]Vo中无此方法异常" + e);
						} catch (IllegalArgumentException e) {
							throw new Exception("VO中" + key + "属性类型" + parameterType + "与Map中值为" + mapValue + "的类型不匹配");
						} catch (IllegalAccessException e) {
							throw new IllegalAccessException("[mapBind]IllegalAccessException异常");
						} catch (ParseException e) {
							throw new ParseException("[mapBind]ParseException异常", 0);
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Class voClass = AnnotationVO.class;
		Table str = (Table) voClass.getAnnotation(Table.class);
		Field filed = obtainPKField(voClass);
		System.out.println("结果--》" + filed.getName());
	}
}
