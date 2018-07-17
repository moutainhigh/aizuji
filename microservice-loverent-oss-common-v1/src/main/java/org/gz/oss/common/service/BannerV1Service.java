package org.gz.oss.common.service;

import java.util.List;

import org.gz.oss.common.entity.BannerV1;

public interface BannerV1Service {

	List<BannerV1> findAll();
	
	BannerV1 getById(Integer id);

}
