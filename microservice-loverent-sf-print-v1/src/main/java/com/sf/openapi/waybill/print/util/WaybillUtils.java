package com.sf.openapi.waybill.print.util;

import com.sf.openapi.waybill.print.dto.CargoInfoDto;
import com.sf.openapi.waybill.print.dto.WaybillDto;
import com.sf.openapi.waybill.print.dto.WaybillPrintDto;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.StringUtils;

public final class WaybillUtils {
	private WaybillUtils() {
	}

	private static String formatWaybillNo(String bno) {
		String formatBno = "";
		if ((bno != null) && (!"".equals(bno))) {
			int block = bno.length() / 4;
			formatBno = formatBno + bno.substring(0, block) + "  ";
			formatBno = formatBno + bno.substring(block, 2 * block) + "  ";
			formatBno = formatBno + bno.substring(2 * block, 3 * block) + "  ";
			formatBno = formatBno + bno.substring(3 * block);
		}
		return formatBno;
	}

	private static boolean isEmpty(String str) {
		return (str == null) || ("".equals(str));
	}

	private static String getCargoInfoStr(List<CargoInfoDto> cargoInfoDtoList) {
		if ((cargoInfoDtoList != null) && (!cargoInfoDtoList.isEmpty())) {
			String cargoStr = "";
			for (CargoInfoDto dto : cargoInfoDtoList) {
				cargoStr = cargoStr + dto.getCargo() + "\r\n";
			}
			return cargoStr;
		}
		return "";
	}

	private static String getCargoCountStr(List<CargoInfoDto> cargoInfoDtoList) {
		if ((cargoInfoDtoList != null) && (!cargoInfoDtoList.isEmpty())) {
			String cargoStr = "";
			for (CargoInfoDto dto : cargoInfoDtoList) {
				cargoStr = cargoStr + dto.getCargoCount() + (isEmpty(dto.getCargoUnit()) ? "" : dto.getCargoUnit())
						+ "\r\n";
			}
			return cargoStr;
		}
		return "";
	}

	public static List<WaybillPrintDto> initWaybillPrintDtoList(List<WaybillDto> waybillDtoList) {
		List<WaybillPrintDto> waybillPrintDtoList = null;
		if ((waybillDtoList != null) && (!waybillDtoList.isEmpty())) {
			waybillPrintDtoList = new ArrayList<WaybillPrintDto>();
			for (WaybillDto waybillDto : waybillDtoList) {
				initWaybillPrintDto(waybillDto, waybillPrintDtoList);
			}
		}
		return waybillPrintDtoList;
	}

	private static void initWaybillPrintDto(WaybillDto waybillDto, List<WaybillPrintDto> waybillPrintDtoList) {
		if (waybillDto != null) {
			String mailNo = waybillDto.getMailNo();
			if (!isEmpty(mailNo)) {
				initMainWaybill(waybillDto, waybillPrintDtoList);

				String[] bnos = mailNo.split(",");
				if (bnos.length > 1) {
					for (int i = 1; i < bnos.length; i++) {
						String childBno = bnos[i];
						int childNum = i + 1;
						initChildWaybill(waybillDto, waybillPrintDtoList, childBno, childNum);
					}

				}

				String returnTrackingNo = waybillDto.getReturnTrackingNo();
				if (!isEmpty(returnTrackingNo))
					initReturnTrackingWaybill(waybillDto, waybillPrintDtoList);
			}
		}
	}

	private static void initReturnTrackingWaybill(WaybillDto waybillDto, List<WaybillPrintDto> waybillPrintDtoList) {
		WaybillPrintDto waybillPrintDto = new WaybillPrintDto();
		waybillPrintDto.setAddedService("POD");
		String cargo = "签回单";
		String cargoCount = "1";
		if(CollectionUtils.isNotEmpty(waybillDto.getCargoInfoDtoList())) {
			cargoCount = waybillDto.getCargoInfoDtoList().size()+"";
			if(StringUtils.hasText(waybillDto.getCargoInfoDtoList().get(0).getCargo())) {
				cargo = waybillDto.getCargoInfoDtoList().get(0).getCargo();
			}
			if(StringUtils.hasText(waybillDto.getCargoInfoDtoList().get(0).getCargoUnit())) {
				waybillPrintDto.setCargoUnit(waybillDto.getCargoInfoDtoList().get(0).getCargoUnit());
			}
		}
		waybillPrintDto.setCargo(cargo);
		waybillPrintDto.setCargoCount(cargoCount);
		waybillPrintDto.setDeliverAddress(waybillDto.getConsignerAddress());
		waybillPrintDto.setDeliverCity(waybillDto.getConsignerCity());
		waybillPrintDto.setDeliverCompany(waybillDto.getConsignerCompany());
		waybillPrintDto.setDeliverCounty(waybillDto.getConsignerCounty());
		waybillPrintDto.setDeliverMobile(waybillDto.getConsignerMobile());
		waybillPrintDto.setDeliverName(waybillDto.getConsignerName());
		waybillPrintDto.setDeliverProvince(waybillDto.getConsignerProvince());
		waybillPrintDto.setDeliverShipperCode(waybillDto.getConsignerShipperCode());
		waybillPrintDto.setDeliverTel(waybillDto.getConsignerTel());
		waybillPrintDto.setConsignerAddress(waybillDto.getDeliverAddress());
		waybillPrintDto.setConsignerCity(waybillDto.getDeliverCity());
		waybillPrintDto.setConsignerCompany(waybillDto.getDeliverCompany());
		waybillPrintDto.setConsignerCounty(waybillDto.getDeliverCounty());
		waybillPrintDto.setConsignerMobile(waybillDto.getDeliverMobile());
		waybillPrintDto.setConsignerName(waybillDto.getDeliverName());
		waybillPrintDto.setConsignerProvince(waybillDto.getDeliverProvince());
		waybillPrintDto.setConsignerShipperCode(waybillDto.getDeliverShipperCode());
		waybillPrintDto.setConsignerTel(waybillDto.getDeliverTel());
		waybillPrintDto.setDestCode(waybillDto.getZipCode());
		waybillPrintDto.setZipCode(waybillDto.getDestCode());
		waybillPrintDto.setExpressType(99);
		waybillPrintDto.setOrderNo(waybillDto.getOrderNo());
		waybillPrintDto.setPayMethod(3);
		waybillPrintDto.setPiece(1);
		String mailNo = waybillDto.getReturnTrackingNo();
		waybillPrintDto.setMailNo(mailNo);
		waybillPrintDto.setMailNoStr(formatWaybillNo(mailNo));
		waybillPrintDto.setLogo(waybillDto.getLogo());
		waybillPrintDto.setSftelLogo(waybillDto.getSftelLogo());
		waybillPrintDto.setMonthAccount(waybillDto.getMonthAccount());
		waybillPrintDto.setInsureValue(waybillDto.getInsureValue());
		waybillPrintDtoList.add(waybillPrintDto);
	}

	private static void initChildWaybill(WaybillDto waybillDto, List<WaybillPrintDto> waybillPrintDtoList,
			String childBno, int childNum) {
		WaybillPrintDto waybillPrintDto = initCommonWaybillPrintDto(waybillDto);
		waybillPrintDto.setChildMailNo(childBno);
		waybillPrintDto.setChildMailNoStr(formatWaybillNo(childBno));
		waybillPrintDto.setChildNum(childNum);
		waybillPrintDtoList.add(waybillPrintDto);
	}

	private static void initMainWaybill(WaybillDto waybillDto, List<WaybillPrintDto> waybillPrintDtoList) {
		WaybillPrintDto waybillPrintDto = initCommonWaybillPrintDto(waybillDto);
		waybillPrintDtoList.add(waybillPrintDto);
	}

	private static WaybillPrintDto initCommonWaybillPrintDto(WaybillDto waybillDto) {
		WaybillPrintDto waybillPrintDto = new WaybillPrintDto();
		waybillPrintDto.setAddedService(isEmpty(waybillDto.getCodValue()) ? "" : "COD");
		waybillPrintDto.setCargo(getCargoInfoStr(waybillDto.getCargoInfoDtoList()));
		waybillPrintDto.setCargoCount(getCargoCountStr(waybillDto.getCargoInfoDtoList()));
		waybillPrintDto.setCodValue(waybillDto.getCodValue());
		waybillPrintDto.setConsignerAddress(waybillDto.getConsignerAddress());
		waybillPrintDto.setConsignerCity(waybillDto.getConsignerCity());
		waybillPrintDto.setConsignerCompany(waybillDto.getConsignerCompany());
		waybillPrintDto.setConsignerCounty(waybillDto.getConsignerCounty());
		waybillPrintDto.setConsignerMobile(waybillDto.getConsignerMobile());
		waybillPrintDto.setConsignerName(waybillDto.getConsignerName());
		waybillPrintDto.setConsignerProvince(waybillDto.getConsignerProvince());
		waybillPrintDto.setConsignerShipperCode(waybillDto.getConsignerShipperCode());
		waybillPrintDto.setConsignerTel(waybillDto.getConsignerTel());
		waybillPrintDto.setDeliverAddress(waybillDto.getDeliverAddress());
		waybillPrintDto.setDeliverCity(waybillDto.getDeliverCity());
		waybillPrintDto.setDeliverCompany(waybillDto.getDeliverCompany());
		waybillPrintDto.setDeliverCounty(waybillDto.getDeliverCounty());
		waybillPrintDto.setDeliverMobile(waybillDto.getDeliverMobile());
		waybillPrintDto.setDeliverName(waybillDto.getDeliverName());
		waybillPrintDto.setDeliverProvince(waybillDto.getDeliverProvince());
		waybillPrintDto.setDeliverShipperCode(waybillDto.getDeliverShipperCode());
		waybillPrintDto.setDeliverTel(waybillDto.getDeliverTel());
		waybillPrintDto.setDestCode(waybillDto.getDestCode());
		waybillPrintDto.setZipCode(waybillDto.getZipCode());
		waybillPrintDto.setElectric(waybillDto.getExpressType() == 3 ? "E" : "D");
		waybillPrintDto.setExpressType(waybillDto.getExpressType());
		waybillPrintDto.setInsureValue(waybillDto.getInsureValue());
		waybillPrintDto.setMonthAccount(waybillDto.getMonthAccount());
		waybillPrintDto.setOrderNo(waybillDto.getOrderNo());
		waybillPrintDto.setPayArea(waybillDto.getPayMethod() == 3 ? waybillDto.getPayArea() : "");
		waybillPrintDto.setPayMethod(waybillDto.getPayMethod());
		waybillPrintDto.setPiece(waybillDto.getMailNo().split(",").length);
		waybillPrintDto.setRemark(getCargoRemarkStr(waybillDto.getCargoInfoDtoList()));
		waybillPrintDto.setSku(getCargoSkuStr(waybillDto.getCargoInfoDtoList()));
		String mailNo = waybillDto.getMailNo().split(",")[0];
		waybillPrintDto.setMailNo(mailNo);
		waybillPrintDto.setMailNoStr(formatWaybillNo(mailNo));
		waybillPrintDto.setLogo(waybillDto.getLogo());
		waybillPrintDto.setSftelLogo(waybillDto.getSftelLogo());

		return waybillPrintDto;
	}

	private static String getCargoSkuStr(List<CargoInfoDto> cargoInfoDtoList) {
		if ((cargoInfoDtoList != null) && (!cargoInfoDtoList.isEmpty())) {
			String cargoSkuStr = "";
			for (CargoInfoDto dto : cargoInfoDtoList) {
				cargoSkuStr = cargoSkuStr + dto.getSku() + "\r\n";
			}
			return cargoSkuStr;
		}
		return "";
	}

	private static String getCargoRemarkStr(List<CargoInfoDto> cargoInfoDtoList) {
		if ((cargoInfoDtoList != null) && (!cargoInfoDtoList.isEmpty())) {
			String cargoRemarkStr = "";
			for (CargoInfoDto dto : cargoInfoDtoList) {
				cargoRemarkStr = cargoRemarkStr + dto.getRemark() + "\r\n";
			}
			return cargoRemarkStr;
		}
		return "";
	}
}