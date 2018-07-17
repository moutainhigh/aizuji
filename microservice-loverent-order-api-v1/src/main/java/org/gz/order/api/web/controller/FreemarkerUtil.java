package org.gz.order.api.web.controller;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.SAXException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author Freemarker模版工具类
 */
public class FreemarkerUtil {

  final static Logger logger = LoggerFactory.getLogger(FreemarkerUtil.class);

  public static void main(String args[]) throws IOException, TemplateException, ParserConfigurationException,
                                         SAXException, DocumentException {
    // 2.生成合同pdf文件到指定文件夹下面，以订单号为文件名
    Map<String, Object> map = new HashMap<>();
    // 乙方
    map.put("realName", "张三");
    // 身份证
    map.put("idNo", "SO12355555");
    // 收货地址
    map.put("address", "广东深圳宝安区西乡中软大厦1005");
    // 联系电话
    map.put("phoneNum", "15899663322");
    // 产品名称
    map.put("matreielName", "iphone X");
    // 签约金额
    map.put("signContractAmount", "8150.00");
    String materielSpecName = "黑色,32G,全网通";
    String[] mStrings = materielSpecName.split(",");
    // 规格
    map.put("materielSpecName1", mStrings[0]);
    map.put("materielSpecName2", mStrings[1]);
    map.put("materielSpecName3", mStrings[2]);
    // 租期
    map.put("leaseTerm", "12");
    // IMEI/SN
    map.put("imei", "123123123" + "/" + "eee555");
    // 租金
    map.put("leaseAmount", "850");

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    // 租用日期 起
    map.put("RentStartTime", sdf.format(new Date()));

    Calendar cld = Calendar.getInstance();
    cld.setTime(new Date());
    cld.add(Calendar.MONTH, 12);
    Date d2 = cld.getTime();
    // 设置订单结束租时间 （通过租用日期 + 租机总月份）
    map.put("RentEndTime", sdf.format(d2));
    // 意外保障服务费
    map.put("premium", "120.00");

    System.out.println(generatePdfFromTemplate("D:\\font\\", "S1000", "agreement", "userLease.ftl", map));

  }

  /***
   * 功能：生成PDF文件
   * 
   * @param parentPath:pdf和 font字体存放路径
   * @param fileName 文件名称（合同编号）
   * @param moduleCode:合同和html存放子路径
   * @param templateName:合同模板文件名称
   * @param mapDate :封装合同数据的HashMap
   * @throws IOException
   * @throws TemplateException
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws DocumentException
   */
  @SuppressWarnings("deprecation")
  public static String generatePdfFromTemplate(String parentPath, String fileName, String moduleCode, String templateName, Map<String, Object> mapData) throws IOException,
                                                                                                                                                        TemplateException,
                                                                                                                                                        ParserConfigurationException,
                                                                                                                                                        SAXException,
                                                                                                                                                        DocumentException {
    // 创建一个Configuration实例
    // 创建模版对象
    // Template t = null;
    // String basePath = parentPath; // FreeMarker的模版文件夹位置
    String outputFile;
    // 将生成的内容写入html中
    File tempFile;
    Writer out = null;
    // String tempoutputFile = contractPath + File.separator + fileName
    // + "temp.pdf";
    OutputStream os = null;
    outputFile = "";
    try {
      Configuration cfg = null;

      cfg = new Configuration();
      cfg.setDefaultEncoding("utf-8");
      // 设置FreeMarker的模版文件夹位置
      // cfg.setDirectoryForTemplateLoading(new File(basePath));
      // t = cfg.getTemplate(templateName);
      cfg.setClassLoaderForTemplateLoading(FreemarkerUtil.class.getClassLoader(), moduleCode);
      Template t = cfg.getTemplate(templateName);

      t.setEncoding("utf-8");
      // 在模版上执行插值操作，并输出到制定的输出流中

      String contractPath = "";
      contractPath = parentPath + moduleCode; // 存放空白pdf合同文件的路径
      // 判断合同存放目录是否存在
      File dirFile = new File(contractPath);
      if (!dirFile.exists()) {
        dirFile.mkdirs();
      }

      tempFile = new File(contractPath + File.separator + fileName + ".html");
      if (!tempFile.exists()) {
        tempFile.createNewFile();
      }
      out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), "utf-8"));
      StringWriter stringWriter = new StringWriter();
      BufferedWriter writer = new BufferedWriter(stringWriter);
      t.setEncoding("utf-8");
      t.process(mapData, writer);
      String htmlStr = stringWriter.toString();
      t.process(mapData, out);

      String url = tempFile.toURI().toURL().toString();
      outputFile = contractPath + File.separator + fileName + ".pdf";
      os = new FileOutputStream(outputFile);
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document doc = builder.parse(new ByteArrayInputStream(htmlStr.getBytes("utf-8")));
      ITextRenderer renderer = new ITextRenderer();
      logger.info("文档生成目录" + outputFile);
      renderer.setDocument(doc, url);
      // 解决中文问题 宋体字
      String osName = System.getProperty("os.name"); // 操作系统名称
      if (osName.contains("Windows")) {
        renderer.getFontResolver().addFont(parentPath + "/simsun.ttc",
          BaseFont.IDENTITY_H,
          BaseFont.NOT_EMBEDDED);
      } else {
        renderer.getFontResolver().addFont(parentPath + "/simsun.ttf",
          BaseFont.IDENTITY_H,
          BaseFont.NOT_EMBEDDED);
      }
      renderer.layout();
      renderer.createPDF(os);
      tempFile.delete();
    } finally {
      try {
        out.flush();
      } catch (Exception e) {
      }
      try {
        out.close();
      } catch (Exception e) {
      }
      try {
        os.close();
      } catch (Exception e) {
      }
    }

    // 删除生成的临时文件.html

    // 将pdf文件先加水印然后输出
    // if(borrowerType.equals(ProjectParticAccTypeConstants.SHANG_HU))

    // setWatermark(bos, tempoutputFile, parentPath,
    // 16,moduleCode,borrowerType,pic);

    //
    return outputFile;
  }

  /*public static void setWatermark(BufferedOutputStream bos, String input, String parentPath, int permission,String modulecode,String borrowerType,Boolean pic)
  		throws DocumentException, IOException {
  	PdfReader reader = new PdfReader(input);
  	PdfStamper stamper = new PdfStamper(reader, bos);
  	int total = reader.getNumberOfPages() + 1;
  	PdfContentByte content;
  	BaseFont base = null;// BaseFont.createFont("STSong-Light",
  							// "UniGB-UCS2-H",BaseFont.EMBEDDED);
  	String osName = System.getProperty("os.name"); // 操作系统名称
  	if (osName.contains("Windows")) {
  		base = BaseFont.createFont(parentPath + "fonts/simsun.ttc,1", BaseFont.IDENTITY_H,
  				BaseFont.NOT_EMBEDDED);
  		// renderer.getFontResolver().addFont(parentPath +
  		// "fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
  		System.out.println("windows");
  	} else {
  		// base = BaseFont.createFont(parentPath + "fonts/simsun.ttc",
  		// BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
  		base = BaseFont.createFont(parentPath + "fonts/simsun.ttf", BaseFont.IDENTITY_H,
  				BaseFont.NOT_EMBEDDED);
  		System.out.println("linux");
  	}
  	PdfGState gs = new PdfGState();
  	if(pic)
  	{
  	for (int i = 1; i < total; i++) {
  		// content = stamper.getOverContent(i);// 在内容上方加水印
  		if (i == total - 1) {
  			System.out.println("执行");
  			content = stamper.getUnderContent(i);// 在内容下方加水印
  			gs.setFillOpacity(0.2f);
  			// content.setGState(gs);
  			content.beginText();
  			content.setColorFill(Color.LIGHT_GRAY);
  			content.setFontAndSize(base, 50);
  			content.setTextMatrix(70, 200);
  			// content.showTextAligned(Element.ALIGN_CENTER,
  			StringBuffer imgPath=new StringBuffer();
  			//盖商户章
  			if(borrowerType!=null && borrowerType.equals(ProjectParticAccTypeConstants.SHANG_HU))
  			{
  				//借款企业合同章					
  				imgPath=imgPath.append(parentPath).append("fonts").append(File.separator);
  				Image image = Image.getInstance(imgPath.append(modulecode).append(".png").toString());
  				int imageheight=450;
  				if(modulecode.equals("wyb"))
  				{
  					imageheight=390;
  				}
  				if(modulecode.equals("qyb"))
  				{
  					imageheight=330;
  				}
  				if(modulecode.equals("xsh"))
  				{
  					imageheight=660;
  				}
  				if (modulecode.equals("zqzr")) {
  					imageheight=50;
  				}
  				
  				image.setAbsolutePosition(100,imageheight); // set the first background
  				image.scaleToFit(125, 125);
  				content.addImage(image);
  			}
  			
  			//荷叶企业章
  			imgPath=new StringBuffer();
  			imgPath=imgPath.append(parentPath).append("fonts").append(File.separator);
  			int imageheight=450;
  			
  			if(modulecode.equals("xsh"))
  			{					
  				 imgPath=imgPath.append("xshdb").append(".png");
  				 imageheight=490;
  			}
  			else
  			{
  				imgPath=imgPath.append("heye").append(".png");
  			}
  			Image image2 = Image.getInstance(imgPath.toString());
  			int imagecompany=450;
  			if(modulecode.equals("wyb"))
  			{
  				imagecompany=270;
  			}
  			if(modulecode.equals("qyb"))
  			{
  				imagecompany=326;
  			}
  			if(modulecode.equals("xsh"))
  			{
  				imagecompany=500;
  			}
  			image2.setAbsolutePosition(200,imagecompany); // set the first background
  			// image of the absolute
  			image2.scaleToFit(125, 125);
  			content.addImage(image2);
  			content.setColorFill(Color.BLACK);				
  			content.setFontAndSize(base, 8);
  			content.endText();
  		}
  	}		
  	}
  	stamper.close();
  	reader.close();	
  	File f=new File(input);
  	if(f.exists())
  		f.delete();
  }*/
}
