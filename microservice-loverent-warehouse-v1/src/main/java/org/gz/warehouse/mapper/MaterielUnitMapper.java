package org.gz.warehouse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gz.warehouse.entity.MaterielUnit;
import org.gz.warehouse.entity.MaterielUnitQuery;

/**
 * 物料单位Mapper
 * @author hxj
 *
 */
@Mapper
public interface MaterielUnitMapper {

	/**
	 * 插入数据
	 * @param m
	 * @return
	 */
	int insert(MaterielUnit m);

	/**
	 * 查询重复数量
	 * @param query
	 * @return
	 */
	int queryUniqueCount(MaterielUnitQuery query);

	/**
	 * 修改数据
	 * @param m
	 * @return
	 */
	int update(MaterielUnit m);

	/**
	 * 删除数据
	 * @param idList
	 * @return
	 */
	int deleteByIds(List<Integer> ids);

	/**
	 * 查询materiel_basic_info表使用的数量
	 * @param idList
	 * @return
	 */
	int queryUsedCount(List<Integer> ids);

	/**
	 * 根据主键ID查询详情
	 * @param id
	 * @return
	 */
	MaterielUnit selectByPrimaryKey(@Param("id")Integer id);

	/**
	 * 查询分页总条数
	 * @param m
	 * @return
	 */
	int queryPageCount(MaterielUnitQuery m);

	/**
	 * 查询分页列表
	 * @param m
	 * @return
	 */
	List<MaterielUnit> queryPageList(MaterielUnitQuery m);

	
}
