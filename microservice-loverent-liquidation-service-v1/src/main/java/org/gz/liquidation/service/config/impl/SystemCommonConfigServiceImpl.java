package org.gz.liquidation.service.config.impl;

import java.util.List;

import javax.annotation.Resource;

import org.gz.liquidation.entity.SystemCommonConfigEntity;
import org.gz.liquidation.mapper.SystemCommonConfigMapper;
import org.gz.liquidation.service.config.SystemCommonConfigService;
import org.springframework.stereotype.Service;

@Service
public class SystemCommonConfigServiceImpl implements SystemCommonConfigService {

	@Resource
	private SystemCommonConfigMapper systemCommonConfigMapper;
	
	@Override
	public SystemCommonConfigEntity selectOne(SystemCommonConfigEntity scc) {
		return systemCommonConfigMapper.selectOne(scc);
	}

	@Override
	public List<SystemCommonConfigEntity> selectList(SystemCommonConfigEntity scc) {
		return systemCommonConfigMapper.selectList(scc);
	}

}
