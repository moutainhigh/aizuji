package org.gz.liquidation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.common.dto.RefundLogQueryReq;
import org.gz.liquidation.entity.RefundLog;
/**
 * 
 * @Description:	退款记录Mapper
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月22日 	Created
 */
@Mapper
public interface RefundLogMapper {
    /**
     * 
     * @Description: 新增列对应不为空的属性
     * @param refundLog
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年1月22日
     */
    int insertSelective(RefundLog refundLog);
    /**
     * 
     * @Description: 根据id查询单条记录
     * @param id
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年1月22日
     */
    RefundLog selectByPrimaryKey(Long id);
    /**
     * 
     * @Description: 选择性更新字段
     * @param refundLog
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年1月22日
     */
    int updateByPrimaryKeySelective(RefundLog refundLog);
    /**
     * 
     * @Description: 查询减免记录列表
     * @param refundLogReq
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年1月22日
     */
    List<RefundLog> selectList(RefundLogQueryReq refundLogReq);
    /**
     * 
     * @Description: 分页查询退款记录
     * @param queryDto
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年2月7日
     */
    List<RefundLog> selectPage(QueryDto queryDto);
}