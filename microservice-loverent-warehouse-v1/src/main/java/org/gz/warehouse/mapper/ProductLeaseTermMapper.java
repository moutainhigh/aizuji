package org.gz.warehouse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gz.warehouse.entity.ProductLeaseTerm;

@Mapper
public interface ProductLeaseTermMapper {
    
    /**
     * 查询产品所有租期列表
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月14日
     */
    List<ProductLeaseTerm> findAll(@Param("modelId") Integer modelId);
}
