package org.gz.oss.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.gz.oss.common.entity.*;

import java.util.List;

@Mapper
public interface ShopwindowDao {

    /**
     * 修改
     * @param sho
     * @return ResponseResult
     */
    int update(ShopwindowVO sho);

    /**
     * 新增活动时间
     * @param at
     * @return ResponseResult
     */
    int insertActivityTime(ActivityCommodity at);

    /**
     * 判断当前活动时间是否合理
     * @param at
     * @return ActivityTime
     */
    List<ActivityTime> queryActivityTime(ActivityCommodity at);

    /**
     * 修改活动时间
     * @param at
     * @return ResponseResult
     */
    int updateActivityTime(ActivityCommodity at);

    /**
     * 删除活动
     * @param id
     * @return int
     */
    int deleteActivity(Long id);

    /**
     * 删除活动对应的商品
     * @param id
     */
    void deleteActivityCommodity(Long id);

    /**
     * 新增活动商品
     * @param sh
     * @return ResponseResult
     */
    void insertActivityCommodity(List<ActivityCommodity> ac);

    /**
     * 查询活动商品列表
     * @return ResponseResult
     */
    List<ActivityTime> queryActivityList();

    /**
     * 根据活动ID，获取活动对应商品
     * @param id
     * @return ResponseResult
     */
    List<ShopwindowCommodityRelation> queryActivityCommodity(int id);

    /**
     * 根据活动商品Id，修改活动商品
     * @param list
     * @return ResponseResult
     */
    void updateActivityCommodity(List<ActivityCommodity> list);

    /**
     * 获取所有橱窗位名称和对应商品数量
     * @return ResponseResult
     */
    List<Shopwindow> findShopwindowCount();

    /**
     * 获取橱窗位列表
     * @return ResponseResult
     */
    List<Shopwindow> findAllList();

    /**
     * 获取所有橱窗位和商品
     * @return IndexInfo
     * @param date
     */
    List<IndexInfo> getShopwindowAndCommondity(String date);


    /**
     * 根据橱窗位ID获取橱窗位对应的商品
     * @param id
     * @return ResponseResult
     */
    List<ShopwindowCommodityRelation> queryShopwindowCommodityById(int id);

    /**
     * 根据橱窗ID，删除以前的数据
     * @param shopwindowId
     */
    void deleteActivityCommodityByShopwindowId(Integer shopwindowId);

    /**
     * 根据活动ID，判断当前活动时间是否能做修改
     * @param ac
     * @return ActivityTime
     */

    ActivityTime queryActivityTimeById(ActivityCommodity ac);

    /**
     * 获取售卖橱窗下的所有商品
     * @return ShopwindowCommodityRelation
     */
    List<ShopwindowCommodityRelation> getSellShopwindowCommodity();

    /**
     * 根据商品类型,删除旧数据
     * @param type
     */
    void deleteActivityByType(Integer shopwindowId);

    /**
     * 根据当前时间，查询已开始的活动下的商品总数
     * @param date
     * @return
     */
    Shopwindow getActivityShopwindowCommodityCount(String date);
}
