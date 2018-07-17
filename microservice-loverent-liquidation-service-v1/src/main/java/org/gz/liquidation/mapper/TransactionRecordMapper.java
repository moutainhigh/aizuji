package org.gz.liquidation.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.entity.TransactionRecord;
/**
 * 
 * @Description:TODO	交易流水记录mapper
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月23日 	Created
 */
@Mapper
public interface TransactionRecordMapper {
	/**
	 * 
	 * @Description: TODO 新增数据（属性不为null）
	 * @param record
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月23日
	 */
    int insertSelective(TransactionRecord record);
    /**
     * 
     * @Description: TODO 根据主键查询单条记录
     * @param id
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月23日
     */
    TransactionRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TransactionRecord record);

    int updateByPrimaryKey(TransactionRecord record);
    /**
     * 
     * @Description: TODO 更新状态
     * @param transactionRecord
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月23日
     */
    int updateStateSelective(TransactionRecord transactionRecord);
    /**
     * 
     * @Description: TODO 分页查询
     * @param queryDto
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月23日
     */
    List<TransactionRecord> selectPage(QueryDto queryDto);
    /**
     * 
     * @Description: TODO 查询列表
     * @param transactionRecord	查询对象
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月26日
     */
    List<TransactionRecord> selectByTransactionRecord(TransactionRecord transactionRecord);
    /**
     * 
     * @Description: TODO 统计记录数
     * @param transactionRecord
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年1月4日
     */
    int selectCountStatistics(TransactionRecord transactionRecord);
    /**
     * 
     * @Description: TODO 统计交易金额
     * @param transactionRecord
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年1月4日
     */
    BigDecimal selectSumAmount(TransactionRecord transactionRecord);
    /**
     * 
     * @Description: TODO 根据条件条件记录数
     * @param transactionRecord
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年2月2日
     */
    int selectCount(TransactionRecord transactionRecord);
    /**
     * 
     * @Description: 根据订单号查询最新的交易记录
     * @param orderSN	订单号
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年2月12日
     */
    TransactionRecord queryLatest(String orderSN);
}