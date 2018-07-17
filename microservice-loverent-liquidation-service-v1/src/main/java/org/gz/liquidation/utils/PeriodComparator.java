package org.gz.liquidation.utils;

import java.util.Comparator;

import org.gz.liquidation.entity.RepaymentSchedule;
/**
 * 
 * @Description:账期比较器，排序使用
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月2日 	Created
 */
public class PeriodComparator implements Comparator<RepaymentSchedule> {

	@Override
	public int compare(RepaymentSchedule o1, RepaymentSchedule o2) {
		return o1.getPeriodOf() - o2.getPeriodOf();
	}

}
