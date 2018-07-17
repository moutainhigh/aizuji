package org.gz.warehouse.service.materiel;

import java.util.List;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.MaterielUnit;
import org.gz.warehouse.entity.MaterielUnitQuery;

public interface MaterielUnitService {

	ResponseResult<MaterielUnit> insert(MaterielUnit m);

	ResponseResult<MaterielUnit> update(MaterielUnit m);

	ResponseResult<MaterielUnit> deleteByIds(String ids);

	ResponseResult<MaterielUnit> selectByPrimaryKey(Integer id);

	ResponseResult<ResultPager<MaterielUnit>> queryByPage(MaterielUnitQuery m);

	/** 
	* @Description: 
	* @param @param m
	* @param @return
	* @return ResponseResult<?>
	* @throws 
	*/
	ResponseResult<MaterielUnit> setEnableFlag(MaterielUnit m);

	/** 
	* @Description: 
	* @param @return
	* @return ResponseResult<List<MaterielUnit>>
	* @throws 
	*/
	ResponseResult<List<MaterielUnit>> queryAllEnabledList();

}
