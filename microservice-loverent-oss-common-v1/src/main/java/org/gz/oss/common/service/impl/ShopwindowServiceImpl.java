package org.gz.oss.common.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.AssertUtils;
import org.gz.common.utils.StringUtils;
import org.gz.oss.common.Enum.ActivityStatus;
import org.gz.oss.common.Enum.CommodityType;
import org.gz.oss.common.Enum.ShopwindowType;
import org.gz.oss.common.dao.ShopwindowDao;
import org.gz.oss.common.entity.*;
import org.gz.oss.common.feign.IMaterielService;
import org.gz.oss.common.feign.IProductService;
import org.gz.oss.common.service.ShopwindowService;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.ProductInfoQvo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopwindowServiceImpl implements ShopwindowService{

    @Autowired
    public ShopwindowDao shopwindowDao;

    @Autowired
    public IProductService productService;

    @Autowired
    public IMaterielService materielService;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseResult<?> update(ShopwindowVO sho) {
        if(this.shopwindowDao.update(sho) <= 0){
            return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(), ResponseStatus.DATA_UPDATED_ERROR.getMessage(), null);
        }
        return ResponseResult.buildSuccessResponse();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseResult<?> insertActivityTime(ActivityCommodity at) {
        //判断当前活动时间是否合理
         List<ActivityTime> list = this.shopwindowDao.queryActivityTime(at);
         if(list.size() > 0){
             return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), "该活动时间已存在其他活动时间中", null);
         }
        at.setCreateTime(new Date());
        this.shopwindowDao.insertActivityTime(at);
        return ResponseResult.buildSuccessResponse();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseResult<?> updateActivityTime(ActivityCommodity at) {
        //查出需要修改活动的时间，跟当前活动做对比， 如果当前时间存在于活动时间中，则不予修改
        ActivityTime activityTime = this.shopwindowDao.queryActivityTimeById(at);
        Date current = new Date();
        if(current.getTime() >= activityTime.getStartTime().getTime() && current.getTime() <= activityTime.getEndTime().getTime()){
            return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), "该活动已开始不能做修改", null);
        }
        //修改活动时间
        this.shopwindowDao.updateActivityTime(at);
        return ResponseResult.buildSuccessResponse();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseResult<?> deleteActivity(Long id) {
        //先删除活动
        if(this.shopwindowDao.deleteActivity(id) <= 0){
            return ResponseResult.build(ResponseStatus.DATA_DELETED_ERROR.getCode(), ResponseStatus.DATA_DELETED_ERROR.getMessage(), null);
        }
        //删除活动对应的商品
        this.shopwindowDao.deleteActivityCommodity(id);
        return ResponseResult.buildSuccessResponse();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseResult<?> insertActivityCommodity(ActivityCommodity ac) {
        // 判断当前活动是否已存在活动开始时间中
         List<ActivityTime> timeList = this.shopwindowDao.queryActivityTime(ac);
         if(timeList.size() > 0){
            return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), "该活动时间已存在其他活动时间中", null);
        }
        //新增活动时间
        ActivityCommodity activity = ac.getAttaList().get(0);
        ac.setCreateTime(new Date());
        ac.setShopwindowId(activity.getShopwindowId());
        if(this.shopwindowDao.insertActivityTime(ac) <= 0){
            return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), ResponseStatus.DATA_CREATE_ERROR.getMessage(), null);
        }
        List<ActivityCommodity> list = ac.getAttaList();
        for (ActivityCommodity a : list){
            a.setId(ac.getId());
        }
        //新增活动商品
        this.shopwindowDao.insertActivityCommodity(list);
        return ResponseResult.buildSuccessResponse();
    }


    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseResult<?> updateActivityCommodity(ActivityCommodity ac) {
        //查出需要修改活动的时间，跟当前活动做对比， 如果当前时间存在于活动时间中，则不予修改
        ActivityTime activityTime = this.shopwindowDao.queryActivityTimeById(ac);
        Date current = new Date();
        if(current.getTime() >= activityTime.getStartTime().getTime() && current.getTime() <= activityTime.getEndTime().getTime()){
            return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), "该活动已开始不能做修改", null);
        }
        //修改活动时间
        this.shopwindowDao.updateActivityTime(ac);
        //根据活动ID，删除以前的数据
        this.shopwindowDao.deleteActivityCommodity(Long.valueOf(ac.getId()));
        //新增活动商品
        List<ActivityCommodity> comList = ac.getAttaList();
        for (ActivityCommodity activity : comList){
            activity.setId(ac.getId());
        }
        this.shopwindowDao.insertActivityCommodity(comList);
        return ResponseResult.buildSuccessResponse();
    }


    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseResult<?> insertSaleAndLeaseCommodity(ActivityCommodity sh) {
        List<ActivityCommodity> list = sh.getAttaList();
        this.shopwindowDao.insertActivityCommodity(list);
        return ResponseResult.buildSuccessResponse();
    }



    @Override
    public ResponseResult<?> queryActivityList() {
        List<ActivityTime> list = this.shopwindowDao.queryActivityList();
        for (ActivityTime  a : list){
            Date current = new Date();
            Date startTime = a.getStartTime();
            Date endTime = a.getEndTime();
            //判断活动时间是否已开始
            if(current.getTime() >= startTime.getTime() && current.getTime() <= endTime.getTime()){
                a.setStatus(ActivityStatus.START.getCode());
            }else if(current.getTime() > endTime.getTime()){
                //判断活动时间是否已结束
                a.setStatus(ActivityStatus.OVER.getCode());
            }else if(current.getTime() < startTime.getTime()){
                //判断活动时间是否未开始
                a.setStatus(ActivityStatus.UNSTART.getCode());
            }
        }
        return ResponseResult.buildSuccessResponse(list);
    }

    @Override
    public ResponseResult<?> queryActivityCommodity(int id) {
        List<ShopwindowCommodityRelation> list = this.shopwindowDao.queryActivityCommodity(id);
        List<ShopwindowCommodityRelation> resultList = getCommodityInfo(list);
        return ResponseResult.buildSuccessResponse(resultList);
    }


    @Override
    public ResponseResult<?> queryShopwindowCommodityById(int id) {
        List<ShopwindowCommodityRelation> list = this.shopwindowDao.queryShopwindowCommodityById(id);
        List<ShopwindowCommodityRelation> resultList = getCommodityInfo(list);
        return ResponseResult.buildSuccessResponse(resultList);
    }



    private List<ShopwindowCommodityRelation> getCommodityInfo(List<ShopwindowCommodityRelation> list){
        for (int i = 0 ; i < list.size() ; i++){
            ShopwindowCommodityRelation sh = setProductInfo(list.get(i));
            list.get(i).setModelName(sh.getModelName());
            list.get(i).setProductInfo(sh.getProductInfo());
        }
        return list;
    }

    /**
     * 查询产品信息
     * @param a
     * @return ShopwindowCommodityRelation
     */
    private ShopwindowCommodityRelation setProductInfo(ShopwindowCommodityRelation a){
        if(a.getType().intValue() == CommodityType.SALE.getCode()){
            // 如果是关联售卖商品 根据产品id获取产品信息
            ProductInfo pInfo = new ProductInfo();
            pInfo.setId(a.getProductId());
            pInfo.setImeiNo(a.getImeiNo());
            pInfo.setSnNo(a.getSnNo());
            // 根据产品ID、imeiNo、snNo获取售卖商品详细信息
            ResponseResult<ProductInfo> result = this.productService.getSaleCommodityInfoById(pInfo);
           // ResponseResult<ProductInfo> result = productService.getByIdOrPdtNo(pInfo);
            if(result.isSuccess() && null != result.getData()){
                ProductInfo pro = result.getData();
                pro.setMaterielIntroduction("");
                pro.setAttaUrlList(null);
                a.setProductInfo(pro);
            }
        }else if(a.getType().intValue() == CommodityType.LEASE.getCode()){
            // 如果关联租赁商品 根据型号id获取型号名称
            if(a.getModelId() == null){
                return a;
            }
            ResponseResult<?> res = materielService.getMaterielModelDetail(a.getModelId().intValue());
            if (res.isSuccess() && null != res.getData()) {
                Map<String, Object> map = (HashMap) res.getData();
                a.setModelName(map.get("materielModelName").toString());
                Object materielClassIdObj = map.get("materielClassId");
                Object materielBrandIdObj = map.get("materielBrandId");
               // a.setMaterielClassId(materielClassIdObj == null ? null : (Integer) (materielClassIdObj));
             //   a.setMaterielBrandId(materielBrandIdObj == null ? null : (Integer) (materielBrandIdObj));
            }
        }
        return a;
    }



    @Override
    public ResponseResult<?> updateSaleAndLeaseCommodity(ActivityCommodity ac) {
        List<ActivityCommodity> comList = ac.getAttaList();
        //根据橱窗ID，删除以前的数据
        this.shopwindowDao.deleteActivityCommodityByShopwindowId(ac.getShopwindowId());
        this.shopwindowDao.insertActivityCommodity(comList);
        return ResponseResult.buildSuccessResponse();
    }

    @Override
    public List<ShopwindowCommodityRelation> getSellShopwindowCommodity() {
        return this.shopwindowDao.getSellShopwindowCommodity();
    }

    @Override
    public ResponseResult<?> findShopwindowCount() {
        List<Shopwindow> list = this.shopwindowDao.findShopwindowCount();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        for (Shopwindow sh : list){
            if(sh.getType().intValue() == ShopwindowType.ACTIVITY.getCode()){
                Shopwindow shopwindow = this.shopwindowDao.getActivityShopwindowCommodityCount(date);
                if (null != shopwindow){
                    sh.setTotalCount(shopwindow.getTotalCount());
                }else{
                    sh.setTotalCount(0);
                }
            }
        }
        return ResponseResult.buildSuccessResponse(list);
    }

    @Override
    public ResponseResult<?> findAllList() {
        List<Shopwindow> list = this.shopwindowDao.findAllList();
        return ResponseResult.buildSuccessResponse(list);
    }

    @Override
    public ResponseResult<?> findIndexInfo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        //获取所有橱窗位和商品
        List<IndexInfo> info = this.shopwindowDao.getShopwindowAndCommondity(date);
        //存放售卖商品
        List<Long> saleList = new ArrayList<>();
        List<ProductInfoQvo> sellParam = new ArrayList<>();
        //存放租赁商品
        List<Long> leaseList = new ArrayList<>();
        ProductInfoQvo param = new ProductInfoQvo();
        for (int i = 0 ; i < info.size() ; i++){
            if(info.get(i).getCommodityType().intValue() == CommodityType.SALE.getCode()){
                ProductInfoQvo vo = new ProductInfoQvo();
                //售卖商品
                vo.setProductId(info.get(i).getProductId());
                vo.setImeiNo(info.get(i).getImeiNo());
                vo.setSnNo(info.get(i).getSnNo());
                sellParam.add(vo);
            }else if(info.get(i).getCommodityType().intValue() == CommodityType.LEASE.getCode()){
                //租赁商品
                leaseList.add(info.get(i).getModelId());
            }
        }
        //vo.setIdList(saleList);
        param.setParamList(sellParam);
        //根据多个产品ID、imeiNo、snNo，批量查询售卖商品详情
        ResponseResult<?> saleResult = productService.batchQuerySellCommoidtyInfo(param);
        //ResponseResult<?> saleResult = productService.getSaleCommodityInfo(vo);
        if(saleResult.isSuccess()  && !StringUtils.isEmpty(saleResult.getData())){
            List<ProductInfo> list = (List<ProductInfo>) saleResult.getData();
            for (int i = 0 ; i < info.size() ; i++){
                IndexInfo data = info.get(i);
                if(null == data.getProductId()){
                    continue;
                }
                for (int j = 0 ; j < list.size() ; j++){
                    ProductInfo pro = list.get(j);
                    if(data.getProductId().longValue() == pro.getId().longValue() && data.getImeiNo().equals(pro.getImeiNo()) && data.getSnNo().equals(pro.getSnNo())){
                        data.setMaterielModelName(pro.getModelName());
                        data.setMaterielSpecValue(pro.getSpecBatchNoValues());
                        data.setConfigValue(pro.getConfigValue());
                        data.setMaterielName(pro.getMaterielName());
                        data.setImage(pro.getAttaUrl());
                        data.setShowAmount(pro.getShowAmount());
                        data.setSignContractAmount(pro.getSignContractAmount());
                        data.setDayLeaseAmount(pro.getDayLeaseAmount());
                        data.setLeaseAmount(pro.getLeaseAmount());
                        data.setDisplayLeaseType(pro.getDisplayLeaseType());
                        data.setProductType(pro.getProductType() == null ? 0 : pro.getProductType());
                        data.setStorageCount(pro.getStorageCount() == null ? 0 : pro.getStorageCount());
                        data.setDiscount(this.calcDiscount(pro.getShowAmount(), pro.getSignContractAmount()));
                        data.setLocationCode(pro.getLocationCode());
                        data.setImeiNo(pro.getImeiNo());
                        data.setSnNo(pro.getSnNo());
                        continue;
                    }
                }
                System.out.print("租赁：产品ID为：" + data.getProductId() +"-----" + "型号ID为：" + data.getModelId());
            }
        }
        //根据租赁商品的参数，查询租赁的详细信息
        ProductInfoQvo ro = new ProductInfoQvo();
        ro.setMaterielModelIdList(leaseList);
        ResponseResult<List<ProductInfo>> leaseResult = productService.queryProductInfoListWithMaterielImageByModelIds(ro);
        if (leaseResult.isSuccess() && !StringUtils.isEmpty(leaseResult.getData())){
            //拼接售卖商品数据
            List<ProductInfo> reusltList = leaseResult.getData();
            for (int i = 0 ; i < info.size() ; i++){
                IndexInfo data = info.get(i);
                if(null == data.getModelId()){
                    continue;
                }
                for (int j = 0 ; j < reusltList.size() ; j++){
                    ProductInfo pro = reusltList.get(j);
                    if(data.getModelId().intValue() == pro.getMaterielModelId()){
                        data.setMaterielModelName(pro.getModelName());
                        data.setMaterielSpecValue(pro.getSpecBatchNoValues());
                        data.setConfigValue(pro.getConfigValue());
                        data.setMaterielName(pro.getMaterielName());
                        data.setImage(pro.getAttaUrl());
                        data.setShowAmount(pro.getShowAmount());
                        data.setSignContractAmount(pro.getSignContractAmount());
                        data.setTermValue(pro.getTermValue());
                        data.setDayLeaseAmount(pro.getDayLeaseAmount());
                        data.setLeaseAmount(pro.getLeaseAmount());
                        data.setDisplayLeaseType(pro.getDisplayLeaseType());
                        data.setProductType(pro.getProductType() == null ? 0 : pro.getProductType());
                        data.setStorageCount(pro.getStorageCount() == null ? 0 : pro.getStorageCount());
                        data.setDiscount(this.calcDiscount(pro.getShowAmount(), pro.getSignContractAmount()));
                        if (null != pro.getShowAmount() && null != pro.getTermValue() && null != pro.getLeaseAmount()){
                            // 节省值
                            data.setEconomizeValue(this.calcSaveAmount(pro.getShowAmount(),pro.getTermValue(),pro.getLeaseAmount()));
                        }else{
                            data.setEconomizeValue(new BigDecimal(0));
                        }
                        //data.setEconomizeValue(pro.getSignContractAmount().subtract(pro.getShowAmount()));
                        data.setImeiNo(pro.getImeiNo());
                        data.setSnNo(pro.getSnNo());

                    }
                }
                System.out.print("售卖：产品ID为：" + data.getProductId() +"-----" + "型号ID为：" + data.getModelId());
            }
        }

        //将数据根据橱窗位分类
        Map<Integer,List<IndexInfo>> resultMap = new HashMap<>();
        for (IndexInfo in : info){
            if(resultMap.containsKey(in.getShopwindowType())){
                resultMap.get(in.getShopwindowType()).add(in);
            }else{
                List<IndexInfo> resultList = new ArrayList<>();
                resultList.add(in);
                resultMap.put(in.getShopwindowType(),resultList);
            }
        }
        return ResponseResult.buildSuccessResponse(resultMap);
    }

    /**
     * 计算折扣率=签约价值/显示价值*10
     * @param showAmount 显示价值
     * @param signContractAmount 签约价值
     * @return BigDecimal
     */
    private BigDecimal calcDiscount(BigDecimal showAmount, BigDecimal signContractAmount) {
        BigDecimal result = new BigDecimal(0);
        if (showAmount != null && signContractAmount != null && showAmount.compareTo(result) == 1
                && signContractAmount.compareTo(result) == 1 && showAmount.compareTo(signContractAmount) == 1) {
            result = signContractAmount.divide(showAmount,2, BigDecimal.ROUND_UP).multiply(new BigDecimal(10));
        }
        return result;
    }

    /**
     *
     * @Description: 计算节省金额
     * @param  originalPrice 原价
     * @param  termValue 月数
     * @param leaseAmount 月租金
     * @throws
     */
    private BigDecimal calcSaveAmount(BigDecimal originalPrice,Integer termValue,BigDecimal leaseAmount) {
        BigDecimal result = new BigDecimal(0);
        if (originalPrice != null && AssertUtils.isPositiveNumber4Int(termValue) && leaseAmount.compareTo(result) == 1) {
            result = originalPrice.subtract(leaseAmount.multiply(new BigDecimal(termValue)));
        }
        return result;
    }


    @Override
    public ResponseResult<?> getSaleCommodityById(ProductInfo param) {
        ResponseResult<?> result = productService.getSaleCommodityInfoById(param);
        return ResponseResult.buildSuccessResponse(result.getData());
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public ResponseResult<?> supplement(List<ActivityCommodity> dataList) {
        //根据商品类型,删除旧数据
        this.shopwindowDao.deleteActivityCommodityByShopwindowId(dataList.get(0).getShopwindowId());
        //将补仓的数据新增到运营系统表中
        this.shopwindowDao.insertActivityCommodity(dataList);
        return ResponseResult.buildSuccessResponse();
    }

    @Override
    public ResponseResult<?> getAmountByModelId(ProductInfoQvo vo) {
        ResponseResult<List<ProductInfo>> resultList = this.productService.getMinAmountByModelId(vo);
        return resultList;
    }


}
