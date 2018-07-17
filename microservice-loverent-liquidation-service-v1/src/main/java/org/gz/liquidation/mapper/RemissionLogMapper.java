package org.gz.liquidation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.entity.RemissionLog;
/**
 * 
 * @Description:TODO	减免记录Mapper
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月22日 	Created
 */
@Mapper
public interface RemissionLogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(RemissionLog record);
    /**
     * 
     * @Description: TODO 新增列对应不为空的属性
     * @param record
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年1月22日
     */
    int insertSelective(RemissionLog record);
    /**
     * 
     * @Description: TODO 根据id查询单条记录
     * @param id
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年1月22日
     */
    RemissionLog selectByPrimaryKey(Long id);
    /**
     * 
     * @Description: TODO 选择性更新字段
     * @param record
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年1月22日
     */
    int updateByPrimaryKeySelective(RemissionLog record);
    int updateByPrimaryKey(RemissionLog record);
    /**
     * 
     * @Description: TODO 分页查询
     * @param queryDto
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年2月8日
     */
    List<RemissionLog> selectPage(QueryDto queryDto);
}