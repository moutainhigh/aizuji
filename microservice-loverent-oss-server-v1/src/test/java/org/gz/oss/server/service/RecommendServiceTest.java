package org.gz.oss.server.service;

import lombok.extern.slf4j.Slf4j;
import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.dto.RecommendDto;
import org.gz.oss.common.entity.Recommend;
import org.gz.oss.common.service.RecommendService;
import org.gz.oss.server.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
public class RecommendServiceTest extends BaseTest {

    @Resource
    private RecommendService recommendService;

    /**
     * 通过推荐位id或者类型查询推荐位关联的产品和商品信息
     */
    @Test
    public void queryProductWithCommodityListByRecommendInfo() {
        RecommendDto r = new RecommendDto();
        r.setSource(30);
        r.setId(24);
        ResponseResult<?> result = this.recommendService.queryProductWithCommodityListByRecommendInfo(r);
        log.info("APP-通过推荐位id或者类型查询推荐位关联的产品和商品信息数据长度为：{}" + result.getErrCode() + ":" + result.getErrMsg());
        assertTrue(result.isSuccess());
    }

    @Test
    public void RecomendServiceImplTest(){
        RecommendDto r = new RecommendDto();
        r.setSource(30);
        r.setId(24);
        List<Recommend> result = this.recommendService.queryRecommendList(r);
        log.info("结果为：{}" + result.size());
        //log.info("APP-通过推荐位id或者类型查询推荐位关联的产品和商品信息数据长度为：{}" + result.getErrCode() + ":" + result.getErrMsg());
      //  assertTrue(result.isSuccess());
    }
}