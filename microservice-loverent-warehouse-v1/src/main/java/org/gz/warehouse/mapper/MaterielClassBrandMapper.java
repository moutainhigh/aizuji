/**
 * 
 */
package org.gz.warehouse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gz.warehouse.entity.MaterielClassBrand;
import org.gz.warehouse.entity.MaterielClassBrandVO;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月15日 下午4:26:50
 */
@Mapper
public interface MaterielClassBrandMapper {

	/** 
	* @Description: 
	* @param @param materielClassId
	* @param @return
	* @return List<MaterielClassBrandVO>
	* @throws 
	*/
	List<MaterielClassBrandVO> queryBrands(@Param("materielClassId") Integer materielClassId);

	/** 
	* @Description: 
	* @param @param materielClassId
	* @param @return
	* @return List<MaterielClassBrandVO>
	* @throws 
	*/
	List<MaterielClassBrandVO> queryLinkedBrandList(@Param("materielClassId") Integer materielClassId);
	
	List<Integer> queryLinkedBrandIds(@Param("materielClassId") Integer materielClassId);

	/** 
	* @Description: 
	* @param @param delList
	* @return void
	* @throws 
	*/
	int batchDelete(@Param("materielClassId")Integer materielClassId,@Param("delList")List<MaterielClassBrand> delList);

	/** 
	* @Description: 
	* @param @param addList
	* @return void
	* @throws 
	*/
	int batchAdd(List<MaterielClassBrand> addList);

}
