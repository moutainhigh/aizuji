package org.gz.warehouse.service.materiel;

import java.util.List;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.common.vo.MaterielModelResp;
import org.gz.warehouse.common.vo.MaterielModelVo;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.ProductInfoQvo;
import org.gz.warehouse.entity.MaterielModel;
import org.gz.warehouse.entity.MaterielModelQuery;

/**
 * 物料型号service
 * Author	Version		Date		Changes
 * liuyt 	1.0  		2017年12月12日 	Created
 */
public interface MaterielModelService {

    /**
     * 根据品牌id查询物料型号列表
     * @param brandId
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月12日
     */
    List<MaterielModel> queryMaterielModelListByBrandId(Integer brandId);

	/**
	 * @Description: ${根据品牌id查询物料型号列表-----包含缩略图}
	 * @param ${tags}
	 * @return ${return_type}
	 * @throws
	 * @author WenMing.Zhou
	 * @date 2018/3/22 18:57
	 */
	ResultPager<MaterielModel> queryMaterielModelPicListByBrandId(MaterielModelQuery m);

	/** 
	* @Description: 
	* @param @param m
	* @param @return
	* @return ResponseResult<?>
	* @throws 
	*/
	ResponseResult<MaterielModel> insert(MaterielModel m);

	/** 
	* @Description: 
	* @param @param m
	* @param @return
	* @return ResponseResult<?>
	* @throws 
	*/
	ResponseResult<MaterielModel> update(MaterielModel m);

	/** 
	* @Description: 
	* @param @param m
	* @param @return
	* @return ResponseResult<?>
	* @throws 
	*/
	ResponseResult<MaterielModel> setEnableFlag(MaterielModel m);

	/** 
	* @Description: 
	* @param @param ids
	* @param @return
	* @return ResponseResult<?>
	* @throws 
	*/
	ResponseResult<MaterielModel> deleteByIds(String ids);

	/** 
	* @Description: 
	* @param @param id
	* @param @return
	* @return ResponseResult<?>
	* @throws 
	*/
	ResponseResult<MaterielModel> selectByPrimaryKey(Integer id);

	/** 
	* @Description: 
	* @param @param m
	* @param @return
	* @return ResponseResult<ResultPager<MaterielModel>>
	* @throws 
	*/
	ResponseResult<ResultPager<MaterielModel>> queryByPage(MaterielModelQuery m);

	/**
	 * 查询所有有效物料型号
	 * @return
	 */
    List<MaterielModel> queryAllAvalibles(ProductInfoQvo qvo);
    
	/** 
	* @Description: 
	* @param @param ids
	* @param @return
	* @return ResponseResult<List<MaterielModelResp>>
	* @throws 
	*/
	ResponseResult<List<MaterielModelResp>> queryModelList(String ids);

	List<MaterielModelVo> getMaterielModelById(List<Integer> modelIdList);

	/**
	 * 根据型号ID查询最低月租金
	 * @param vo
	 * @return ResponseResult
	 */
    ResponseResult<List<ProductInfo>> getMinAmountByModelId(ProductInfoQvo vo);
}
