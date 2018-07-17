package org.gz.warehouse.service.materiel;

import static org.junit.Assert.assertTrue;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.MaterielUnit;
import org.gz.warehouse.entity.MaterielUnitQuery;
import org.gz.warehouse.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MaterielUnitServiceTest extends BaseTest {

	@Autowired
	private MaterielUnitService materielUnitService;

	@Test(timeout=ONE_SECOND)
	public void testInsert() {
		MaterielUnit m = new MaterielUnit();
		m.setUnitName("批");
		m.setUnitCode("PPP");
		m.setEnableFlag(1);
		m.setRemark("用于手机");
		ResponseResult<MaterielUnit> result =  materielUnitService.insert(m);
		assertTrue(result.isSuccess());
	}

	@Test(timeout=ONE_SECOND)
	public void testUpdate() {
		MaterielUnit m = new MaterielUnit();
		m.setId(1);
		m.setUnitName("台");
		m.setUnitCode("PCS");
		m.setEnableFlag(1);
		m.setRemark("用于电脑");
		ResponseResult<MaterielUnit> result =  materielUnitService.update(m);
		assertTrue(result.isSuccess());
	}
	
	@Test(timeout=ONE_SECOND)
	public void testSetEnableFlag() {
		MaterielUnit m = new MaterielUnit();
		m.setId(1);
		m.setEnableFlag(0);
		ResponseResult<MaterielUnit> result =  materielUnitService.setEnableFlag(m);
		assertTrue(result.isSuccess());
	}
	
	@Test(timeout=ONE_SECOND)
	public void testSelectByPrimaryKey() {
		ResponseResult<MaterielUnit> result =  materielUnitService.selectByPrimaryKey(1);
		assertTrue(result.isSuccess()&&result.getData().getId().intValue()==1);
	}
	
	@Test(timeout=ONE_SECOND)
	public void testQueryByPage() {
		MaterielUnitQuery m = new MaterielUnitQuery();
		m.setCurrPage(1);
		m.setPageSize(1);
//		m.setEnableFlag(0);
		m.setUnitName("台");
		ResponseResult<ResultPager<MaterielUnit>> result =  materielUnitService.queryByPage(m);
		//assertTrue(result.isSuccess()&&result.getData().getData().size()==1);
		System.err.println(result);
	}
	
	@Test(timeout=ONE_SECOND)
	public void testDeleteByIds() {
		ResponseResult<MaterielUnit> result =  materielUnitService.deleteByIds("1,2,3");
		assertTrue(result.isSuccess());
	}
}
