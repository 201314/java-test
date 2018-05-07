package com.gitee.linzl.image;

import java.io.File;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class ImageUtil {

	/**
	 * 导入标签，使用metadata-extractor
	 * 
	 * @param args
	 * @throws Exception
	 * @throws ImageProcessingException
	 */
	public static void main(String[] args) throws ImageProcessingException, Exception {
		File jpegFile = new File("F:\\BaiduNetdiskDownload\\IMG_20170910_144224_HDR.jpg");
		printImageTags(jpegFile);
	}

	/**
	 * 读取照片里面的信息
	 * 
	 * ExifDirectoryBase 包含图片基础参数
	 */
	private static void printImageTags(File file) throws ImageProcessingException, Exception {
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
}
