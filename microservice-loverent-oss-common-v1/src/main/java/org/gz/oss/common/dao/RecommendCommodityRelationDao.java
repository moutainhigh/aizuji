/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.oss.common.dao;

import java.util.List;

import org.gz.oss.common.dto.RecommendCommodityRelationDto;
import org.gz.oss.common.dto.UpdateDto;
import org.gz.oss.common.entity.RecommendCommodityRelation;
/**
 * RecommendCommodityRelation Dao
 * 
 * @author liuyt
 * @date 2018-03-20
 */
public interface RecommendCommodityRelationDao {

    void update(UpdateDto<RecommendCommodityRelation> updateDto);

    void add(RecommendCommodityRelation recommendCommodityRelation);

    List<RecommendCommodityRelation> findAll();

    RecommendCommodityRelation getById(Integer id);

    void deleteByRecommendId(Integer recommendId);

    void addBatch(List<RecommendCommodityRelation> list);
	
	/**
	 * 
	 * @Description: 如果需要分页或动态排序查询，将参数修改为QueryDto,再将查询条件、动态排序、分页查询数据放进QueryDto即可，无须修改mapper文件
	 * @return
	 * @throws
	 * createBy:zhuangjianfa              
	 * createDate:2017年1月25日上午11:15:06
	 */
	public List<RecommendCommodityRelation> queryList(RecommendCommodityRelationDto dto);
}
