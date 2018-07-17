package org.gz.oss.common.service;

/**
 * @author WenMing.Zhou
 * @Description: ${todo}
 * @date 2018/4/3 14:18
 */
public interface ProductService {


    /**
     * 查询产品是否存在售卖橱窗、租赁橱窗、抢购、banner配置
     * @param productId
     * @return
     */
    int queryProductIsHasConfig(Long productId);
}
