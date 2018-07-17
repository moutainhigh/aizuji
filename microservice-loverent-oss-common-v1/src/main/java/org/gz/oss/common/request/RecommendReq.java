package org.gz.oss.common.request;

import java.util.List;

import org.gz.oss.common.entity.Recommend;
import org.gz.oss.common.entity.RecommendCommodityRelation;


public class RecommendReq {

    /**
     * 推荐位id
     */
    private Integer                          recommendId;

    private List<Recommend> recommendReqList;

    private List<RecommendCommodityRelation> rcrList;

    public List<Recommend> getRecommendReqList() {
        return recommendReqList;
    }

    public void setRecommendReqList(List<Recommend> recommendReqList) {
        this.recommendReqList = recommendReqList;
    }

    public List<RecommendCommodityRelation> getRcrList() {
        return rcrList;
    }

    public void setRcrList(List<RecommendCommodityRelation> rcrList) {
        this.rcrList = rcrList;
    }

    public Integer getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(Integer recommendId) {
        this.recommendId = recommendId;
    }
}
