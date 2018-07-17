/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.oss.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.oss.common.entity.BannerV1;
/**
 * Banner Dao
 * 
 * @author zhangguoliang
 * @date 2017-12-15
 */
@Mapper
public interface BannerV1Dao{
	
	public List<BannerV1> findAll();
	
	public BannerV1 getById(Integer id);
}
