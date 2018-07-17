package org.gz.warehouse.service.materiel.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.warehouse.common.WarehouseErrorCode;
import org.gz.warehouse.converter.String2IntListConverter;
import org.gz.warehouse.entity.MaterielBrand;
import org.gz.warehouse.entity.MaterielBrandQuery;
import org.gz.warehouse.mapper.MaterielBrandMapper;
import org.gz.warehouse.service.materiel.MaterielBrandService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MaterielBrandServiceImpl implements MaterielBrandService {

	@Resource
	private MaterielBrandMapper materielBrandMapper;

	@Override
	public List<MaterielBrand> queryMaterielBrandListByClassId(Integer classId) {
		return materielBrandMapper.queryMaterielBrandListByClassId(classId);
	
	}

	@Override
	public ResponseResult<MaterielBrand> insert(MaterielBrand m) {
		ResponseResult<MaterielBrand> vr = this.validateMaterielBrand(m);
		if(vr.isSuccess()) {
			try {
				if(this.materielBrandMapper.insert(m)>0) {
					return ResponseResult.buildSuccessResponse(m);
				}
			} catch (Exception e) {
				log.error("新增物料品牌失败:{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return vr;
	}

	private ResponseResult<MaterielBrand> validateMaterielBrand(MaterielBrand m) {
		//根据品牌名称，品牌编码验证唯一性
		MaterielBrandQuery query = new MaterielBrandQuery(m);
		int count = this.materielBrandMapper.queryUniqueCount(query);
		return count>0?ResponseResult.build(WarehouseErrorCode.MATERIEL_BRAND_REPEAT_ERROR.getCode(), WarehouseErrorCode.MATERIEL_BRAND_REPEAT_ERROR.getMessage(), m):ResponseResult.buildSuccessResponse();
	}

	@Override
	public ResponseResult<MaterielBrand> update(MaterielBrand m) {
		ResponseResult<MaterielBrand> vr = this.validateMaterielBrand(m);
		if(vr.isSuccess()) {
			try {
				if(this.materielBrandMapper.update(m)>0) {
					return ResponseResult.buildSuccessResponse(m);
				}
			} catch (Exception e) {
				log.error("修改物料品牌失败:{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return vr;
	}

	@Override
	public ResponseResult<MaterielBrand> deleteByIds(String ids) {
		if(StringUtils.hasText(ids)) {
			List<Integer> idList = new String2IntListConverter(",").convert(ids);
			boolean canDelete = this.assertCanDelete(idList);
			if(canDelete) {
				try {
					if(this.materielBrandMapper.deleteByIds(idList)>0) {
						return ResponseResult.buildSuccessResponse();
					}
				} catch (Exception e) {
					log.error("批量删除物料品牌失败:{}",e.getLocalizedMessage());
					//转换底层异常
					throw new ServiceException(e.getLocalizedMessage());
				}
			}else {
				return ResponseResult.build(WarehouseErrorCode.MATERIEL_BRAND_USING_ERROR.getCode(), WarehouseErrorCode.MATERIEL_BRAND_USING_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}

	/**
	 * 断言能否删除
	 */
	private boolean assertCanDelete(List<Integer> idList) {
		return this.materielBrandMapper.queryUsedCount(idList)==0;
	}

	@Override
	public ResponseResult<MaterielBrand> selectByPrimaryKey(Long id) {
		return ResponseResult.buildSuccessResponse(this.materielBrandMapper.selectByPrimaryKey(id));
	}

	@Override
	public ResponseResult<ResultPager<MaterielBrand>> queryByPage(MaterielBrandQuery m) {
		int totalNum = this.materielBrandMapper.queryPageCount(m);
		List<MaterielBrand> list = new ArrayList<MaterielBrand>(0);
		if(totalNum>0) {
			list = this.materielBrandMapper.queryPageList(m);
		}
		ResultPager<MaterielBrand> data = new ResultPager<MaterielBrand>(totalNum, m.getCurrPage(), m.getPageSize(), list);
		return ResponseResult.buildSuccessResponse(data);
	}
}
