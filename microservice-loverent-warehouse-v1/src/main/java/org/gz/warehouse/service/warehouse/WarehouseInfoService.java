package org.gz.warehouse.service.warehouse;

import java.util.List;

import org.gz.common.constants.ZTreeSimpleNode;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.warehouse.WarehouseInfo;
import org.gz.warehouse.entity.warehouse.WarehouseInfoQuery;
import org.gz.warehouse.entity.warehouse.WarehouseLocationRelation;
import org.gz.warehouse.entity.warehouse.WarehouseLocationReq;

public interface WarehouseInfoService {

    /**
     * 
     * @Description: TODO 新增方法
     * @param warehouseInfo
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月16日
     */
    ResponseResult<WarehouseInfo> insertSelective(WarehouseInfo warehouseInfo);
    /**
     * 
     * @Description: TODO 查询单条数据
     * @param id
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月16日
     */
    ResponseResult<WarehouseInfo> selectByPrimaryKey(Integer id);
    /**
     * 
     * @Description: TODO 更新
     * @param warehouseInfo
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月16日
     */
    ResponseResult<WarehouseInfo> updateByPrimaryKeySelective(WarehouseInfo warehouseInfo);
    /**
     * 
     * @Description: TODO 分页查询
     * @param warehouseInfoQuery
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月16日
     */
    public ResponseResult<ResultPager<WarehouseInfo>> queryByPage(WarehouseInfoQuery warehouseInfoQuery);
    /**
     * 
     * @Description: TODO 配置库位
     * @param warehouseLocationReq
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月16日
     */
    ResponseResult<WarehouseLocationRelation> insertLocation(WarehouseLocationReq warehouseLocationReq);
    /**
     * 
     * @Description: TODO 查询库位信息
     * @param wareHouseId
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月18日
     */
    ResponseResult<List<WarehouseLocationRelation>> queryList(Integer wareHouseId);
	/** 
	* @Description: 
	* @param @return
	* @return ZTreeSimpleNode
	* @throws 
	*/
	ZTreeSimpleNode getTree(int rootId);
	/** 
	* @Description: 
	* @param @param q
	* @param @return
	* @return ResponseResult<List<WarehouseInfo>>
	* @throws 
	*/
	List<WarehouseInfo> queryPageList(WarehouseInfoQuery q);
}