/**
 * 
 */
package com.sf.openapi.waybill.print.service.impl;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.gz.common.resp.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.sf.openapi.waybill.print.constant.TemplateTypeEnum;
import com.sf.openapi.waybill.print.ds.SignConfirmForA4DS2;
import com.sf.openapi.waybill.print.ds.WaybillPrintDS;
import com.sf.openapi.waybill.print.dto.SignConfirmDto;
import com.sf.openapi.waybill.print.dto.SignConfirmDto2;
import com.sf.openapi.waybill.print.dto.WaybillDto;
import com.sf.openapi.waybill.print.dto.WaybillPrintDto;
import com.sf.openapi.waybill.print.req.SignConfirmPrintRequest;
import com.sf.openapi.waybill.print.req.WaybillPrintRequest;
import com.sf.openapi.waybill.print.runner.PrintServerRunner;
import com.sf.openapi.waybill.print.service.SFPrintService;
import com.sf.openapi.waybill.print.util.Base64Utils;
import com.sf.openapi.waybill.print.util.WaybillUtils;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2018年3月6日 上午11:42:14
 */
@Service
@Slf4j
public class SFPrintServiceImpl implements SFPrintService {

	@Autowired
	private PrintServerRunner runner;
	
	@Override
	public ResponseResult<?> printWaybill(WaybillPrintRequest waybillReq) {
		ResponseResult<?> result = null;
		try {
			String templateType = waybillReq.getType();
			log.info("templateType:"+templateType);
			String output = waybillReq.getOutput();
			boolean isOpenPrinterFlag = output.equals("noAlertPrint") ? false : true;
			List<WaybillDto> waybillDtoList = waybillReq.getWaybillList();
			for(WaybillDto bill:waybillDtoList) {
				//设置LOGO,可修改成自己公司的LOGO
				bill.setLogo(runner.getLogo_95538_abs_path());
				bill.setSftelLogo(runner.getLogo_sf_abs_path());
				//设置目的地编码
				if(StringUtils.hasText(bill.getConsignerCity())) {
					bill.setDestCode(runner.getAreaCode(bill.getConsignerCity()));
				}
			}
			List<WaybillPrintDto> waybillPrintDtoList = WaybillUtils.initWaybillPrintDtoList(waybillDtoList);
			WaybillPrintDS waybillPrintDS = new WaybillPrintDS();
			waybillPrintDS.setWayBillList(waybillPrintDtoList);
			InputStream templateFile = runner.getByTemplateType(templateType);// 根据类型加载模板文件
			log.info("templateFile:"+templateFile);
			JasperReport jsperReport = (JasperReport) JRLoader.loadObject(templateFile);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jsperReport, new HashMap<String, Object>(),waybillPrintDS);
			if ("print".equals(output)) {
				result = getPrintResultResponse(JasperPrintManager.printReport(jasperPrint, isOpenPrinterFlag), null);
			} else if ("noAlertPrint".equals(output)) {
				log.info("noAlertPrint:");
				result = getPrintResultResponse(JasperPrintManager.printReport(jasperPrint, isOpenPrinterFlag), null);
			} else if ("image".equals(output)) {
				int pageSize = jasperPrint.getPages().size();
				List<String> imageList = new ArrayList<String>();
				for (int index = 0; index < pageSize; index++) {
					Image image = JasperPrintManager.printPageToImage(jasperPrint, index, 4.0F);
					String imageStr = getPrintImageResponse(toBufferedImage(image));
					if (StringUtils.hasText(imageStr)) {
						imageList.add(imageStr);
					}
				}
				result = ResponseResult.buildSuccessResponse(imageList);
			} else {
				result = getPrintResultResponse(false, "非法output参数");
			}
		} catch (Exception e) {
			result = getPrintResultResponse(false, e.getMessage());
		}
		log.info("print result:"+result);
		return result;
	}

	private ResponseResult<String> getPrintResultResponse(boolean isSuccess, String errmsg) {
		if (isSuccess) {
			return ResponseResult.buildSuccessResponse("电子运单打印成功!");
		} else {
			errmsg = StringUtils.hasText(errmsg) ? errmsg : "电子运单打印失败!";
			return ResponseResult.build(5000, errmsg, null);
		}
	}

	private String getPrintImageResponse(BufferedImage image) throws IOException {
		if (image != null) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(image, "png", out);
			byte[] imageByte = out.toByteArray();
			return Base64Utils.encode(imageByte);
		}
		return "";
	}

	private BufferedImage toBufferedImage(Image image) {
		if ((image instanceof BufferedImage)) {
			return (BufferedImage) image;
		}
		image = new ImageIcon(image).getImage();
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			int transparency = 1;

			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
		} catch (HeadlessException localHeadlessException) {
		}
		if (bimage == null) {
			int type = 12;

			bimage = new BufferedImage(image.getWidth(null) * 4, image.getHeight(null) * 4, type);
		}

		Graphics g = bimage.createGraphics();

		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bimage;
	}

	
	@Override
	public ResponseResult<byte[]> exportSignConfirm(SignConfirmPrintRequest signConfirmPrintReq) {
		StringBuilder content = new StringBuilder();
		SignConfirmDto signConfirmDto = signConfirmPrintReq.getList().get(0);
		content.append("本人：").append(signConfirmDto.getCName()).append("，");
		content.append("身份证号：").append(signConfirmDto.getIdCard()).append("，");
		content.append("在深圳市国智互联网科技有限公司旗下“爱租机”平台租赁一台").append(signConfirmDto.getModelName()).append("，");
		content.append("合同编号：").append(signConfirmDto.getOrderNo()).append("，");
		content.append("本人确认合同有效性并已签收全部协议中约定的设备(设备SN/IMEI号为").append(signConfirmDto.getMobileMark()).append(")。");
		SignConfirmDto2  signConfirmDto2 = new SignConfirmDto2();
		signConfirmDto2.setContent(content.toString());
		ResponseResult<byte[]> result = null;
		try {
			InputStream templateFile = runner.getByTemplateType(TemplateTypeEnum.TEMPLATE_SIGN_CONFIRM.getType());
			JasperReport jsperReport = (JasperReport) JRLoader.loadObject(templateFile);
			SignConfirmForA4DS2 ds = new SignConfirmForA4DS2(Lists.newArrayList(signConfirmDto2));
			Map<String, Object> parameters = new HashMap<String,Object>();
			JasperPrint jasperPrint = JasperFillManager.fillReport(jsperReport, parameters,ds);
			JRPdfExporter exporter=new JRPdfExporter();  
			ByteArrayOutputStream outPut=new ByteArrayOutputStream();  
			//创建jasperPrint  
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);  
            //生成输出流  
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outPut);  
            //屏蔽copy功能  
            exporter.setParameter(JRPdfExporterParameter.IS_ENCRYPTED,Boolean.FALSE);  
            //加密  
            exporter.setParameter(JRPdfExporterParameter.IS_128_BIT_KEY,Boolean.FALSE);  
            exporter.exportReport();  
            result = ResponseResult.buildSuccessResponse(outPut.toByteArray());
		}catch(Exception e) {
			log.error("导出签收确认单失败:{}",e.getLocalizedMessage());
			result = ResponseResult.build(1000, "导出签收确认单失败", null);
		}
		return result;
	}
}
