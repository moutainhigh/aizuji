package org.gz.oss.server.service;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.entity.ActivityCommodity;
import org.gz.oss.common.entity.ActivityTimeVO;
import org.gz.oss.common.entity.ShopwindowVO;
import org.gz.oss.common.service.ShopwindowService;
import org.gz.oss.server.test.BaseTest;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.ProductInfoQvo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 橱窗位单元测试
 * @author daiqingwen
 * @Date 2018-03-14 下午20:02
 */
@Slf4j
public class ShopwindowServiceTest  extends BaseTest {

    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public ShopwindowService shopwindowService;

    /**
     *修改橱窗信息
     */
    @Test
    public void updateTest(){
        ShopwindowVO vo = new ShopwindowVO();
        vo.setId(1);
        vo.setImage("www.tmall.com");
        vo.setName("促销活动橱窗");
        vo.setPosition(2);
        vo.setType(20);
        ResponseResult<?> result =  this.shopwindowService.update(vo);
        log.info("修改橱窗信息结果为：" + result.getErrCode() + ":" + result.getErrMsg());
        assertTrue(result.isSuccess());
    }

    /**
     * 新增活动时间
     */
    @Test
    @SneakyThrows
    public void insertActivityTest(){
        ActivityCommodity vo = new ActivityCommodity();
        vo.setShopwindowId(1);
        vo.setStartTime("2018-04-01 07:00:00");
        vo.setEndTime("2018-04-01 07:00:00");
        vo.setCreateTime(new Date());
        ResponseResult<?> result =  this.shopwindowService.insertActivityTime(vo);
        log.info("新增活动时间结果为：" + result.getErrCode() + ":" + result.getErrMsg());
        assertTrue(result.isSuccess());
    }

    /**
     * 修改活动时间
     */
    @Test
    @SneakyThrows
    public void updateActivityTest(){
        ActivityCommodity vo = new ActivityCommodity();
        vo.setId(1);
        vo.setShopwindowId(1);
        vo.setStartTime("2018-04-01 07:00:00");
        vo.setEndTime("2018-04-01 07:00:00");
        ResponseResult<?> result =  this.shopwindowService.updateActivityTime(vo);
        log.info("修改活动时间结果为：" + result.getErrCode() + ":" + result.getErrMsg());
        assertTrue(result.isSuccess());
    }

    /**
     * 删除活动
     */
    @Test
    public void deleteActivityTest(){
        Long id = 1L;
        String commodityId = "1,2";
        ResponseResult<?> result = this.shopwindowService.deleteActivity(id);
        log.info("删除活动结果为：" + result.getErrCode() + ":" + result.getErrMsg());
        assertTrue(result.isSuccess());
    }

    /**
     * 新增促销橱窗活动商品
     */
    @Test
    public void insertActivityCommodityTest(){
        ActivityCommodity sh = new ActivityCommodity();
        sh.setImeiNo("P00002397865742");
        sh.setPosition(3);
        sh.setProductId(23543L);
        sh.setShopwindowId(2);
        sh.setSnNo("werfwe23423432432");
        sh.setType(10);
        ResponseResult<?> result = this.shopwindowService.insertActivityCommodity(sh);
        log.info("新增活动结果为：" + result.getErrCode() + ":" + result.getErrMsg());
        assertTrue(result.isSuccess());
    }

    /**
     * 根据活动商品Id，修改活动商品
     */
    @Test
    public void updateActivityCommodityTest(){
        ActivityCommodity sh = new ActivityCommodity();
        sh.setCommodityId(2L);
        sh.setImeiNo("P0000232132");
        sh.setPosition(1);
        sh.setProductId(2323L);
        sh.setSnNo("werfwe23423432432");
        sh.setType(10);
        ResponseResult<?> result = this.shopwindowService.updateActivityCommodity(sh);
        log.info("根据活动商品Id，修改活动商品结果为：" + result.getErrCode() + ":" + result.getErrMsg());
        assertTrue(result.isSuccess());
    }

    /**
     * 获取活动列表
     */
    @Test
    public void queryActivityListTest(){
        ResponseResult<?> result = this.shopwindowService.queryActivityList();
        log.info("获取活动列表结果为：" + result.getErrCode() + ":" + result.getErrMsg());
        assertTrue(result.isSuccess());
    }

    /**
     * 根据活动ID，获取活动对应商品
     */
    @Test
    public void queryActivityCommodityTest(){
        int id = 8;
        ResponseResult<?> result = this.shopwindowService.queryActivityCommodity(id);
        log.info("根据活动ID，获取活动对应商品结果为：" + result.getErrCode() + ":" + result.getErrMsg());
        assertTrue(result.isSuccess());
    }

    /**
     * 根据橱窗ID，获取橱窗对应的商品
     */
    @Test
    public void queryShopwindowCommodityByIdTest(){
        int id = 3;
        ResponseResult<?> result = this.shopwindowService.queryShopwindowCommodityById(id);
        log.info("根据橱窗ID，获取橱窗对应的商品结果为：" + result.getErrCode() + ":" + result.getErrMsg());
        assertTrue(result.isSuccess());

    }

    /**
     * 查询橱窗和对应的商品
     */
    @Test
    public void findShopwindowCount(){
        ResponseResult<?> result = this.shopwindowService.findShopwindowCount();
        log.info("根据橱窗ID，获取橱窗对应的商品结果为：" + result.getErrCode() + ":" + result.getErrMsg());
        assertTrue(result.isSuccess());
    }


    /**
     * APP-根据产品id活动售卖商品详情
     */
    @Test
    public void getSaleCommodityByIdTest(){
        ProductInfo in = new ProductInfo();
        in.setId(3L);
        ResponseResult<?> result = this.shopwindowService.getSaleCommodityById(in);
        log.info("APP-根据产品id活动售卖商品详情结果为：" + result.getErrCode() + ":" + result.getErrMsg());
        System.out.print(">>>>>>>>>>>>>>>>"+result);
        assertTrue(result.isSuccess());
    }

    /**
     * APP-获取首页信息
     */
    @Test
    public void getIndexInfo(){
        ResponseResult<?> result = this.shopwindowService.findIndexInfo();
        log.info("APP-获取首页详情结果为：" + result.getErrCode() + ":" + result.getErrMsg());
        System.out.print(">>>>>>>>>>>>>>>>"+result);
        assertTrue(result.isSuccess());
    }

    /**
     * 根据多个型号ID，获取最低月租金
     */
    @Test
    public void getAmountByModelId() {
        ProductInfoQvo vo = new ProductInfoQvo();
        List<Long> list = new ArrayList<>();
        Long a = 1L;
        Long b = 2L;
        Long c = 3L;
        list.add(a);
        list.add(b);
        list.add(c);
        vo.setMaterielModelIdList(list);
        ResponseResult<?> result = this.shopwindowService.getAmountByModelId(vo);
        System.out.print(">>>>>>>>>>>>>>>>"+result);
        log.info("APP-根据产品id活动售卖商品详情结果为：" + result.getErrCode() + ":" + result.getErrMsg());
        assertTrue(result.isSuccess());
    }


}