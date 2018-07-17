/**
 * 
 */
package org.gz.warehouse.service.warehouse.impl;

import java.util.ArrayList;
import java.util.List;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.common.vo.WarehouseCommodityReq;
import org.gz.warehouse.common.vo.WarehouseCommodityResp;
import org.gz.warehouse.mapper.warehouse.WarehouseCommodityInfoMapper;
import org.gz.warehouse.service.warehouse.WarehouseCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2018年3月14日 下午4:17:06
 */
@Service
public class WarehouseCommodityServiceImpl implements WarehouseCommodityService {

	@Autowired
	private WarehouseCommodityInfoMapper commodityInfoMapper;
	
	@Override
	public ResponseResult<ResultPager<WarehouseCommodityResp>> queryWarehouseLocationCommoditys(WarehouseCommodityReq q) {
		int totalNum =  this.commodityInfoMapper.queryWarehouseLocationCommodityCount(q);
		List<WarehouseCommodityResp> list = new ArrayList<WarehouseCommodityResp>(0);
		if(totalNum>0) {
			list = this.commodityInfoMapper.queryWarehouseLocationCommodityList(q);
		}
		return ResponseResult.buildSuccessResponse(new ResultPager<WarehouseCommodityResp>(totalNum,q.getCurrPage(),q.getPageSize(),list)); 
	}

}
