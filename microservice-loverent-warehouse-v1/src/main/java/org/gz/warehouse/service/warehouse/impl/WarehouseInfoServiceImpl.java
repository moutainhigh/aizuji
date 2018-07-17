package org.gz.warehouse.service.warehouse.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.gz.common.constants.ZTreeSimpleNode;
import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.warehouse.common.WarehouseErrorCode;
import org.gz.warehouse.converter.PrefixTurnConverter;
import org.gz.warehouse.converter.String2IntListConverter;
import org.gz.warehouse.entity.warehouse.WarehouseInfo;
import org.gz.warehouse.entity.warehouse.WarehouseInfoQuery;
import org.gz.warehouse.entity.warehouse.WarehouseLocationRelation;
import org.gz.warehouse.entity.warehouse.WarehouseLocationReq;
import org.gz.warehouse.mapper.warehouse.WarehouseInfoMapper;
import org.gz.warehouse.mapper.warehouse.WarehouseLocationRelationMapper;
import org.gz.warehouse.service.warehouse.WarehouseInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

@Service
public class WarehouseInfoServiceImpl implements WarehouseInfoService {
    @Resource
    private WarehouseInfoMapper warehouseInfoMapper;
    @Resource
    private WarehouseLocationRelationMapper warehouseLocationRelationMapper;
    @Override
    public ResponseResult<WarehouseInfo> insertSelective(WarehouseInfo warehouseInfo) {
        // 1.验证唯一性(仓库编码)
        ResponseResult<WarehouseInfo> validateResult = this.validateWarehouseLocation(warehouseInfo);
        // 2.写入数据
        if (validateResult.isSuccess()) {
            try {
                if (this.warehouseInfoMapper.insertSelective(warehouseInfo) > 0) {
                    return ResponseResult.buildSuccessResponse(warehouseInfo);
                }
            } catch (Exception e) {
                // 转换底层异常
                throw new ServiceException(e.getLocalizedMessage());
            }
        }
        return validateResult;
    }

    @Override
    public ResponseResult<WarehouseInfo> selectByPrimaryKey(Integer id) {
        try {
            return ResponseResult.buildSuccessResponse(warehouseInfoMapper.selectByPrimaryKey(id));
        } catch (Exception e) {
         // 转换底层异常
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseResult<WarehouseInfo> updateByPrimaryKeySelective(WarehouseInfo warehouseInfo) {
        try {
            if (this.warehouseInfoMapper.updateByPrimaryKeySelective(warehouseInfo) > 0) {
                return ResponseResult.buildSuccessResponse(warehouseInfo);
            }
        } catch (Exception e) {
            // 转换底层异常
            throw new ServiceException(e.getLocalizedMessage());
        }
        return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(), ResponseStatus.DATA_UPDATED_ERROR.getMessage(), null);
    }

    @Override
    public ResponseResult<ResultPager<WarehouseInfo>> queryByPage(WarehouseInfoQuery warehouseInfoQuery) {
        int totalNum = this.warehouseInfoMapper.queryPageCount(warehouseInfoQuery);
        List<WarehouseInfo> list = new ArrayList<WarehouseInfo>(0);
        if(totalNum>0) {
            list = this.warehouseInfoMapper.queryPageList(warehouseInfoQuery);
        }
        ResultPager<WarehouseInfo> data = new ResultPager<WarehouseInfo>(totalNum, warehouseInfoQuery.getCurrPage(), warehouseInfoQuery.getPageSize(), list);
        return ResponseResult.buildSuccessResponse(data);
    }
    
    private ResponseResult<WarehouseInfo> validateWarehouseLocation(WarehouseInfo m) {
        WarehouseInfoQuery query = new WarehouseInfoQuery(m);
        int count = this.warehouseInfoMapper.queryPageCount(query);
        return count>0?ResponseResult.build(WarehouseErrorCode.WAREHOUSE_INFO_REPEAT_ERROR.getCode(), WarehouseErrorCode.WAREHOUSE_INFO_REPEAT_ERROR.getMessage(), m):ResponseResult.buildSuccessResponse();
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public ResponseResult<WarehouseLocationRelation> insertLocation(WarehouseLocationReq warehouseLocationReq) {
        Integer warehouseId = warehouseLocationReq.getWarehouseId();
        List<Integer> submitList = StringUtils.hasText(warehouseLocationReq.getLocationIds())
                ?new String2IntListConverter().convert(warehouseLocationReq.getLocationIds()):new ArrayList<Integer>(0);
        // 关联的库位id集合
        List<Integer> oldList = warehouseLocationRelationMapper.queryLocationIdsByWarehouseId(warehouseId);
        
        List<Integer> addList = submitList;
        // 要删除库位id集合
        List<WarehouseLocationRelation> delList = null;
        if(CollectionUtils.isNotEmpty(oldList)){
            List<Integer> delIdList = (List<Integer>) CollectionUtils.subtract(oldList, submitList);
            if(CollectionUtils.isNotEmpty(delIdList)){
                // 判断是否可以删除库位
                ResponseResult<WarehouseLocationRelation> result = canDeleteLocation(warehouseId, delIdList);
                if(!result.isSuccess()){
                    return result;
                }
            }
            delList = createWarehouseLocationRelation(warehouseId, delIdList);
            // 要新增的库位
            addList = (List<Integer>) CollectionUtils.subtract(submitList, oldList);
        }
        
        List<WarehouseLocationRelation> saveList = createWarehouseLocationRelation(warehouseId, addList);
        // 先删除再新增
        if(CollectionUtils.isNotEmpty(delList)){
            warehouseLocationRelationMapper.deleteRelationBatch(warehouseId,delList);
        }
        if(CollectionUtils.isNotEmpty(saveList)){
            warehouseLocationRelationMapper.insertLocation(saveList);
        }
        return ResponseResult.buildSuccessResponse();
    }
    /**
     * 
     * @Description: TODO 构建仓库和库位关联实体
     * @param warehouseId   仓库id
     * @param locationIdList    仓位id集合
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月18日
     */
    private List<WarehouseLocationRelation> createWarehouseLocationRelation(Integer warehouseId,List<Integer> locationIdList){
      if(warehouseId == null || CollectionUtils.isEmpty(locationIdList)){
          return null;
      }
      List<WarehouseLocationRelation> list = new ArrayList<>(locationIdList.size());
      for (Integer locationId : locationIdList) {
          WarehouseLocationRelation e = new WarehouseLocationRelation(warehouseId, locationId);
          list.add(e);
      }
      return list;
    }

    @Override
    public ResponseResult<List<WarehouseLocationRelation>> queryList(Integer wareHouseId) {
        List<WarehouseLocationRelation> list = new ArrayList<WarehouseLocationRelation>(0);
        try {
            list = this.warehouseLocationRelationMapper.queryList(wareHouseId);
            return ResponseResult.buildSuccessResponse(list);
        } catch (Exception e) {
            throw new ServiceException(e.getLocalizedMessage());
        }
    }
   
    /**
     * 
     * @Description: 校验是否可以删除库位
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月18日
     */
    private ResponseResult<WarehouseLocationRelation> canDeleteLocation(Integer warehouseId,List<Integer> locationIdList){
        // 判断库位是否被使用
        return ResponseResult.buildSuccessResponse();
        
    }

    /**
     * 使用递归算法生成树
     */
	@Override
	public ZTreeSimpleNode getTree(int rootId) {
		ZTreeSimpleNode node = this.getNodeById(rootId);
		List<ZTreeSimpleNode> childNodes = this.getChildrenByParentId(rootId); 
		if(CollectionUtils.isNotEmpty(childNodes)) {
			for(ZTreeSimpleNode child : childNodes){//遍历子节点
				ZTreeSimpleNode n = getTree(child.getId()); //递归
				node.getChildren().add(n);
			}
		}else {
			//查询库位，拼装库位信息
			List<ZTreeSimpleNode> locationList = this.queryWarehouseLocationList(node.getId());
			//为防止ZTree键冲突，此处对库位进行了转码
			List<ZTreeSimpleNode> transformList =FluentIterable.from(locationList).transform(new Function<ZTreeSimpleNode,ZTreeSimpleNode>() {
				@Override
				public ZTreeSimpleNode apply(ZTreeSimpleNode input) {
					String newId = new PrefixTurnConverter().transform(String.valueOf(input.getId()));
					input.setId(Integer.valueOf(newId));
					input.setIsParent("false");
					input.setOpen("false");//叶子节点不展开
					input.setpId(node.getId());
					return input;
				}
			}).toList();
			node.getChildren().addAll(transformList);
		}
		return node;
	}
	
	/** 
	* @Description: 
	* @param  warehouseId
	* @return List<ZTreeSimpleNode>
	*/
	private List<ZTreeSimpleNode> queryWarehouseLocationList(Integer warehouseId) {
		return this.warehouseInfoMapper.queryWarehouseLocationList(warehouseId);
	}

	private ZTreeSimpleNode getNodeById(int id) {
		return this.warehouseInfoMapper.getNodeById(id);
	}
	
	private List<ZTreeSimpleNode> getChildrenByParentId(int pId){
		return this.warehouseInfoMapper.getChildrenByParentId(pId);
	}

	@Override
	public List<WarehouseInfo> queryPageList(WarehouseInfoQuery q) {
		return this.warehouseInfoMapper.queryPageList(q);
	}

}
