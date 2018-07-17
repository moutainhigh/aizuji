/**
 * 
 */
package com.sf.openapi.waybill.print.req;

import java.util.ArrayList;
import java.util.List;

import org.gz.common.utils.AssertUtils;

import com.sf.openapi.waybill.print.constant.AppKeyConstants;
import com.sf.openapi.waybill.print.constant.OutputTypeEnum;
import com.sf.openapi.waybill.print.constant.TemplateTypeEnum;
import com.sf.openapi.waybill.print.dto.CargoInfoDto;
import com.sf.openapi.waybill.print.dto.WaybillDto;

public class WaybillPrintRequestFactory {

	public static WaybillPrintRequest create(String mailNo, String orderNo, String destCode, String monthAccount,
			String consignerName, String consignerProvince, String consignerCity, String consignerCounty,
			String consignerAddress, String consignerCompany, String consignerMobile, String consignerTel,
			String returnTrackingNo) {
		WaybillPrintRequest waybillReq = new WaybillPrintRequest();
		waybillReq.setOutput(OutputTypeEnum.NO_ARERT_PRINT.getType());
		waybillReq.setType(TemplateTypeEnum.TEMPLATE_100MM_210MM.getType());
		WaybillDto waybill = new WaybillDto();
		waybill.setAppId(AppKeyConstants.APP_ID);
		waybill.setAppKey(AppKeyConstants.APP_KEY);
		waybill.setMailNo(mailNo);
		waybill.setOrderNo(orderNo);
		waybill.setExpressType(2);
		waybill.setMonthAccount(monthAccount);
		waybill.setPayArea("755G");
		waybill.setPayMethod(3);
		waybill.setZipCode("755");
		waybill.setDestCode(destCode);
		waybill.setReturnTrackingNo(returnTrackingNo);
		// 设置发件人信息
		waybill.setDeliverProvince("广东省");
		waybill.setDeliverCity("深圳市");
		waybill.setDeliverCounty("南山区");
		waybill.setDeliverAddress("高新南一道中科大厦18B");
		waybill.setDeliverCompany("深圳市国智互联网科技互联网科技有限公司");
		waybill.setDeliverName("国智互联");
		waybill.setDeliverTel("0755-86568135");
		// 设置收件人信息
		waybill.setConsignerProvince(consignerProvince);
		waybill.setConsignerCity(consignerCity);
		waybill.setConsignerCounty(consignerCounty);
		waybill.setConsignerAddress(consignerAddress);
		waybill.setConsignerCompany(consignerCompany);
		waybill.setConsignerMobile(consignerMobile);
		waybill.setConsignerTel(consignerTel);
		waybill.setConsignerName(consignerName);
		List<WaybillDto> waybillList = new ArrayList<WaybillDto>();
		waybillList.add(waybill);
		waybillReq.setWaybillList(waybillList);
		return waybillReq;
	}

	public static WaybillPrintRequest create(String mailNo, String orderNo, String destCode, String monthAccount,
			String consignerName, String consignerProvince, String consignerCity, String consignerCounty,
			String consignerAddress, String consignerCompany, String consignerMobile, String consignerTel,
			String returnTrackingNo, String cargo,Integer cargoCount,String cargoUnit,String insureValue) {
		WaybillPrintRequest waybillReq = new WaybillPrintRequest();
		waybillReq.setOutput(OutputTypeEnum.NO_ARERT_PRINT.getType());
		waybillReq.setType(TemplateTypeEnum.TEMPLATE_100MM_210MM.getType());
		WaybillDto waybill = new WaybillDto();
		waybill.setAppId(AppKeyConstants.APP_ID);
		waybill.setAppKey(AppKeyConstants.APP_KEY);
		waybill.setMailNo(mailNo);
		waybill.setOrderNo(orderNo);
		waybill.setExpressType(2);
		waybill.setMonthAccount(monthAccount);
		waybill.setPayArea("755G");
		waybill.setPayMethod(3);
		waybill.setZipCode("755");
		waybill.setDestCode(destCode);
		waybill.setReturnTrackingNo(returnTrackingNo);
		waybill.setInsureValue(insureValue);
		// 设置发件人信息
		waybill.setDeliverProvince("广东省");
		waybill.setDeliverCity("深圳市");
		waybill.setDeliverCounty("南山区");
		waybill.setDeliverAddress("高新南一道中科大厦18B");
		waybill.setDeliverCompany("深圳市国智互联网科技互联网科技有限公司");
		waybill.setDeliverName("国智互联");
		waybill.setDeliverTel("0755-86568135");
		// 设置收件人信息
		waybill.setConsignerProvince(consignerProvince);
		waybill.setConsignerCity(consignerCity);
		waybill.setConsignerCounty(consignerCounty);
		waybill.setConsignerAddress(consignerAddress);
		waybill.setConsignerCompany(consignerCompany);
		waybill.setConsignerMobile(consignerMobile);
		waybill.setConsignerTel(consignerTel);
		waybill.setConsignerName(consignerName);
		List<CargoInfoDto> cargoInfoDtoList = new ArrayList<CargoInfoDto>();
		CargoInfoDto cargoDto = new CargoInfoDto();
		cargoDto.setCargo(cargo);
		if(AssertUtils.isPositiveNumber4Int(cargoCount)) {
			cargoDto.setCargoCount(cargoCount);
		}
		cargoDto.setCargoUnit(cargoUnit);
		cargoInfoDtoList.add(cargoDto);
		waybill.setCargoInfoDtoList(cargoInfoDtoList);
		List<WaybillDto> waybillList = new ArrayList<WaybillDto>();
		waybillList.add(waybill);
		waybillReq.setWaybillList(waybillList);
		return waybillReq;
	}
}
