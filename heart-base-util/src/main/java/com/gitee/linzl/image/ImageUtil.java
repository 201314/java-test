package com.gitee.linzl.image;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;

public class ImageUtil {
	/**
	 * 读取照片里面的信息
	 * 
	 * 使用metadata-extractor
	 * 
	 * ExifDirectoryBase 包含图片基础参数
	 */
	public static void printImageTags(File file) throws ImageProcessingException, Exception {
		Metadata metadata = ImageMetadataReader.readMetadata(file);
		for (Directory directory : metadata.getDirectories()) {
			for (Tag tag : directory.getTags()) {
				String tagName = tag.getTagName(); // 标签名
				String desc = tag.getDescription(); // 标签信息
				tag.getTagType();// 标签类型
				System.out.println(tag);
				if (tagName.equals("Image Height")) {
					System.out.println("图片高度: " + desc);
				} else if (tagName.equals("Image Width")) {
					System.out.println("图片宽度: " + desc);
				} else if (tagName.equals("Date/Time Original")) {
					System.out.println("拍摄时间: " + desc);
				} else if (tagName.equals("GPS Latitude")) {
					System.err.println("纬度 : " + desc);
				} else if (tagName.equals("GPS Longitude")) {
					System.err.println("经度: " + desc);
				}
			}
		}
	}

	/**
	 * 缩放图片
	 * 
	 * @param stream
	 *            输入流
	 * @param out
	 *            输出流
	 * @param width
	 *            缩放后宽度
	 * @param height
	 *            缩放后高度
	 */
	public void zoom(InputStream stream, OutputStream out, int width, int height) throws IOException {
		Builder<? extends InputStream> image = Thumbnails.of(stream);
		// 按比例缩放
		image.scale(0.25f);
		// 按大小缩放
		image.size(width, height);
		image.toOutputStream(out);
	}

	/**
	 * 裁剪
	 * 
	 * @param stream
	 *            输入流
	 * @param out
	 *            输出流
	 * @param width
	 *            裁剪宽度
	 * @param height
	 *            裁剪高度
	 * @throws IOException
	 */
	public void regionImage(InputStream stream, OutputStream out, int width, int height) throws IOException {
		/**
		 * 图片中心400*400的区域
		 */
		Thumbnails.of(stream).sourceRegion(Positions.CENTER, width, height).size(200, 200).keepAspectRatio(false)
				.toOutputStream(out);
	}

	public void regionCenterImage(InputStream stream, OutputStream out, int width, int height) throws IOException {
		/**
		 * 图片右下400*400的区域
		 */
		Thumbnails.of(stream).sourceRegion(Positions.BOTTOM_RIGHT, width, height).size(200, 200).keepAspectRatio(false)
				.toOutputStream(out);
		/**
		 * 指定坐标
		 */
		Thumbnails.of(stream).sourceRegion(600, 500, 400, 400).size(200, 200).keepAspectRatio(false)
				.toOutputStream(out);
	}

	/**
	 * 拼接图片（图片会有重叠）
	 */
	/**
	 * 合并图片
	 */

	/**
	 * 添加文字水印、图片水印
	 * 
	 * @param stream
	 *            输入流
	 * @param out
	 *            输出流
	 */
	public void addWatermark(InputStream stream, OutputStream out) throws IOException {
		String path = "D://";
		path = path + "shuiyin.png";
		Builder<? extends InputStream> image = Thumbnails.of(stream);
		File file = new File(path);
		// 设置大小，必须
		image.scale(1f);
		// 添加水印，位置，水印图片，透明度
		image.watermark(Positions.CENTER, ImageIO.read(file), 0.5f);
		// 设置输出品质
		image.outputQuality(0.8f);
		image.toOutputStream(out);
	}

	/**
	 * 旋转图片
	 * 
	 * @param stream
	 *            输入流
	 * @param out
	 *            输出流
	 * @param angle
	 *            旋转角度
	 */
	public void rotate(InputStream stream, OutputStream out, int angle) throws IOException {
		Builder<? extends InputStream> image = Thumbnails.of(stream);
		// 正数顺时针旋转，负数逆时针旋转
		image.rotate(angle);
		image.toOutputStream(out);
	}

	/**
	 * 转化图像格式
	 * 
	 * @param stream
	 *            输入流
	 * @param stream
	 *            输出流
	 * @param format
	 *            转换格式
	 * @throws IOException
	 */
	public void formatImage(InputStream stream, String format, OutputStream out) throws IOException {
		Thumbnails.of(stream).size(1280, 1024).outputFormat(format).toOutputStream(out);
	}

	/**
	 * 彩色变黑白
	 */
	/**
	 * 压缩图片
	 */

	public static void main(String[] args) throws Exception {
		Thumbnails.of("D:/test/11133.jpg").size(100, 180).toFiles(Rename.PREFIX_HYPHEN_THUMBNAIL);
	}
}
