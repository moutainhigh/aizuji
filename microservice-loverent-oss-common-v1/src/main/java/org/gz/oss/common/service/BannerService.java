package org.gz.oss.common.service;

import java.util.List;

import org.gz.oss.common.entity.Banner;

public interface BannerService {

	void add(Banner banner);
	
	List<Banner> findAll();
	
	Banner getById(Integer id);
	
	void batchUpdate(List<Banner> bannerList);
	
	void update(Banner banner);

    void delete(Integer id);
}
