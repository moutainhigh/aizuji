package org.gz.warehouse.service.materiel.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.warehouse.common.WarehouseErrorCode;
import org.gz.warehouse.common.vo.MaterielModelResp;
import org.gz.warehouse.common.vo.MaterielModelVo;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.ProductInfoQvo;
import org.gz.warehouse.converter.String2IntListConverter;
import org.gz.warehouse.entity.MaterielModel;
import org.gz.warehouse.entity.MaterielModelQuery;
import org.gz.warehouse.entity.MaterielModelSpec;
import org.gz.warehouse.entity.MaterielModelSpecQuery;
import org.gz.warehouse.mapper.MaterielBasicInfoMapper;
import org.gz.warehouse.mapper.MaterielModelMapper;
import org.gz.warehouse.mapper.MaterielModelSpecMapper;
import org.gz.warehouse.service.materiel.MaterielModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class MaterielModelServiceImpl implements MaterielModelService {

    @Autowired
    private MaterielModelMapper materielModelMapper;

    @Autowired
    private MaterielModelSpecMapper materielModelSpecMapper;
    
    @Autowired
    private MaterielBasicInfoMapper materielBasicInfoMapper;
    
    @Override
    public List<MaterielModel> queryMaterielModelListByBrandId(Integer brandId) {
        return materielModelMapper.queryMaterielModelListByBrandId(brandId);
    }

	@Override
	public ResultPager<MaterielModel> queryMaterielModelPicListByBrandId(MaterielModelQuery m) {
		int queryPageTotal = materielModelMapper.queryPageTotal(m.getBrandId());
		List<MaterielModel> list=new ArrayList<>();
		if (queryPageTotal>0){
			list=materielModelMapper.queryMaterielModelPicListByBrandId(m);
		}
		ResultPager<MaterielModel> resultPager=new ResultPager<>(queryPageTotal,m.getCurrPage(),m.getPageSize(),list);
		return resultPager;
	}

	/** 
	 * Title: 
	 * <Description:
	 * @param m
	 * @return 
	*/
    @Transactional
	@Override
	public ResponseResult<MaterielModel> insert(MaterielModel m) {
		//1.验证唯一性(分类，品牌+型号名称是否重复)
		ResponseResult<MaterielModel> vr = this.validateMaterielModel(m);
		//2.写入数据
		if(vr.isSuccess()) {
			try {
				if(this.materielModelMapper.insert(m)>0) {
					if(CollectionUtils.isNotEmpty(m.getModelSpecList())) {
						List<MaterielModelSpec> modelSpecList = m.getModelSpecList();
						for(MaterielModelSpec modelSpec : modelSpecList) {
							modelSpec.setMaterielModelId(m.getId());
							modelSpec.setSpecBatchNo(genSpecBatchNo(m));
						}
						//验重
						if(!validateRepeatModelSpecList(modelSpecList)) {
							//写入型号规格
							this.materielModelSpecMapper.batchInsert(modelSpecList);
						}
						else {
							throw new ServiceException(WarehouseErrorCode.MATERIEL_MODELSPEC_REPEAT_ERROR.getCode(),WarehouseErrorCode.MATERIEL_MODELSPEC_REPEAT_ERROR.getMessage());
						}
					}
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
	* @Description: 验重
	* @param  modelSpecList
	* @return boolean，重复返回true,不重复返回false
	*/
	private boolean validateRepeatModelSpecList(List<MaterielModelSpec> modelSpecList) {
		HashSet<MaterielModelSpec> result = new HashSet<MaterielModelSpec>(modelSpecList);
		return modelSpecList.size()!=result.size();
	}

	/**
	 * 生成批次规格号
	 */
	private String genSpecBatchNo(MaterielModel m) {
		String id= String.valueOf(m.getId());
		int length = id.length();
		StringBuffer result = new StringBuffer("S");
		//不足5位补0
		for(int i=length;i<6;i++) {
			if(i==5) {
				result.append(id);
			}else {
				result.append("0");
			}
		}
		return result.toString();
	}

	private ResponseResult<MaterielModel> validateMaterielModel(MaterielModel m) {
		MaterielModelQuery query = new MaterielModelQuery(m);
		int count = this.materielModelMapper.queryUniqueCount(query);
		return count>0?ResponseResult.build(WarehouseErrorCode.MATERIEL_MODEL_REPEAT_ERROR.getCode(), WarehouseErrorCode.MATERIEL_MODEL_REPEAT_ERROR.getMessage(), m):ResponseResult.buildSuccessResponse();
	}

	
	@Override
	public ResponseResult<MaterielModel> update(MaterielModel m) {
		//1.验证唯一性(主键ID+分类ID+品牌ID+型号名称是否重复)
		ResponseResult<MaterielModel> vr = this.validateMaterielModel(m);
		//2.更新数据
		if(vr.isSuccess()) {
			try {
				if(this.materielModelMapper.update(m)>0) {
					String specBatchNo = genSpecBatchNo(m);
					if(CollectionUtils.isNotEmpty(m.getModelSpecList())) {
						List<MaterielModelSpec> modelSpecList = m.getModelSpecList();
						for(MaterielModelSpec modelSpec : modelSpecList) {
							modelSpec.setMaterielModelId(m.getId());
							modelSpec.setSpecBatchNo(specBatchNo);
						}
						//输入验重，不对现有数据验重
						if(!validateRepeatModelSpecList(modelSpecList)) {
							//先删除旧数据
							this.materielModelSpecMapper.batchDelete(m.getId(),specBatchNo);
							//写入型号规格
							this.materielModelSpecMapper.batchInsert(modelSpecList);
						}
						else {
							throw new ServiceException(WarehouseErrorCode.MATERIEL_MODELSPEC_REPEAT_ERROR.getCode(),WarehouseErrorCode.MATERIEL_MODELSPEC_REPEAT_ERROR.getMessage());
						}
					}
					return ResponseResult.buildSuccessResponse(m);
				}
			} catch (Exception e) {
				//转换底层异常
				throw new ServiceException(e.getLocalizedMessage());
			}
		}
		return vr;
	}


	@Override
	public ResponseResult<MaterielModel> setEnableFlag(MaterielModel m) {
		MaterielModel queryModel = this.materielModelMapper.selectByPrimaryKey(m.getId());
		if(queryModel!=null&&queryModel.getEnableFlag().intValue()!=m.getEnableFlag().intValue()) {
			queryModel.setEnableFlag(m.getEnableFlag());
			this.materielModelMapper.setEnableFlag(queryModel);
		}
		return ResponseResult.buildSuccessResponse(queryModel);
	}

	@Override
	public ResponseResult<MaterielModel> deleteByIds(String ids) {
		if(StringUtils.hasText(ids)) {
			List<Integer> materielModelIdList = new String2IntListConverter(",").convert(ids);
			//断言能否删除
			boolean canDelete = this.assertCanDelete(materielModelIdList);
			if(canDelete) {
				try {
					//先删除从表数据
					if(this.materielModelSpecMapper.batchDeleteByModelIdList(materielModelIdList)>0) {
						//再删除主表数据
						this.materielModelMapper.deleteByIds(materielModelIdList);
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
	* @Description: 断言能否删除
	* @param materielModelIdList
	* @return boolean
	*/
	private boolean assertCanDelete(List<Integer> materielModelIdList) {
		return this.materielBasicInfoMapper.queryUsedCountByModelIds(materielModelIdList)==0;
	}

	@Override
	public ResponseResult<MaterielModel> selectByPrimaryKey(Integer id) {
		MaterielModel m = this.materielModelMapper.selectByPrimaryKey(id);
		if(m!=null) {
			MaterielModelSpecQuery query = new MaterielModelSpecQuery(id,this.genSpecBatchNo(m));
			query.setCurrPage(1);
			query.setPageSize(Integer.MAX_VALUE);
			List<MaterielModelSpec> modelSpecList = this.materielModelSpecMapper.queryPageList(query);
			m.setModelSpecList(modelSpecList);
		}
		return ResponseResult.buildSuccessResponse(m);
	}

	@Override
	public ResponseResult<ResultPager<MaterielModel>> queryByPage(MaterielModelQuery m) {
		int totalNum = this.materielModelMapper.queryPageCount(m);
		List<MaterielModel> list = new ArrayList<MaterielModel>(0);
		if(totalNum>0) {
			list = this.materielModelMapper.queryPageList(m);
		}
		ResultPager<MaterielModel> data = new ResultPager<MaterielModel>(totalNum, m.getCurrPage(), m.getPageSize(), list);
		return ResponseResult.buildSuccessResponse(data);
	}

	@Override
    public List<MaterielModel> queryAllAvalibles(ProductInfoQvo qvo) {
        return materielModelMapper.queryAllAvalibles(qvo);
	}
	
	@Override
	public ResponseResult<List<MaterielModelResp>> queryModelList(String ids) {
		if(StringUtils.hasText(ids)) {
			List<Integer> idList = new String2IntListConverter().convert(ids);
			List<MaterielModelResp> list =  this.materielModelMapper.queryModelList(idList);
			return ResponseResult.buildSuccessResponse(list);
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}

    @Override
    public List<MaterielModelVo> getMaterielModelById(List<Integer> modelIdList) {
        return materielModelMapper.getMaterielModelById(modelIdList);
    }

    @Override
    public ResponseResult<List<ProductInfo>> getMinAmountByModelId(ProductInfoQvo vo) {
		try {
			//型号ID
			List<Long> param = vo.getMaterielModelIdList();
			//根据型号id，查询最低月租金
			List<ProductInfo> list = this.materielModelMapper.getMinAmountByModelId(param);
			return ResponseResult.buildSuccessResponse(list);
		}catch (Exception e){
			log.error("根据型号ID查询最低月租金失败：{}",e);
			return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(), null);
		}

    }
}
