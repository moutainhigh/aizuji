package org.gz.warehouse.service.product.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.AssertUtils;
import org.gz.warehouse.common.WarehouseErrorCode;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.ProductInfoQvo;
import org.gz.warehouse.common.vo.ProductMaterielIntroReq;
import org.gz.warehouse.common.vo.ProductMaterielIntroResp;
import org.gz.warehouse.common.vo.RentProductReq;
import org.gz.warehouse.common.vo.RentProductResp;
import org.gz.warehouse.common.vo.SaleProductReq;
import org.gz.warehouse.common.vo.SaleProductResp;
import org.gz.warehouse.common.vo.WarehouseCodeEnum;
import org.gz.warehouse.common.vo.WarehouseCommodityReq;
import org.gz.warehouse.common.vo.WarehouseCommodityResp;
import org.gz.warehouse.common.vo.WarehouseLocationCodeEnum;
import org.gz.warehouse.constants.ProductConstants;
import org.gz.warehouse.constants.ProductTypeEnum;
import org.gz.warehouse.entity.MaterielBasicInfoQuery;
import org.gz.warehouse.entity.MaterielBasicInfoVO;
import org.gz.warehouse.entity.MaterielModelSpec;
import org.gz.warehouse.entity.ProductLeaseTerm;
import org.gz.warehouse.mapper.ProductInfoMapper;
import org.gz.warehouse.mapper.ProductLeaseTermMapper;
import org.gz.warehouse.service.materiel.MaterielBasicInfoService;
import org.gz.warehouse.service.materiel.MaterielSpecService;
import org.gz.warehouse.service.product.ProductService;
import org.gz.warehouse.service.warehouse.WarehouseCommodityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

	@Resource
	private ProductInfoMapper productInfoMapper;

	@Resource
	private ProductLeaseTermMapper productLeaseTermMapper;

	@Resource
	private MaterielBasicInfoService materielService;

	@Resource
	private MaterielSpecService materielSpecService;

	@Resource
	private WarehouseCommodityService warehouseCommodityService;

	@Resource
	private MaterielBasicInfoService materielBasicInfoService;

	@Override



	@Transactional
	public void addProduct(ProductInfo pInfo) {
		// 校验唯一性
		pInfo.setMaterielId(this.getMaterielIdByMaterielInfo(pInfo));
		this.validateProductIsUnique(pInfo);
		// 设置初始状态为下架
		pInfo.setIsDeleted(ProductConstants.PRODUCT_IS_DELETED_YES);
		productInfoMapper.add(pInfo);

		// 生成产品编号 规则为fp + 自增长八位数字 不足部分补0
		String idStr = pInfo.getId().toString();
		int idLen = idStr.length();
		StringBuffer productNo = new StringBuffer(ProductConstants.PRODUCT_NO_PREFIX);
		for (int i = 0; i < ProductConstants.PRODUCT_NO_LENGTH - idLen; i++) {
			productNo.append("0");
		}
		productNo.append(idStr);

		// 更新信息
		ProductInfo uptInfo = new ProductInfo();
		uptInfo.setId(pInfo.getId());
		uptInfo.setProductNo(productNo.toString());
		productInfoMapper.update(uptInfo);
	}

	@Override
	public ResultPager<ProductInfo> queryProductList(ProductInfoQvo qvo) {
		int totalNum = this.productInfoMapper.queryPageCount(qvo);
		List<ProductInfo> list = new ArrayList<ProductInfo>();
		if (totalNum > 0) {
			list = this.productInfoMapper.queryList(qvo);
		}
		return new ResultPager<ProductInfo>(totalNum, qvo.getCurrPage(), qvo.getPageSize(),list);
	}

	@Override
	public ProductInfo getByIdOrPdtNo(ProductInfo pInfo) {
		return productInfoMapper.getByIdOrPdtNo(pInfo);
	}

	@Override
	public ProductInfo getProductInfoById(Long id) {
		if (AssertUtils.isPositiveNumber4Long(id)) {
			return this.getByIdOrPdtNo(new ProductInfo(id));
		}
		return null;
	}

	@Override
	public void updateProductInfo(ProductInfo pInfo) {
		ProductInfo pi = this.getByIdOrPdtNo(pInfo);
		pi.setDepreciateAmouts(pInfo.getDepreciateAmouts());
		pi.setFloatAmount(pInfo.getFloatAmount());
		pi.setIsDeleted(pInfo.getIsDeleted());
		pi.setLeaseAmount(pInfo.getLeaseAmount());
		pi.setLeaseTermId(pInfo.getLeaseTermId());
		pi.setDayLeaseAmount(pInfo.getDayLeaseAmount());
		pi.setMaterielId(this.getMaterielIdByMaterielInfo(pInfo));
		pi.setPremium(pInfo.getPremium());
		pi.setProductType(pInfo.getProductType());
		pi.setSesameCredit(pInfo.getSesameCredit());
		pi.setShowAmount(pInfo.getShowAmount());
		pi.setSignContractAmount(pInfo.getSignContractAmount());
		pi.setUpdateBy(pInfo.getUpdateBy());
		pi.setUpdateOn(pInfo.getUpdateOn());
		pi.setDisplayLeaseType(pInfo.getDisplayLeaseType());
		pi.setBrokenScreenAmount(pInfo.getBrokenScreenAmount());
		productInfoMapper.updateAll(pi);
	}

	@Override
	public List<ProductLeaseTerm> queryAllProductLeaseTerm(Integer modelId) {
		return productLeaseTermMapper.findAll(modelId);
	}

	@Override
	public void updateProductIsEnable(ProductInfo pInfo) {
		productInfoMapper.update(pInfo);
	}

	@Override
	public void updateProductUnableByMateielId(Long mateielId) {
		if (mateielId == null) {
			throw new ServiceException(ResponseStatus.PARAMETER_VALIDATION.getCode(),
					ResponseStatus.PARAMETER_VALIDATION.getMessage());
		}
		this.productInfoMapper.updateProductUnableByMateielId(mateielId);
	}

	/**
	 * 
	 * @return
	 * @throws @createBy:liuyt
	 *             createDate:2017年12月19日
	 */
	private Long getMaterielIdByMaterielInfo(ProductInfo pInfo) {
		MaterielBasicInfoQuery qvo = new MaterielBasicInfoQuery();
		qvo.setMaterielClassId(pInfo.getMaterielClassId());
		qvo.setMaterielBrandId(pInfo.getMaterielBrandId());
		qvo.setMaterielModelId(pInfo.getMaterielModelId());
		qvo.setSpecBatchNo(pInfo.getSpecBatchNo());
		qvo.setMaterielNewConfigId(pInfo.getMaterielNewConfigId());
		qvo.setEnableFlag(1);
		List<MaterielBasicInfoVO> list = materielService.queryList(qvo);
		if (CollectionUtils.isEmpty(list)) {
			throw new ServiceException(WarehouseErrorCode.MATERIEL_BASIC_NOTEXISTS_ERROR.getCode(),
					WarehouseErrorCode.MATERIEL_BASIC_NOTEXISTS_ERROR.getMessage());
			//return ResponseResult.build(WarehouseErrorCode.MATERIEL_BASIC_NOTEXISTS_ERROR.getCode(), WarehouseErrorCode.MATERIEL_BASIC_NOTEXISTS_ERROR.getMessage(), null);
		}
		if (list.size() > 1) {
			throw new ServiceException(WarehouseErrorCode.MATERIEL_BASIC_REPEAT_ERROR.getCode(),
					WarehouseErrorCode.MATERIEL_BASIC_REPEAT_ERROR.getMessage());
			//return ResponseResult.build(WarehouseErrorCode.MATERIEL_BASIC_REPEAT_ERROR.getCode(),WarehouseErrorCode.MATERIEL_BASIC_REPEAT_ERROR.getMessage(), null);
		}
		MaterielBasicInfoVO vo = list.get(0);
		return vo.getId();
	}

	/**
	 * 
	 * @param pInfo
	 * @throws @createBy:liuyt
	 *             createDate:2017年12月24日
	 */
	private ResponseResult<?> validateProductIsUnique(ProductInfo pInfo) {

		ProductInfoQvo qvo = new ProductInfoQvo();
		qvo.setMaterielId(pInfo.getMaterielId());
		qvo.setFloatAmount(pInfo.getFloatAmount());
		qvo.setLeaseAmount(pInfo.getLeaseAmount());
		qvo.setDayLeaseAmount(pInfo.getDayLeaseAmount());
		qvo.setLeaseTermId(pInfo.getLeaseTermId());
		qvo.setPremium(pInfo.getPremium());
		qvo.setSesameCredit(pInfo.getSesameCredit());
		qvo.setShowAmount(pInfo.getShowAmount());
		qvo.setSignContractAmount(pInfo.getSignContractAmount());
		qvo.setBrokenScreenAmount(pInfo.getBrokenScreenAmount());
		List<ProductInfo> list = this.productInfoMapper.queryList(qvo);
		if (CollectionUtils.isEmpty(list)) {
			return ResponseResult.build(WarehouseErrorCode.MATERIEL_BASIC_NOTEXISTS_ERROR.getCode(), WarehouseErrorCode.MATERIEL_BASIC_NOTEXISTS_ERROR.getMessage(), null);
		} else {
			Long id = list.get(0).getId();
			if (id != pInfo.getId()) {
//				throw new ServiceException(WarehouseErrorCode.PRODUCT_APPLY_REPEAT_ERROR.getCode(),
//						WarehouseErrorCode.PRODUCT_APPLY_REPEAT_ERROR.getMessage());
				return ResponseResult.build(WarehouseErrorCode.PRODUCT_APPLY_REPEAT_ERROR.getCode(), WarehouseErrorCode.PRODUCT_APPLY_REPEAT_ERROR.getMessage(), null);
			}
		}

		return ResponseResult.buildSuccessResponse();
	}

	@Override
	public List<ProductInfo> queryAllProductList() {
		return this.productInfoMapper.queryAllProductList();
	}

	@Override
	public ProductInfo getLeasePriceLowestProduct(ProductInfoQvo qvo) {
		if (StringUtils.isNotEmpty(qvo.getMaterielSpecValue())) {
			// 规格值不为空时根据该值筛选对应的规格批次号 如果找不到则返回null
			MaterielModelSpec mms = new MaterielModelSpec();
			mms.setMaterielModelId(qvo.getMaterielModelId());
			mms.setMaterielSpecValue(qvo.getMaterielSpecValue());
			String specBatchNo = materielSpecService.getSpecBatchNoBySpecInfo(mms);
			if (specBatchNo == null) {
				return null;
			} else {
				qvo.setSpecBatchNo(specBatchNo);
			}
		}
		qvo.setIsDeleted(ProductConstants.PRODUCT_IS_DELETED_NO);
		ProductInfo productInfo = this.productInfoMapper.getLeasePriceLowestProduct(qvo);
		if (productInfo!=null){
		    //判断价格是否为空，为空则不做计算
			if (null != productInfo.getShowAmount() && null != productInfo.getTermValue() && null != productInfo.getLeaseAmount()){
				// 节省值
				productInfo.setEconomizeValue(this.calcSaveAmount(productInfo.getShowAmount(),productInfo.getTermValue(),productInfo.getLeaseAmount()));
			}else{
				productInfo.setEconomizeValue(new BigDecimal(0));
			}
			//计算总租金
			if(null != productInfo.getLeaseAmount() && null != productInfo.getTermValue()){
				productInfo.setTotalAmount(productInfo.getLeaseAmount().multiply(BigDecimal.valueOf(productInfo.getTermValue())).setScale(2,BigDecimal.ROUND_UP));
			}else{
				productInfo.setTotalAmount(new BigDecimal(0));
			}
		}

		return productInfo;
	}


	@Override
	public ProductInfo getProductInfoWithCommodityList(ProductInfoQvo qvo) {
		// 先查询租赁和售卖产品基本信息
		ProductInfo productInfo = this.getLeasePriceLowestProduct(qvo);
		// 产品类型为售卖 通过物料ID查询对应的商品列表
		if (productInfo != null && productInfo.getProductType() == ProductTypeEnum.TYPE_SALE.getCode()) {
			WarehouseCommodityReq q = new WarehouseCommodityReq(productInfo.getMaterielId(), WarehouseCodeEnum.NEW,WarehouseLocationCodeEnum.AVAILABLE,qvo.getCurrPage(),qvo.getPageSize());
//			q.setMaterielBasicInfoId(productInfo.getMaterielId());
			ResponseResult<ResultPager<WarehouseCommodityResp>> result = warehouseCommodityService
					.queryWarehouseLocationCommoditys(q);
			if (result.isSuccess()) {
				productInfo.setWarehouseCommodityResp(result.getData());

			}
		}
		return productInfo;
	}

	@Override
	public ProductInfo getSellAndLeaseProductList(ProductInfoQvo qvo) {
		// 先查询租赁和售卖产品基本信息
		ProductInfo productInfo = this.getLeaseAndSellProduct(qvo);
		// 产品类型为售卖 通过物料ID查询对应的商品列表
		if (productInfo != null && productInfo.getProductType() == ProductTypeEnum.TYPE_SALE.getCode()) {
			WarehouseCommodityReq q = new WarehouseCommodityReq(productInfo.getMaterielId(), WarehouseCodeEnum.NEW,WarehouseLocationCodeEnum.AVAILABLE,qvo.getCurrPage(),qvo.getPageSize());
//			q.setMaterielBasicInfoId(productInfo.getMaterielId());
			ResponseResult<ResultPager<WarehouseCommodityResp>> result = warehouseCommodityService
					.queryWarehouseLocationCommoditys(q);
			if (result.isSuccess()) {
				productInfo.setWarehouseCommodityResp(result.getData());

			}
		}
		return productInfo;
	}

    /**
     * 根据多个查询条件查询租赁和售卖产品信息
     * @param qvo
     * @return ProductInfo
     */
    private ProductInfo getLeaseAndSellProduct(ProductInfoQvo qvo){
        if (StringUtils.isNotEmpty(qvo.getMaterielSpecValue())) {
            // 规格值不为空时根据该值筛选对应的规格批次号 如果找不到则返回null
            MaterielModelSpec mms = new MaterielModelSpec();
            mms.setMaterielModelId(qvo.getMaterielModelId());
            mms.setMaterielSpecValue(qvo.getMaterielSpecValue());
            String specBatchNo = materielSpecService.getSpecBatchNoBySpecInfo(mms);
            if (specBatchNo == null) {
                return null;
            } else {
                qvo.setSpecBatchNo(specBatchNo);
            }
        }
        qvo.setIsDeleted(ProductConstants.PRODUCT_IS_DELETED_NO);
        ProductInfo productInfo = this.productInfoMapper.getLeaseAndSellProduct(qvo);
		if (productInfo!=null){
			if (null != productInfo.getShowAmount() && null != productInfo.getTermValue() && null != productInfo.getLeaseAmount()){
				// 节省值
				productInfo.setEconomizeValue(this.calcSaveAmount(productInfo.getShowAmount(),productInfo.getTermValue(),productInfo.getLeaseAmount()));
			}else{
				productInfo.setEconomizeValue(new BigDecimal(0));
			}
		}
        return productInfo;
    }

	@Override
	public List<ProductInfo> queryProductInfoListWithMaterielImageByModelIds(ProductInfoQvo qvo) {
        List<ProductInfo> result = new ArrayList<>();
        List<Long> list = qvo.getMaterielModelIdList();
        for (Long l : list){
            ProductInfoQvo q = new ProductInfoQvo();
            q.setMaterielModelId(l.intValue());
           // ProductInfo productInfo = this.productInfoMapper.queryProductInfoWithMaterieImageByModelId(q);
            ProductInfo productInfo = this.productInfoMapper.queryProductMaterieImageByModelIds(q);
			//根据产品ID获取图文介绍和轮播图
			if(null != productInfo){
				ProductInfo info = getPrimaryImage(productInfo);
				result.add(info);
			}
		}
		return result;
	}

	@Override
	public ProductInfo queryProductInfoWithMaterieImageByModelId(ProductInfoQvo qvo) {
		ProductInfo productInfo = this.productInfoMapper.queryProductInfoWithMaterieImageByModelId(qvo);
		//根据产品ID获取图文介绍和轮播图
		//ProductInfo result = getPrimaryImage(productInfo);
		return productInfo;
	}

	@Override
	public List<ProductInfo> getSaleCommodityInfo(ProductInfoQvo qvo) {
		return this.productInfoMapper.getSaleCommodityInfo(qvo);
	}

	@Override
	public ResponseResult<ProductInfo> getSaleCommodityInfoById(ProductInfo vo) {
		// 根据产品ID获取售卖商品详情
		ProductInfo info = this.productInfoMapper.getSaleCommodityInfoById(vo);
		if(null == info){
			return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), "商品信息为空", null);
		}
		// 折扣率
		info.setDiscount(this.calcDiscount(info.getShowAmount(), info.getSignContractAmount()));
		// 节省值
		//info.setEconomizeValue(this.calcSaveAmount(info.getShowAmount(),info.getTermValue(),info.getLeaseAmount()));
		//根据产品ID获取图文介绍和轮播图
		ProductInfo result = getPrimaryImage(info);
		return ResponseResult.buildSuccessResponse(result);
	}

	/**
	 * 根据产品ID，获取主物料轮播图
	 * @param info
	 */
	private ProductInfo getPrimaryImage(ProductInfo info) {
		List<Long> list = new ArrayList<>();
		list.add(info.getId());
		ProductMaterielIntroReq p = new ProductMaterielIntroReq();
		p.setProductIds(list);
		List<ProductMaterielIntroResp> result = this.materielBasicInfoService.queryProductMaterielIntros(p);
		if(result.size() > 0){
			for (ProductMaterielIntroResp q : result){
				//图文介绍
				if(StringUtils.isNotEmpty(q.getMaterielIntroduction())){
					info.setMaterielIntroduction(q.getMaterielIntroduction());
				}
				//轮播图
				info.setAttaUrlList(q.getAttaUrls());
				//去第一张轮播图
				info.setAttaUrl(q.getAttaUrls().get(0));
			}
		}
		return info;
	}

	/**
	 * 根据品牌ID，型号ID,成色，分类查询售卖商品列表
	 */
	@Override
	public ResultPager<RentProductResp> queryRentCommodityPage(RentProductReq vo) {
		List<RentProductResp> rentProductResps = productInfoMapper.queryRentCommodityPageCount(vo);
		int totalNum=0;
		if (rentProductResps!=null){
		totalNum=rentProductResps.size();
		}
		List<RentProductResp> list = new ArrayList<RentProductResp>(0);
		if (rentProductResps!=null && rentProductResps.size() > 0) {
			// 根据品牌ID，型号ID，成色ID获取商品信息
			list = this.productInfoMapper.queryRentCommodityPageList(vo);
			if (CollectionUtils.isNotEmpty(list)) {
				for (RentProductResp info : list) {
					info.setDiscount(this.calcDiscount(info.getShowAmount(), info.getSignContractAmount()));
					info.setSaveAmount(this.calcSaveAmount(info.getShowAmount(), info.getTermValue(),info.getLeaseAmount()));
				}
			}
		}
		return new ResultPager<>(totalNum, vo.getCurrPage(), vo.getPageSize(), list);
	}


	/**
	 * 根据品牌ID，型号ID,成色，分类查询租赁商品列表
	 * @param vo
	 * @return
	 */
	@Override
	public ResultPager<RentProductResp> getRentCommodityInfoByBrandIdAndModelIdAndConfigid(RentProductReq vo) {

		List<RentProductResp> rentProductResps = productInfoMapper.queryRentCommodityPageCountForRent(vo);
		int totalNum=0;
		if (rentProductResps!=null){
			totalNum=rentProductResps.size();
		}
		List<RentProductResp> list = new ArrayList<RentProductResp>(0);
		if (rentProductResps!=null && rentProductResps.size() > 0) {
			// 根据品牌ID，型号ID，成色ID获取商品信息
			list = this.productInfoMapper.getRentCommodityInfoByBrandIdAndModelIdAndConfigid(vo);
			if (CollectionUtils.isNotEmpty(list)) {
				for (RentProductResp info : list) {
					info.setDiscount(this.calcDiscount(info.getShowAmount(), info.getSignContractAmount()));
					info.setSaveAmount(this.calcSaveAmount(info.getShowAmount(), info.getTermValue(),info.getLeaseAmount()));
				}
			}
		}
		return new ResultPager<>(totalNum, vo.getCurrPage(), vo.getPageSize(), list);
	}

	/**
	 * 计算折扣率=签约价值/显示价值*10
	 * @param showAmount 显示价值
	 * @param signContractAmount 签约价值
	 * @return BigDecimal
	 */
	private BigDecimal calcDiscount(BigDecimal showAmount, BigDecimal signContractAmount) {
		BigDecimal result = new BigDecimal(0);
		if (showAmount != null && signContractAmount != null && showAmount.compareTo(result) == 1
				&& signContractAmount.compareTo(result) == 1 && showAmount.compareTo(signContractAmount) == 1) {
			result = signContractAmount.divide(showAmount,2, BigDecimal.ROUND_UP).multiply(new BigDecimal(10));
		}
		return result;
	}

	/**
	 * 
	* @Description: 计算节省金额
	* @param  originalPrice 原价
	* @param  termValue 月数
	* @param leaseAmount 月租金
	* @throws
	 */
	private BigDecimal calcSaveAmount(BigDecimal originalPrice,Integer termValue,BigDecimal leaseAmount) {
		BigDecimal result = new BigDecimal(0);
		if (originalPrice != null && AssertUtils.isPositiveNumber4Int(termValue) && leaseAmount.compareTo(result) == 1) {
			result = originalPrice.subtract(leaseAmount.multiply(new BigDecimal(termValue)));
		}
		return result;
	}
	/**
	 * 按型号，品牌查询可售商品列表
	 */
	@Override
	public ResponseResult<ResultPager<SaleProductResp>> queryAvailableProductList(SaleProductReq req) {
		int totalNum = 0;
		List<SaleProductResp> list = new ArrayList<SaleProductResp>(0);
		if (req != null && CollectionUtils.isNotEmpty(req.getProductIds())) {
			totalNum = productInfoMapper.queryAvailableProductCount(req);
			if (totalNum > 0) {
				// 先按型号查询，再按品牌查询
				list = this.productInfoMapper.queryAvailableProductList(req);
			}
		}
		return ResponseResult.buildSuccessResponse(new ResultPager<SaleProductResp>(0, req.getCurrPage(), req.getPageSize(), list));
	}

	@Override
	public List<Long> querySaleProductIdList(Long materielBasicId) {
		return this.productInfoMapper.querySaleProductIdList(materielBasicId);
	}

	@Override
	public List<ProductInfo> batchQuerySellCommoidtyInfo(ProductInfoQvo qvo) {
		List<ProductInfoQvo> list = qvo.getParamList();
		return this.productInfoMapper.batchQuerySellCommoidtyInfo(list);
	}

    @Override
    public ProductInfo getLeaseProductList(ProductInfoQvo qvo) {
        if (StringUtils.isNotEmpty(qvo.getMaterielSpecValue())) {
            // 规格值不为空时根据该值筛选对应的规格批次号 如果找不到则返回null
            MaterielModelSpec mms = new MaterielModelSpec();
            mms.setMaterielModelId(qvo.getMaterielModelId());
            mms.setMaterielSpecValue(qvo.getMaterielSpecValue());
            String specBatchNo = materielSpecService.getSpecBatchNoBySpecInfo(mms);
            if (specBatchNo == null) {
                return null;
            } else {
                qvo.setSpecBatchNo(specBatchNo);
            }
        }
        qvo.setIsDeleted(ProductConstants.PRODUCT_IS_DELETED_NO);
        ProductInfo productInfo = this.productInfoMapper.getLeaseProductList(qvo);
        if (productInfo!=null){
            //判断价格是否为空，为空则不做计算
            if (null != productInfo.getShowAmount() && null != productInfo.getTermValue() && null != productInfo.getLeaseAmount()){
                // 节省值
                productInfo.setEconomizeValue(this.calcSaveAmount(productInfo.getShowAmount(),productInfo.getTermValue(),productInfo.getLeaseAmount()));
            }else{
                productInfo.setEconomizeValue(new BigDecimal(0));
            }
            //计算总租金
            if(null != productInfo.getLeaseAmount() && null != productInfo.getTermValue()){
                productInfo.setTotalAmount(productInfo.getLeaseAmount().multiply(BigDecimal.valueOf(productInfo.getTermValue())).setScale(2,BigDecimal.ROUND_UP));
            }else{
                productInfo.setTotalAmount(new BigDecimal(0));
            }

        }

        return productInfo;
    }

	@Override
	public ProductInfo getLeaseProductInfo(ProductInfoQvo qvo) {
		if (StringUtils.isNotEmpty(qvo.getMaterielSpecValue())) {
			// 规格值不为空时根据该值筛选对应的规格批次号 如果找不到则返回null
			MaterielModelSpec mms = new MaterielModelSpec();
			mms.setMaterielModelId(qvo.getMaterielModelId());
			mms.setMaterielSpecValue(qvo.getMaterielSpecValue());
			String specBatchNo = materielSpecService.getSpecBatchNoBySpecInfo(mms);
			if (specBatchNo == null) {
				return null;
			} else {
				qvo.setSpecBatchNo(specBatchNo);
			}
		}
		qvo.setIsDeleted(ProductConstants.PRODUCT_IS_DELETED_NO);
		ProductInfo productInfo = this.productInfoMapper.getLeaseLowestDetail(qvo);
		if (productInfo!=null){
			//判断价格是否为空，为空则不做计算
			if (null != productInfo.getShowAmount() && null != productInfo.getTermValue() && null != productInfo.getLeaseAmount()){
				// 节省值
				productInfo.setEconomizeValue(this.calcSaveAmount(productInfo.getShowAmount(),productInfo.getTermValue(),productInfo.getLeaseAmount()));
			}else{
				productInfo.setEconomizeValue(new BigDecimal(0));
			}
			//计算总租金
			if(null != productInfo.getLeaseAmount() && null != productInfo.getTermValue()){
				productInfo.setTotalAmount(productInfo.getLeaseAmount().multiply(BigDecimal.valueOf(productInfo.getTermValue())).setScale(2,BigDecimal.ROUND_UP));
			}else{
				productInfo.setTotalAmount(new BigDecimal(0));
			}

		}

		return productInfo;
	}

	@Override
	public ResponseResult<ResultPager<SaleProductResp>> queryAvailableSplitProductList(SaleProductReq req) {
		List<SaleProductResp> list  = this.productInfoMapper.queryAvailableModelProductList(req);//先按型号推荐
		if(CollectionUtils.isEmpty(list)) {
			list  = this.productInfoMapper.queryAvailableBrandProductList(req);//再按品牌推荐
		}
		if(CollectionUtils.isEmpty(list)) {
			list  = this.productInfoMapper.queryAvailableOtherBrandProductList(req);//再按其它品牌推荐
		}
		return ResponseResult.buildSuccessResponse(new ResultPager<SaleProductResp>(list.size(),req.getCurrPage(),req.getPageSize(),list));
	}

}
