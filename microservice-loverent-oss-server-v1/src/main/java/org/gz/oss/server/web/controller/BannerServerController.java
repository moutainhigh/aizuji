package org.gz.oss.server.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.oss.common.entity.BannerV1;
import org.gz.oss.common.service.BannerV1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/banner")
@Slf4j
public class BannerServerController {
	
	@Autowired
	private BannerV1Service bannerService;
	
	
    
    
	  /**
     * 查询所有banner列表
     * @return
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年12月16日
     */
    @PostMapping(value = "/queryAllList")
    public ResponseResult<Map<String, List<BannerV1>>> queryAllList() {
    	ResponseResult<Map<String, List<BannerV1>>> result = new ResponseResult<>();
        try {
        	Map<String, List<BannerV1>> map = new HashMap<>();
        	List<BannerV1> banners = new ArrayList<>();
        	List<BannerV1> showcase = new ArrayList<>();
        	
        	List<BannerV1> bannerList = bannerService.findAll();
        	for (BannerV1 banner : bannerList) {
        		if (banner.getResource() == 3) {
        			showcase.add(banner);
        		} else {
        			banners.add(banner);
        		}
        	}
        	map.put("bannse", banners);
        	map.put("showcase", showcase);
        	
        	result.setData(map);
        } catch (Exception e) {
            log.error("queryBannerAllList失败：{}", e); 
            ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.DATABASE_ERROR);
        }
        return result;
    }
    
    /**
     * 查询所有banner列表
     * @return
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年12月16日
     */
    @PostMapping(value = "/queryAll")
    public ResponseResult<List<BannerV1>> queryAll() {
    	ResponseResult<List<BannerV1>> result = new ResponseResult<>();
        try {
        	List<BannerV1> list = bannerService.findAll();
        	result.setData(list);
        } catch (Exception e) {
            log.error("queryBannerAllList失败：{}", e); 
            ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.DATABASE_ERROR);
        }
        return result;
    }
    
    /**
     * 通过id查询banner
     * @param id
     * @return
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年12月18日
     */
    @PostMapping(value="/getById")
    public ResponseResult<?> getById(Integer id) {
        try {
        	BannerV1 banner = bannerService.getById(id);
            return ResponseResult.buildSuccessResponse(banner);
        } catch (Exception e) {
            log.error("getById失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }
 
    
   
}
