package org.gz.warehouse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gz.warehouse.entity.MaterielSpec;
import org.gz.warehouse.entity.MaterielSpecQuery;
import org.gz.warehouse.entity.MaterielSpecVo;

/**
 * 物料规格Mapper
 * 
 * @author hxj
 *
 */
@Mapper
public interface MaterielSpecMapper {

	/**
	 * 插入数据
	 * 
	 * @param m
	 * @return
	 */
	int insert(MaterielSpec m);

	/**
	 * 查询重复数量
	 * 
	 * @param query
	 * @return
	 */
	int queryUniqueCount(MaterielSpecQuery query);

	/**
	 * 更新数据
	 * 
	 * @param m
	 * @return
	 */
	int update(MaterielSpec m);

	/**
	 * 删除数据
	 * @param idList
	 * @return
	 */
	int deleteByIds(List<Integer> idList);

	/**
	 * 根据主键ID查询详情
	 * @param id
	 * @return
	 */
	MaterielSpec selectByPrimaryKey(@Param("id")Integer id);

	/**
	 * 查询分页总数
	 * @param m
	 * @return
	 */
	int queryPageCount(MaterielSpecQuery m);

	/**
	 * 查询分页列表
	 * @param m
	 * @return
	 */
	List<MaterielSpec> queryPageList(MaterielSpecQuery m);

	int queryUsedCount(List<Integer> idList);

    /**
     * 根据型号Id查询对应的规格批次信息
     * @param modelId
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月15日
     */
    List<MaterielSpecVo> queryMaterielSpecListByModelId(Integer modelId);
}
