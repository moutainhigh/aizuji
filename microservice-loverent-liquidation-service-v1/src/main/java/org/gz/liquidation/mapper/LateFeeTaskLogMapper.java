package org.gz.liquidation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.liquidation.entity.LateFeeTaskLogEntity;
/**
 * 
 * @Description:滞纳金定时任务执行日志Mapper
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月17日 	Created
 */
@Mapper
public interface LateFeeTaskLogMapper {

    int insert(LateFeeTaskLogEntity lateFeeTaskLogEntity);

    int insertSelective(LateFeeTaskLogEntity lateFeeTaskLogEntity);

    /**
     * 
     * @Description: 批量新增
     * @param list
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年3月8日
     */
    void insertBatch(List<LateFeeTaskLogEntity> list);
    /**
     * 
     * @Description: 根据条件查询数据
     * @param lateFeeEntity 实体对象参数
     * @return 实体对象
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年3月12日
     */
    int checkIsExecuted(LateFeeTaskLogEntity lateFeeTaskLogEntity);
}