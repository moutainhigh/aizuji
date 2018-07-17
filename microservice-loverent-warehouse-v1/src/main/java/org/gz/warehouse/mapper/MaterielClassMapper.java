package org.gz.warehouse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gz.common.constants.ZTreeSimpleNode;
import org.gz.warehouse.entity.MaterielClass;
import org.gz.warehouse.entity.MaterielClassQuery;

@Mapper
public interface MaterielClassMapper {

    /**
     * 查询所有物料分类列表
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月12日
     */
    List<MaterielClass> queryAllMaterielClassList();

	int insert(MaterielClass m);

	int update(MaterielClass m);

	int deleteByIds(List<Integer> idList);

	int queryUsedCount(List<Integer> idList);

	MaterielClass selectByPrimaryKey(@Param("id")Integer id);

	int queryPageCount(MaterielClassQuery m);

	List<MaterielClass> queryPageList(MaterielClassQuery m);

	int queryUniqueCount(MaterielClassQuery query);
	
	List<MaterielClass> queryChildList(@Param("parentId")Integer parentId);

	List<ZTreeSimpleNode> getChildrenByParentId(@Param("parentId")Integer parentId);

	ZTreeSimpleNode getNodeById(@Param("id")Integer id);


}
