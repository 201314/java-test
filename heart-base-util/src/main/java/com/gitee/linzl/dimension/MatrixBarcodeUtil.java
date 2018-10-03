package com.gitee.linzl.dimension;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
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
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 关于条形码的校验算法 http://www.xdemo.org/barcode-validate/
 * <p>
 * 使用google 的Zxing
 * 
 * @see --------------------------------------------------------------------------
 *      ---------------------------------------------
 * @see 首页--https://code.google.com/p/zxing
 * @see 介绍
 *      --用于解析多种格式条形码(EAN-13)和二维码(QRCode)的开源Java类库,其提供了多种应用的类库,如javase/jruby/cpp
 *      /csharp/android
 * @see 说明--下载到的ZXing-2.2.zip是它的源码,我们在JavaSE中使用时需用到其core和javase两部分
 * @see 可直接引入它俩的源码到项目中
 *      ,或将它俩编译为jar再引入,这是我编译好的：http://download.csdn.net/detail/jadyer/6245849
 * @see --------------------------------------------------------------------------
 *      ---------------------------------------------
 * @see 经测试:用微信扫描GBK编码的中文二维码时出现乱码,用UTF-8编码时微信可正常识别
 * @see 并且MultiFormatWriter.encode()时若传入hints参数来指定UTF-8编码中文时,微信压根就不识别所生成的二维码
 * @see 所以这里使用的是这种方式new String(content.getBytes("UTF-8"), "ISO-8859-1")
 * @see --------------------------------------------------------------------------
 *      ---------------------------------------------
 * @see 将logo图片加入二维码中间时,需注意以下几点
 * @see 1)生成二维码的纠错级别建议采用最高等级H,这样可以增加二维码的正确识别能力(我测试过,不设置级别时,二维码工具无法读取生成的二维码图片)
 * @see 2)头像大小最好不要超过二维码本身大小的1/5,而且只能放在正中间部位,这是由于二维码本身结构造成的(你就把它理解成图片水印吧)
 * @see 3)在仿照腾讯微信在二维码四周增加装饰框,那么一定要在装饰框和二维码之间留出白边,这是为了二维码可被识别
 * @see --------------------------------------------------------------------------
 *      ---------------------------------------------
 * @version v1.0
 * @history v1.0-->方法新建,目前仅支持二维码的生成和解析,生成二维码时支持添加logo头像
 * @author linzl
 */
public class MatrixBarcodeUtil {
	// private static final int BARCODE_WIDTH = 105;// 条形码宽度
	// private static final int BARCODE_HEIGHT = 50;// 条形码高度

	private static final int QRCODE_WIDTH = 200;// 二维码宽度
	private int width = QRCODE_WIDTH;

	private static final int QRCODE_HEIGHT = 200;// 二维码高度
	private int height = QRCODE_HEIGHT;

	private BarcodeFormat format;
	private MatrixToImageConfig config;
	private Map<EncodeHintType, Object> hints;

	private String content;
	private File file;
	private File logo;

	public static class WriteBuilder {
		private MatrixBarcodeUtil zxing;

		/**
		 * @param contents
		 *            生成二维码的内容
		 * @param file
		 *            输出到文件
		 */
		public WriteBuilder(String content, File file) {
			zxing = new MatrixBarcodeUtil();
			zxing.content = content;
			zxing.file = file;
			zxing.format = BarcodeFormat.QR_CODE;
			zxing.config = new MatrixToImageConfig(0xFF000001, 0xFFFFFFFF);
			zxing.hints = new HashMap<EncodeHintType, Object>();
		}

		/**
		 * 生成二维码的内容
		 * 
		 * @param contents
		 * @return
		 */
		public WriteBuilder contents(String contents) {
			zxing.content = contents;
			return this;
		}

		public WriteBuilder width(int width) {
			zxing.width = width;
			return this;
		}

		public WriteBuilder height(int height) {
			zxing.height = height;
			return this;
		}

		/**
		 * 二维码颜色RGB 16进制
		 * 
		 * @param onColor
		 *            二维码的颜色,默认黑色
		 * @param offColor
		 *            二维码外空缺部分颜色,默认白色
		 * @return
		 */
		public WriteBuilder color(int onColor, int offColor) {
			zxing.config = new MatrixToImageConfig(onColor, offColor);
			return this;
		}

		public WriteBuilder format(BarcodeFormat format) {
			zxing.format = format;
			return this;
		}

		public WriteBuilder errorCorrectionLevel(ErrorCorrectionLevel level) {
			// 指定纠错级别(L--7%,M--15%,Q--25%,H--30%)
			zxing.hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			return this;
		}

		public WriteBuilder charset(Charset charset) {
			// 编码
			zxing.hints.put(EncodeHintType.CHARACTER_SET, charset.name());
			return this;
		}

		/**
		 * 必须大于0
		 * 
		 * @param margin
		 *            边框大小设置
		 * @return
		 */
		public WriteBuilder margin(Integer margin) {
			if (margin == null || margin.intValue() <= 0) {
				return this;
			}
			zxing.hints.put(EncodeHintType.MARGIN, margin);
			return this;
		}

		public WriteBuilder shape(SymbolShapeHint shape) {
			zxing.hints.put(EncodeHintType.DATA_MATRIX_SHAPE, shape);
			return this;
		}

		public WriteBuilder hints(Map<EncodeHintType, Object> hints) {
			zxing.hints = hints;
			return this;
		}

		public WriteBuilder logo(File logo) {
			zxing.logo = logo;
			return this;
		}

		public MatrixBarcodeUtil build() {// 进行参数校验
			if (!zxing.hints.containsKey(EncodeHintType.CHARACTER_SET)) {
				// 编码
				zxing.hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			}

			if (!zxing.hints.containsKey(EncodeHintType.ERROR_CORRECTION)) {
				// 容错率：容错率越高,二维码的有效像素点就越多，使用 ErrorCorrectionLevel 中的枚举
				// 指定纠错级别(L--7%,M--15%,Q--25%,H--30%)
				zxing.hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			}

			if (!zxing.hints.containsKey(EncodeHintType.MARGIN)) {
				// margin 边框大小设置
				zxing.hints.put(EncodeHintType.MARGIN, 1);
			}
			return zxing;
		}
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
	 * 
	 * @throws WriterException
	 * @throws IOException
	 */
	public boolean create() throws WriterException, IOException {
		// 二维码内容,码制,宽度,高度,二维码参数
		BitMatrix matrix = new MultiFormatWriter().encode(content, format, width, height, hints);

		String fileName = this.file.getName();

		BufferedImage fileImage = null;
		if (config == null) {
			fileImage = MatrixToImageWriter.toBufferedImage(matrix);
		} else {
			fileImage = MatrixToImageWriter.toBufferedImage(matrix, config);
		}

		// 为二维码增加Logo
		if (logo != null && logo.exists() && logo.isFile()) {
			BufferedImage logImage = ImageIO.read(logo);

			// 设置logo图片宽度为二维码图片的五分之一
			int logoWidth = fileImage.getWidth() / 5;
			// 设置logo图片高度为二维码图片的五分之一
			int logoHeight = fileImage.getHeight() / 5;
			// 设置logo图片的位置,这里令其居中
			int logoX = (fileImage.getWidth() - logoWidth) / 2;
			// 设置logo图片的位置,这里令其居中
			int logoY = (fileImage.getHeight() - logoHeight) / 2;

			Graphics2D graphics = fileImage.createGraphics();
			graphics.drawImage(logImage, logoX, logoY, logoWidth, logoHeight, null);

			// 给logo画边框
			// 构造一个具有指定线条宽度以及 cap 和 join 风格的默认值的实心 BasicStroke
			// graphics.setStroke(new BasicStroke(2));
			// graphics.setColor(Color.RED);
			// graphics.drawRect(logoX, logoY, logoWidth, logoHeight);

			graphics.dispose();
		}

		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		return ImageIO.write(fileImage, ext, this.file);
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
			System.out.println("二维码图片[" + imagePath + "]解析失败,堆栈轨迹如下");
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}
}
