package com.gitee.linzl.annotation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
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

public class ToStringBuilder {
	// 类名，对应的字段
	public static final Map<String, Field[]> declaredFieldsCache = new ConcurrentHashMap<>(256);

	public static String toString(Object object) {
		return toString(object, Charset.forName("gb2312"));
	}

	public static String toString(Object object, Charset charset) {
		return toString(object, charset, "|");
	}

	public static String toString(Object object, Charset charset, String separator) {
		return toString(object, charset, separator, System.lineSeparator());
	}

	/**
	 * @param object
	 * @param charsetName
	 *            编码
	 * @param separator
	 *            每个字段间的分隔符
	 * @param end
	 *            属性拼装完成后的结束符
	 * @return
	 */
	public static String toString(Object object, Charset charset, String separator, String end) {
		Class<?> clazz = object.getClass();
		String fullPath = clazz.getName();
		Field[] cacheFields = declaredFieldsCache.get(fullPath);
		if (cacheFields == null) {// 未缓存
			System.out.println("未缓存");
			List<Field> fieldList = new ArrayList<>();
			Field[] fields = clazz.getDeclaredFields();
			Collections.addAll(fieldList, fields);
			while (clazz.getSuperclass() != null) {
				clazz = clazz.getSuperclass();
				Field[] superFields = clazz.getDeclaredFields();
				Collections.addAll(fieldList, superFields);
			}
			AccessibleObject.setAccessible(fields, true);

			List<Field> list = new ArrayList<>();
			for (Field field : fieldList) {
				FieldEncrypt fileField = field.getAnnotation(FieldEncrypt.class);
				if (fileField != null) {
					list.add(field);
				}
			}
			// 排序
			list.sort((first, second) -> {
				return Integer.compare(first.getAnnotation(FieldEncrypt.class).order(),
						second.getAnnotation(FieldEncrypt.class).order());
			});
			cacheFields = list.toArray(new Field[0]);
			declaredFieldsCache.put(fullPath, cacheFields);
		} else {
			System.out.println("已经缓存");
		}

		StringBuilder sb = new StringBuilder();
		// 组装数据
		for (Field field : cacheFields) {
			field.setAccessible(true);
			FieldEncrypt fileField = field.getAnnotation(FieldEncrypt.class);

			try {
				String value = "";
				String format = fileField.format();
				if (field.getType().isAssignableFrom(LocalDateTime.class)) {
					LocalDateTime time = (LocalDateTime) field.get(object);
					value = time.format(DateTimeFormatter.ofPattern(format));
				} else if (field.getType().isAssignableFrom(LocalDate.class)) {
					LocalDate time = (LocalDate) field.get(object);
					value = time.format(DateTimeFormatter.ofPattern(format));
				} else if (field.getType().isAssignableFrom(Date.class)) {
					Date time = (Date) field.get(object);
					SimpleDateFormat sdf = new SimpleDateFormat(format);
					value = sdf.format(time);
				} else {
					value = String.valueOf(field.get(object));
				}

				int minusLength = fileField.length() - value.getBytes(charset).length;// 需要填足的长度
				if (minusLength > 0) {
					value = append(value, minusLength, fileField.padding(), fileField.direct());
				}

				Class<? extends Encrypt> enCls = fileField.encrypt();
				Encrypt encrypt = enCls.newInstance();
				value = encrypt.encrypt(value);

				sb.append(value).append(separator);
			} catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
				e.printStackTrace();
			}
		}
		return sb.append(end).toString();
	}

	/**
	 * @param source
	 *            源数据
	 * @param minusLength
	 *            需要填充的长度
	 * @param fill
	 *            填充的内容
	 * @param direct
	 *            填充的位置
	 * @return
	 */
	public static String append(String source, int minusLength, String fill, PaddingDirection direct) {
		StringBuilder sb = new StringBuilder();
		for (int start = 0; start < minusLength; start++) {
			sb.append(fill);
		}
		if (direct == PaddingDirection.LEFT) {// 左补
			return sb.toString() + source;
		} else if (direct == PaddingDirection.RIGHT) {// 右补
			return source + sb.toString();
		}
		return source;
	}
}
