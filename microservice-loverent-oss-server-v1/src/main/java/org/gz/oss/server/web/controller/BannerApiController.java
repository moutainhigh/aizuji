package org.gz.oss.server.web.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.oss.common.entity.Banner;
import org.gz.oss.common.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/banner")
@Slf4j
public class BannerApiController {
	
	@Autowired
	private BannerService bannerService;
	
    /**
    * 查询
    * @return ResponseResult<Advertising>
    */
    @GetMapping(value = "/queryBannerList")
    public ResponseResult<?> queryBannerList() {
        try {
            List<Banner> list = this.bannerService.findAll();
            return ResponseResult.buildSuccessResponse(list);
        } catch (Exception e) {
            log.error("获取banner列表失败：{}", e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),
                ResponseStatus.DATA_REQUERY_ERROR.getMessage(),
                null);
        }
    }
}
