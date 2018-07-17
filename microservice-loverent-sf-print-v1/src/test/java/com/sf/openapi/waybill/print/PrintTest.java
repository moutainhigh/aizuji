/**
 * 
 */
package com.sf.openapi.waybill.print;

import java.io.File;
import java.io.FileOutputStream;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.standard.PrinterName;

import org.gz.common.resp.ResponseResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.sf.openapi.waybill.print.constant.TemplateTypeEnum;
import com.sf.openapi.waybill.print.dto.SignConfirmDto;
import com.sf.openapi.waybill.print.req.SignConfirmPrintRequest;
import com.sf.openapi.waybill.print.req.WaybillPrintRequest;
import com.sf.openapi.waybill.print.req.WaybillPrintRequestFactory;
import com.sf.openapi.waybill.print.service.SFPrintService;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2018年3月1日 上午10:12:27
 */
public class PrintTest extends BaseTest {

	@Autowired
	private SFPrintService printService;

	@Test
	public void testGetLookupPrintServices() {
		PrintService[] printSerivces = PrintServiceLookup.lookupPrintServices(null, null);
		System.err.println("printSerivces:" + printSerivces.length);
		for (PrintService printService : printSerivces) {
			System.err.println(printService.getName());
		}
		System.err.println("defaultPrintServiceName:" + PrintServiceLookup.lookupDefaultPrintService().getName());
	}

	/**
	 * 检查是否存在指定的打印服务 @Description: @param @return void @throws
	 */
	@Test
	public void test() {

		HashAttributeSet hs = new HashAttributeSet();

		String printerName = "ZDesigner GK888t";

		hs.add(new PrinterName(printerName, null));

		PrintService[] pss = PrintServiceLookup.lookupPrintServices(null, hs);

		if (pss.length == 0) {
			System.out.println("无法找到打印机:" + printerName);
		} else {
			System.err.println("找到打印机:" + pss[0].getName());
		}
	}

	@Test
	public void testPrint() {
		WaybillPrintRequest waybillReq = WaybillPrintRequestFactory.create("SO1801260000000331", "SO1801260000000331",
				null, "7550034925", "胡小军", "广东省", "深圳市", "宝安区", "石岩镇应人石新村12巷2栋", "", "18682017314", "",
				"1801260000000331");
		this.printService.printWaybill(waybillReq);
	}

	

	@Test
	public void testExportSignConfirm() throws Exception {
		SignConfirmPrintRequest signConfirmPrintReq = new SignConfirmPrintRequest();
		SignConfirmDto dto = new SignConfirmDto();
		dto.setCName("胡小军");
		dto.setIdCard("511081198201273335");
		dto.setMobileMark("FFMR8BSZG5MN/35440106303814");
		dto.setModelName("iphone 7 128G 玫瑰金");
		dto.setOrderNo("SBN20180116132049");
		signConfirmPrintReq.setTemplateType(TemplateTypeEnum.TEMPLATE_SIGN_CONFIRM.getType());
		signConfirmPrintReq.setList(Lists.newArrayList(dto));
		ResponseResult<byte[]> result = this.printService.exportSignConfirm(signConfirmPrintReq);
		if (result.isSuccess()) {
			File file = new File("c:/pic/report.pdf");
			FileOutputStream outputStream = null;
			try {
				outputStream = new FileOutputStream(file);
				outputStream.write(result.getData());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (outputStream != null) {
					outputStream.close();
				}
			}

		}

	}
	
	@Test
	public void testExportSignConfirm2() throws Exception {
		SignConfirmPrintRequest signConfirmPrintReq = new SignConfirmPrintRequest();
		SignConfirmDto dto = new SignConfirmDto();
		dto.setCName("胡小军");
		dto.setIdCard("511081198201273335");
		dto.setMobileMark("FFMR8BSZG5MN/35440106303814");
		dto.setModelName("iphone 7 128G 玫瑰金");
		dto.setOrderNo("SBN20180116132049");
		signConfirmPrintReq.setTemplateType(TemplateTypeEnum.TEMPLATE_SIGN_CONFIRM.getType());
		signConfirmPrintReq.setList(Lists.newArrayList(dto));
		ResponseResult<byte[]> result = this.printService.exportSignConfirm(signConfirmPrintReq);
		if (result.isSuccess()) {
			File file = new File("c:/pic/report.pdf");
			FileOutputStream outputStream = null;
			try {
				outputStream = new FileOutputStream(file);
				outputStream.write(result.getData());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (outputStream != null) {
					outputStream.close();
				}
			}
		}

	}
	
}
