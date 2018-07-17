/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.risk.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gz.risk.bean.ViolationDto;
import org.gz.risk.entity.Violation;
/**
 * User Dao
 * 
 * @author zhuangjianfa
 * @date 2017-07-03
 */
public interface ViolationMapper extends BaseDao<Violation> {
	
	/**
	 * 
	 * @Description: 如果需要分页或动态排序查询，将参数修改为QueryDto,再将查询条件、动态排序、分页查询数据放进QueryDto即可，无须修改mapper文件
	 * @return
	 * @throws
	 * createBy:zhuangjianfa              
	 * createDate:2017年1月25日上午11:15:06
	 */
	public List<Violation> queryList(ViolationDto dto);
	
	
	/**
	 * @Description: 新增并返回主键
	 * @param violation
	 * @return
	 * @throws
	 * createBy:zhangguoliang            
	 * createDate:2017年8月4日
	 */
	public Long addGetPK(Violation violation);
	
	
	public void updateSwitch(Map<String, Integer> map);
	
	
	public Map<String, Object> querySwitch();
	
	public List<Violation> queryListByUserId(long userId);
	
	public List<Violation> selectByMeal(HashMap<String, Object> map);
	
	public Violation selectByVio(HashMap<String, Object> map);
	
}
