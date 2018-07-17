package org.gz.liquidation.service.transactionrecord;

import java.math.BigDecimal;
import java.util.List;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.PayReq;
import org.gz.liquidation.common.dto.PreparePayResp;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.common.dto.TransactionRecordQueryReq;
import org.gz.liquidation.common.dto.TransactionRecordReq;
import org.gz.liquidation.common.dto.TransactionRecordResp;
import org.gz.liquidation.entity.TransactionRecord;

public interface TransactionRecordService {
	/**
	 * 
	 * @Description: 新增流水记录
	 * @param transactionRecord
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月23日
	 */
	void addTransactionRecord(TransactionRecord transactionRecord);
	/**
	 * 
	 * @Description: 更新流水记录状态
	 * @param transactionRecord
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月23日
	 */
	void updateState(TransactionRecord transactionRecord);
	/**
	 * 
	 * @Description: 分页查询
	 * @param queryDto
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月23日
	 */
	ResultPager<TransactionRecordResp> selectPage(QueryDto queryDto);
	/**
	 * 
	 * @Description: 查询交易记录
	 * @param transactionRecord 交易记录实体
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月4日
	 */
	List<TransactionRecord> selectByTransactionRecord(TransactionRecord transactionRecord);
	/**
	 * 
	 * @Description: 查询单个对象
	 * @param id
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月30日
	 */
	TransactionRecord selectOneById(Long id);
	/**
	 * 
	 * @Description: 统计记录数方法
	 * @param transactionRecord
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月4日
	 */
	int selectCountStatistics(TransactionRecord transactionRecord);
	/**
	 * 
	 * @Description: 统计交易金额
	 * @param transactionRecord
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月4日
	 */
	BigDecimal selectSumAmount(TransactionRecord transactionRecord);
	/**
	 * 
	 * @Description: 手动完成支付
	 * @param req
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月8日
	 */
	ResponseResult<?> payCorrected(TransactionRecordReq req);
	/***
	 * 
	 * @Description: 验证交易
	 * @param transactionRecord
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月10日
	 */
	ResponseResult<?> verifyTransaction(TransactionRecord transactionRecord);
	/**
	 * 
	 * @Description: 生成预支付订单号，生成交易流水记录
	 * @param payReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月10日
	 */
	ResponseResult<PreparePayResp> preparePay(PayReq payReq);
	/**
	 * 
	 * @Description: 验证是否可以进行交易
	 * @param orderSN 订单号
	 * @return true 是 false 否
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月2日
	 */
	Boolean validateTrade(String orderSN); 
	/**
	 * 
	 * @Description: 查询最新的交易记录
	 * @param orderSN 订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月12日
	 */
	ResponseResult<TransactionRecordResp> queryLatest(String orderSN);
	/**
	 * 
	 * @Description: 交易统计(交易成功记录数和成交金额)
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月22日
	 */
	ResponseResult<?> queryStatistics(TransactionRecordQueryReq req);
	/**
	 * 
	 * @Description: 主动查询支付宝交易状态
	 * @param list 交易记录集合
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月28日
	 */
	void queryAlipayTrade(List<TransactionRecord> list);
}
