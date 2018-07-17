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
import org.gz.oss.common.dto.UpdateDto;
import org.gz.oss.common.entity.Banner;
/**
 * Banner Dao
 * 
 * @author zhangguoliang
 * @date 2017-12-15
 */
@Mapper
public interface BannerDao{
	
	public void update(UpdateDto<Banner> updateDto);
	
	public void add(Banner banner);
	
	public List<Banner> findAll();
	
	public Banner getById(Integer id);

    public void delete(Integer id);
}
