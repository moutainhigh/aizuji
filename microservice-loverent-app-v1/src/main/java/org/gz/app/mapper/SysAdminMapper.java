/**
 * 
 */
package org.gz.app.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.gz.app.entity.SysAdmin;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月4日 下午4:55:59
 */
@Mapper
public interface SysAdminMapper {

	/** 
	* @Description: 
	* @param @param admin
	* @param @return
	* @return int
	* @throws 
	*/
	int insert(SysAdmin admin);

	/** 
	* @Description: 
	* @param @param id
	* @param @return
	* @return SysAdmin
	* @throws 
	*/
	SysAdmin selectById(Long id);

}
