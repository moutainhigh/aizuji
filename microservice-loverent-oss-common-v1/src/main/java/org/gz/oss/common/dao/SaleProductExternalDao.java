/**
 * 
 */
package org.gz.oss.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.oss.common.dto.SaleProductCountReq;
import org.gz.oss.common.dto.SaleProductCountResp;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2018年4月2日 上午11:37:08
 */
@Mapper
public interface SaleProductExternalDao {

	/** 
	* @Description: 
	* @param @param req
	* @param @return
	* @return List<SaleProductCountResp>
	* @throws 
	*/
	List<SaleProductCountResp> querySaleProductCountList(SaleProductCountReq req);

}
