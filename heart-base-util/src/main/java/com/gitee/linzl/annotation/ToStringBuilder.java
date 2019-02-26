package com.gitee.linzl.annotation;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToStringBuilder {
	private static String charsetName;
	// TODO 增加Map 缓存一些属性数据

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
	public static String toString(Object object, String charset, String separator, String end) {
		charsetName = charset;
		Class<?> clazz = object.getClass();

		List<Field> fieldList = new ArrayList<>();
		Field[] fields = clazz.getDeclaredFields();
		Collections.addAll(fieldList, fields);
		while (clazz.getSuperclass() != null) {
			clazz = clazz.getSuperclass();
			Field[] superFields = clazz.getDeclaredFields();
			Collections.addAll(fieldList, superFields);
		}
		AccessibleObject.setAccessible(fields, true);

		List<Order> list = new ArrayList<>();
		Map<String, Field> map = new HashMap<String, Field>();

		for (Field field : fieldList) {
			FieldEncrypt fileField = field.getAnnotation(FieldEncrypt.class);
			if (fileField != null) {
				Order order = new Order();
				order.setOrder(fileField.order());
				order.setName(field.getName());
				list.add(order);
				map.put(field.getName(), field);
			}
		}
		// 排序
		list.sort((order1, order2) -> {
			return Integer.compare(order1.getOrder(), order2.getOrder());
		});

		StringBuilder sb = new StringBuilder();
		// 组装数据
		list.stream().forEach((order) -> {
			Field field = map.get(order.getName());
			field.setAccessible(true);
			FieldEncrypt fileField = field.getAnnotation(FieldEncrypt.class);

			try {
				int length = fileField.length();
				Padding padding = fileField.padding();
				PaddingDirection direct = fileField.direct();
				String format = fileField.format();

				String value = null;
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

				boolean notEnough = false;
				if (value.getBytes(charsetName).length < length) {
					notEnough = true;
				}

				if (notEnough) {
					if (padding == Padding.SPACE && direct == PaddingDirection.LEFT) {// 左补空格
						value = appendSpaceBefore(value, length);
					} else if (padding == Padding.SPACE && direct == PaddingDirection.RIGHT) {// 右补空格
						value = appendSpaceAfter(value, length);
					} else if (padding == Padding.ZERO && direct == PaddingDirection.LEFT) {// 左补0
						value = appendZeroBefore(value, length);
					} else if (padding == Padding.ZERO && direct == PaddingDirection.RIGHT) {// 右补0
						value = appendZeroAfter(value, length);
					}
				}

				Class<? extends Encrypt> enCls = fileField.encrypt();
				Encrypt encrypt = enCls.newInstance();
				value = encrypt.encrypt(value);

				sb.append(value).append(separator);
			} catch (IllegalArgumentException | IllegalAccessException | InstantiationException
					| UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		});
		return sb.append(end).toString();
	}

	private static int getLength(String src) {
		try {
			return src.getBytes(charsetName).length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 将source被足fullLength长度，后补空格
	 * 
	 * @param source
	 * @param fullLength
	 * @return
	 */
	public static String appendSpaceAfter(String source, int fullLength) {
		int length = fullLength - getLength(source);
		return length > 0 ? source + String.format("%" + length + "s", "") : source;
	}

	public static String appendSpaceBefore(String source, int fullLength) {
		int length = fullLength - getLength(source);
		return length > 0 ? String.format("%" + length + "s", "") + source : source;
	}

	public static String appendZeroBefore(String number, int length) {
		StringBuilder sb = new StringBuilder();
		for (int start = number.length(); start < length; start++) {
			sb.append("0");
		}
		return sb.toString() + number;
	}

	public static String appendZeroAfter(long number, int length) {
		return appendZeroAfter(String.valueOf(number), length);
	}

	public static String appendZeroAfter(String number, int length) {
		StringBuilder sb = new StringBuilder(number);
		for (int start = number.length(); start < length; start++) {
			sb.append("0");
		}
		return sb.toString();
	}

	public static String appendZeroBefore(long number, int length) {
		StringBuilder sb = new StringBuilder();
		for (int start = 0; start < length; start++) {
			sb.append("0");
		}
		DecimalFormat df = new DecimalFormat(sb.toString());
		return df.format(number);
	}
}
