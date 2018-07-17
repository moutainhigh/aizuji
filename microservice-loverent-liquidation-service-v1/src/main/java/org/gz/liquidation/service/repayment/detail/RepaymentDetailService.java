package org.gz.liquidation.service.repayment.detail;

import java.util.List;

import org.gz.liquidation.entity.RepaymentDetailEntity;

public interface RepaymentDetailService {
	/**
	 * 
	 * @Description: 新增还款记录
	 * @param repaymentRecordEntity 还款记录实体
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月13日
	 */
	void insertSelective(RepaymentDetailEntity repaymentDetailEntity);
	/**
	 * 
	 * @Description: 查询还款记录列表
	 * @param repaymentRecordEntity
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月13日
	 */
	List<RepaymentDetailEntity> selectList(RepaymentDetailEntity repaymentDetailEntity);
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
