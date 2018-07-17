package org.gz.app.service.user.impl;

import org.gz.app.entity.SysAdmin;
import org.gz.app.mapper.SysAdminMapper;
import org.gz.app.service.user.SysAdminService;
import org.gz.common.resp.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*@Service*/
public class SysAdminServiceImpl implements SysAdminService {

	/*@Autowired*/
	private SysAdminMapper adminMapper;
	
	@Override
	public ResponseResult<SysAdmin> addSysAdmin(SysAdmin admin) {
		int flag =  adminMapper.insert(admin);
		return flag>0?ResponseResult.buildSuccessResponse(admin):ResponseResult.build(1000, "database access exceptin", admin);
	}

	@Override
	public SysAdmin getSysAdmin(Long id) {
		return adminMapper.selectById(id);
	}

}
