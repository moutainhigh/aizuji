/**
 * 
 */
package org.gz.warehouse.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2018年3月21日 下午2:52:25
 */
public class PDFTest {

	@Test
	public void test() throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		File destFile = new File("c:\\pic\\test.pdf");
		if(destFile.exists()) {
			destFile.delete();
		}
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(destFile));
		// 设置字体
		BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font FontChinese24 = new Font(bfChinese, 24, Font.BOLD);
		Font FontChinese18 = new Font(bfChinese, 18, Font.BOLD);
		Font FontChinese16 = new Font(bfChinese, 16, Font.BOLD);
		Font FontChinese12 = new Font(bfChinese, 12, Font.NORMAL);
		Font FontChinese11Bold = new Font(bfChinese, 11, Font.BOLD);
		Font FontChinese11 = new Font(bfChinese, 11, Font.ITALIC);
		Font FontChinese11Normal = new Font(bfChinese, 11, Font.NORMAL);

		document.open();//打开文档
		
		Paragraph title = new Paragraph("爱租机产品签收确认单",FontChinese16);
		
		title.setAlignment(Element.ALIGN_CENTER);//居中对齐
		
		document.add(title);
		
		Paragraph content = new Paragraph("本人：邹美艳，身份证号：431026198810040620，在深圳市国智互联网科技有限公司旗下“爱租机”平台租赁一台99新" + 
				"iPhone 7 Plus 全网通,32G ,黑色，合同编号：SO 1803190000001615，本人确认合同有效性并已签收全部协议中约定的设备(设" + 
				"备SN/IMEI号为001sn/001IMEI)。",FontChinese12);
		content.setSpacingBefore(50f);//设置上方空白区域
		content.setLeading(30f);//设置行间距
		content.setFirstLineIndent(20f);//设置首行缩进
		document.add(content);
		
		Paragraph clientSignParagraph = new Paragraph("客户签字:",FontChinese12);
		clientSignParagraph.setSpacingBefore(150f);//设置上方空白区域
		document.add(clientSignParagraph);
		
		Paragraph clientIdParagraph = new Paragraph("客户身份证号:",FontChinese12);
		clientIdParagraph.setSpacingBefore(30f);//设置上方空白区域
		document.add(clientIdParagraph);
		
		Paragraph signDateParagraph = new Paragraph("签署日期:",FontChinese12);
		signDateParagraph.setSpacingBefore(30f);//设置上方空白区域
		document.add(signDateParagraph);
		 // 关闭文档
		 document.close();
		// 关闭书写器
		 writer.close();
	}
}
