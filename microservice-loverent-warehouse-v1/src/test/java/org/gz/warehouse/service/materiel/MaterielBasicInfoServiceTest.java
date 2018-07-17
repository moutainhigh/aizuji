/**
 * 
 */
package org.gz.warehouse.service.materiel;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.JsonUtils;
import org.gz.warehouse.common.vo.MaterielBasicInfoReq;
import org.gz.warehouse.common.vo.MaterielBasicInfoResp;
import org.gz.warehouse.common.vo.ProductMaterielIntroReq;
import org.gz.warehouse.common.vo.ProductMaterielIntroResp;
import org.gz.warehouse.entity.MaterielBasicImages;
import org.gz.warehouse.entity.MaterielBasicInfo;
import org.gz.warehouse.entity.MaterielBasicInfoQuery;
import org.gz.warehouse.entity.MaterielBasicInfoVO;
import org.gz.warehouse.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

/**
 * @Title:
 * @author hxj
 * @date 2017年12月16日 下午5:25:16
 */
public class MaterielBasicInfoServiceTest extends BaseTest {

	@Autowired
	private MaterielBasicInfoService materielBasicInfoService;

	/**
	 */
	@Test
	public void testInsert() {
		MaterielBasicInfo m = new MaterielBasicInfo();
		List<MaterielBasicImages> attaList = new ArrayList<MaterielBasicImages>();
		attaList.add(new MaterielBasicImages("http://127.0.0.1/fdafa"));
		m.setAttaList(attaList);
		m.setCreateBy(1L);
		m.setUpdateBy(m.getCreateBy());
		//m.setMaterielBarCode("P0000000222");
		m.setMaterielBrandId(1);
		m.setMaterielClassId(4);
		m.setMaterielName("Apple5S");
		m.setMaterielModelId(2);
		m.setMaterielUnitId(1);
		m.setRemark("备注备注");
		m.setSpecBatchNo("S00012");
		ResponseResult<MaterielBasicInfo> result = this.materielBasicInfoService.insert(m);
		assertTrue(result.isSuccess() && result.getData().getMaterielBarCode().equals(m.getMaterielModelId() + m.getMaterielCode()));
	}

	@Test
	public void testGetDetail() {
		ResponseResult<MaterielBasicInfo> result = this.materielBasicInfoService.getDetail(3L);
		assertTrue(result.isSuccess());
		System.err.println(result);
	}
	
	@Test
	public void testQueryPage() {
		MaterielBasicInfoQuery query = new MaterielBasicInfoQuery();
		query.setCurrPage(1);
		query.setPageSize(10);
		ResponseResult<ResultPager<MaterielBasicInfoVO>>  result = this.materielBasicInfoService.queryByPage(query);
		System.err.println(result.getData());
		assertTrue(result.isSuccess());
		System.err.println(result.getData().getTotalNum());
	}
	
	@Test
	public void testUpdate() {
		ResponseResult<MaterielBasicInfo> result = this.materielBasicInfoService.getDetail(3L);
		MaterielBasicInfo m = result.getData();
		Long orgVersion = m.getVersion();//原始版本
		m.setUpdateBy(1L);
		result = this.materielBasicInfoService.update(m);
		assertTrue(result.isSuccess());
		result = this.materielBasicInfoService.getDetail(3L);
		m = result.getData();
		Long newVersion = m.getVersion();//新版本
		assertTrue(newVersion.longValue()==orgVersion.longValue()+1L);
	}
	
	@Test
	public void testQueryMainMaterielBasicInfo() {
		MaterielBasicInfoReq req = new MaterielBasicInfoReq();
		req.setMaterielModelId(2);
		req.setMaterielFlag(1);
		System.err.println(req);
		ResponseResult<MaterielBasicInfoResp> result = ResponseResult.buildSuccessResponse(this.materielBasicInfoService.queryMainMaterielBasicInfo(req));
		System.err.println(result);
	}
	
	@Test
	public void testQueryMainMaterielBasicIntroductionInfo() {
		MaterielBasicInfoReq req = new MaterielBasicInfoReq();
		req.setMaterielModelId(2);
		req.setMaterielFlag(1);
		System.err.println(req);
		ResponseResult<MaterielBasicInfoResp> result = ResponseResult.buildSuccessResponse(this.materielBasicInfoService.queryMainMaterielBasicIntroductionInfo(req));
		System.err.println(result);
	}
	
	@Test
	public void testQueryProductMaterielIntros() {
		long start = System.currentTimeMillis();
		ProductMaterielIntroReq q = new ProductMaterielIntroReq();
		q.setProductIds(Lists.newArrayList(1L,2L,7L));
		List<ProductMaterielIntroResp>  list = this.materielBasicInfoService.queryProductMaterielIntros(q);
		String result = JsonUtils.toJsonString(ResponseResult.buildSuccessResponse(list));
		System.err.println("result:"+result);
		long end = System.currentTimeMillis();
		System.err.println("take "+(end-start)+" ms");
	}

}
