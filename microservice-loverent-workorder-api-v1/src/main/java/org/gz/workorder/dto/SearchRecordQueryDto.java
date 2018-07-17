package org.gz.workorder.dto;

import java.util.List;

import org.gz.common.entity.QueryPager;
import org.gz.workorder.entity.SearchRecord;

/**
 * SearchRecordQueryDto  Dto 对象
 * 
 * @author phd
 * @date 2018-01-17
 */
public class SearchRecordQueryDto extends QueryPager{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long totalNum;
	private List<SearchRecord> data;
	private String operator;
	public Long getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}
	public List<SearchRecord> getData() {
		return data;
	}
	public void setData(List<SearchRecord> data) {
		this.data = data;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	

}

