/**
 * 
 */
package com.sf.openapi.waybill.print.ds;

import java.util.List;

import com.sf.openapi.waybill.print.dto.SignConfirmDto2;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class SignConfirmForA4DS2 implements JRDataSource {

	private List<SignConfirmDto2> list;

	private int index = -1;

	public SignConfirmForA4DS2(List<SignConfirmDto2> list) {
		this.list = list;
	}

	@Override
	public boolean next() throws JRException {
		this.index += 1;
		return this.index < this.list.size();
	}

	@Override
	public Object getFieldValue(JRField jrField) throws JRException {
		SignConfirmDto2 waybill = (SignConfirmDto2) this.list.get(this.index);
		String fieldName = jrField.getName();
		Object rs = null;
		if ("content".equals(fieldName)) {
			rs = waybill.getContent();
		}
		return rs;
	}

}
