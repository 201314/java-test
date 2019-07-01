package com.gitee.linzl.annotation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ToObjectBuilder {
	// 类名，对应的字段
	public static final Map<String, Field[]> declaredFieldsCache = new ConcurrentHashMap<>(256);

	/**
	 * @param content
	 * @param charset   编码
	 * @param separator 每个字段间的分隔符
	 * @param end       属性拼装完成后的结束符
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static <T> T toObject(int start, String content, Class<T> clazz, String separator, String end)
			throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		String fullPath = clazz.getName();
		Field[] cacheFields = declaredFieldsCache.get(fullPath);
		if (cacheFields == null) {// 未缓存
			System.out.println("未缓存");
			List<Field> fieldList = new ArrayList<>();
			Field[] fields = clazz.getDeclaredFields();

			Collections.addAll(fieldList, fields);
			Class tmp = clazz;
			while (tmp.getSuperclass() != null) {
				tmp = clazz.getSuperclass();
				Field[] superFields = tmp.getDeclaredFields();
				Collections.addAll(fieldList, superFields);
			}
			AccessibleObject.setAccessible(fields, true);

			List<Field> list = new ArrayList<>();
			for (Field field : fieldList) {
				FileField fileField = field.getAnnotation(FileField.class);
				if (fileField != null) {
					list.add(field);
				}
			}
			// 排序
			list.sort((first, second) -> {
				return Integer.compare(first.getAnnotation(FileField.class).order(),
						second.getAnnotation(FileField.class).order());
			});
			cacheFields = list.toArray(new Field[0]);
			declaredFieldsCache.put(fullPath, cacheFields);
		} else {
			System.out.println("已经缓存");
		}

		String[] columns = content.split(separator);

		T obj = (T) clazz.newInstance();
		// 组装数据
		for (int index = 0, length = cacheFields.length; index < length; index++) {
			Field field = cacheFields[index];
			field.setAccessible(true);
			FileField fileField = field.getAnnotation(FileField.class);

			String value = columns[index];
			try {
				Class<? extends Encrypt> deCls = fileField.encrypt();
				if (deCls != NoneDecrypt.class) {
					Encrypt encrypt = deCls.newInstance();
					value = encrypt.encrypt(value);
				}

				String format = fileField.format();
				if (field.getType().isAssignableFrom(LocalDateTime.class)) {
					field.set(obj, LocalDateTime.parse(value, DateTimeFormatter.ofPattern(format)));
				} else if (field.getType().isAssignableFrom(LocalDate.class)) {
					field.set(obj, LocalDate.parse(value, DateTimeFormatter.ofPattern(format)));
				} else if (field.getType().isAssignableFrom(Date.class)) {
					SimpleDateFormat sdf = new SimpleDateFormat(format);
					field.set(obj, sdf.parse(value));
				} else if (field.getType().isAssignableFrom(byte.class)
						|| field.getType().isAssignableFrom(Byte.class)) {
					field.set(obj, Byte.valueOf(value));
				} else if (field.getType().isAssignableFrom(short.class)
						|| field.getType().isAssignableFrom(Short.class)) {
					field.set(obj, Short.valueOf(value));
				} else if (field.getType().isAssignableFrom(int.class)
						|| field.getType().isAssignableFrom(Integer.class)) {
					field.set(obj, Integer.valueOf(value));
				} else if (field.getType().isAssignableFrom(long.class)
						|| field.getType().isAssignableFrom(Long.class)) {
					field.set(obj, Long.valueOf(value));
				} else if (field.getType().isAssignableFrom(float.class)
						|| field.getType().isAssignableFrom(Float.class)) {
					field.set(obj, Float.valueOf(value));
				} else if (field.getType().isAssignableFrom(double.class)
						|| field.getType().isAssignableFrom(Double.class)) {
					field.set(obj, Double.valueOf(value));
				} else {
					field.set(obj, String.valueOf(value));
				}
			} catch (IllegalArgumentException | IllegalAccessException | InstantiationException | ParseException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
}
