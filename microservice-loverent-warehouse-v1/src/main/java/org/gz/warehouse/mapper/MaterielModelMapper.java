package org.gz.warehouse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gz.warehouse.common.vo.MaterielModelResp;
import org.gz.warehouse.common.vo.MaterielModelVo;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.ProductInfoQvo;
import org.gz.warehouse.entity.MaterielModel;
import org.gz.warehouse.entity.MaterielModelQuery;

@Mapper
public interface MaterielModelMapper {

    /**
     * 通过品牌Id查询型号列表
     * @param brandId
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月12日
     */
    List<MaterielModel> queryMaterielModelListByBrandId(Integer brandId);

	/**
	 * 通过品牌Id查询型号列表(包含缩略图)
	 * @param brandId
	 * @return
	 * @throws
	 * createBy:周文明
	 * createDate:2018年3月22日
	 */
	List<MaterielModel> queryMaterielModelPicListByBrandId(MaterielModelQuery m);

	int queryPageTotal(Integer brandId);

	/** 
	* @Description: 
	* @param @param m
	* @param @return
	* @return int
	* @throws 
	*/
	int insert(MaterielModel m);

	/** 
	* @Description: 
	* @param @param query
	* @param @return
	* @return int
	* @throws 
	*/
	int queryUniqueCount(MaterielModelQuery query);

	/** 
	* @Description: 
	* @param @param id
	* @param @return
	* @return MaterielModel
	* @throws 
	*/
	MaterielModel selectByPrimaryKey(@Param("id")Integer id);

	/** 
	* @Description: 更新数据
	* @param @param m
	* @param @return
	* @return int
	* @throws 
	*/
	int update(MaterielModel m);

	/** 
	* @Description: 
	* @param @param queryModel
	* @return void
	* @throws 
	*/
	int setEnableFlag(MaterielModel queryModel);

	/** 
	* @Description: 
	* @param @param m
	* @param @return
	* @return int
	* @throws 
	*/
	int queryPageCount(MaterielModelQuery m);

	/** 
	* @Description: 
	* @param @param m
	* @param @return
	* @return List<MaterielModel>
	* @throws 
	*/
	List<MaterielModel> queryPageList(MaterielModelQuery m);

	/** 
	* @Description: 
	* @param @param idList
	* @param @return
	* @return int
	* @throws 
	*/
	int deleteByIds(List<Integer> idList);

	/**
	 * 
	 * @return
	 */
    List<MaterielModel> queryAllAvalibles(ProductInfoQvo qvo);


	/** 
	* @Description: 
	* @param @param idList
	* @param @return
	* @return List<MaterielModelResp>
	* @throws 
	*/
	List<MaterielModelResp> queryModelList(List<Integer> idList);

	List<MaterielModelVo> getMaterielModelById(List<Integer> modelIdList);

	/**
	 * 根据型号Id，查询最低月租金
	 * @param param
	 * @return ProductInfo
	 */
    List<ProductInfo> getMinAmountByModelId(List<Long> param);
}
