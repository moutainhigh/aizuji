package org.gz.warehouse.mapper.warehouse;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.common.constants.ZTreeSimpleNode;
import org.gz.warehouse.entity.warehouse.WarehouseInfo;
import org.gz.warehouse.entity.warehouse.WarehouseInfoQuery;
@Mapper
public interface WarehouseInfoMapper {

    int deleteByPrimaryKey(Integer id);
    /**
     * 
     * @Description: TODO 新增
     * @param record
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月16日
     */
    int insertSelective(WarehouseInfo record);
    /**
     * 
     * @Description: TODO 查询单条记录
     * @param id
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月16日
     */
    WarehouseInfo selectByPrimaryKey(Integer id);
    /**
     * 
     * @Description: TODO 更新
     * @param record
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月16日
     */
    int updateByPrimaryKeySelective(WarehouseInfo record);
    /**
     * 
     * @Description: TODO 分页查询
     * @param query
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月16日
     */
    public List<WarehouseInfo> queryPageList(WarehouseInfoQuery query);
    
    int queryPageCount(WarehouseInfoQuery query);
    
    
	/** 
	* @Description: 
	* @param id
	* @return ZTreeSimpleNode
	*/
	ZTreeSimpleNode getNodeById(int id);
	
	
	/** 
	* @Description: 
	* @param pId
	* @return List<ZTreeSimpleNode>
	*/
	List<ZTreeSimpleNode> getChildrenByParentId(int pId);
	/** 
	* @Description: 
	* @param @param warehouseId
	* @param @return
	* @return List<ZTreeSimpleNode>
	* @throws 
	*/
	List<ZTreeSimpleNode> queryWarehouseLocationList(Integer warehouseId);
	/** 
	* @Description: 
	* @param @param string
	* @param @return
	* @return WarehouseInfo
	* @throws 
	*/
	WarehouseInfo queryWarehouseInfoByCode(String string);
    
}