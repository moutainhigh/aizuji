package org.gz.order.common.dto;

import java.io.Serializable;
import java.util.List;

import org.gz.common.entity.QueryPager;
import org.gz.order.common.entity.RentState;

/**
 * RentState 实体类
 * 
 * @author pk
 * @date 2017-12-13
 */
public class RentStateDtoPage extends QueryPager implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2691557538416146238L;

    /**总记录数*/
    private Long              totalNum;
  	
  	private List<RentState> data;

  	public Long getTotalNum() {
  		return totalNum;
  	}

  	public void setTotalNum(Long totalNum) {
  		this.totalNum = totalNum;
  	}

	public List<RentState> getData() {
		return data;
	}

	public void setData(List<RentState> data) {
		this.data = data;
	}

  	
}