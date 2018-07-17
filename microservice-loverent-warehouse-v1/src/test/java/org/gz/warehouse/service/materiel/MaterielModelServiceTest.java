/**
 * 
 */
package org.gz.warehouse.service.materiel;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.common.vo.MaterielModelResp;
import org.gz.warehouse.entity.MaterielModel;
import org.gz.warehouse.entity.MaterielModelQuery;
import org.gz.warehouse.entity.MaterielModelSpec;
import org.gz.warehouse.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月16日 下午2:17:15
 */
public class MaterielModelServiceTest extends BaseTest{

	@Autowired
	private MaterielModelService materielModelService;
	
	/**
	 * 
	* @Description: 测试正常插入
	 */
	@Test
	public void testInsert() {
		MaterielModel m = new MaterielModel();
		m.setMaterielClassId(4);
		m.setMaterielBrandId(1);
		m.setMaterielModelName("iPhone 9");
		m.setMaterielModelDesc("iPhone 9");
		List<MaterielModelSpec> modelSpecList=new ArrayList<MaterielModelSpec>();
		modelSpecList.add(new MaterielModelSpec(1,"4G"));//内存
		modelSpecList.add(new MaterielModelSpec(2,"全网通"));//网络
		modelSpecList.add(new MaterielModelSpec(3,"金色"));//颜色
		m.setModelSpecList(modelSpecList);
		ResponseResult<MaterielModel> result = this.materielModelService.insert(m);
		assertTrue(result.isSuccess());
	}
	
	/**
	 * 
	* @Description: 测试重复插入
	 */
	@Test(expected=ServiceException.class)
	public void testInsert2() {
		MaterielModel m = new MaterielModel();
		m.setMaterielClassId(4);
		m.setMaterielBrandId(1);
		m.setMaterielModelName("iPhone 9");
		m.setMaterielModelDesc("iPhone 9");
		List<MaterielModelSpec> modelSpecList=new ArrayList<MaterielModelSpec>();
		modelSpecList.add(new MaterielModelSpec(1,"4G"));//内存
		modelSpecList.add(new MaterielModelSpec(1,"4G"));//网络
		modelSpecList.add(new MaterielModelSpec(3,"金色"));//颜色
		m.setModelSpecList(modelSpecList);
		this.materielModelService.insert(m);
	}
	
	@Test
	public void testSelectByPrimaryKey() {
		ResponseResult<MaterielModel> result = this.materielModelService.selectByPrimaryKey(2);
		assertTrue(result.isSuccess());
		System.err.println(result.getData());
	}
	
	@Test
	public void testUpdate1() {
		ResponseResult<MaterielModel> result = this.materielModelService.selectByPrimaryKey(2);
		MaterielModel m = result.getData();
		result = this.materielModelService.update(m);
		assertTrue(result.isSuccess());
	}
	
	

	@Test
	public void testSetEnableFlag() {
		MaterielModel m = new MaterielModel();
		m.setId(7);
		m.setEnableFlag(1);
		ResponseResult<MaterielModel> result =  this.materielModelService.setEnableFlag(m);
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void testQueryMaterielModelListByBrandId() {
		List<MaterielModel> list=this.materielModelService.queryMaterielModelListByBrandId(1);
		assertTrue(list!=null&&list.size()>0);
	}

	@Test
	public void testQueryByPage() {
		MaterielModelQuery m = new MaterielModelQuery();
		m.setMaterielClassId(4);
		m.setMaterielBrandId(1);
		m.setEnableFlag(1);
		m.setMaterielModelName("iPhone 9");
		ResponseResult<ResultPager<MaterielModel>> result = this.materielModelService.queryByPage(m);
		assertTrue(result.isSuccess()&&result.getData().getData().size()>0);
	}

	@Test
	public void testDeleteByIds() {
		ResponseResult<MaterielModel> result = this.materielModelService.deleteByIds("7");
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void testQueryModelList() {
		String ids="1,2";
		ResponseResult<List<MaterielModelResp>> result = this.materielModelService.queryModelList(ids);
		System.err.println(result);
		assertTrue(result.isSuccess());
	}
}
