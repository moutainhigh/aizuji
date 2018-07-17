package org.gz.warehouse.mapper.warehouse;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gz.warehouse.entity.warehouse.WarehouseLocationRelation;
/**
 * 
 * @Description:TODO    仓库库位关联表mapper
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月18日 	Created
 */
@Mapper
public interface WarehouseLocationRelationMapper {
    /**
     * 
     * @Description: TODO 根据id统计
     * @param id
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月18日
     */
    int selectCountById(WarehouseLocationRelation warehouseLocationRelation);
    /**
     * 
     * @Description: TODO 根据id删除
     * @param id
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月18日
     */
    int deleteRelation(Long id);
    /**
     * 
     * @Description: TODO 根据id查询列表
     * @param id
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月18日
     */
    List<WarehouseLocationRelation> queryList(Integer id);
    /**
     * 
     * @Description: TODO 配置库位
     * @param list
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月16日
     */
    int insertLocation(List<WarehouseLocationRelation> list);
    List<Integer> queryLocationIdsByWarehouseId(Integer warehouseId);
    /**
     * 
     * @Description: TODO 批量删除
     * @param warehouseId
     * @param list
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月18日
     */
    void deleteRelationBatch(@Param("warehouseId")Integer warehouseId,@Param("list")List<WarehouseLocationRelation> list);
}
