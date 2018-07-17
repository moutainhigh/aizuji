package org.gz.liquidation.service.refund;

import org.gz.common.entity.ResultPager;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.common.dto.RefundLogResp;
import org.gz.liquidation.entity.RefundLog;
/**
 * 
 * @Description:TODO	退款服务接口
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年2月7日 	Created
 */
public interface RefundLogService {
	/**
	 * 
	 * @Description: TODO 新增退款记录
	 * @param refundLog
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月7日
	 */
	void insertSelective(RefundLog refundLog);
	/**
	 * 
	 * @Description: TODO 分页查询退款记录
	 * @param queryDto
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月7日
	 */
	ResultPager<RefundLogResp> selectPage(QueryDto queryDto);
	/**
	 * 
	 * @Description: 获取退款截图
	 * @param id 主键id
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月20日
	 */
	String getImgUrl(Long id);
}
