package org.gz.warehouse.service.materiel;

import static org.junit.Assert.assertTrue;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.MaterielSpec;
import org.gz.warehouse.entity.MaterielSpecQuery;
import org.gz.warehouse.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MaterielSpecServiceTest extends BaseTest {

	@Autowired
	private MaterielSpecService materielSpecService;

	@Test
	public void testInsert() {
		MaterielSpec m = new MaterielSpec();
		m.setSpecName("网络");
		m.setRemark("网络");
		ResponseResult<MaterielSpec> result = this.materielSpecService.insert(m);
		assertTrue(result.isSuccess());
		m = new MaterielSpec();
		m.setSpecName("颜色");
		m.setRemark("颜色");
		result = this.materielSpecService.insert(m);
		assertTrue(result.isSuccess());
	}

	@Test
	public void testUpdate() {
		ResponseResult<MaterielSpec> querySpec = this.materielSpecService.selectByPrimaryKey(1);
		ResponseResult<MaterielSpec> result = this.materielSpecService.update(querySpec.getData());
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void testQueryByPage() {
		MaterielSpecQuery query = new MaterielSpecQuery();
		query.setCurrPage(1);
		query.setPageSize(10);
		query.setSpecName("内存");
		query.setEnableFlag(0);
		ResponseResult<ResultPager<MaterielSpec>> result = this.materielSpecService.queryByPage(query);
		assertTrue(result.isSuccess());
		assertTrue(result.getData().getData().size()>0);
	}
	
	@Test
	public void testSetEnableFlag() {
		MaterielSpec m = new MaterielSpec();
		m.setId(1);
		m.setEnableFlag(1);
		ResponseResult<MaterielSpec> result = this.materielSpecService.setEnableFlag(m);
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void testDeleteByIds() throws InterruptedException {
		MaterielSpec m = new MaterielSpec();
		m.setSpecName("其它");
		m.setRemark("其它");
		ResponseResult<MaterielSpec> result = this.materielSpecService.insert(m);
		Thread.sleep(10*1000);
		result=this.materielSpecService.deleteByIds(String.valueOf(m.getId()));
		assertTrue(result.isSuccess());
	}
}
