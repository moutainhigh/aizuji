package org.gz.warehouse.service.product;

import java.util.List;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.ProductInfoQvo;
import org.gz.warehouse.common.vo.RentProductReq;
import org.gz.warehouse.common.vo.RentProductResp;
import org.gz.warehouse.common.vo.SaleProductReq;
import org.gz.warehouse.common.vo.SaleProductResp;
import org.gz.warehouse.entity.ProductLeaseTerm;

/**
 * 产品相关接口
 * @Description:TODO
 * Author	Version		Date		Changes
 * liuyt 	1.0  		2017年12月14日 	Created
 */
public interface ProductService {

    /**
     * 添加产品
     * @param pInfo
     * @throws
     * createBy:liuyt
     * createDate:2017年12月14日
     */
    void addProduct(ProductInfo pInfo);

    /**
     * 查询产品列表
     * @return
     * @throws
     * createBy:liuyt
     * createDate:2017年12月14日
     */
    ResultPager<ProductInfo> queryProductList(ProductInfoQvo qvo);
    
    /**
     * 查询所有产品列表
     * @return
     * @throws
     * createBy:zhangguoliang
     * createDate:2017年12月22日
     */
    List<ProductInfo> queryAllProductList();

    /**
     * 根据id查询产品信息
     * @param id
     * @return
     * @throws
     * createBy:liuyt
     * createDate:2017年12月14日
     */
    ProductInfo getByIdOrPdtNo(ProductInfo pInfo);
    
    /**
     * 
    * @Description: 根据产品ID获取产品信息
    * @param  id
    * @return ProductInfo
     */
    ProductInfo getProductInfoById(Long id);

    /**
     * 更新产品信息
     * @param pInfo
     * @throws
     * createBy:liuyt
     * createDate:2017年12月14日
     */
    void updateProductInfo(ProductInfo pInfo);

    /**
     * 查询产品所有租期列表
     * @return
     * @throws
     * createBy:liuyt
     * createDate:2017年12月14日
     */
    List<ProductLeaseTerm> queryAllProductLeaseTerm(Integer modelId);

    /**
     *  更新产品上下架
     * @param pInfo
     * @throws
     * @createBy:liuyt
     * @createDate:2017年12月19日
     */
    void updateProductIsEnable(ProductInfo pInfo);

    /**
     * 根据物料id下架所有相关产品
     * @param mateielId
     * @throws
     * @createBy:liuyt
     * @createDate:2017年12月22日
     */
    void updateProductUnableByMateielId(Long mateielId);

    /**
     * 查询租金最低的产品
     */
    ProductInfo getLeasePriceLowestProduct(ProductInfoQvo qvo);

    /**
     * 查询产品信息和关联的售卖商品列表
     * @return
     * @throws
     * @createBy:liuyt
     * @createDate:2018年3月15日
     */
    ProductInfo getProductInfoWithCommodityList(ProductInfoQvo qvo);

    /**
     * 根据多个型号id查询产品信息和对应的主物料主图信息
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2018年3月15日
     */
    List<ProductInfo> queryProductInfoListWithMaterielImageByModelIds(ProductInfoQvo qvo);

    /**
     * 根据单个型号id查询产品信息和对应的主物料主图信息
     * @param qvo
     * @return
     */
    ProductInfo queryProductInfoWithMaterieImageByModelId(ProductInfoQvo qvo);

    /**
     * 获取售卖商品详细信息
     * @param list
     * @createBy daiqingwen
     * @return ResponseResult
     */
    List<ProductInfo> getSaleCommodityInfo(ProductInfoQvo qvo);

    /**
     * 根据产品ID、imeiNo、snNo获取山脉商品详细信息
     * @param vo
     * @return ResponseResult
     */
    ResponseResult<ProductInfo> getSaleCommodityInfoById(ProductInfo vo);

    /**
     * 根据品牌ID，型号ID，成色ID，分类ID查询商品列表（售卖）
     * @param vo
     * @return
     */
    ResultPager<RentProductResp> queryRentCommodityPage(RentProductReq vo);

    /**
     * 根据品牌ID，型号ID，成色ID，分类ID查询商品列表（租赁）
     * @param vo
     * @return
     */
    ResultPager<RentProductResp> getRentCommodityInfoByBrandIdAndModelIdAndConfigid(RentProductReq vo);



	/** 
	* @Description: 
	* @param @param req
	* @param @return
	* @return ResponseResult<ResultPager<SaleProductResp>>
	* @throws 
	*/
	ResponseResult<ResultPager<SaleProductResp>> queryAvailableProductList(SaleProductReq req);

    /**
     * 查询售卖和租赁商品列表
     * @param
     * @return
     */
    ProductInfo getSellAndLeaseProductList(ProductInfoQvo qvo);
    
    /** 
	* @Description: 根据物料ID查询售卖产品ID列表
	* @param materielId  物料ID
	* @return List<Long> 售卖产品ID列表
	*/
	List<Long> querySaleProductIdList(Long materielId);

    /**
     * app首页-根据多个产品ID，imeiNo、snNo批量查询商品详细信息
     * @param qvo
     * @return ResponseResult
     */
    List<ProductInfo> batchQuerySellCommoidtyInfo(ProductInfoQvo qvo);

    /**
     * 获取产品租期列表
     * @param
     * @return
     */
    ProductInfo getLeaseProductList(ProductInfoQvo qvo);

    /**
     *  查询租赁商品详情
     * @param qvo
     * @return
     */
    ProductInfo getLeaseProductInfo(ProductInfoQvo qvo);

	/** 
	* @Description: 
	* @param @param req
	* @param @return
	* @return ResponseResult<ResultPager<SaleProductResp>>
	* @throws 
	*/
	ResponseResult<ResultPager<SaleProductResp>> queryAvailableSplitProductList(SaleProductReq req);
}
