package org.gz.warehouse.mapper.warehouse;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gz.warehouse.entity.warehouse.StorageLocation;
import org.gz.warehouse.entity.warehouse.StorageLocationQuery;
@Mapper
public interface StorageLocationMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StorageLocation storageLocation);
    /**
     * 
     * @Description: TODO 根据值是否为空进行保存
     * @param record
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月16日
     */
    int insertSelective(StorageLocation storageLocation);

    StorageLocation selectByPrimaryKey(Integer id);
    /**
     * 
     * @Description: TODO 更新不为空的值
     * @param record
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月16日
     */
    int updateByPrimaryKeySelective(StorageLocation storageLocation);

    int updateByPrimaryKey(StorageLocation storageLocation);
    
    public int queryPageCount(StorageLocationQuery query);
    /**
     * 
     * @Description: TODO 分页查询
     * @param query
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月16日
     */
    public List<StorageLocation> queryPageList(StorageLocationQuery query);

	/** 
	* @Description: 
	* @param warehouseId
	* @param locationCode
	* @return StorageLocation
	*/
	StorageLocation queryLocation(@Param("warehouseId")Integer warehouseId, @Param("locationCode")String locationCode);
    
}