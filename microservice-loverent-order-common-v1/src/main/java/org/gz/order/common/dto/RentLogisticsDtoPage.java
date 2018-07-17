package org.gz.order.common.dto;

import java.io.Serializable;
import java.util.List;

import org.gz.common.entity.QueryPager;

/**
 * RentLogistics 实体类
 * 
 * @author pk
 * @date 2018-03-08
 */
public class RentLogisticsDtoPage extends QueryPager implements Serializable{


  /**
  * 
  */
  private static final long serialVersionUID = -5913394826257443241L;

  /**总记录数*/
  private Long              totalNum;
	
	private List<RentLogisticsDto> data;

	public Long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}

	public List<RentLogisticsDto> getData() {
		return data;
	}

	public void setData(List<RentLogisticsDto> data) {
		this.data = data;
	}
	
	
	
}
