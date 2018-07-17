/**
 * 
 */
package org.gz.app.service.user;

import org.gz.app.entity.SysAdmin;
import org.gz.common.resp.ResponseResult;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月4日 下午4:55:34
 */
public interface SysAdminService {

	/** 
	* @Description: 
	* @param admin
	* @return ResponseResult<?>
	* @throws 
	*/
	ResponseResult<SysAdmin> addSysAdmin(SysAdmin admin);

	/** 
	* @Description: 
	* @param id
	* @return SysAdmin
	* @throws 
	*/
	SysAdmin getSysAdmin(Long id);

}
