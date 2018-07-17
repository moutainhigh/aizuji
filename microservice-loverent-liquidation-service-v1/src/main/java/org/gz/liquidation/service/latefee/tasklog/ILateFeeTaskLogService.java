package org.gz.liquidation.service.latefee.tasklog;

import org.gz.liquidation.entity.LateFeeTaskLogEntity;

public interface ILateFeeTaskLogService {
	/**
	 * 
	 * @Description: 新增执行日志
	 * @param lateFeeTaskLogEntity
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月17日
	 */
	void insert(LateFeeTaskLogEntity lateFeeTaskLogEntity);
	/**
	 * 
	 * @Description: 校验任务是否执行过
	 * @param lateFeeTaskLogEntity
	 * @return true 是 false 否
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月17日
	 */
	boolean checkIsExecuted(LateFeeTaskLogEntity lateFeeTaskLogEntity);
}
