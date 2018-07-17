package org.gz.warehouse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gz.warehouse.entity.MaterielBrand;
import org.gz.warehouse.entity.MaterielBrandQuery;

@Mapper
public interface MaterielBrandMapper {

    /**
     * 根据物料分类Id查询品牌列表
     * @param classId
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月12日
     */
    List<MaterielBrand> queryMaterielBrandListByClassId(Integer classId);

	/** 
	* @Description: 根据品牌名称，品牌编码查询重复数量
	* @param  query
	* @return int
	*/
	int queryUniqueCount(MaterielBrandQuery query);

	/** 
	* @Description: 插入数据
	* @param m
	*/
	int insert(MaterielBrand m);

	/** 
	* @Description: 修改数据
	* @param m
	* @return int
	*/
	int update(MaterielBrand m);

	/** 
	* @Description: 
	* @param @param idList
	* @param @return
	* @return int
	* @throws 
	*/
	int deleteByIds(List<Integer> idList);

	/** 
	* @Description: 
	* @param @param id
	* @param @return
	* @return MaterielBrand
	* @throws 
	*/
	MaterielBrand selectByPrimaryKey(@Param("id")Long id);

	/** 
	* @Description: 
	* @param m
	* @return int
	*/
	int queryPageCount(MaterielBrandQuery m);

	/** 
	* @Description: 
	* @param m
	* @return List<MaterielBrand>
	*/
	List<MaterielBrand> queryPageList(MaterielBrandQuery m);

	/** 
	* @Description: 
	* @param idList
	* @return int
	*/
	int queryUsedCount(List<Integer> idList);

}
