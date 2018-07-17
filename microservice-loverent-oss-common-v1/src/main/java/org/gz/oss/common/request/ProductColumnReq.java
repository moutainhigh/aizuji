package org.gz.oss.common.request;

import java.io.Serializable;
import java.util.List;

import org.gz.oss.common.entity.ProductColumn;

public class ProductColumnReq implements Serializable{

	private static final long serialVersionUID = 217738335029602235L;
	
	private List<ProductColumn> columnReqList;

	public List<ProductColumn> getColumnReqList() {
		return columnReqList;
	}

	public void setColumnReqList(List<ProductColumn> columnReqList) {
		this.columnReqList = columnReqList;
	}
	
}
