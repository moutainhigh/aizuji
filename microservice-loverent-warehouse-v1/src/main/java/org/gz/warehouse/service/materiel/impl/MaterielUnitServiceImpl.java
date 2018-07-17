package org.gz.warehouse.service.materiel.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.warehouse.common.WarehouseErrorCode;
import org.gz.warehouse.converter.String2IntListConverter;
import org.gz.warehouse.entity.MaterielUnit;
import org.gz.warehouse.entity.MaterielUnitQuery;
import org.gz.warehouse.mapper.MaterielUnitMapper;
import org.gz.warehouse.service.materiel.MaterielUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 物料单位服务类
 * @author hxj
 *
 */
@Service
public class MaterielUnitServiceImpl implements MaterielUnitService {

	@Autowired
	private MaterielUnitMapper materielUnitMapper;
	
	/**
	 * 插入物料单位
	 */
	@Override
	public ResponseResult<MaterielUnit> insert(MaterielUnit m) {
		//1.验证唯一性(规格名称，物料编码)
		ResponseResult<MaterielUnit> vr = this.validateMaterielUnit(m);
		//2.写入数据
		if(vr.isSuccess()) {
			try {
				if(this.materielUnitMapper.insert(m)>0) {
					return ResponseResult.buildSuccessResponse(m);
				}
			} catch (Exception e) {
				//转换底层异常
				throw new ServiceException(e.getLocalizedMessage());
			}
		}
		return vr;
	}

	/**
	 * 验证数据：主要验证唯一性
	 * @param m
	 * @return
	 */
	private ResponseResult<MaterielUnit> validateMaterielUnit(MaterielUnit m) {
		MaterielUnitQuery query = new MaterielUnitQuery(m);
		int count = this.materielUnitMapper.queryUniqueCount(query);
		return count>0?ResponseResult.build(WarehouseErrorCode.MATERIEL_UNIT_REPEAT_ERROR.getCode(), WarehouseErrorCode.MATERIEL_UNIT_REPEAT_ERROR.getMessage(), m):ResponseResult.buildSuccessResponse();
	}

	/**
	 * 更新物料单位
	 */
	@Override
	public ResponseResult<MaterielUnit> update(MaterielUnit m) {
		//1.验证唯一性(id,规格名称，物料编码)
		ResponseResult<MaterielUnit> vr = this.validateMaterielUnit(m);
		//2.修改数据
		if(vr.isSuccess()) {
			try {
				if(this.materielUnitMapper.update(m)>0) {
					return ResponseResult.buildSuccessResponse(m);
				}
			} catch (Exception e) {
				//转换底层异常
				throw new ServiceException(e.getLocalizedMessage());
			}
		}
		return vr;
	}

	/**
	 * 删除物料单位
	 */
	@Override
	public ResponseResult<MaterielUnit> deleteByIds(String ids) {
		if(StringUtils.hasText(ids)) {
			List<Integer> idList = new String2IntListConverter(",").convert(ids);
			//断言能否删除
			boolean canDelete = this.assertCanDelete(idList);
			if(canDelete) {
				try {
					if(this.materielUnitMapper.deleteByIds(idList)>0) {
						return ResponseResult.buildSuccessResponse();
					}
				} catch (Exception e) {
					//转换底层异常
					throw new ServiceException(e.getLocalizedMessage());
				}
			}else {
				return ResponseResult.build(WarehouseErrorCode.MATERIEL_UNIT_USING_ERROR.getCode(), WarehouseErrorCode.MATERIEL_UNIT_USING_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}

	/**
	 * 断言是否能删除
	 * @param idList
	 * @return
	 */
	private boolean assertCanDelete(List<Integer> idList) {
		if(CollectionUtils.isNotEmpty(idList)) {
			return this.materielUnitMapper.queryUsedCount(idList)==0;
		}
		return false;
	}

	/**
	 * 根据主键ID查询详情
	 */
	@Override
	public ResponseResult<MaterielUnit> selectByPrimaryKey(Integer id) {
		return ResponseResult.buildSuccessResponse(this.materielUnitMapper.selectByPrimaryKey(id));
	}

	/**
	 * 查询分页列表
	 */
	@Override
	public ResponseResult<ResultPager<MaterielUnit>> queryByPage(MaterielUnitQuery m) {
		int totalNum = this.materielUnitMapper.queryPageCount(m);
		List<MaterielUnit> list = new ArrayList<MaterielUnit>(0);
		if(totalNum>0) {
			list = this.materielUnitMapper.queryPageList(m);
		}
		ResultPager<MaterielUnit> data = new ResultPager<MaterielUnit>(totalNum, m.getCurrPage(), m.getPageSize(), list);
		return ResponseResult.buildSuccessResponse(data);
	}

	@Override
	public ResponseResult<MaterielUnit> setEnableFlag(MaterielUnit m) {
		MaterielUnit queryUnit = this.materielUnitMapper.selectByPrimaryKey(m.getId());
		if(queryUnit==null) {
			return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(), m);
		}
		//仅在物料单位存在的情况下，且原始标志与期望标志不同的情况下设置
		if(m.getEnableFlag().intValue()!=queryUnit.getEnableFlag().intValue()) {
			queryUnit.setEnableFlag(m.getEnableFlag());
			this.materielUnitMapper.update(queryUnit);
		}
		return ResponseResult.buildSuccessResponse(queryUnit);
	}

	/* 
	 * Title: 
	 * <Description: </p> 
	 * @return 
	 * @see org.gz.warehouse.service.materiel.MaterielUnitService#queryAllList() 
	*/
	@Override
	public ResponseResult<List<MaterielUnit>> queryAllEnabledList() {
		MaterielUnitQuery m = new MaterielUnitQuery();
		m.setCurrPage(1);
		m.setPageSize(Integer.MAX_VALUE);
		m.setEnableFlag(1);//默认查询启用
		List<MaterielUnit> list = this.materielUnitMapper.queryPageList(m);
		return ResponseResult.buildSuccessResponse(list);
	}

}
