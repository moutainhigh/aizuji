package org.gz.oss.common.service.impl;

import org.gz.oss.common.dao.ProductDao;
import org.gz.oss.common.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WenMing.Zhou
 * @Description: ${todo}
 * @date 2018/4/3 14:22
 */
@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductDao productDao;

    @Override
    public int queryProductIsHasConfig(Long productId) {
        return productDao.queryProductIsHasConfig(productId);
    }
}
