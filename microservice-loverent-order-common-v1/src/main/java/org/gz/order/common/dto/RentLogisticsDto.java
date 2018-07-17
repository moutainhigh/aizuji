package org.gz.order.common.dto;

import java.io.Serializable;
import java.util.List;

import org.gz.order.common.entity.RentLogistics;

/**
 * RentLogistics 实体类
 * 
 * @author pk
 * @date 2018-03-08
 */
public class RentLogisticsDto extends RentLogistics implements Serializable{


  /**
  * 
  */
  private static final long serialVersionUID = -5913394826257443241L;
  /**
   * 物流节点
   */
	private List<LogisticsNodes> logisticsNodes;
	public List<LogisticsNodes> getLogisticsNodes() {
		return logisticsNodes;
	}
	public void setLogisticsNodes(List<LogisticsNodes> logisticsNodes) {
		this.logisticsNodes = logisticsNodes;
	}
	
	
}
