package org.gz.warehouse.service.materiel.impl;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.gz.common.constants.ZTreeSimpleNode;
import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.AssertUtils;
import org.gz.warehouse.common.WarehouseErrorCode;
import org.gz.warehouse.converter.String2IntListConverter;
import org.gz.warehouse.entity.*;
import org.gz.warehouse.mapper.MaterielBasicInfoMapper;
import org.gz.warehouse.mapper.MaterielClassBrandMapper;
import org.gz.warehouse.mapper.MaterielClassMapper;
import org.gz.warehouse.service.materiel.MaterielClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class MaterielClassServiceImpl implements MaterielClassService {

    @Autowired
    private MaterielClassMapper materielClassMapper;
    
    @Autowired
    private MaterielClassBrandMapper materielClassBrandMapper;

    @Autowired
    private MaterielBasicInfoMapper materielBasicInfoMapper;
    
    
    @Override
    public List<MaterielClass> queryMaterielClassList(MaterielClass mc) {
        return null;
    }

    @Override
    public MaterielClass queryAllMaterielClassList() {
        return this.getTree(0);
    }

    @Transactional
	@Override
	public ResponseResult<MaterielClass> insert(MaterielClass m) {
		ResponseResult<MaterielClass> vr = this.validateMaterielClass(m);
		if(vr.isSuccess()) {
			try {
				//设置typeLevel=parentTypeLevel+1
				this.setTypeLevel(m);
				//新增分类业务规则，分类名称同级别唯一，分类编码全局唯一，分类编码自动生成规则：C+5位数字
				if(this.materielClassMapper.insert(m)>0) {
					String typeCode = this.genTypeCode(m);
					m.setTypeCode(typeCode);
					//默认排序规则为主键ID,即按插入顺序升序排列
					m.setSortOrder(m.getId());
					if(this.materielClassMapper.update(m)>0) {
						return ResponseResult.buildSuccessResponse(m);
					}
				}
			} catch (Exception e) {
				log.error("新增物料分类失败:{}",e.getLocalizedMessage());
				//转换底层异常
				throw new ServiceException(e.getLocalizedMessage());
			}
		}
		return vr;

	}
	
	
	/**
	 * 设置分类级别
	 * @param m
	 */
	private void setTypeLevel(MaterielClass m) {
		ResponseResult<MaterielClass> parent = this.selectByPrimaryKey(m.getParentId());
		if(parent.isSuccess()&&parent.getData()!=null) {
			m.setTypeLevel(parent.getData().getTypeLevel().intValue()+1);
		}else {
			throw new ServiceException("parent is null");
		}
	}

	/**
	 * 生成类型编码
	 */
	private String genTypeCode(MaterielClass m) {
		String id= String.valueOf(m.getId());
		int length = id.length();
		StringBuffer result = new StringBuffer("C");
		//不足5位补0
		for(int i=length;i<6;i++) {
			if(i==5) {
				result.append(id);
			}else {
				result.append("0");
			}
		}
		return result.toString();
	}

	/** 
	* @Description: 
	* @param @param m
	* @param @return
	* @return ResponseResult<MaterielClass>
	* @throws 
	*/
	private ResponseResult<MaterielClass> validateMaterielClass(MaterielClass m) {
		//根据类别名称验证唯一性
		MaterielClassQuery query = new MaterielClassQuery(m);
		int count = this.materielClassMapper.queryUniqueCount(query);
		return count>0?ResponseResult.build(WarehouseErrorCode.MATERIEL_CLASS_REPEAT_ERROR.getCode(), WarehouseErrorCode.MATERIEL_CLASS_REPEAT_ERROR.getMessage(), m):ResponseResult.buildSuccessResponse();
	}

	@Override
	public ResponseResult<MaterielClass> update(MaterielClass m) {
		ResponseResult<MaterielClass> vr = this.validateMaterielClass(m);
		if(vr.isSuccess()) {
			if(!AssertUtils.isPositiveNumber4Int(m.getId())) {
				return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), "修改物料分类时，id为必传参数", null);
			}
			MaterielClass q = this.materielClassMapper.selectByPrimaryKey(m.getId());
			if(q==null) {
				return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), "物料分类不存在", null);
			}
			try {
				q.setTypeName(m.getTypeName());
				q.setParentId(m.getParentId());
				this.setTypeLevel(q);
				if(this.materielClassMapper.update(q)>0) {
					return ResponseResult.buildSuccessResponse(m);
				}
			} catch (Exception e) {
				log.error("新增物料分类失败:{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return vr;
	}

	@Override
	public ResponseResult<MaterielClass> deleteByIds(String ids) {
		if(StringUtils.hasText(ids)) {
			List<Integer> idList = new String2IntListConverter(",").convert(ids);
			boolean canDelete = this.assertCanDelete(idList);
			if(canDelete) {
				try {
					if(this.materielClassMapper.deleteByIds(idList)>0) {
						return ResponseResult.buildSuccessResponse();
					}
				} catch (Exception e) {
					log.error("批量删除物料分类失败:{}",e.getLocalizedMessage());
					//转换底层异常
					throw new ServiceException(e.getLocalizedMessage());
				}
			}else {
				return ResponseResult.build(WarehouseErrorCode.MATERIEL_CLASS_USING_ERROR.getCode(), WarehouseErrorCode.MATERIEL_CLASS_USING_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}

	private boolean assertCanDelete(List<Integer> idList) {
		return this.materielClassMapper.queryUsedCount(idList)==0;
	}

	@Override
	public ResponseResult<MaterielClass> selectByPrimaryKey(Integer id) {
		return ResponseResult.buildSuccessResponse(this.materielClassMapper.selectByPrimaryKey(id));
	}

	@Override
	public ResponseResult<ResultPager<MaterielClass>> queryByPage(MaterielClassQuery m) {
		int totalNum = this.materielClassMapper.queryPageCount(m);
		List<MaterielClass> list = new ArrayList<MaterielClass>(0);
		if(totalNum>0) {
			list = this.materielClassMapper.queryPageList(m);
		}
		ResultPager<MaterielClass> data = new ResultPager<MaterielClass>(totalNum, m.getCurrPage(), m.getPageSize(), list);
		return ResponseResult.buildSuccessResponse(data);
	}

	@Override
	public ResponseResult<List<MaterielClassBrandVO>> queryBrands(Integer materielClassId) {
		List<MaterielClassBrandVO> list = this.materielClassBrandMapper.queryBrands(materielClassId);
		return ResponseResult.buildSuccessResponse(list);
	}

	
	@Override
	public ResponseResult<MaterielClassBrand> canDeleteBrand(MaterielClassBrandReq m) {
		Integer materielClassId = m.getMaterielClassId();
		List<Integer> materielBrandIdList = new String2IntListConverter().convert(m.getMaterielBrandIds());
		return this.canDeleteBrand(materielClassId, materielBrandIdList);
	}


    private ResponseResult<MaterielClassBrand> canDeleteBrand(Integer materielClassId,List<Integer> materielBrandIdList){
		boolean res= this.materielBasicInfoMapper.queryUsedCountByClassBrand(materielClassId,materielBrandIdList)==0;
		return res?ResponseResult.buildSuccessResponse():ResponseResult.build(WarehouseErrorCode.MATERIEL_BASIC_USING_ERROR.getCode(), WarehouseErrorCode.MATERIEL_BASIC_USING_ERROR.getMessage(), null);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public ResponseResult<MaterielClassBrand> configBrand(MaterielClassBrandReq m) {
		Integer materielClassId=m.getMaterielClassId();
		//页面提交上来的品牌ID列表,此处不允许前端提交空值
		List<Integer> submitBrandList = StringUtils.hasText(m.getMaterielBrandIds())?new String2IntListConverter().convert(m.getMaterielBrandIds()):new ArrayList<Integer>(0);
		
		//查询当前分类已关联的品牌ID
		List<Integer> linkedBrandList = this.materielClassBrandMapper.queryLinkedBrandIds(m.getMaterielClassId());
		
		List<Integer> addBrandList = submitBrandList;//新增关联品牌ID
		List<MaterielClassBrand> delList=null;//去除的关联品牌ID
		
		if(CollectionUtils.isNotEmpty(linkedBrandList)) {//不为空，则获取删除的品牌ID与新增的品牌ID
			List<Integer> tempLinkedBrandList = new ArrayList<Integer>(linkedBrandList);
			List<Integer> tempSubmitBrandList = new ArrayList<Integer>(submitBrandList);
			//找出页面删除的品牌ids
			List<Integer> delBrandList = (List<Integer>) CollectionUtils.subtract(tempLinkedBrandList, tempSubmitBrandList);
			if(CollectionUtils.isNotEmpty(delBrandList)) {//提前判断删除，若无法删除直接快速失败
				ResponseResult<MaterielClassBrand> canDelResp = this.canDeleteBrand(materielClassId, delBrandList);
				if(canDelResp.isSuccess()==false) {
					return canDelResp;
				}
			}
		    delList = this.createMaterielClassBrandList(materielClassId,delBrandList);
		    //找出新增品牌
		    addBrandList = (List<Integer>) CollectionUtils.subtract(tempSubmitBrandList,tempLinkedBrandList);
		}
		List<MaterielClassBrand> addList = this.createMaterielClassBrandList(materielClassId, addBrandList);
		//先批量删除，再批量新增
		if(CollectionUtils.isNotEmpty(delList)) {
			this.materielClassBrandMapper.batchDelete(materielClassId,delList);
		}
		if(CollectionUtils.isNotEmpty(addList)) {
			this.materielClassBrandMapper.batchAdd(addList);
		}
		return ResponseResult.buildSuccessResponse();
	}

	private List<MaterielClassBrand> createMaterielClassBrandList(Integer materielClassId, List<Integer> brandIdList) {
		if(CollectionUtils.isNotEmpty(brandIdList)) {
			List<MaterielClassBrand> list = new ArrayList<MaterielClassBrand>(brandIdList.size());
			for(Integer brandId:brandIdList) {
				list.add(new MaterielClassBrand(materielClassId,brandId));
			}
			return list;
		}
		return null;
	}

	@Override
	public MaterielClass getTree(Integer rootId) {
		MaterielClass node = this.materielClassMapper.selectByPrimaryKey(rootId);
		List<MaterielClass> childNodes = this.materielClassMapper.queryChildList(rootId); 
		for(MaterielClass child : childNodes){//遍历子节点
			MaterielClass n = getTree(child.getId()); //递归
			node.getChildClassList().add(n);
		}
		return node;
	}

	@Override
	public ZTreeSimpleNode getZTree(Integer rootId) {
		ZTreeSimpleNode node = this.getNodeById(rootId);
		List<ZTreeSimpleNode> childNodes = this.getChildrenByParentId(rootId); 
		if(CollectionUtils.isNotEmpty(childNodes)) {
			for(ZTreeSimpleNode child : childNodes){//遍历子节点
				ZTreeSimpleNode n = getZTree(child.getId()); //递归
				node.getChildren().add(n);
			}
		}else {
			node.setIsParent("false");
			node.setOpen("false");
		}
		return node;
	}

	/** 
	* @Description: 
	* @param @param rootId
	* @param @return
	* @return List<ZTreeSimpleNode>
	* @throws 
	*/
	private List<ZTreeSimpleNode> getChildrenByParentId(Integer parentId) {
		return materielClassMapper.getChildrenByParentId(parentId);
	}

	/** 
	* @Description: 
	* @param @param rootId
	* @param @return
	* @return ZTreeSimpleNode
	* @throws 
	*/
	private ZTreeSimpleNode getNodeById(Integer id) {
		return this.materielClassMapper.getNodeById(id);
	}

}
