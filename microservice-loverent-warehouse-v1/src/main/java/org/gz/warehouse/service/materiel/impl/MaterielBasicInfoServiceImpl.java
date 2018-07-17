package org.gz.warehouse.service.materiel.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.gz.common.entity.ResultPager;
import org.gz.common.entity.UploadBody;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.AssertUtils;
import org.gz.warehouse.common.WarehouseErrorCode;
import org.gz.warehouse.common.vo.MaterielBasicImagesResp;
import org.gz.warehouse.common.vo.MaterielBasicInfoReq;
import org.gz.warehouse.common.vo.MaterielBasicInfoResp;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.ProductInfoQvo;
import org.gz.warehouse.common.vo.ProductMaterielIntroReq;
import org.gz.warehouse.common.vo.ProductMaterielIntroResp;
import org.gz.warehouse.constants.ProductConstants;
import org.gz.warehouse.constants.ProductTypeEnum;
import org.gz.warehouse.entity.MaterielBasicImages;
import org.gz.warehouse.entity.MaterielBasicInfo;
import org.gz.warehouse.entity.MaterielBasicInfoQuery;
import org.gz.warehouse.entity.MaterielBasicInfoVO;
import org.gz.warehouse.feign.IUploadAliService;
import org.gz.warehouse.mapper.MaterielBasicImagesMapper;
import org.gz.warehouse.mapper.MaterielBasicInfoMapper;
import org.gz.warehouse.service.materiel.MaterielBasicInfoService;
import org.gz.warehouse.service.product.ProductService;
import org.gz.warehouse.utils.ImageZoomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.base.Strings;
import com.google.common.io.Files;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@CacheConfig(cacheNames = "MaterielBasicInfo")
public class MaterielBasicInfoServiceImpl implements MaterielBasicInfoService {

	@Autowired
	private MaterielBasicInfoMapper materielBasicInfoMapper;
	
	@Autowired
	private MaterielBasicImagesMapper materielBasicImagesMapper;
	
	@Autowired
	private ProductService productService;
	
	
	@Autowired
	private IUploadAliService uploadAliService;
	
	/**
	 * 插入数据
	 */
	@Transactional
	@Override
	public ResponseResult<MaterielBasicInfo> insert(MaterielBasicInfo m) {
		//1.验证唯一性(物料名称)
		ResponseResult<MaterielBasicInfo> vr = this.validateMaterielBasic(m);
		//2.写入数据
		if(vr.isSuccess()) {
			try {
				//写入数据时，默认更新人，更新时间与创建人，创建时间相同
				m.setUpdateBy(m.getCreateBy());
				m.setUpdateOn(m.getCreateOn());
				m.setThumbnailUrl(this.getThumbnailUrl(m.getAttaList()));
				if(this.materielBasicInfoMapper.insert(m)>0) {
					//更新物料编码
					m.setMaterielCode(genMaterielCode(String.valueOf(m.getId())));
					m.setMaterielBarCode(this.genMaterielBarCode(m));
					this.materielBasicInfoMapper.updateMaterielCode(m);
					//批量插入新附件
					this.batchInsertAttament(m);
					return ResponseResult.buildSuccessResponse(m);
				}
			} catch (Exception e) {
				//转换底层异常
				throw new ServiceException(e.getLocalizedMessage());
			}
		}
		return vr;
	}

	/** 
	* @Description: 生成物料条码，生成规则：物料条码=型号ID+物料编码
	* @param m
	* @return String
	*/
	private String genMaterielBarCode(MaterielBasicInfo m) {
		return m.getMaterielModelId()+m.getMaterielCode();
	}

	/** 
	* @Description: 批量插入附件
	* @param m
	*/
	private void batchInsertAttament(MaterielBasicInfo m) {
		List<MaterielBasicImages> attaList = m.getAttaList();
		List<MaterielBasicImages> newAttaList = new ArrayList<MaterielBasicImages>();
		MaterielBasicImages temp = null;
		//存储附件
		if(CollectionUtils.isNotEmpty(attaList)) {
		  for (Iterator<MaterielBasicImages> it = attaList.iterator(); it.hasNext();) {
			  temp = it.next();
			  if(StringUtils.hasText(temp.getAttaUrl())) {
				  temp.setMaterielBasicInfoId(m.getId());
				  newAttaList.add(temp);
			  }
		  }
		  if(CollectionUtils.isNotEmpty(newAttaList)) {
			  this.materielBasicImagesMapper.batchInsert(newAttaList);
		  }
		}
	}
	
	/** 
	* @Description: 使用第一张图生成缩略图
	* @param attaList
	* @return String
	*/
	private String getThumbnailUrl(List<MaterielBasicImages> attaList) {
		if(CollectionUtils.isNotEmpty(attaList)) {
			try {
				for(MaterielBasicImages temp:attaList) {
					String fileUrl = temp.getAttaUrl();
					if(StringUtils.hasText(fileUrl)) {
						String fileExtension = "."+Files.getFileExtension(fileUrl);
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						ImageZoomUtils.zoomBySize(fileUrl,160,160,1.0f,os,false);
						byte[] picArr = os.toByteArray();
						//上传图片
		        		UploadBody dataMap = new UploadBody();
		    			dataMap.setFile(picArr);
		    			dataMap.setBucketName("image/materiel/pic/");
		    			dataMap.setReturnPathType(1);
		    			dataMap.setFileType(fileExtension);
		    			ResponseResult<String> d = uploadAliService.uploadToOSSFileInputStrem(dataMap);
						return d.getData();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 生成物料编码
	 */
	private String genMaterielCode(String id) {
		return "P"+Strings.padStart(id, 8, '0');
	}

	/** 
	* @Description: 业务检难
	* @param @param m
	* @return ResponseResult<MaterielUnit>
	*/
	private ResponseResult<MaterielBasicInfo> validateMaterielBasic(MaterielBasicInfo m) {
		MaterielBasicInfoQuery nameQuery = new MaterielBasicInfoQuery();
		nameQuery.setId(m.getId());
		nameQuery.setMaterielName(m.getMaterielName());
		int count = this.materielBasicInfoMapper.queryUniqueCount(nameQuery);
		if(count>0) {
			return ResponseResult.build(WarehouseErrorCode.MATERIEL_BASIC_REPEAT_ERROR.getCode(), "物料名称重复", m);
		}
		MaterielBasicInfoQuery skuQuery = new MaterielBasicInfoQuery();
		skuQuery.setId(m.getId());
		skuQuery.setMaterielClassId(m.getMaterielClassId());
		skuQuery.setMaterielBrandId(m.getMaterielBrandId());
		skuQuery.setMaterielModelId(m.getMaterielModelId());
		skuQuery.setSpecBatchNo(m.getSpecBatchNo());
		skuQuery.setMaterielUnitId(m.getMaterielUnitId());
		skuQuery.setMaterielNewConfigId(m.getMaterielNewConfigId());
		count = this.materielBasicInfoMapper.queryUniqueCount(skuQuery);
		if(count>0) {
			return ResponseResult.build(WarehouseErrorCode.MATERIEL_BASIC_REPEAT_ERROR.getCode(), "物料SKU重复", m);
		}
		if(CollectionUtils.isEmpty(m.getAttaList())){
			return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), "物料必须上传图片", null);	
		}
		boolean isBlank = true;
		for(MaterielBasicImages img : m.getAttaList()) {
			if(StringUtils.hasText(img.getAttaUrl())) {
				isBlank=false;
				break;
			}
		}
		if(isBlank) {
			return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), "物料必须上传图片", null);	
		}
		if(!StringUtils.hasText(m.getMaterielIntroduction())) {
			return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), "物料必须填写物料介绍", null);	
		}
		if(m.getMaterielFlag().intValue()==1) {
			MaterielBasicInfoQuery mainMaterielQuery = new MaterielBasicInfoQuery();
			mainMaterielQuery.setId(m.getId());
			mainMaterielQuery.setMaterielModelId(m.getMaterielModelId());
			mainMaterielQuery.setMaterielFlag(m.getMaterielFlag());
			count = this.materielBasicInfoMapper.queryUniqueCount(mainMaterielQuery);
			if(count>0) {
				return ResponseResult.build(WarehouseErrorCode.MATERIEL_BASIC_REPEAT_ERROR.getCode(), "同种型号只允许出现一个主物料", m);
			}
		}
		return ResponseResult.buildSuccessResponse();
	}

	/**
	 * 修改数据
	 */
	@CacheEvict(allEntries=true)
	@Override
	public ResponseResult<MaterielBasicInfo> update(MaterielBasicInfo m) {
		//1.验证唯一性(id,规格名称，物料编码)
		ResponseResult<MaterielBasicInfo> vr = this.validateMaterielBasic(m);
		//2.修改数据
		if(vr.isSuccess()) {
			try {
				MaterielBasicInfo queryInfo = this.materielBasicInfoMapper.selectByPrimaryKey(m.getId());
				if(queryInfo!=null) {
					if(queryInfo.getEnableFlag().intValue()==1) {
						return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), "不能修改已启用的物料", null);	
					}
					m.setVersion(queryInfo.getVersion());//设置版本号
					m.setUpdateOn(new Date());//更新人由前端传入
					m.setThumbnailUrl(this.getThumbnailUrl(m.getAttaList()));
					if(this.materielBasicInfoMapper.update(m)>0) {
						//存储附件
						List<MaterielBasicImages> attaList = m.getAttaList();
						if(CollectionUtils.isNotEmpty(attaList)) {//前台不传值，不清库
						  //删除原始附件
						  this.materielBasicImagesMapper.deleteByMaterielBasicInfoId(m.getId());
						  //插入新附件
						  this.batchInsertAttament(m);
						}
						return ResponseResult.buildSuccessResponse(m);
					}else {
						log.error("物料基础信息并发修改异常,id:{}",m.getId());
						throw new ServiceException("并发修改异常");
					}
				}
			} catch (Exception e) {
				//转换底层异常
				throw new ServiceException(e.getLocalizedMessage());
			}
		}
		return vr;
	}

	@Override
	public ResponseResult<MaterielBasicInfo> getDetail(Long id) {
		if(AssertUtils.isPositiveNumber4Long(id)) {
			MaterielBasicInfo info = this.materielBasicInfoMapper.selectByPrimaryKey(id);
			if(info!=null) {
				List<MaterielBasicImages> attaList =this.materielBasicImagesMapper.queryAttaListByMaterielBasicInfoId(id);
				info.setAttaList(attaList);
			}
			return ResponseResult.buildSuccessResponse(info);
		}	
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);	
	}

	@Override
	public ResponseResult<ResultPager<MaterielBasicInfoVO>> queryByPage(MaterielBasicInfoQuery m) {
		int totalNum = this.materielBasicInfoMapper.queryPageCount(m);
		List<MaterielBasicInfoVO> list = new ArrayList<MaterielBasicInfoVO>(0);
		if(totalNum>0) {
			list = this.materielBasicInfoMapper.queryPageList(m);
		}
		ResultPager<MaterielBasicInfoVO> data = new ResultPager<MaterielBasicInfoVO>(totalNum, m.getCurrPage(), m.getPageSize(), list);
		return ResponseResult.buildSuccessResponse(data);
	}

    @Override
    public List<MaterielBasicInfoVO> queryList(MaterielBasicInfoQuery m) {
        return materielBasicInfoMapper.queryList(m);
    }

	@Transactional
	@Override
	public ResponseResult<MaterielBasicInfo> setEnableFlag(MaterielBasicInfo m) {
		MaterielBasicInfo q = this.materielBasicInfoMapper.selectByPrimaryKey(m.getId());
		if(q!=null) {
			if(m.getEnableFlag().intValue()!=q.getEnableFlag().intValue()) {
				if(m.getEnableFlag().intValue()==0) {//如果是停用,检查产品物料关联表，若存在有上架产品，则不允许停用物料
					ProductInfoQvo qvo  = new ProductInfoQvo();
					qvo.setMaterielId(m.getId());
					qvo.setCurrPage(1);
					qvo.setPageSize(Integer.MAX_VALUE);
					ResultPager<ProductInfo> pager = this.productService.queryProductList(qvo);
					if(pager!=null&&CollectionUtils.isNotEmpty(pager.getData())) {
						for(ProductInfo p : pager.getData()) {
							if(p.getIsDeleted().byteValue()==ProductConstants.PRODUCT_IS_DELETED_NO) {
								return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), "该物料存在上架产品，无法禁用", null);	
							}
						}
					}
				}
				q.setEnableFlag(m.getEnableFlag());
				this.materielBasicInfoMapper.setEnableFlag(q);
			}
			return ResponseResult.buildSuccessResponse(null);
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}

	@Override
	public List<MaterielBasicImages> queryAttaListByMaterielBasicInfoId(Long materielBasicInfoId) {
		return this.materielBasicImagesMapper.queryAttaListByMaterielBasicInfoId(materielBasicInfoId);
	}

	
	@Override
	public MaterielBasicInfoResp queryMainMaterielBasicInfo(MaterielBasicInfoReq req) {
		MaterielBasicInfoResp resp = this.materielBasicInfoMapper.queryMainMaterielBasicShortInfo(req);//修改不带图文介绍
		if(resp!=null) {
			List<MaterielBasicImages> attaList = this.materielBasicImagesMapper.queryAttaListByMaterielBasicInfoId(resp.getId());
			List<MaterielBasicImagesResp> converAttaList = new ArrayList<MaterielBasicImagesResp>();
			for(MaterielBasicImages input:attaList) {
				MaterielBasicImagesResp temp = new MaterielBasicImagesResp();
				temp.setId(input.getId());
				temp.setMaterielBasicInfoId(input.getMaterielBasicInfoId());
				temp.setAttaUrl(input.getAttaUrl());
				converAttaList.add(temp);
			}
			resp.setAttaList(converAttaList);
		}
		return resp;
	}
	
	@Cacheable
	@Override
	public MaterielBasicInfoResp queryMainMaterielBasicIntroductionInfo(MaterielBasicInfoReq req) {
		System.err.println("queryMainMaterielBasicInfo....");
		return this.materielBasicInfoMapper.queryMainMaterielBasicIntroductionInfo(req);
	}

	@Cacheable
	@Override
	public List<ProductMaterielIntroResp> queryProductMaterielIntros(ProductMaterielIntroReq q) {
		System.err.println("queryProductMaterielIntros...");
		List<ProductMaterielIntroResp> result = new ArrayList<ProductMaterielIntroResp>();
		for(Long productId : q.getProductIds()) {
			ProductInfo p = this.productService.getProductInfoById(productId);
			if(p!=null&&AssertUtils.isPositiveNumber4Long(p.getMaterielId())) {
				Long destMaterielId = null;
				if(p.getProductType().intValue()==ProductTypeEnum.TYPE_SALE.getCode()) {//售卖
					//售卖查自身物料的图文介绍和附件
					destMaterielId = p.getMaterielId();
				}
				if(p.getProductType().intValue()==ProductTypeEnum.TYPE_LEASE.getCode()||p.getProductType().intValue()==ProductTypeEnum.TYPE_PURCHASING.getCode()) {//租赁
					//租赁查主物料的图文介绍和附件
					destMaterielId = this.materielBasicInfoMapper.queryMainMaterielBasicId(p.getMaterielId());
				}
				if(AssertUtils.isPositiveNumber4Long(destMaterielId)) {
					String materielIntroduction = this.materielBasicInfoMapper.queryOnlyMaterielIntroduction(destMaterielId);
					List<String> attaUrls = this.materielBasicInfoMapper.queryOnlyMaterielAttaUrl(destMaterielId);
					result.add(new ProductMaterielIntroResp(materielIntroduction,attaUrls));
				}
			}
		}
		return result;
	}
}
