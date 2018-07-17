/**
 * 
 */
package org.gz.warehouse.service.materiel.impl;

import org.gz.common.utils.AssertUtils;
import org.gz.warehouse.common.vo.MaterielSpecParaResp;
import org.gz.warehouse.mapper.MaterielSpecParaMapper;
import org.gz.warehouse.service.materiel.MaterielSpecParaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2018年3月22日 下午3:20:49
 */
@Service
public class MaterielSpecParaServiceImpl implements MaterielSpecParaService {

	@Autowired
	private MaterielSpecParaMapper materielSpecParaMapper;
	
	@Override
	public MaterielSpecParaResp queryMaterielSpecPara(Long materielBasicInfoId) {
		if(AssertUtils.isPositiveNumber4Long(materielBasicInfoId)) {
			return this.materielSpecParaMapper.queryMaterielSpecPara(materielBasicInfoId);
		}
		return null;
	}

}
