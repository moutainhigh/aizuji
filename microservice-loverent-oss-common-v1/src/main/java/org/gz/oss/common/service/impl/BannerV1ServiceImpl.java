package org.gz.oss.common.service.impl;

import java.util.List;

import org.gz.oss.common.dao.BannerV1Dao;
import org.gz.oss.common.entity.BannerV1;
import org.gz.oss.common.service.BannerV1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BannerV1ServiceImpl implements BannerV1Service {

	@Autowired
	private BannerV1Dao bannerV1Dao;


	@Override
	public List<BannerV1> findAll() {
		return bannerV1Dao.findAll();
	}

	@Override
	public BannerV1 getById(Integer id) {
		return bannerV1Dao.getById(id);
	}
	

}
