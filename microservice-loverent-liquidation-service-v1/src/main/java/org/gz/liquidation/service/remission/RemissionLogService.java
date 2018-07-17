package org.gz.liquidation.service.remission;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.LateFeeRemissionReq;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.common.dto.RemissionLogResp;
import org.gz.liquidation.entity.RemissionLog;
/**
 * 
 * @Description:	减免记录接口
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年2月7日 	Created
 */
public interface RemissionLogService {
	/**
	 * 
	 * @Description:  新增
	 * @param remissionLog
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月22日
	 */
	void insertSelective(RemissionLog remissionLog);
	/**
	 * 
	 * @Description: 查询减免记录列表
	 * @param queryDto
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月22日
	 */
	ResultPager<RemissionLogResp> selectPage(QueryDto queryDto);
	/**
	 * 
	 * @Description: 滞纳金减免
	 * @param lateFeeRemissionReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月5日
	 */
	ResponseResult<String> doLateFeeRemission(LateFeeRemissionReq lateFeeRemissionReq);
	
}
