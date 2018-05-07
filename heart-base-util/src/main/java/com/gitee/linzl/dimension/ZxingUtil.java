package com.gitee.linzl.dimension;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 关于条形码的校验算法 http://www.xdemo.org/barcode-validate/
 * <p>
 * 使用google 的Zxing
 * 
 * @author linzl
 */
public class ZxingUtil {
	// private static final int BARCODE_WIDTH = 105;// 条形码宽度
	// private static final int BARCODE_HEIGHT = 50;// 条形码高度

	private static final int QRCODE_WIDTH = 200;// 二维码宽度
	private static final int QRCODE_HEIGHT = 200;// 二维码高度

	private String charset = "UTF-8";

	private MatrixToImageConfig config;
	private Map<EncodeHintType, Object> hints;

	private String content;
	private File targetFile;

	/**
	 * 生成条形码使用，使用默认宽度、高度
	 * 
	 * @param content
	 *            文本内容，商品的是一串数字
	 * @param targetFile
	 *            条形码保存的全路径
	 */
	// public TwoDimensionUtil(String content, File targetFile) {
	// this.content = content;
	// this.targetFile = targetFile;
	// }

	/**
	 * 生成黑白二维码使用，使用默认宽度、高度
	 * 
	 * @param content
	 *            文本内容
	 * @param targetFile
	 *            二维码保存的全路径
	 */
	public ZxingUtil(String content, File targetFile) {
		this(content, targetFile, 0xFF000001, 0xFFFFFFFF);
	}

	/**
	 * 生成二维码使用，使用默认宽度、高度
	 * 
	 * @param content
	 *            文本内容
	 * @param targetFile
	 *            二维码保存的全路径
	 * @param onColor
	 *            二维码颜色RGB 16进制,0则默认使用黑色
	 * @param offColor
	 *            二维码外的空缺部分的颜色RGB 16进制，0则默认使用白色
	 */
	public ZxingUtil(String content, File targetFile, int onColor, int offColor) {
		this.content = content;
		this.targetFile = targetFile;
		config = new MatrixToImageConfig(onColor, offColor);
	}

	/**
	 * 设置QR二维码参数:默认UTF－8编码,中等容错率,二维码边框为1
	 */
	private Map<EncodeHintType, Object> setEncodeHintType() {
		return setEncodeHintType(ErrorCorrectionLevel.H, 1, null);
	}

	/**
	 * * 设置QR二维码参数
	 * 
	 * @param level
	 *            容错率：容错率越高,二维码的有效像素点就越多，使用 ErrorCorrectionLevel 中的枚举
	 * @param margin
	 *            二维码边框大小设置,必须大于0
	 * @param charset
	 *            编码
	 * @return
	 */
	public Map<EncodeHintType, Object> setEncodeHintType(ErrorCorrectionLevel level, int margin, String charset) {
		hints = new HashMap<EncodeHintType, Object>();
		this.charset = (charset == null ? this.charset : charset);
		// 编码
		hints.put(EncodeHintType.CHARACTER_SET, this.charset);
		// 容错率：容错率越高,二维码的有效像素点就越多
		hints.put(EncodeHintType.ERROR_CORRECTION, level == null ? ErrorCorrectionLevel.H : level);
		// margin 边框大小设置
		hints.put(EncodeHintType.MARGIN, margin < 0 ? 1 : margin);
		// try {
		// content = new String(content.getBytes(), this.charset);
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		return hints;
	}

	/**
	 * 在内存中创建二维码:默认码制BarcodeFormat.QR_CODE,默认长宽各为200
	 * 
	 * @throws WriterException
	 * @throws IOException
	 */
	public void createQRcode() throws WriterException, IOException {
		createQRcode(QRCODE_WIDTH, QRCODE_HEIGHT);
	}

	/**
	 * 在内存中创建二维码:默认码制BarcodeFormat.QR_CODE
	 * 
	 * @throws WriterException
	 * @throws IOException
	 */
	public void createQRcode(int width, int height) throws WriterException, IOException {
		createQRcode(BarcodeFormat.QR_CODE, width, height);
	}

	/**
	 * 在内存中创建二维码:默认长宽各为200
	 * 
	 * @param format
	 *            码制，具体查看BarcodeFormat
	 * @throws WriterException
	 * @throws IOException
	 */
	public void createQRcode(BarcodeFormat format, int width, int height) throws WriterException, IOException {
		create(format, width, height, setEncodeHintType());
	}

	/**
	 * 在内存中创建条形码\二维码
	 * 
	 * @param format
	 *            码制，具体查看BarcodeFormat
	 * @param width
	 *            码宽度
	 * @param height
	 *            码高度
	 * @param encodeHintType
	 *            条形码可为null,主要用于二维码设置
	 *            {@link com.zq.cn.dimension.ZxingDimensionUtils#
	 *            (ErrorCorrectionLevel level, int margin, String charset)
	 *            setEncodeHintType}
	 * @throws WriterException
	 * @throws IOException
	 */
	public void create(BarcodeFormat format, int width, int height, Map<EncodeHintType, Object> encodeHintType)
			throws WriterException, IOException {
		// 二维码内容,码制,宽度,高度,二维码参数
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, format, width, height, encodeHintType);

		String fileName = this.targetFile.getName();
		Path path = FileSystems.getDefault().getPath(this.targetFile.getParentFile().getPath(), fileName);

		// 输出图像
		if (config == null) {
			MatrixToImageWriter.writeToPath(bitMatrix, fileName.substring(fileName.lastIndexOf(".") + 1), path);
		} else {
			MatrixToImageWriter.writeToPath(bitMatrix, fileName.substring(fileName.lastIndexOf(".") + 1), path, config);
		}
	}

	/**
	 * 为二维码增加Logo
	 * 
	 * @param imagePath
	 *            二维码图片存放路径(含文件名)
	 * @param logoPath
	 *            logo头像存放路径(含文件名)
	 * @see 其原理类似于图片加水印
	 */
	public void addQRLogo(File imagePath, File logoPath) throws IOException {
		BufferedImage image = ImageIO.read(imagePath);
		// 图片如果非常大呢,需要通过图片组件，缩小到很小很小？？？
		int logoWidth = image.getWidth() / 5; // 设置logo图片宽度为二维码图片的五分之一
		int logoHeight = image.getHeight() / 5; // 设置logo图片高度为二维码图片的五分之一
		int logoX = (image.getWidth() - logoWidth) / 2; // 设置logo图片的位置,这里令其居中
		int logoY = (image.getHeight() - logoHeight) / 2; // 设置logo图片的位置,这里令其居中
		Graphics2D graphics = image.createGraphics();
		graphics.drawImage(ImageIO.read(logoPath), logoX, logoY, logoWidth, logoHeight, null);
		graphics.dispose();
		String name = imagePath.getName();
		ImageIO.write(image, name.substring(name.lastIndexOf(".") + 1), new File(imagePath.getPath()));
	}

	/**
	 * 读取二维码、条形码内容,貌似只能读取zxing自己生成的
	 * 
	 * @param imagePath
	 *            二维码文件
	 * @return
	 */
	public static String read(File imagePath) {
		return read(imagePath, null);
	}

	/**
	 * 读取二维码、条形码内容,貌似只能读取zxing自己生成的
	 * 
	 * @param imagePath
	 *            二维码、条形码文件
	 * @param charset
	 *            指定文本编码
	 * @return
	 */
	public static String read(File imagePath, String charset) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(imagePath);
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

			Map<DecodeHintType, Object> decodeHintType = new HashMap<DecodeHintType, Object>();
			decodeHintType.put(DecodeHintType.CHARACTER_SET, charset);

			MultiFormatReader reader = new MultiFormatReader();
			Result result = reader.decode(binaryBitmap, decodeHintType);// 对图像进行解码
			return result.getText();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}
}
