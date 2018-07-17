/**
 * 
 */
package org.gz.warehouse.service.warehouse.impl;

import java.util.ArrayList;
import java.util.List;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityTrackQuery;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityTrackVO;
import org.gz.warehouse.mapper.warehouse.WarehouseCommodityTrackMapper;
import org.gz.warehouse.service.warehouse.WarehouseCommodityTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2018年1月2日 下午3:00:31
 */
@Service
public class WarehouseCommodityTrackServiceImpl implements WarehouseCommodityTrackService {


	@Autowired
	private WarehouseCommodityTrackMapper trackMapper;
	
	@Override
	public ResponseResult<ResultPager<WarehouseCommodityTrackVO>> queryByPage(WarehouseCommodityTrackQuery q) {
		int totalNum = this.trackMapper.queryPageCount(q);
	    List<WarehouseCommodityTrackVO> list = new ArrayList<WarehouseCommodityTrackVO>(0);
	    if(totalNum>0) {
	            list = this.trackMapper.queryPageList(q);
	    }
        ResultPager<WarehouseCommodityTrackVO> data = new ResultPager<WarehouseCommodityTrackVO>(totalNum, q.getCurrPage(), q.getPageSize(), list);
        return ResponseResult.buildSuccessResponse(data);
	}

}
