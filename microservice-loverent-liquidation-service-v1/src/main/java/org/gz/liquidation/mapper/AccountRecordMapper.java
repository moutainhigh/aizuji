package org.gz.liquidation.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.gz.liquidation.common.dto.DownpaymentStatisticsReq;
import org.gz.liquidation.common.dto.DownpaymentStatisticsResp;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.entity.AccountRecord;

/**
 * 
 * @Description:TODO	科目记录mapper
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月21日 	Created
 */
@Mapper
public interface AccountRecordMapper {

	    int insertSelective(AccountRecord record);

	    AccountRecord selectByPrimaryKey(Long id);

	    int updateByPrimaryKeySelective(AccountRecord record);
	    /**
	     * 
	     * @Description: TODO 批量保存
	     * @param list
	     * @throws
	     * createBy:liaoqingji            
	     * createDate:2017年12月22日
	     */
	    void insertBatch(List<AccountRecord> list);
	    /**
	     * 
	     * @Description: TODO 	查询列表
	     * @param accountRecord
	     * @return
	     * @throws
	     * createBy:liaoqingji            
	     * createDate:2018年1月30日
	     */
	    List<AccountRecord> selectList(AccountRecord accountRecord);
	    /**
	     * 
	     * @Description: TODO 计算科目金额
	     * @param accountRecord
	     * @return
	     * @throws
	     * createBy:liaoqingji            
	     * createDate:2017年12月23日
	     */
	    BigDecimal selectSumAmount(AccountRecord accountRecord);
	    /**
	     * 
	     * @Description: TODO 分页查询
	     * @param queryDto
	     * @return
	     * @throws
	     * createBy:liaoqingji            
	     * createDate:2017年12月23日
	     */
	    List<AccountRecord> selectPage(QueryDto queryDto);
	    /**
	     * 
	     * @Description: TODO 统计金额
	     * @param accountRecord
	     * @return
	     * @throws
	     * createBy:liaoqingji            
	     * createDate:2017年12月25日
	     */
	    List<AccountRecord> selectAmount(AccountRecord accountRecord);
	    /**
	     * 
	     * @Description: TODO 首期款统计
	     * @return
	     * @throws
	     * createBy:liaoqingji            
	     * createDate:2018年1月4日
	     */
	    List<DownpaymentStatisticsResp> selectDownpaymentStatistics(DownpaymentStatisticsReq downpaymentStatisticsReq);
	    /**
	     * 
	     * @Description: TODO 查询集合列表
	     * @param map
	     * @return
	     * @throws
	     * createBy:liaoqingji            
	     * createDate:2018年2月1日
	     */
	    List<AccountRecord> selectRevenueList(Map<String,Object> map);
	    /**
	     * 
	     * @Description: 根据订单号统计滞纳金和减免滞纳金
	     * @param list
	     * @return
	     * @throws
	     * createBy:liaoqingji            
	     * createDate:2018年3月7日
	     */
	    List<AccountRecord> sumLatefee(List<String> list);
}
