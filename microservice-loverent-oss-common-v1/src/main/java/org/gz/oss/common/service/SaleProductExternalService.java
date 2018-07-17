/**
 * 
 */
package org.gz.oss.common.service;

import java.util.List;

import org.gz.oss.common.dto.SaleProductCountReq;
import org.gz.oss.common.dto.SaleProductCountResp;

/**
 * @Title: 售卖产品外部服务
 * @author hxj
 * @Description: 
 * @date 2018年4月2日 上午11:07:50
 */
public interface SaleProductExternalService {

	/** 
	* @Description: 根据产品ID,物料ID查询售卖商品列表
	* @param  req
	*/
	List<SaleProductCountResp> querySaleProductCountList(SaleProductCountReq req);

}
