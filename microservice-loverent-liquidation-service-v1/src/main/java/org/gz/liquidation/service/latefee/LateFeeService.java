package org.gz.liquidation.service.latefee;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.LateFeeRemissionReq;
import org.gz.liquidation.common.dto.RepaymentReq;
import org.gz.liquidation.entity.LateFeeEntity;
import org.gz.liquidation.entity.RepaymentSchedule;

/**
 * 
 * @Description:	滞纳金服务接口
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年2月8日 	Created
 */
public interface LateFeeService {
	/**
	 * 
	 * @Description: 滞纳金计算
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月8日
	 */
	public void calculateLateFee();
	/**
	 * 
	 * @Description: 批量更新逾期天数和违约状态
	 * @param list
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月9日
	 */
	void updateOverdueDayBatch(List<RepaymentSchedule> list);
	/**
	 * 
	 * @Description: 查询滞纳金列表
	 * @param list 订单号集合
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月8日
	 */
	List<LateFeeEntity> selectListByOrderSNs(List<String> list);
	/**
	 * 
	 * @Description: 批量新增
	 * @param list
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月9日
	 */
	void batchInsert(List<LateFeeEntity> list);
	/**
	 * 
	 * @Description: 批量更新
	 * @param list
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月9日
	 */
	void batchUpdate(List<LateFeeEntity> list);
	/**
	 * 
	 * @Description: 批量失效
	 * @param list
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月9日
	 */
	void batchUpdateEnableFlag(List<LateFeeEntity> list);
	/**
	 * 
	 * @Description: 批量保存或者更新
	 * @param list
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月12日
	 */
	void saveOrUpdate(List<LateFeeEntity> list);
	/**
	 * 
	 * @Description: 批量查询数据是否存在
	 * @param list 订单号集合
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月12日
	 */
	List<LateFeeEntity> batchQueryIsExist(List<String> list);
	/**
	 * 
	 * @Description: 查询应付滞纳金
	 * @param orderSN 订单号
	 * @return 产生的滞纳金，如果没有则返回0
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月12日
	 */
	BigDecimal lateFeePayable(String orderSN);
	/**
	 * 
	 * @Description: 查询滞纳金实体对象
	 * @param lateFeeEntity 实体对象参数
	 * @return Optional<LateFeeEntity>
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月12日
	 */
	Optional<LateFeeEntity> selectLateFee(LateFeeEntity lateFeeEntity);
	/**
	 * 
	 * @Description: 减免滞纳金
	 * @param lateFeeRemissionReq
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月13日
	 */
	ResponseResult<String> doLateFeeRemission(LateFeeRemissionReq lateFeeRemissionReq);
	/**
	 * 
	 * @Description: 通过主键id选择性更新不为null的属性值
	 * @param lateFeeEntity
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月13日
	 */
	void updateByPrimaryKeySelective(LateFeeEntity lateFeeEntity);
	/**
	 * 
	 * @Description: 偿还滞纳金
	 * @param repaymentReq
	 * @param userId 用户id
	 * @param orderSN 订单号
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月13日
	 */
	void repayLateFee(RepaymentReq repaymentReq,Long userId,String orderSN);
	/**
	 * 
	 * @Description: 数据是否存在
	 * @param lateFeeEntity
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月17日
	 */
	LateFeeEntity selectByCondition(LateFeeEntity lateFeeEntity);
	/**
	 * 
	 * @Description: 新增对象(属性不为空的值才保存到数据库)
	 * @param lateFeeEntity
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月17日
	 */
	void insertSelective(LateFeeEntity lateFeeEntity);
	   /**
     * 
     * @Description: 查询未结清的滞纳金列表数据
     * @param orderSN 订单号
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年3月19日
     */
    List<LateFeeEntity> selectUnsettledList(String orderSN);
    /**
     * 
     * @Description: 核销滞纳金
     * @param list
     * @return 核销后的数据集合
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年3月19日
     */
    Optional<List<LateFeeEntity>> writeOffLateFee(RepaymentReq repaymentReq, List<LateFeeEntity> list);
}
