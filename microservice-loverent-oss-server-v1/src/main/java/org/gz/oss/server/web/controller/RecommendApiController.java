package org.gz.oss.server.web.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.oss.common.dto.RecommendDto;
import org.gz.oss.common.entity.Recommend;
import org.gz.oss.common.entity.RecommendCommodityRelation;
import org.gz.oss.common.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recommend")
@Slf4j
public class RecommendApiController {
	
	@Autowired
    private RecommendService recommendService;
	
    /**
    * 查询推荐位列表
    * @return ResponseResult<Advertising>
    */
    @PostMapping(value = "/queryRecommendList")
    public ResponseResult<?> queryRecommendList(@RequestBody RecommendDto dto) {
        try {
            List<Recommend> list = this.recommendService.queryRecommendList(dto);
            return ResponseResult.buildSuccessResponse(list);
        } catch (Exception e) {
            log.error("获取推荐位列表失败：{}", e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),
                ResponseStatus.DATA_REQUERY_ERROR.getMessage(),
                null);
        }
    }

    /**
     * 根据id或者类型查询推荐位下对应的产品关联
     * @return ResponseResult<Advertising>
     */
    @PostMapping(value = "/queryProductWithCommodityListByRecommendInfo")
    public ResponseResult<?> queryProductWithCommodityListByRecommendInfo(@RequestBody RecommendDto dto) {
        try {
            dto.setMaterielFlag(1); //查主物料
            return this.recommendService.queryProductWithCommodityListByRecommendInfo(dto);
        } catch (Exception e) {
            log.error("根据id或者类型查询推荐位下对应的产品关联失败：{}", e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),
                ResponseStatus.DATA_REQUERY_ERROR.getMessage(),
                null);
        }
    }
}
