/**
 * 
 */
package org.gz.warehouse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gz.warehouse.common.vo.MaterielBasicInfoReq;
import org.gz.warehouse.common.vo.MaterielBasicInfoResp;
import org.gz.warehouse.entity.MaterielBasicInfo;
import org.gz.warehouse.entity.MaterielBasicInfoQuery;
import org.gz.warehouse.entity.MaterielBasicInfoVO;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月16日 上午10:47:43
 */
@Mapper
public interface MaterielBasicInfoMapper {

	/** 
	* @Description:根据分类ID,品牌ID列表查询使用量
	* @param materielClassId
	* @param materielBrandIdList
	* @return int
	*/
	int queryUsedCountByClassBrand(@Param("materielClassId")Integer materielClassId, @Param("materielBrandIdList")List<Integer> materielBrandIdList);

	/** 
	* @Description: 根据模型ID列表查询使用量
	* @param  idList
	* @return int
	*/
	int queryUsedCountByModelIds(List<Integer> idList);

	/** 
	* @Description: 
	* @param @param m
	* @param @return
	* @return int
	* @throws 
	*/
	int insert(MaterielBasicInfo m);

	/** 
	* @Description: 根据主键和物料名称查询重复数量
	* @param query
	* @return int
	*/
	int queryUniqueCount(MaterielBasicInfoQuery query);

	/** 
	* @Description: 
	* @param  id
	* @return MaterielBasicInfo
	*/
	MaterielBasicInfo selectByPrimaryKey(@Param("id")Long id);

	/** 
	* @Description: 
	* @param @param m
	* @param @return
	* @return int
	* @throws 
	*/
	int queryPageCount(MaterielBasicInfoQuery m);

	/** 
	* @Description: 
	* @param @param m
	* @param @return
	* @return List<MaterielBasicInfoVO>
	* @throws 
	*/
	List<MaterielBasicInfoVO> queryPageList(MaterielBasicInfoQuery m);

	/** 
	* @Description: 
	* @param @param m
	* @param @return
	* @return int
	* @throws 
	*/
	int update(MaterielBasicInfo m);

    /**
     * 查询物料信息
     * @param m
     * @return
     * createBy:liuyt            
     * createDate:2017年12月21日
     */
    List<MaterielBasicInfoVO> queryList(MaterielBasicInfoQuery m);

	/** 
	* @Description: 
	* @param  q
	* @return int
	*/
	int setEnableFlag(MaterielBasicInfo q);

	/** 
	* @Description: 更新物料编码
	* @param  m
	* @return int
	*/
	int updateMaterielCode(MaterielBasicInfo m);

	/** 
	* @Description: 
	* @param @param req
	* @param @return
	* @return MaterielBasicInfoResp
	* @throws 
	*/
	MaterielBasicInfoResp queryMainMaterielBasicInfo(MaterielBasicInfoReq req);

	/** 
	* @Description: 
	* @param @param req
	* @param @return
	* @return MaterielBasicInfoResp
	* @throws 
	*/
	MaterielBasicInfoResp queryMainMaterielBasicShortInfo(MaterielBasicInfoReq req);

	
	/** 
	* @Description: 
	* @param @param req
	* @param @return
	* @return MaterielBasicInfoResp
	* @throws 
	*/
	MaterielBasicInfoResp queryMainMaterielBasicIntroductionInfo(MaterielBasicInfoReq req);

	/** 
	* @Description: 根据物料的分类ID,品牌ID,型号ID查询主物料ID
	* @param  id
	* @return Long
	*/
	Long queryMainMaterielBasicId(@Param("id")Long id);

	/** 
	* @Description: 根据物料ID查询物料图文介绍
	* @param  id
	* @return String
	*/
	String queryOnlyMaterielIntroduction(@Param("id")Long id);

	/** 
	* @Description: 根据物料ID查询物料附件列表
	* @param id
	* @return List<String>
	*/
	List<String> queryOnlyMaterielAttaUrl(@Param("materielBasicInfoId")Long materielBasicInfoId);

}
