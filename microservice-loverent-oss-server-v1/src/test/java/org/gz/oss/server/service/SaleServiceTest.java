package org.gz.oss.server.service;

import lombok.extern.slf4j.Slf4j;
import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.entity.MaterielModelQuery;
import org.gz.oss.common.feign.IProductService;
import org.gz.oss.server.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * @author WenMing.Zhou
 * @Description: ${todo}
 * @date 2018/3/22 16:13
 */
@Slf4j
public class SaleServiceTest extends BaseTest {

    @Autowired
    private IProductService productService;

    @Test
    public void querySpecValue(){
        Map<String,Object> map=new HashMap<>();
        map.put("imeiNo","6666imei");
        map.put("snNo","6666sn");
        List<Map<String,Object>> list=new ArrayList<>();
        list.add(map);
        ResponseResult<?> saleCommodityInfo = productService.getSaleCommodityInfo(list);

        assertTrue(saleCommodityInfo.isSuccess());
    }



    @Test
    public void queryMaterielBrandListByClassId(){
        ResponseResult<?> responseResult = productService.queryMaterielBrandListByClassId(1);
    }

    @Test
    public void queryMaterielModelPicListByBrandId(){
        MaterielModelQuery materielModelQuery =new MaterielModelQuery();
        materielModelQuery.setBrandId(1);
        materielModelQuery.setCurrPage(1);
        materielModelQuery.setPageSize(1);
        ResponseResult<?> responseResult = productService.queryMaterielModelPicListByBrandId(materielModelQuery);
    }

}
