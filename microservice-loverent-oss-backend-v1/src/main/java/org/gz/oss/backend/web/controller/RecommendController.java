package org.gz.oss.backend.web.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.oss.common.dto.RecommendDto;
import org.gz.oss.common.entity.Recommend;
import org.gz.oss.common.entity.RecommendCommodityRelation;
import org.gz.oss.common.request.RecommendReq;
import org.gz.oss.common.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 推荐位controller
 * @Description:TODO
 * Author	Version		Date		Changes
 * liuyt 	1.0  		2018年3月19日 	Created
 */
@RestController
@Slf4j
@RequestMapping("/recommend")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;

    /**
     * 查询推荐位列表
     */
    @PostMapping(value = "/queryRecommendList")
    public ResponseResult<?> queryRecommendList(RecommendDto dto) {
        try {
            List<Recommend> list = recommendService.queryRecommendList(dto);
            return ResponseResult.buildSuccessResponse(list);
        } catch (Exception e) {
            log.error("queryRecommendList失败：{}", e);
            return ResponseResult.build(ResponseStatus.FALL_BACK.getCode(),
                ResponseStatus.FALL_BACK.getMessage(),
                e.getMessage());
        }
    }

    /**
     * 添加推荐位
     */
    @PostMapping(value = "/addRecommend")
    public ResponseResult<?> addRecommend(Recommend recommend) {
        try {
            recommendService.addRecommend(recommend);
            return ResponseResult.buildSuccessResponse();
        } catch (Exception e) {
            log.error("addRecommend失败：{}", e);
            return ResponseResult.build(ResponseStatus.FALL_BACK.getCode(),
                ResponseStatus.FALL_BACK.getMessage(),
                e.getMessage());
        }
    }

    /**
     * 修改推荐位信息
     */
    @PostMapping(value = "/updateRecommend")
    public ResponseResult<?> updateRecommendInfo(Recommend recommend) {
        try {
            recommendService.updateRecommendInfo(recommend);
            return ResponseResult.buildSuccessResponse();
        } catch (Exception e) {
            log.error("updateRecommendInfo失败：{}", e);
            return ResponseResult.build(ResponseStatus.FALL_BACK.getCode(),
                ResponseStatus.FALL_BACK.getMessage(),
                e.getMessage());
        }
    }

    /**
     * 删除推荐位信息
     */
    @PostMapping(value = "/delRecommend")
    public ResponseResult<?> delRecommendInfo(Integer id) {
        try {
            recommendService.delRecommendInfo(id);
            return ResponseResult.buildSuccessResponse();
        } catch (Exception e) {
            log.error("delRecommendInfo失败：{}", e);
            return ResponseResult.build(ResponseStatus.FALL_BACK.getCode(),
                ResponseStatus.FALL_BACK.getMessage(),
                e.getMessage());
        }
    }

    /**
     * 批量更新推荐位位置
     */
    @PostMapping(value = "/batchUpdate")
    public ResponseResult<?> batchUpdate(RecommendReq req) {
        try {
            recommendService.batchUpdate(req.getRecommendReqList());
            return ResponseResult.buildSuccessResponse();
        } catch (Exception e) {
            log.error("batchUpdate失败：{}", e);
            return ResponseResult.build(ResponseStatus.FALL_BACK.getCode(),
                ResponseStatus.FALL_BACK.getMessage(),
                e.getMessage());
        }
    }

    /**
     * 通过推荐位id和类型查询推荐位关联的产品和商品信息
     */
    @RequestMapping(value = "/queryProductWithCommodityListByRecommendInfo")
    public ResponseResult<?> queryProductWithCommodityListByRecommendInfo(@RequestBody RecommendDto qvo) {
        try {
            return this.recommendService.queryProductWithCommodityListByRecommendInfo(qvo);
        } catch (Exception e) {
            log.error("queryProductWithCommodityListByRecommendInfo失败：{}", e);
            return ResponseResult.build(ResponseStatus.FALL_BACK.getCode(),
                ResponseStatus.FALL_BACK.getMessage(),
                e.getMessage());
        }
    }

    /**
     * 批量更新推荐位商品管理
     */
    @RequestMapping(value = "/batchUpdateRecommendCommodityRelation")
    public ResponseResult<?> batchUpdateRecommendCommodityRelation(RecommendReq req) {
        try {
            recommendService.batchUpdateRecommendCommodityRelation(req);
            return ResponseResult.buildSuccessResponse();
        } catch (Exception e) {
            log.error("batchUpdateRecommendCommodityRelation失败：{}", e);
            return ResponseResult.build(ResponseStatus.FALL_BACK.getCode(),
                ResponseStatus.FALL_BACK.getMessage(),
                e.getMessage());
        }
    }
}
