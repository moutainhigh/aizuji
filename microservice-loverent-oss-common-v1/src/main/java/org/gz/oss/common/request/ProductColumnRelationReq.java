package org.gz.oss.common.request;

import java.io.Serializable;
import java.util.List;

import org.gz.oss.common.entity.ProductColumnRelation;

public class ProductColumnRelationReq implements Serializable{
	
	private static final long serialVersionUID = -3290822174915558255L;
	
	private List<ProductColumnRelation> relationReqList;
	
	private Integer delColumnId;

	public Integer getDelColumnId() {
		return delColumnId;
	}

	public void setDelColumnId(Integer delColumnId) {
		this.delColumnId = delColumnId;
	}

	public List<ProductColumnRelation> getRelationReqList() {
		return relationReqList;
	}

	public void setRelationReqList(List<ProductColumnRelation> relationReqList) {
		this.relationReqList = relationReqList;
	}
	
}
