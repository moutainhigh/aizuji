/**
 * 
 */
package com.sf.openapi.waybill.print.ds;

import java.util.List;

import com.sf.openapi.waybill.print.dto.SignConfirmDto;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2018年3月6日 下午6:20:06
 */
public class SignConfirmForA4DS implements JRDataSource {

	private List<SignConfirmDto> list;

	private int index = -1;

	public SignConfirmForA4DS(List<SignConfirmDto> list) {
		this.list = list;
	}

	@Override
	public boolean next() throws JRException {
		this.index += 1;
		return this.index < this.list.size();
	}

	@Override
	public Object getFieldValue(JRField jrField) throws JRException {
		SignConfirmDto waybill = (SignConfirmDto) this.list.get(this.index);
		String fieldName = jrField.getName();
		Object rs = null;
		if ("cName".equals(fieldName))
			rs = waybill.getCName();
		else if ("idCard".equals(fieldName))
			rs = waybill.getIdCard();
		else if ("orderNo".equals(fieldName))
			rs = waybill.getOrderNo();
		else if ("modelName".equals(fieldName))
			rs = waybill.getModelName();
		else if ("mobileMark".equals(fieldName))
			rs = waybill.getMobileMark();
		return rs;
	}

}
