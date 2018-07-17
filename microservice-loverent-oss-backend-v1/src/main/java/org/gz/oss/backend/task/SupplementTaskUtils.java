package org.gz.oss.backend.task;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.oss.common.entity.ActivityCommodity;
import org.gz.oss.common.entity.ShopwindowCommodityRelation;
import org.gz.oss.common.feign.IProductService;
import org.gz.oss.common.service.ShopwindowService;
import org.gz.warehouse.common.vo.SaleProductReq;
import org.gz.warehouse.common.vo.SaleProductResp;
import org.gz.warehouse.common.vo.SaleProductShortResp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 自动补仓定时任务
 * @author daiqingwen
 * @date 2018/3/24 上午 11:19
 */

@Component
@Slf4j
@Transactional
public class SupplementTaskUtils {

    @Autowired
    public ShopwindowService shopwindowService;

    @Autowired
    public IProductService iProductService;

    //@Scheduled(cron = "0 0/2 * * * ?") // 每15分钟刷新一次
    public ResponseResult<?> supplementTask(){
        log.info("补仓定时任务启动........");
        long startTime = System.currentTimeMillis();    //获取开始时间
        try {
            //获取售卖橱窗下的所有商品
            List<ShopwindowCommodityRelation> list = this.shopwindowService.getSellShopwindowCommodity();
            if(CollectionUtils.isEmpty(list)){
                return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), "定时补仓没有查询出售卖橱窗位下的商品", null);
            }
            List<Long> productId = new ArrayList<>();
            for (int i = 0 ; i < list.size() ; i++){
                ShopwindowCommodityRelation sh = list.get(i);
                productId.add(sh.getProductId());
            }
            SaleProductReq sq = new SaleProductReq();
            sq.setProductIds(productId);
//            sq.setCurrPage(1);
//           sq.setPageSize(Integer.MAX_VALUE);
            //调用自动补仓的接口
            ResponseResult<?> result =  iProductService.queryAvailableSplitProductList(sq);
            ResultPager<SaleProductResp> resultPage = (ResultPager<SaleProductResp>) result.getData();
            if(!result.isSuccess() && null == resultPage.getData()){
                return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), "暂无无数据补仓", null);
            }
            List<SaleProductResp> dataList = resultPage.getData();
            List<SaleProductShortResp> queryDataList =  Lists.transform(dataList, new Function<SaleProductResp, SaleProductShortResp>() {
                @Override
                public SaleProductShortResp apply(SaleProductResp input) {
                    SaleProductShortResp destResp= new SaleProductShortResp();
                    BeanUtils.copyProperties(input,destResp);
                    return destResp;
                }
            });

            List<SaleProductShortResp> dbDataList =  Lists.transform(list, new Function<ShopwindowCommodityRelation, SaleProductShortResp>() {
                @Override
                public SaleProductShortResp apply(ShopwindowCommodityRelation input) {
                    SaleProductShortResp destResp= new SaleProductShortResp();
                    BeanUtils.copyProperties(input,destResp);
                    return destResp;
                }
            });
            Set<SaleProductShortResp> queryDataSet = Sets.newLinkedHashSet(queryDataList);//调用库存返回的结果
            Set<SaleProductShortResp> dbDataSet = Sets.newLinkedHashSet(dbDataList);//运营系统表数据
            Set<SaleProductShortResp> needAddSet = null;
         //   Set<SaleProductShortResp> needDelSet = null;
           // List<ActivityCommodity> resultList = new ArrayList<>();
            List<ActivityCommodity> random = new ArrayList<>();
            List<ActivityCommodity> matchResult = new ArrayList<>();
            ResponseResult<?> supplementResult = null;
            if(CollectionUtils.isNotEmpty(queryDataList)){
                needAddSet = Sets.difference(queryDataSet,dbDataSet);//需要新增的记录
                List<SaleProductShortResp> needAddlist = Lists.newArrayList(needAddSet);
                //将补仓查询出的数据和原数据做对比，如果能匹配上原数据，则不进行补仓；如果不能，进行补仓
                matchResult = matchData(needAddlist,list);
                //如果没有匹配到原数据，或者只能匹配原数据其中几条数据，则随机取几个数据进行补仓
                if (matchResult.size() == 0 || matchResult.size() < list.size()){
                    Collections.shuffle(needAddlist);
                    random = getRandomData(needAddlist,list);
                    supplementResult = this.shopwindowService.supplement(random);
                }else{
                    log.info("当前售卖橱窗位的商品未被售卖，则不进行补仓操作，程序结束");
                    return ResponseResult.buildSuccessResponse();
                }
            }


            //先删除旧数据，然后再将补仓的数据
//            ResponseResult<?> supplementResult = this.shopwindowService.supplement(resultList);
            if(!supplementResult.isSuccess() && null == supplementResult.getData()){
                return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), "定时补仓失败", null);
            }
            log.info("定时补仓成功，程序结束");
            long endTime = System.currentTimeMillis();    //获取结束时间
            log.info("补仓程序运行时间为{" + (endTime - startTime) + "ms}");    //输出程序运行时间
            return ResponseResult.buildSuccessResponse();
        } catch (Exception e){
            log.error("定时补仓操作失败：{}",e);
            return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(), "定时补仓失败", null);
        }

    }

    /**
     * 匹配数据
     * @param needAddlist  补仓查询的数据
     * @param originData 原数据
     * @return ActivityCommodity
     */
    private List<ActivityCommodity> matchData(List<SaleProductShortResp> needAddlist ,  List<ShopwindowCommodityRelation> originData){
        List<ActivityCommodity> resultList = new ArrayList<>();
        for (int i = 0 ; i < originData.size();i++){
            if(originData.size() == resultList.size()){
                break;
            }
            ActivityCommodity ac = new ActivityCommodity();
            ShopwindowCommodityRelation sc = originData.get(i);
            for (int j = 0 ; j < needAddlist.size(); j++){
                SaleProductShortResp sp = needAddlist.get(j);
                if(sp.getProductId() == sc.getProductId() && sp.getImeiNo().equals(sc.getImeiNo()) && sp.getSnNo().equals(sc.getSnNo())){
                    ac.setPosition(sc.getPosition());
                    ac.setShopwindowId(sc.getShopwindowId());
                    ac.setProductId(sp.getProductId());
                    ac.setImeiNo(sp.getImeiNo());
                    ac.setSnNo(sp.getSnNo());
                    ac.setType(sc.getType());
                    ac.setId(sc.getActivityId());
                    ac.setModelId(sc.getModelId());
                    resultList.add(ac);
                    break;
                }
            }
        }
        return resultList;
    }

    /**
     * 无匹配数据，则随机取几条数据
     * @param needAddlist  补仓查询的数据
     * @param originData 原数据
     * @return ActivityCommodity
     */
    private List<ActivityCommodity> getRandomData(List<SaleProductShortResp> needAddlist ,  List<ShopwindowCommodityRelation> originData){
        List<ActivityCommodity> resultList = new ArrayList<>();
        for (int i = 0 ; i < needAddlist.size();i++){
            if(originData.size() == resultList.size()){
                break;
            }
            ActivityCommodity ac = new ActivityCommodity();
            SaleProductShortResp sc = needAddlist.get(i);
            ac.setProductId(sc.getProductId());
            ac.setImeiNo(sc.getImeiNo());
            ac.setSnNo(sc.getSnNo());
            ac.setPosition(originData.get(i).getPosition());
            ac.setShopwindowId(originData.get(i).getShopwindowId());
            ac.setType(originData.get(i).getType());
            ac.setId(originData.get(i).getActivityId());
            ac.setModelId(originData.get(i).getModelId());
            resultList.add(ac);

        }
        return resultList;
    }

//    private List<ActivityCommodity> getRandomData( List<ActivityCommodity> resultList,List<SaleProductShortResp> needAddlist,List<ShopwindowCommodityRelation> originData){
//        //将匹配上的数据从补仓返回的数据中移除
//        for (int i = 0;i < resultList.size();i++){
//            if(resultList.size() == originData.size()){
//                break;
//            }
//            ActivityCommodity ac = resultList.get(i);
//            for (int j =0; j < needAddlist.size(); j++){
//                SaleProductShortResp sp = needAddlist.get(j);
//                if(sp.getProductId() == ac.getProductId() && sp.getImeiNo().equals(ac.getImeiNo()) && sp.getSnNo().equals(ac.getSnNo())){
//                    needAddlist.remove(j);
//                    break;
//                }
//            }
//        }
//        //补充数据
//        for (int i = 0 ; i < needAddlist.size(); i++){
//            ActivityCommodity saveData = new ActivityCommodity();
//            SaleProductShortResp sp = needAddlist.get(i);
//            saveData.setProductId(sp.getProductId());
//            saveData.setImeiNo(sp.getImeiNo());
//            saveData.setSnNo(sp.getSnNo());
//            resultList.add(saveData);
//            if(resultList.size() == originData.size()){
//                break;
//            }
//        }
//
//        //移除原数据中和匹配数据相同的数据
//        for (int i = 0 ; i < resultList.size(); i++){
//            ActivityCommodity ac = resultList.get(i);
//            for (int j = 0 ; j < originData.size();j++){
//                ShopwindowCommodityRelation sc = originData.get(j);
//                if(ac.getProductId() == ac.getProductId() && ac.getImeiNo().equals(ac.getImeiNo()) && ac.getSnNo().equals(ac.getSnNo())){
//                    originData.remove(j);
//                    break;
//                }
//            }
//        }
//
//        //填充数据
//        for (int i = 0 ; i < resultList.size(); i++){
//            ActivityCommodity ac = resultList.get(i);
//            for (int j = 0 ; j < originData.size();j++){
//                ShopwindowCommodityRelation sc = originData.get(i);
//                if(null == ac.getShopwindowId() || null == ac.getId()){
//                    ac.setPosition(sc.getPosition());
//                    ac.setShopwindowId(sc.getShopwindowId());
//                    ac.setType(sc.getType());
//                    ac.setId(sc.getActivityId());
//                    ac.setModelId(sc.getModelId());
//                    originData.remove(j);
//                    break;
//                }
//            }
//        }
//        return resultList;
//    }


}
