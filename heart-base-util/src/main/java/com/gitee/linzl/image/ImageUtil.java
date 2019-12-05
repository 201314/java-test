package com.gitee.linzl.image;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Coordinate;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 图片工具
 *
 * @author linzhenlie
 * @date 2019/10/15
 */
public class ImageUtil {
    /**
     * 读取照片里面的信息
     * <p>
     * 使用metadata-extractor,支持获取tif,photoshop等多种com.drew.imaging.FileType
     * <p>
     * ExifDirectoryBase 包含图片基础参数
     */
    public static void readMetadata(File file) throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                // 标签名
                String tagName = tag.getTagName();
                // 标签信息
                String desc = tag.getDescription();
                // 标签类型
                tag.getTagType();
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
     * @param stream 输入流
     * @param out    输出流
     * @param width  缩放后宽度
     * @param height 缩放后高度
     */
    public static void size(InputStream stream, OutputStream out, int width, int height) throws IOException {
        Builder<? extends InputStream> image = Thumbnails.of(stream);
        // 按比例缩放
        image.scale(0.25f);
        // 按大小缩放
        image.size(width, height);
        image.toOutputStream(out);
    }

    /**
     * 裁剪,默认中间区域
     *
     * @param stream 输入流
     * @param out    输出流
     * @param width  裁剪宽度
     * @param height 裁剪高度
     * @throws IOException
     */
    public static void region(InputStream stream, OutputStream out, int width, int height) throws IOException {
        region(stream, out, Positions.CENTER, width, height);
    }

    /**
     * 指定坐标
     */
    public static void region(InputStream stream, OutputStream out, int x, int y, int width, int height) throws IOException {
        region(stream, out, new Coordinate(x, y), width, height);
    }

    public static void region(InputStream stream, OutputStream out, Position position, int width, int height) throws IOException {
        /**
         * 图片position位置的200*200的区域
         */
        Thumbnails.of(stream)
                .sourceRegion(position, width, height)
                .size(200, 200)
                .keepAspectRatio(false)
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
     * @param stream 输入流
     * @param out    输出流
     */
    public static void addWatermark(InputStream stream, File water, OutputStream out) throws IOException {
        Builder<? extends InputStream> image = Thumbnails.of(stream);
        // 设置大小，必须
        image.scale(1f);
        // 添加水印，位置，水印图片，透明度
        image.watermark(Positions.CENTER, ImageIO.read(water), 0.5f);
        // 设置输出品质
        image.outputQuality(0.8f);
        image.toOutputStream(out);
    }

    /**
     * 旋转图片
     *
     * @param stream 输入流
     * @param out    输出流
     * @param angle  旋转角度
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
     * @param stream 输入流
     * @param stream 输出流
     * @param format 转换格式
     * @throws IOException
     */
    public void formatImage(InputStream stream, String format, OutputStream out) throws IOException {
        Thumbnails.of(stream)
                .size(1280, 1024)
                .outputFormat(format)
                .toOutputStream(out);
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
