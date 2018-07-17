package org.gz.warehouse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.ProductInfoQvo;
import org.gz.warehouse.common.vo.RentProductReq;
import org.gz.warehouse.common.vo.RentProductResp;
import org.gz.warehouse.common.vo.SaleProductReq;
import org.gz.warehouse.common.vo.SaleProductResp;
import org.gz.warehouse.entity.MaterielBasicImages;

@Mapper
public interface ProductInfoMapper {

    /**
     * 插入数据
     * @param productInfo
     * @return
     */
    void add(ProductInfo productInfo);

    /**
     * 根据id查询信息
     * @param id
     * @return
     * @throws
     * createBy:liuyt
     * createDate:2017年12月14日
     */
    ProductInfo getByIdOrPdtNo(ProductInfo pInfo);

    /**
     * 更新产品信息
     * @param pInfo
     * @throws
     * createBy:liuyt
     * createDate:2017年12月14日
     */
    void update(ProductInfo pInfo);

    /**
     * 更新产品信息所有字段
     * @param pInfo
     * @throws
     * createBy:liuyt
     * createDate:2017年12月14日
     */
    void updateAll(ProductInfo pInfo);

    /**
     * 查询分页总条数
     * @param qvo
     * @return
     * @throws
     * createBy:liuyt
     * createDate:2017年12月14日
     */
    Integer queryPageCount(ProductInfoQvo qvo);

    /**
     * 根据条件查询分页列表
     * @param qvo
     * @return
     * @throws
     * createBy:liuyt
     * createDate:2017年12月14日
     */
    List<ProductInfo> queryList(ProductInfoQvo qvo);
    
    /**
     * 查询所有的产品列表
     * @return
     * @throws
     * createBy:zhangguoliang
     * createDate:2017年12月22日
     */
    List<ProductInfo> queryAllProductList();

    /**
     * 根据物料id下架所有相关产品
     * @param mateielId
     * @throws
     * createBy:liuyt
     * createDate:2017年12月22日
     */
    void updateProductUnableByMateielId(Long mateielId);

    /**
     * 查询租金最低的产品
     * @param qvo
     * @return
     * @throws
     * createBy:liuyt
     * createDate:2018年1月2日
     */
    ProductInfo getLeasePriceLowestProduct(ProductInfoQvo qvo);

    /**
     * 根据多个型号id查询产品信息和对应的主物料主图信息
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2018年3月15日
     */
    ProductInfo queryProductInfoListWithMaterielImageByModelIds(Long materielBrandId);

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
     * 根据产品ID获取售卖商品详细信息
     * @param vo
     * @return ResponseResult
     */
    ProductInfo getSaleCommodityInfoById(ProductInfo vo);


    List<MaterielBasicImages> getImageListByMaterieId(List list);


	/**
	 * 售卖，，，名字错误
	 * @param vo
	 * @return
	 */
	List<RentProductResp> queryRentCommodityPageCount(RentProductReq vo);

	/**
	 * 租赁
	 * @param vo
	 * @return
	 */
	List<RentProductResp> queryRentCommodityPageCountForRent(RentProductReq vo);


//	getRentCommodityInfoByBrandIdAndModelIdAndConfigid
    
    /**
     * 根据品牌ID，型号ID，成色ID,分类ID查询售卖商品列表（售卖商品）
     * @param vo
     * @return
     */
    List<RentProductResp> queryRentCommodityPageList(RentProductReq vo);

	/**
	 * 根据品牌ID，型号ID，成色ID,分类ID查询租赁商品列表（租赁商品）
	 * @param vo
	 * @return
	 */
	List<RentProductResp> getRentCommodityInfoByBrandIdAndModelIdAndConfigid(RentProductReq vo);


    /**
     * 根据物料ID，获取轮播图
     * @param materielId 物料ID
     * @return
     */
    ProductInfo getImagesByMaterielId(Long materielId);

	/** 
	* @Description: 
	* @param @param productIds
	* @param @return
	* @return int
	* @throws 
	*/
	int queryAvailableProductCount(SaleProductReq req);

	/** 
	* @Description: 
	* @param @param productIds
	* @param @return
	* @return List<SaleProductResp>
	* @throws 
	*/
	List<SaleProductResp> queryAvailableProductList(SaleProductReq req);

    /**
     * 根据多个查询条件查询租赁和售卖产品信息
     * @param qvo
     * @return ProductInfo
     */
    ProductInfo getLeaseAndSellProduct(ProductInfoQvo qvo);
    
    /** 
	* @Description: 根据物料ID查询售卖产品ID列表
	* @param  materielId 售卖产品ID列表
	* @return List<Long> 售卖产品ID列表
	*/
	List<Long> querySaleProductIdList(@Param("materielId")Long materielId);

    /**
     * app首页-根据多个产品ID，imeiNo、snNo批量查询商品详细信息
     * @param qvo
     * @return ResponseResult
     */
    List<ProductInfo> batchQuerySellCommoidtyInfo(List<ProductInfoQvo> list);

    /**
     * 根据所选规格参数查询商品详情
     * @param
     * @return
     */
    ProductInfo getLeaseProductList(ProductInfoQvo qvo);

    /**
     * 获取月租金最低的产品详情
     * @param qvo
     * @return
     */
    ProductInfo getLeaseLowestDetail(ProductInfoQvo qvo);

    /**
     * 根据多个型号id查询产品信息和对应的主物料主图信息
     * @return
     * @throws
     * createBy:liuyt
     * createDate:2018年3月15日
     */
    ProductInfo queryProductMaterieImageByModelIds(ProductInfoQvo q);

	/** 
	* @Description: 
	* @param @param req
	* @param @return
	* @return List<SaleProductResp>
	* @throws 
	*/
	List<SaleProductResp> queryAvailableModelProductList(SaleProductReq req);

	/** 
	* @Description: 
	* @param @param req
	* @param @return
	* @return List<SaleProductResp>
	* @throws 
	*/
	List<SaleProductResp> queryAvailableBrandProductList(SaleProductReq req);

	/** 
	* @Description: 
	* @param @param req
	* @param @return
	* @return List<SaleProductResp>
	* @throws 
	*/
	List<SaleProductResp> queryAvailableOtherBrandProductList(SaleProductReq req);
}
