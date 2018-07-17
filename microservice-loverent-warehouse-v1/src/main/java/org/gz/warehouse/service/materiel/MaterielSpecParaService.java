/**
 * 
 */
package org.gz.warehouse.service.materiel;

import org.gz.warehouse.common.vo.MaterielSpecParaResp;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2018年3月22日 下午3:20:35
 */
public interface MaterielSpecParaService {

	/** 
	* @Description: 
	* @param @param materielBasicInfoId
	* @param @return
	* @return MaterielSpecParaResp
	* @throws 
	*/
	MaterielSpecParaResp queryMaterielSpecPara(Long materielBasicInfoId);

}
