/**
 * 
 */
package com.sf.openapi.waybill.print.runner;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.TreeMap;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import com.google.common.io.Files;
import com.sf.openapi.waybill.print.constant.TemplateTypeEnum;
import com.sf.openapi.waybill.print.util.OSinfoUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PrintServerRunner implements CommandLineRunner {

	@Value("${jasper.template.poster_100mm150mm.path}")
	private String poster_100mm150mm_templatePath;

	@Value("${jasper.template.v3_poster_100mm210mm.path}")
	private String v3_poster_100mm210mm_templatePath;

	@Value("${jasper.template.sing_confirm.path}")
	private String sing_confirm_templatePath;

	@Value("${logo.95538.path}")
	private String logo_95538_path;
	
	private String logo_95538_abs_path;
	
	@Value("${logo.sf.path}")
	private String logo_sf_path;
	
	private String logo_sf_abs_path;
	
	@Value("${areaFile.path}")
	private String areaFilePath;
	
	private TreeMap<String,String> areaMap = new TreeMap<String,String>();
	
	@Override
	public void run(String... args) throws Exception {
		log.info("PrintServerRunner run...");
		checkOS();// 检查操作系统
		checkPrintService();// 检查打印服务,此处若想在不存在打印服务的情况下，禁止启动，可直接抛出异常
		setLogoPath();//设置LOGO路径
		setAreaData();//读取template/area.txt来设置顺丰行政区域
		log.info("PrintServerRunner run over...");
	}


	private void checkOS() {
		log.info("操作系统名称：{}", OSinfoUtils.getOSname().toString());
	}

	private void checkPrintService() {
		PrintService[] printSerivces = PrintServiceLookup.lookupPrintServices(null, null);
		if (printSerivces != null && printSerivces.length > 0) {
			log.info("本地安装的打印服务数：{}", printSerivces.length);
			StringBuilder res = new StringBuilder("[");
			for (PrintService printService : printSerivces) {
				res.append(printService.getName()).append(",");
			}
			String s = res.substring(0, res.length() - 1) + "]";
			log.info("本地安装的打印服务：{}", s);
		}
		log.info("默认打印服务名称:{}", PrintServiceLookup.lookupDefaultPrintService().getName());
	}
	
	private void setLogoPath() {
		log.info("设置LOGO路径...");
		try {
			this.logo_95538_abs_path = ResourceUtils.getFile(logo_95538_path).getCanonicalPath();
			log.info("logo_95538_abs_path:{}",logo_95538_abs_path);
			this.logo_sf_abs_path = ResourceUtils.getFile(logo_sf_path).getCanonicalPath();
			log.info("logo_sf_abs_path:{}",logo_sf_abs_path);
		} catch (Exception e) {
			log.error("设置LOGO路径失败：{}",e.getLocalizedMessage());
		}
		
	}
	
	/** 
	* @Description: 设置顺丰行政区域
	*/
	private void setAreaData() {
		try {
			log.info("设置顺丰行政区域...");
			File areaFile = ResourceUtils.getFile(areaFilePath);
			List<String> lines = Files.readLines(areaFile, Charset.forName("UTF-8"));
			for(String line:lines) {
				String[] arr = line.split(",");
				if(arr.length==2&&StringUtils.hasText(arr[0])&&StringUtils.hasText(arr[1])) {
					areaMap.put(arr[0], arr[1]);
				}
			}
			log.info("设置成功，个数为:{}",areaMap.size());
		} catch (Exception e) {
			log.error("设置顺丰行政区域失败：{}",e.getLocalizedMessage());
		}
	}

	public InputStream getByTemplateType(String templateType) {
		log.info("templateType:{}",templateType);
		InputStream in = null;
		if (StringUtils.hasText(templateType)) {
			ClassLoader cl = PrintServerRunner.class.getClassLoader();
			if (templateType.equals(TemplateTypeEnum.TEMPLATE_100MM_150MM.getType())) {
				in= cl.getResourceAsStream(poster_100mm150mm_templatePath);
			} else if (templateType.equals(TemplateTypeEnum.TEMPLATE_100MM_210MM.getType())) {
				in= cl.getResourceAsStream(v3_poster_100mm210mm_templatePath);
			} else if (templateType.equals(TemplateTypeEnum.TEMPLATE_SIGN_CONFIRM.getType())) {
				in= cl.getResourceAsStream(sing_confirm_templatePath);
			}
		}
		log.info("in:"+in);
		return in;
	}

	public String getAreaCode(String cityName) {
		return areaMap.get(cityName);
	}
	
	public String getLogo_95538_abs_path() {
		return this.logo_95538_abs_path;
		
	}

	public String getLogo_sf_abs_path() {
		return this.logo_sf_abs_path;
	}
	
	
}
