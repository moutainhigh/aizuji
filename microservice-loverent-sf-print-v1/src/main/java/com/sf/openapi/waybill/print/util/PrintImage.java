/**
 * 
 */
package com.sf.openapi.waybill.print.util;

import java.io.FileInputStream;
import java.io.IOException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;

/**
 * @Title: 打印图片工具类
 * @author hxj
 * @date 2018年3月6日 上午10:20:59
 */
public class PrintImage {
	/**
	 * 画图片的方法
	 * 
	 * @param fileName[图片的路径]
	 */
	public void drawImage(String fileName) {
		try {
			DocFlavor dof = null;
			// 根据用户选择不同的图片格式获得不同的打印设备
			if (fileName.endsWith(".gif")) {
				// gif
				dof = DocFlavor.INPUT_STREAM.GIF;
			} else if (fileName.endsWith(".jpg")) {
				// jpg
				dof = DocFlavor.INPUT_STREAM.JPEG;
			} else if (fileName.endsWith(".png")) {
				// png
				dof = DocFlavor.INPUT_STREAM.PNG;
			}
			// 字节流获取图片信息
			FileInputStream fin = new FileInputStream(fileName);
			// 获得打印属性
			PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
			// 每一次默认打印一页
			pras.add(new Copies(1));
			//pras.add(OrientationRequested.PORTRAIT);
			//pras.add(PrintQuality.HIGH);
			// 获得打印设备 ，字节流方式，图片格式
			PrintService pss[] = PrintServiceLookup.lookupPrintServices(dof, pras);
			// 如果没有获取打印机
			if (pss.length == 0) {
				// 终止程序
				return;
			}
			// 获取第一个打印机
			PrintService ps = pss[0];
			System.out.println("Printing image..........." + ps);
			// 获得打印工作
			DocPrintJob job = ps.createPrintJob();
			DocAttributeSet das = new HashDocAttributeSet();

			// 设置打印纸张的大小（以毫米为单位）
			das.add(new MediaPrintableArea(0, 0, 210, 100, MediaPrintableArea.MM));
			// 设置打印内容
			Doc doc = new SimpleDoc(fin, dof, das);
			// 开始打印
			job.print(doc, pras);
			fin.close();
		} catch (IOException ie) {
			// 捕获io异常
			ie.printStackTrace();
		} catch (PrintException pe) {
			// 捕获打印异常
			pe.printStackTrace();
		}
	}

	/**
	 * 主函数
	 * 
	 * @param args
	 * 
	 */
	public static void main(String args[]) {
		PrintImage dp = new PrintImage();
		dp.drawImage("C:\\pic\\1111234549924_1.jpg");
	}

}