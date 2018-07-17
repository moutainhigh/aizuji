package org.gz.oss.common.service;

import java.util.List;

import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.dto.RecommendDto;
import org.gz.oss.common.entity.Recommend;
import org.gz.oss.common.entity.RecommendCommodityRelation;
import org.gz.oss.common.request.RecommendReq;

/**
 * 推荐位service
 * @Description:TODO
 * Author	Version		Date		Changes
 * liuyt 	1.0  		2018年3月20日 	Created
 */
public interface RecommendService {

    /**
     * 查询推荐位列表
     * @return
     * @throws
     * createBy:liuyt
     * createDate:2018年3月20日
     */
    List<Recommend> queryRecommendList(RecommendDto dto);

    /**
     * 增加推荐位
     * @param recommend
     * @throws
     * createBy:liuyt
     * createDate:2018年3月20日
     */
    void addRecommend(Recommend recommend);

    /**
     * 修改推荐位信息
     * @param recommend
     * @throws
     * createBy:liuyt
     * createDate:2018年3月20日
     */
    void updateRecommendInfo(Recommend recommend);

    /**
     * 删除推荐位
     * @param id
     * @throws
     * createBy:liuyt
     * createDate:2018年3月20日
     */
    void delRecommendInfo(Integer id);

    /**
     * 批量更新推荐位位置
     * @param recommendReqList
     * @throws
     * createBy:liuyt
     * createDate:2018年3月20日
     */
    void batchUpdate(List<Recommend> recommendReqList);

    /**
     * 通过推荐位id或者类型查询推荐位关联的产品和商品信息
     * @param qvo
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2018年3月22日
     */
    ResponseResult<?> queryProductWithCommodityListByRecommendInfo(RecommendDto qvo);

    /**
     * 批量更新推荐位商品管理
     * @param req
     * @throws
     * createBy:liuyt            
     * createDate:2018年3月22日
     */
    void batchUpdateRecommendCommodityRelation(RecommendReq req);
}
