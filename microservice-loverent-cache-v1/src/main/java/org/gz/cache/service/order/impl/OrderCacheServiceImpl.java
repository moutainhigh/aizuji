package org.gz.cache.service.order.impl;

import java.util.Map;

import org.gz.cache.commom.behavior.impl.RedisHashOperateBehavior;
import org.gz.cache.commom.constant.OrderRedisConstant;
import org.gz.cache.commom.constant.RedisCacheDB;
import org.gz.cache.service.order.OrderCacheService;
import org.springframework.stereotype.Service;
@Service
public class OrderCacheServiceImpl extends RedisHashOperateBehavior implements OrderCacheService {

  /**
   * 
   */
  private static final long serialVersionUID = -6986994874753842423L;

  public OrderCacheServiceImpl(){
    super(RedisCacheDB.ORDER_DB);
  }

  @Override
  public boolean hsetwaitPayOrder(Map<String, String> map) {
    return hMSet(OrderRedisConstant.ORDER_WAITPAY, map);
  }

  @Override
  public Long hdelwaitPayOrder(String fields) {
    return hDel(OrderRedisConstant.ORDER_WAITPAY, fields);
  }

  @Override
  public Map<String, String> hgetAllwaitPayOrder() {
    return hGetAll(OrderRedisConstant.ORDER_WAITPAY);
  }

}
