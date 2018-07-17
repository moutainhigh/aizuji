/**
 * 
 */
package org.gz.warehouse.service.materiel;

import static org.junit.Assert.*;

import java.util.List;

import org.gz.common.constants.ZTreeSimpleNode;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.MaterielClass;
import org.gz.warehouse.entity.MaterielClassBrand;
import org.gz.warehouse.entity.MaterielClassBrandReq;
import org.gz.warehouse.entity.MaterielClassBrandVO;
import org.gz.warehouse.entity.MaterielClassQuery;
import org.gz.warehouse.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MaterielClassServiceTest extends BaseTest {

	@Autowired
	private MaterielClassService materielClassService;
	
	@Test
	public void testInsert() {
		MaterielClass m = new MaterielClass();
		m.setTypeName("手机2");
		m.setParentId(0);
		System.err.println(m);
		ResponseResult<MaterielClass> result = this.materielClassService.insert(m);
		assertTrue(result.isSuccess());
	}

	@Test
	public void testUpdate1() {
		MaterielClass m = new MaterielClass();
		m.setId(7);
		m.setTypeName("手机22");
		m.setParentId(0);
		System.err.println(m);
		ResponseResult<MaterielClass> result = this.materielClassService.update(m);
		System.err.println(result);
	}

	/**
	 * 测试同级分类名称重复
	 */
	@Test
	public void testUpdate2() {
		MaterielClass m = new MaterielClass();
		m.setTypeName("电脑");
		m.setParentId(0);
		ResponseResult<MaterielClass> result = this.materielClassService.insert(m);
		assertTrue(!result.isSuccess());
	}
	
	/**
	 * 测试不同层级分类名称重复插入
	 */
	@Test
	public void testInsert1() {
		MaterielClassQuery query = new MaterielClassQuery();
		query.setTypeName("电脑");
		query.setTypeLevel(2);
		ResponseResult<ResultPager<MaterielClass>>  queryResult = this.materielClassService.queryByPage(query);
		assertTrue(queryResult.isSuccess());
		MaterielClass queryMaterielClass = queryResult.getData().getData().get(0);
		MaterielClass m = new MaterielClass();
		m.setTypeName("电脑");
		m.setParentId(queryMaterielClass.getId());
		ResponseResult<MaterielClass> result = this.materielClassService.insert(m);
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void testSelectByPrimaryKey() {
		ResponseResult<MaterielClass> result = this.materielClassService.selectByPrimaryKey(7);
		System.err.println(result);
		assertTrue(result.isSuccess()&&result.getData().getId().intValue()==1);
	}

	@Test
	public void testQueryMaterielClassList() {
		MaterielClass mc = new MaterielClass();
		this.materielClassService.queryMaterielClassList(mc);
	}

	@Test
	public void testQueryAllMaterielClassList() {
		this.materielClassService.queryAllMaterielClassList();
	}
	
	@Test
	public void testQueryByPage() {
		MaterielClassQuery query = new MaterielClassQuery();
		query.setTypeName("手机");
		query.setTypeLevel(2);
		System.err.println(query);
		ResponseResult<ResultPager<MaterielClass>>  queryResult = this.materielClassService.queryByPage(query);
		assertTrue(queryResult.isSuccess());
		System.err.println(queryResult);
	}

	@Test
	public void testDeleteByIds() {
		ResponseResult<MaterielClass> result = this.materielClassService.deleteByIds("5,6");
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void testConfigBrand() {
		//查询分类下所有可用品牌
		ResponseResult<List<MaterielClassBrandVO>> result = this.materielClassService.queryBrands(4);
		System.err.println(result.getData());//只关联apple
		//测试全量关联
		MaterielClassBrandReq m = new MaterielClassBrandReq();
		m.setMaterielClassId(4);
		m.setMaterielBrandIds("1,2");
		ResponseResult<MaterielClassBrand> r = this.materielClassService.configBrand(m);
		assertTrue(r.isSuccess());
		result = this.materielClassService.queryBrands(4);
		System.err.println(result.getData());
		//测试无关联删除
		m = new MaterielClassBrandReq();
		m.setMaterielClassId(4);
		m.setMaterielBrandIds("1");
		r = this.materielClassService.configBrand(m);
		assertTrue(r.isSuccess());
		//测试有关联删除
	}

	@Test
	public void testConfigBrand1() {
		//测试无关联删除
		MaterielClassBrandReq m = new MaterielClassBrandReq();
		m.setMaterielClassId(4);
		m.setMaterielBrandIds("1");
		ResponseResult<MaterielClassBrand> r = this.materielClassService.configBrand(m);
		assertTrue(r.isSuccess());
	}
	
	@Test
	public void testConfigBrand2() {
		//测试有关联删除
		MaterielClassBrandReq m = new MaterielClassBrandReq();
		m.setMaterielClassId(4);
		m.setMaterielBrandIds("");
		ResponseResult<MaterielClassBrand> r = this.materielClassService.configBrand(m);
		assertTrue(r.isSuccess()==false);
	}
	
	@Test
	public void testGetTree() {
		MaterielClass materielClass = this.materielClassService.getTree(0);
		System.err.println(materielClass);
	}
	
	@Test
	public void testGetZTree() {
		ZTreeSimpleNode node = this.materielClassService.getZTree(0);
		System.err.println(node);
	}
}
