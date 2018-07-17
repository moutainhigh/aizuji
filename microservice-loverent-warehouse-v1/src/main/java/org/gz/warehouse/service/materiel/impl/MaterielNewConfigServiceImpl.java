package org.gz.warehouse.service.materiel.impl;

import java.util.ArrayList;
import java.util.List;

import org.gz.common.entity.QueryPager;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.warehouse.entity.MaterielBasicInfo;
import org.gz.warehouse.entity.MaterielNewConfig;
import org.gz.warehouse.mapper.MaterielNewConfigMapper;
import org.gz.warehouse.service.materiel.MaterielNewConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MaterielNewConfigServiceImpl implements MaterielNewConfigService {

	@Autowired
	private MaterielNewConfigMapper newConfigMapper;

	@Override
	public ResponseResult<ResultPager<MaterielNewConfig>> queryForPage(QueryPager page) {
		int totalCount = newConfigMapper.queryNewConfigsCout();
		List<MaterielNewConfig> list = new ArrayList<>();
		if(totalCount > 0){
			list = newConfigMapper.queryNewConfigPage(page);
		}
		ResultPager<MaterielNewConfig> data = new ResultPager<MaterielNewConfig>(totalCount, page.getCurrPage(), page.getPageSize(), list);
		return ResponseResult.buildSuccessResponse(data);

	}

	@Override
	public List<MaterielNewConfig> queryAllNewConfigs() {
		return newConfigMapper.queryAllNewConfig();
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseResult<MaterielNewConfig> insertMaterielNewConfig(MaterielNewConfig config) {
		//名称，值不能重复
		List<MaterielNewConfig> list = this.newConfigMapper.queryConfigsByNameAndValue(config);
		if(list.size() > 0) {
			return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), "名称或值已存在", null);
		}
		newConfigMapper.insertNewConfigs(config);
		return ResponseResult.buildSuccessResponse(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseResult<MaterielNewConfig> updateMaterielNewConfig(MaterielNewConfig config) {
		newConfigMapper.updateNewConfigs(config);
		return ResponseResult.buildSuccessResponse(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseResult<?> deleteById(Integer id) {
		//判断materiel_basic_info表有没有引用当前配置Id,若有则不允许删除
		List<MaterielBasicInfo> list = this.newConfigMapper.queryBasicInfo(id);
		if(list.size() > 0) {
			return ResponseResult.build(ResponseStatus.DATA_DELETED_ERROR.getCode(), "新旧配置ID已存在其他表中", null);
		}
	    newConfigMapper.deleteById(id);
		return ResponseResult.buildSuccessResponse();
	}

	@Override
	public ResponseResult<List<MaterielNewConfig>> getAllNewConfig() {
		List<MaterielNewConfig> list = this.newConfigMapper.queryAllNewConfig();
		return ResponseResult.buildSuccessResponse(list);
	}

	@Override
	public ResponseResult<List<MaterielNewConfig>> queryAllavailableNewConfigs() {
		List<MaterielNewConfig> list = this.newConfigMapper.queryAllavailableNewConfigs();
		return ResponseResult.buildSuccessResponse(list);
	}

}
