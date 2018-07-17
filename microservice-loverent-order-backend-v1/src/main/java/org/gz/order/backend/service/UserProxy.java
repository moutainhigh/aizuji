package org.gz.order.backend.service;

import org.gz.common.resp.ResponseResult;
import org.gz.order.common.entity.UserHistory;
import org.gz.user.dto.ContactInfoQueryDto;
import org.gz.user.dto.UserInfoDto;
import org.gz.user.entity.AddressInfo;
import org.gz.user.entity.AppUser;

/**
 * 用户代理
 * @Description:TODO
 * Author	Version		Date		Changes
 * liuyt 	1.0  		2017年12月27日 	Created
 */
public interface UserProxy {

    /**
     * 通过userId查询用户
     * @param userId
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月27日
     */
    ResponseResult<AppUser> queryUserById(Long userId);
    
    /**
     * 通过userId查询用户信息
     * @param userId
     * @return
     */
    ResponseResult<UserInfoDto> queryUserInfoByUserId(Long userId);

    /**
     * 更新用户信息
     * @param user
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月27日
     */
    ResponseResult<?> updateUser(UserHistory user);

    /**
     * 通过用户id查询联系地址
     * @param userId
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月27日
     */
    ResponseResult<AddressInfo> queryAddressByUserId(Long userId);

    /**
     * 通过用户id查询通讯录
     * @param userId
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月27日
     */
    ResponseResult<ContactInfoQueryDto> queryContactInfoByPage(ContactInfoQueryDto queryDto);

    /**
     * 查询用户下单时的快照信息
     * @param orderNo
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2018年1月30日
     */
    ResponseResult<UserHistory> queryUserSnapByOrderNo(String orderNo);

}
