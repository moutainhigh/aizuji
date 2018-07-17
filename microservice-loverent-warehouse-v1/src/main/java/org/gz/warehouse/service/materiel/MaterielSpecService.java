package org.gz.warehouse.service.materiel;

import java.util.List;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.MaterielModelSpec;
import org.gz.warehouse.entity.MaterielSpec;
import org.gz.warehouse.entity.MaterielSpecQuery;
import org.gz.warehouse.entity.MaterielSpecVo;

public interface MaterielSpecService {

	ResponseResult<MaterielSpec> insert(MaterielSpec m);

	ResponseResult<MaterielSpec> update(MaterielSpec m);
	
	ResponseResult<MaterielSpec> deleteByIds(String ids);

	ResponseResult<MaterielSpec> selectByPrimaryKey(Integer id);

	ResponseResult<ResultPager<MaterielSpec>> queryByPage(MaterielSpecQuery m);

	ResponseResult<MaterielSpec> setEnableFlag(MaterielSpec m);

    /**
     * 通过物料型号Id查询对应的规格批次列表信息
     * @param modelId
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月15日
     */
    List<MaterielSpecVo> queryMaterielSpecListByModelId(Integer modelId);

    /**
     * 查询某型号下已配置到产品中的所有规格值
     * @param modelId
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2018年1月3日
     */
    List<MaterielModelSpec> queryAllSpecValueByModelId(Integer modelId);
    
    /**
     * 根据规格信息查询规格批次号
     * @param qvo
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2018年1月3日
     */
    String getSpecBatchNoBySpecInfo(MaterielModelSpec mms);
}
