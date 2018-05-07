package com.gitee.linzl.dimension;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.krysalis.barcode4j.BarcodeClassResolver;
import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.DefaultBarcodeClassResolver;
import org.krysalis.barcode4j.output.bitmap.BitmapBuilder;
import org.krysalis.barcode4j.tools.MimeTypes;

/**
 * 条形码工具类
 * 
 * @author linzl
 *
 * @creatDate 2016年8月31日
 */
public class OneDimensionUtil {
	private static BarcodeClassResolver resolver = new DefaultBarcodeClassResolver();

	public enum CodeType {
		CODABAR("codabar"),

		CODE39("code39"),

		CODE128("code128"),

		EAN_128("ean-128"),

		EAN128("ean128"),

		_2OF5("2of5"),

		INTL2OF5("intl2of5"),

		INTERLEAVED2OF5("interleaved2of5"),

		EAN_13("ean-13"),

		EAN13("ean13"),

		EAN_8("ean-8"),

		EAN8("ean8"),

		UPC_A("upc-a"),

		UPCA("upca"),

		UPC_E("upc-e"),

		UPCE("upce"),

		POSTNET("postnet"),

		ROYAL_MAIL_CBC("royal-mail-cbc"),

		USPS4CB("usps4cb"),

		PDF417("pdf417"),

		DATAMATRIX("datamatrix");

		private String name;

		private CodeType(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public static void toBarcode(String msg, File target) {
		toBarcode(OneDimensionUtil.CodeType.UPCA, msg, target);
	}

	public static void toCodabarBarcode(String msg, File target) {
		toBarcode(OneDimensionUtil.CodeType.CODABAR, msg, target);
	}

	public static void toCode39Barcode(String msg, File target) {
		toBarcode(OneDimensionUtil.CodeType.CODE39, msg, target);
	}

	public static void toCode128Barcode(String msg, File target) {
		toBarcode(OneDimensionUtil.CodeType.CODE128, msg, target);
	}

	public static void toEan_128Barcode(String msg, File target) {
		toBarcode(OneDimensionUtil.CodeType.EAN_128, msg, target);
	}

	public static void toEan128Barcode(String msg, File target) {
		toBarcode(OneDimensionUtil.CodeType.EAN128, msg, target);
	}

	/**
	 * 生成到流
	 *
	 * @param msg
	 * @param ous
	 */
	public static void toBarcode(CodeType codeType, String msg, File target) {
		if (StringUtils.isEmpty(msg) || target == null) {
			return;
		}
		BarcodeGenerator bean = null;
		try {
			bean = (BarcodeGenerator) resolver.resolveBean(codeType.getName()).newInstance();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		// 精细度
		final int dpi = 90;
		String mime = MimeTypes.expandFormat(FilenameUtils.getExtension(target.getName()));

		try {
			BitmapBuilder.outputBarcodeImage(bean, msg, FileUtils.openOutputStream(target), mime, dpi);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}