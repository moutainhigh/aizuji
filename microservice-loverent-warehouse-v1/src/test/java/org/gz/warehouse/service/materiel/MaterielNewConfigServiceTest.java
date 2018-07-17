/**
 * 
 */
package org.gz.warehouse.service.materiel;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.gz.common.entity.QueryPager;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.MaterielNewConfig;
import org.gz.warehouse.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title:
 * @author hxj
 * @date 2018年3月5日 上午9:06:32
 */
@Transactional
@Slf4j
public class MaterielNewConfigServiceTest extends BaseTest {
	
	@Autowired
	private MaterielNewConfigService newConfigService;

	/**
	 * 查询新旧程度配置test
	 */
	@Test
	public void testQueryAllNewConfigs() {
		List<MaterielNewConfig> result = newConfigService.queryAllNewConfigs();
		System.out.println("测试结果为："+ result.toString());
	}

	/**
	 * 新增新旧程度配置test
	 */
	@Test
	public void testInsertMaterielNewConfig() {
		MaterielNewConfig config = new MaterielNewConfig();
		config.setConfigName("非新");
		config.setConfigValue("10新");
		ResponseResult<MaterielNewConfig> result = this.newConfigService.insertMaterielNewConfig(config);
		assertTrue(result.isSuccess() && result.getData().getConfigName().equals(config.getConfigName()));
		System.out.println("新增结果为：" + result.getErrCode() + ":" + result.getErrMsg());
	}
	
	/**
	 * 修改新旧程度配置test
	 */
	@Test
	public void testUpdateMaterielNewConfig(){
		MaterielNewConfig config = new MaterielNewConfig();
		config.setId(3);
		config.setConfigName("非新");
		config.setConfigValue("10新");
		ResponseResult<MaterielNewConfig> result = this.newConfigService.updateMaterielNewConfig(config);
		assertTrue(result.isSuccess() && result.getData().getConfigName().equals(config.getConfigName()));
		System.out.println("修改结果为：" + result.getErrCode() + ":" + result.getErrMsg());
	}
	
	/**
	 * 删除新旧程度配置test
	 */
	@Test
	public void testDeleteMaterielNewConfig() {
		int id = 3;
		ResponseResult<?> result = this.newConfigService.deleteById(id);
		assertTrue(result.isSuccess() );
		System.out.println("修改结果为：" + result.getErrCode() + ":" + result.getErrMsg());
	}
}
