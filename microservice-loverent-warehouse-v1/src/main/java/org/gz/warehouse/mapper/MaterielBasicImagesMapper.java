/**
 * 
 */
package org.gz.warehouse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gz.warehouse.entity.MaterielBasicImages;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月16日 上午10:47:43
 */
@Mapper
public interface MaterielBasicImagesMapper {

	/** 
	* @Description: 批量插入
	* @param attaList
	*/
	int batchInsert(List<MaterielBasicImages> attaList);

	/** 
	* @Description: 根据materielBasicInfoId获取附件列表
	* @param materielBasicInfoId
	* @return List<MaterielBasicImages>
	*/
	List<MaterielBasicImages> queryAttaListByMaterielBasicInfoId(@Param("materielBasicInfoId")Long materielBasicInfoId);


	/** 
	* @Description: 
	* @param @param id
	* @return void
	* @throws 
	*/
	int deleteByMaterielBasicInfoId(@Param("materielBasicInfoId")Long materielBasicInfoId);
}
