package org.gz.liquidation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.liquidation.entity.RepaymentDetailEntity;
@Mapper
public interface RepaymentDetailMapper {

    int insert(RepaymentDetailEntity record);
    /**
     * 
     * @Description: 新增
     * @param record
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年3月14日
     */
    int insertSelective(RepaymentDetailEntity record);

    RepaymentDetailEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RepaymentDetailEntity record);

    int updateByPrimaryKey(RepaymentDetailEntity record);
    /**
     * 
     * @Description: 批量新增
     * @param list
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年3月14日
     */
	void insertBatch(List<RepaymentDetailEntity> list);
}