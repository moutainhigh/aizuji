package org.gz.warehouse.service.materiel;

import java.util.List;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.MaterielBrand;
import org.gz.warehouse.entity.MaterielBrandQuery;

/**
 * 物料品牌接口
 * Author	Version		Date		Changes
 * liuyt 	1.0  		2017年12月12日 	Created
 */
public interface MaterielBrandService {

    /**
     * 通过物料分类id查询对应的品牌列表
     * @param classId
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月12日
     */
    List<MaterielBrand> queryMaterielBrandListByClassId(Integer classId);

	/** 
	* @Description: 新增物料品牌
	* @param  m
	* @return ResponseResult<MaterielBrand>
	*/
	ResponseResult<MaterielBrand> insert(MaterielBrand m);

	/** 
	* @Description: 修改物料品牌
	* @param m
	* @return ResponseResult<MaterielBrand>
	*/
	ResponseResult<MaterielBrand> update(MaterielBrand m);

	/** 
	* @Description: 批量删除物料品牌
	* @param @param ids
	* @return ResponseResult<MaterielBrand>
	*/
	ResponseResult<MaterielBrand> deleteByIds(String ids);

	/** 
	* @Description: 
	* @param @param id
	* @param @return
	* @return ResponseResult<?>
	* @throws 
	*/
	ResponseResult<MaterielBrand> selectByPrimaryKey(Long id);

	/** 
	* @Description: 
	* @param @param m
	* @param @return
	* @return ResponseResult<ResultPager<MaterielUnit>>
	* @throws 
	*/
	ResponseResult<ResultPager<MaterielBrand>> queryByPage(MaterielBrandQuery m);
}
