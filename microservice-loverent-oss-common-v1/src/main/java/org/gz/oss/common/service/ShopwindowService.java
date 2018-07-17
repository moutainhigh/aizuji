package org.gz.oss.common.service;
import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.entity.ActivityCommodity;
import org.gz.oss.common.entity.ActivityTimeVO;
import org.gz.oss.common.entity.ShopwindowCommodityRelation;
import org.gz.oss.common.entity.ShopwindowVO;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.ProductInfoQvo;

import java.util.List;

/**
 * 橱窗位业务接口
 * @author daiqingwen
 * @date 2018/3/13 下午 19:37
 */
public interface ShopwindowService {
    /**
     * 修改
     * @param sho
     * @return ResponseResult
     */
    ResponseResult<?> update(ShopwindowVO sho);

    /**
     * 新增活动时间
     * @param at
     * @return ResponseResult
     */
    ResponseResult<?> insertActivityTime(ActivityCommodity at);

    /**
     * 修改活动时间
     * @param at
     * @return ResponseResult
     */
    ResponseResult<?> updateActivityTime(ActivityCommodity at);

    /**
     * 删除活动
     * @param id 活动ID
     * @return ResponseResult
     */
    ResponseResult<?> deleteActivity(Long id);

    /**
     * 新增活动商品
     * @param ac
     * @return ResponseResult
     */
    ResponseResult<?> insertActivityCommodity(ActivityCommodity ac);

    /**
     * 查询活动列表
     * @return ResponseResult
     */
    ResponseResult<?> queryActivityList();

    /**
     * 根据活动ID，获取活动对应商品
     * @param id
     * @return ResponseResult
     */
    ResponseResult<?> queryActivityCommodity(int id);

    /**
     * 根据活动商品Id，修改活动商品
     * @param sh
     * @return ResponseResult
     */
    ResponseResult<?> updateActivityCommodity(ActivityCommodity sh);

    /**
     * 获取所有橱窗位名称和对应商品数量
     * @return ResponseResult
     */
    ResponseResult<?> findShopwindowCount();

    /**
     * 获取橱窗位列表
     * @return ResponseResult
     */
    ResponseResult<?> findAllList();

    /**
     * 获取首页信息
     * @return ResponseResult
     */
    ResponseResult<?> findIndexInfo();

    /**
     * 根据产品ID获取售卖商品详情
     * @return ResponseResult
     */
    ResponseResult<?> getSaleCommodityById(ProductInfo param);

    /**
     * 新增售卖和租赁商品
     * @param sh
     * @return ResponseResult
     */
    ResponseResult<?> insertSaleAndLeaseCommodity(ActivityCommodity sh);

    /**
     * 根据橱窗位ID获取橱窗位对应的商品
     * @param id
     * @return ResponseResult
     */
    ResponseResult<?> queryShopwindowCommodityById(int id);

    /**
     * 根据商品ID，修改售卖和租赁的商品
     * @param ac
     * @return ResponseResult
     */
    ResponseResult<?> updateSaleAndLeaseCommodity(ActivityCommodity ac);

    /**
     * 获取售卖橱窗下的所有商品
     * @return ShopwindowCommodityRelation
     */
    List<ShopwindowCommodityRelation> getSellShopwindowCommodity();

    /**
     * 先删除旧数据，然后再将补仓的数据添加到运营系统表中
     * @param dataList
     * @return ResponseResult
     */
    ResponseResult<?> supplement(List<ActivityCommodity> dataList);

    /**
     * 根据多个型号ID，获取最低月底金
     * @param vo
     * @return
     */
    ResponseResult<?> getAmountByModelId(ProductInfoQvo vo);
}
