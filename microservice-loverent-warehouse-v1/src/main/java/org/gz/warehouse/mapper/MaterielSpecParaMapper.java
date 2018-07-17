/**
 * 
 */
package org.gz.warehouse.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gz.warehouse.common.vo.MaterielSpecParaResp;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2018年3月22日 下午3:25:20
 */
@Mapper
public interface MaterielSpecParaMapper {

	/** 
	* @Description: 
	* @param @param materielBasicInfoId
	* @param @return
	* @return MaterielSpecParaResp
	* @throws 
	*/
	MaterielSpecParaResp queryMaterielSpecPara(@Param("materielBasicInfoId")Long materielBasicInfoId);

}
