/**
 * 
 */
package org.gz.warehouse.service.materiel;

import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.common.vo.MaterielSpecParaResp;
import org.gz.warehouse.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2018年3月22日 下午3:35:54
 */
public class MaterielSpecParaServiceTest extends BaseTest{

	@Autowired
	private MaterielSpecParaService materielSpecParaService;
	
	@Test
	public void testQueryMaterielSpecPara() {
		MaterielSpecParaResp resp = materielSpecParaService.queryMaterielSpecPara(1L);
		System.err.println(ResponseResult.buildSuccessResponse(resp));
	}

}
