/**
 * 
 */
package org.gz.warehouse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gz.warehouse.entity.MaterielModelSpec;
import org.gz.warehouse.entity.MaterielModelSpecQuery;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月16日 下午1:54:05
 */
@Mapper
public interface MaterielModelSpecMapper {

	/** 
	* @Description: 
	* @param @param modelSpecList
	* @return void
	* @throws 
	*/
	int batchInsert(List<MaterielModelSpec> modelSpecList);

	/**
	 * 
	* @Description: 
	* @param @param query
	* @param @return
	* @return int
	* @throws
	 */
	int queryPageCount(MaterielModelSpecQuery query);
	
	
	/** 
	* @Description: 
	* @param @param query
	* @param @return
	* @return List<MaterielModelSpec>
	* @throws 
	*/
	List<MaterielModelSpec> queryPageList(MaterielModelSpecQuery query);

	/** 
	* @Description: 
	* @param @param id
	* @param @param specBatchNo
	* @return void
	* @throws 
	*/
	int batchDelete(@Param("materielModelId")Integer materielModelId, @Param("specBatchNo")String specBatchNo);

	/** 
	* @Description: 
	* @param @param materielModelIdList
	* @param @return
	* @return int
	* @throws 
	*/
	int batchDeleteByModelIdList(List<Integer> materielModelIdList);

    /**
     * 查询某型号下已配置到产品中的所有规格值（按规格id分组）
     * @param modelId
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2018年1月3日
     */
    List<MaterielModelSpec> querySpecListGroupBySpecId(Integer modelId);

    /**
     * 查询某型号下已配置到产品中的所有规格值（按规格批次号分组）
     * @param modelId
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2018年1月3日
     */
    List<MaterielModelSpec> querySpecListGroupBySpecBatchNo(Integer modelId);
}
