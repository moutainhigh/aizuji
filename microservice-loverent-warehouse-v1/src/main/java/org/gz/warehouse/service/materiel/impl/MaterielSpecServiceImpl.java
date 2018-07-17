package org.gz.warehouse.service.materiel.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.warehouse.common.WarehouseErrorCode;
import org.gz.warehouse.converter.String2IntListConverter;
import org.gz.warehouse.entity.MaterielModelSpec;
import org.gz.warehouse.entity.MaterielSpec;
import org.gz.warehouse.entity.MaterielSpecQuery;
import org.gz.warehouse.entity.MaterielSpecVo;
import org.gz.warehouse.mapper.MaterielModelSpecMapper;
import org.gz.warehouse.mapper.MaterielSpecMapper;
import org.gz.warehouse.service.materiel.MaterielSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MaterielSpecServiceImpl implements MaterielSpecService {

	@Autowired
	private MaterielSpecMapper materielSpecMapper;
	
    @Resource
    private MaterielModelSpecMapper materielModelSpecMapper;

	/**
	 * 插入物料规格
	 */
	@Override
	public ResponseResult<MaterielSpec> insert(MaterielSpec m) {
		// 1.验证唯一性(规格名称)
		ResponseResult<MaterielSpec> validateResult = this.validateMaterielSpec(m);
		// 2.写入数据
		if (validateResult.isSuccess()) {
			try {
				if (this.materielSpecMapper.insert(m) > 0) {
					return ResponseResult.buildSuccessResponse(m);
				}
			} catch (Exception e) {
				// 转换底层异常
				throw new ServiceException(e.getLocalizedMessage());
			}
		}
		return validateResult;
	}

	/**
	 * 验证数据
	 * @param m
	 * @return
	 */
	private ResponseResult<MaterielSpec> validateMaterielSpec(MaterielSpec m) {
		MaterielSpecQuery query = new MaterielSpecQuery(m);
		int count = this.materielSpecMapper.queryUniqueCount(query);
		return count>0?ResponseResult.build(WarehouseErrorCode.MATERIEL_SPEC_REPEAT_ERROR.getCode(), WarehouseErrorCode.MATERIEL_SPEC_REPEAT_ERROR.getMessage(), m):ResponseResult.buildSuccessResponse();
	}

	/**
	 * 更新数据
	 */
	@Override
	public ResponseResult<MaterielSpec> update(MaterielSpec m) {
		//1.验证唯一性(id,规格名称，物料分类ID)
		ResponseResult<MaterielSpec> vr = this.validateMaterielSpec(m);
		//2.修改数据
		if(vr.isSuccess()) {
			try {
				if(this.materielSpecMapper.update(m)>0) {
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
	 * 批量删除
	 */
	@Override
	public ResponseResult<MaterielSpec> deleteByIds(String ids) {
		if(StringUtils.hasText(ids)) {
			List<Integer> idList = new String2IntListConverter(",").convert(ids);
			//断言能否删除
			boolean canDelete = this.assertCanDelete(idList);
			if(canDelete) {
				try {
					if(this.materielSpecMapper.deleteByIds(idList)>0) {
						return ResponseResult.buildSuccessResponse();
					}
				} catch (Exception e) {
					//转换底层异常
					throw new ServiceException(e.getLocalizedMessage());
				}
			}else {
				return ResponseResult.build(WarehouseErrorCode.MATERIEL_SPEC_USING_ERROR.getCode(), WarehouseErrorCode.MATERIEL_SPEC_USING_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}

	/**
	 * 断言能否删除
	 * @param idList
	 * @return
	 */
	private boolean assertCanDelete(List<Integer> idList) {
		return this.materielSpecMapper.queryUsedCount(idList)==0;
	}

	/**
	 * 查询详情
	 */
	@Override
	public ResponseResult<MaterielSpec> selectByPrimaryKey(Integer id) {
		return ResponseResult.buildSuccessResponse(this.materielSpecMapper.selectByPrimaryKey(id));
	}

	/**
	 * 分页查询列表
	 */
	@Override
	public ResponseResult<ResultPager<MaterielSpec>> queryByPage(MaterielSpecQuery m) {
		int totalNum = this.materielSpecMapper.queryPageCount(m);
		List<MaterielSpec> list = new ArrayList<MaterielSpec>(0);
		if(totalNum>0) {
			list = this.materielSpecMapper.queryPageList(m);
		}
		ResultPager<MaterielSpec> data = new ResultPager<MaterielSpec>(totalNum, m.getCurrPage(), m.getPageSize(), list);
		return ResponseResult.buildSuccessResponse(data);
	}

	@Override
	public ResponseResult<MaterielSpec> setEnableFlag(MaterielSpec m) {
		MaterielSpec querySpec = this.materielSpecMapper.selectByPrimaryKey(m.getId());
		if(querySpec==null) {
			return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(), m);
		}
		if(querySpec.getEnableFlag().intValue()!=m.getEnableFlag().intValue()) {
			querySpec.setEnableFlag(m.getEnableFlag());
			this.materielSpecMapper.update(querySpec);
		}
		return ResponseResult.buildSuccessResponse(querySpec);
	}

    @Override
    public List<MaterielSpecVo> queryMaterielSpecListByModelId(Integer modelId) {
        return materielSpecMapper.queryMaterielSpecListByModelId(modelId);
    }

    @Override
    public List<MaterielModelSpec> queryAllSpecValueByModelId(Integer modelId) {
        List<MaterielModelSpec> list = this.materielModelSpecMapper.querySpecListGroupBySpecId(modelId);
        for (MaterielModelSpec mms : list) {
            if (mms.getMaterielSpecId() == 1) {
                // 内存 去掉G并排序
                String[] strArr = mms.getMaterielSpecValue().replaceAll("G", "").split(",");
                int[] arr = new int[strArr.length];
                for (int i = 0; i < strArr.length; i++) {
                    arr[i] = Integer.parseInt(strArr[i]);
                }
                Arrays.sort(arr);

                // 补上G 并合并
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < arr.length; i++) {
                    sb = sb.append(arr[i]).append("G");
                    if (i != arr.length - 1) {
                        sb = sb.append(",");
                    }
                }
                mms.setMaterielSpecValue(sb.toString());
            }
        }
        return list;
    }

    @Override
    public String getSpecBatchNoBySpecInfo(MaterielModelSpec mms) {
        // 先根据型号id查询所有的规格批次号和规格值（多个值用,拼接）
        List<MaterielModelSpec> list = this.materielModelSpecMapper.querySpecListGroupBySpecBatchNo(mms.getMaterielModelId());

        if (CollectionUtils.isNotEmpty(list)) {
            String specValues = null;
            String[] dbSpecValueArr = null;
            String[] paramSpecValueArr = mms.getMaterielSpecValue().split(",");
            out:for (MaterielModelSpec msVo : list) {
                specValues = msVo.getMaterielSpecValue();
                if (StringUtils.isEmpty(specValues)) {
                    continue out;
                }
                dbSpecValueArr = specValues.split(",");
                for (String s : dbSpecValueArr) {
                    // 如果参数传递的规格值不包含该项规格批次号所需规格 跳过当次外循环
                    boolean flag = this.arrContains(paramSpecValueArr, s);
                    if (!flag) {
                        continue out;
                    }
                }
                // 所有规格项都满足时 返回规格批次号
                return msVo.getSpecBatchNo();
            }
        }
        return null;
    }

    /**
     * 检查数组是否包含某个元素
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2018年1月3日
     */
    private boolean arrContains(String[] arr, String targetValue) {
        for (String s : arr) {
            if (s.equals(targetValue)) {
                return true;
            }
        }
        return false;
    }

}
