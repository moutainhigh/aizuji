package org.gz.warehouse.service.materiel;

import java.util.List;

import org.gz.common.constants.ZTreeSimpleNode;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.*;

public interface MaterielClassService {

    /**
     * 根据条件查询分类列表
     * @param mc
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月12日
     */
    List<MaterielClass> queryMaterielClassList(MaterielClass mc);

    /**
     * 查询所有分类列表（list里面包含所以大分类  每个大分类里面包含各个对应小分类列表  按树状返回）
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月12日
     */
    MaterielClass queryAllMaterielClassList();
    
    /**
     * 
    * @Description: 获取物料分类树
    * @param  rootId 根节点
    * @return List<MaterielClass>
     */
    MaterielClass getTree(Integer rootId);
    
    /**
     * 
    * @Description: 获取物料分类ZTree树
    * @param @param rootId
    * @param @return
    * @return ZTreeSimpleNode
    * @throws
     */
    ZTreeSimpleNode getZTree(Integer rootId);

	/** 
	* @Description: 
	* @param @param m
	* @param @return
	* @return ResponseResult<?>
	* @throws 
	*/
	ResponseResult<MaterielClass> insert(MaterielClass m);

	/** 
	* @Description: 
	* @param @param m
	* @param @return
	* @return ResponseResult<?>
	* @throws 
	*/
	ResponseResult<MaterielClass> update(MaterielClass m);

	/** 
	* @Description: 
	* @param @param ids
	* @param @return
	* @return ResponseResult<?>
	* @throws 
	*/
	ResponseResult<MaterielClass> deleteByIds(String ids);

	/** 
	* @Description: 
	* @param @param id
	* @param @return
	* @return ResponseResult<?>
	* @throws 
	*/
	ResponseResult<MaterielClass> selectByPrimaryKey(Integer id);

	/** 
	* @Description: 
	* @param @param m
	* @param @return
	* @return ResponseResult<ResultPager<MaterielClass>>
	* @throws 
	*/
	ResponseResult<ResultPager<MaterielClass>> queryByPage(MaterielClassQuery m);

	/** 
	* @Description: 
	* @param @param m
	* @param @return
	* @return ResponseResult<MaterielClassBrand>
	* @throws 
	*/
	ResponseResult<MaterielClassBrand> configBrand(MaterielClassBrandReq m);

	/** 
	* @Description: 
	* @param @param materielClassId
	* @param @return
	* @return ResponseResult<List<MaterielClassBrandVO>>
	* @throws 
	*/
	ResponseResult<List<MaterielClassBrandVO>> queryBrands(Integer materielClassId);

	/** 
	* @Description: 判断能否从分类中删除品牌ID,依据materiel_basic_info表
	* @param @param m
	* @param @return
	* @return ResponseResult<?>
	* @throws 
	*/
	ResponseResult<?> canDeleteBrand(MaterielClassBrandReq m);


}
