package org.gz.cache.service.order;

import java.util.Map;

/**
 * 订单操作redis缓存Service Author Version Date Changes 临时工 1.0 2018年3月13日 Created
 */
public interface OrderCacheService {

  /**
   * 将未支付的订单放入redis缓存
   * 
   * @param key:
   * @param fields：rentRecordNo
   * @param value: System.currentTimeMillis()
   * @param Map<String, String>
   * @throws createBy:临时工 createDate:2018年3月13日
   */
  public boolean hsetwaitPayOrder(Map<String, String> map);

  /**
   * 将支付完成或者超时的订单从缓存中删除
   * 
   * @param fields
   * @return
   * @throws createBy:临时工 createDate:2018年3月13日
   */
  public Long hdelwaitPayOrder(String fields);

  /**
   * 获取缓存中所有的未支付订单map对象
   * 
   * @return
   * @throws createBy:临时工 createDate:2018年3月13日
   */
  public Map<String, String> hgetAllwaitPayOrder();
}
