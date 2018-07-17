/**
 * 
 */
package org.gz.warehouse.service.materiel;

import static org.junit.Assert.assertTrue;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.MaterielBrand;
import org.gz.warehouse.entity.MaterielBrandQuery;
import org.gz.warehouse.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MaterielBrandServiceTest extends BaseTest{

	@Autowired
	private MaterielBrandService materielBrandService;
	

	@Test
	public void testInsert() {
		MaterielBrand m = new MaterielBrand();
		m.setBrandName("apple");
		m.setBrandCode("apple");
		m.setRemark("apple");
		ResponseResult<MaterielBrand> result = this.materielBrandService.insert(m);
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void testInsert2() {
		MaterielBrand m = new MaterielBrand();
		m.setBrandName("apple");
		m.setBrandCode("apple");
		m.setRemark("apple");
		ResponseResult<MaterielBrand> result = this.materielBrandService.insert(m);
		System.err.println("result:"+result.toString());
		assertTrue(!result.isSuccess());
	}

	@Test
	public void testUpdate() {
		MaterielBrand m = new MaterielBrand();
		m.setId(1L);
		m.setBrandName("apple");
		m.setBrandCode("applex");
		m.setRemark("applex");
		ResponseResult<MaterielBrand> result = this.materielBrandService.update(m);
		assertTrue(result.isSuccess());
	}


	@Test
	public void testSelectByPrimaryKey() {
		ResponseResult<MaterielBrand> result = this.materielBrandService.selectByPrimaryKey(1L);
		System.err.println(result);
		assertTrue(result.isSuccess());
	}

	@Test
	public void testQueryByPage() {
		MaterielBrandQuery m = new MaterielBrandQuery();
		m.setBrandCode("applex");
		m.setBrandName("apple");
		m.setCurrPage(1);
		m.setPageSize(10);
		m.setId(1L);
		System.err.println(m);
		ResponseResult<ResultPager<MaterielBrand>>  result = this.materielBrandService.queryByPage(m);
		System.err.println(result);
	}
	
	@Test
	public void testDeleteByIds() {
		ResponseResult<MaterielBrand> result = this.materielBrandService.deleteByIds("1,2");
		assertTrue(result.isSuccess());
	}

}
