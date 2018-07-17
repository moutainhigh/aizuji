/**
 * 
 */
package org.gz.warehouse.utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.common.io.Files;

import net.coobird.thumbnailator.Thumbnails;

/**
 * @Title: 图片缩放工具类
 * @author hxj
 * @Description:
 * @date 2018年1月2日 上午11:31:17
 */
public final class ImageZoomUtils {

	private ImageZoomUtils() {

	}

	/**
	 * 批量缩放文件
	 * @Description: 按指定大小进行缩放 注意：当图片原始宽、高小于指定宽、高时，图片不会进行缩放;
	 *               当图片原始宽比width小，高比height大，则高缩放到height，图片比例保持不变;
	 *               当图片原始宽比width大，高比height小，则宽缩放到width，图片比例保持不变;
	 *               当图片原始宽比width大，高比height大，图片则按比例缩小，输出指定的width或height
	 * @param files
	 *            文件绝对路径文件列表
	 * @param width
	 *            期望的宽
	 * @param height
	 *            期望的高
	 * @param outFile
	 *            输出输出目录
	 * @return boolean 缩放成功，返回true,出现异常返回false
	 */
	public static boolean zoomBySize(Iterable<String> files, int width, int height, File outFile) {
		boolean result = false;
		try {
			Thumbnails.fromFilenames(files).size(width, height).toFile(outFile);
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据指定URL缩放图片
	 * @Description: 按指定大小进行缩放
	 * 注意：
	 * 当图片原始宽、高小于指定宽、高时，图片不会进行缩放;
	 * 当图片原始宽比width小，高比height大，则高缩放到height，图片比例保持不变;
	 * 当图片原始宽比width大，高比height小，则宽缩放到width，图片比例保持不变;
	 * 当图片原始宽比width大，高比height大，图片则按比例缩小，输出指定的width或height 
	 * @param fileUrl 文件URL
	 * @param width   期望宽度
	 * @param height  期望高度
	 * @param quality 缩放质量，其值只能在0.0f-1.0f之间
	 * @param os 输出流 
	 * @param closeOutputStream 是否关闭输出流
	 * @return boolean 缩放成功，返回true,出现异常返回false
	 */
	public static boolean zoomBySize(String fileUrl, int width, int height, float quality, OutputStream os,boolean closeOutputStream) {
		boolean result = false;
		try {
			URL url = new URL(fileUrl);
			Thumbnails.of(url).size(width, height).outputQuality(quality).toOutputStream(os);
			result = true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (closeOutputStream&&os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static void main(String[] args) throws FileNotFoundException {
		OutputStream os = new FileOutputStream("c:/pic/b.jpg");
		String fileUrl = "https://osstest-01.oss-cn-beijing.aliyuncs.com/materiel/pic/M2018010211590825800000.jpg";
		String fileExtension = Files.getFileExtension(fileUrl);
		System.err.println("fileExtension="+fileExtension);
		zoomBySize(fileUrl, 100, 50, 1.0f, os,true);
	}
}
