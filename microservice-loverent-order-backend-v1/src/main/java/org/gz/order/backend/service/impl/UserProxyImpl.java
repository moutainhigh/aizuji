package org.gz.order.backend.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.JsonUtils;
import org.gz.order.backend.service.UserProxy;
import org.gz.order.common.entity.UserHistory;
import org.gz.order.server.service.UserHistoryService;
import org.gz.user.dto.ContactInfoQueryDto;
import org.gz.user.dto.UserInfoDto;
import org.gz.user.entity.AddressInfo;
import org.gz.user.entity.AppUser;
import org.gz.user.entity.UserInfo;
import org.gz.user.service.AddressInfoService;
import org.gz.user.service.ContactInfoService;
import org.gz.user.service.UserInfoService;
import org.gz.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProxyImpl implements UserProxy {

    private Logger             logger = LoggerFactory.getLogger(UserProxyImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ContactInfoService contactInfoService;

    @Autowired
    private AddressInfoService addressInfoService;

    @Autowired
    private UserHistoryService userHistoryService;

    @Value("${aliyun.oss.accessUrlPrefix}")
    private String             accessUrlPrefix;

    @Autowired
    private UserInfoService    userInfoService;

    @Override
    public ResponseResult<AppUser> queryUserById(Long userId) {
        return userService.queryUserById(userId);
    }
    
    @Override
	public ResponseResult<UserInfoDto> queryUserInfoByUserId(Long userId) {
		return userInfoService.queryByUserId(userId);
	}

    @Override
    @Transactional
    public ResponseResult<?> updateUser(UserHistory user) {
        // 先更新到订单系统
        userHistoryService.update(user);

        // 同步到用户系统
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getUserId());
        userInfo.setBirthday(user.getBirthday());
        userInfo.setAge(user.getAge());
        userInfo.setJob(user.getJob());
        userInfo.setMarital(user.getMarital());
        userInfo.setEducation(user.getEducation());
        userInfo.setIndustry(user.getIndustry());
        userInfo.setMonthIncome(user.getMonthIncome());
        userInfo.setPhoneUserTime(user.getPhoneUserTime());
        userInfo.setUserEmail(user.getUserEmail());
        userInfo.setCompanyName(user.getCompanyName());
        userInfo.setEntryTime(user.getEntryTime());
        userInfo.setPosition(user.getPosition());
        userInfo.setCompanyContactNumber(user.getCompanyContactNumber());
        userInfo.setCompanyAddress(user.getCompanyAddress());
        userInfo.setSchoolName(user.getSchoolName());
        userInfo.setLivingExpenses(user.getLivingExpenses());
        userInfo.setSchoolAddress(user.getSchoolAddress());
        userInfo.setHasLoanRecord(user.getHasLoanRecord());
        userInfo.setLoanAmount(user.getLoanAmount());
        userInfo.setMonthRepayment(user.getMonthRepayment());
        userInfo.setCreateTime(user.getCreateTime());
        userInfo.setUpdateTime(user.getUpdateTime());
        logger.info("userInfoService.updateUserInfo begin.. the param:{}", JsonUtils.toJsonString(userInfo));
        ResponseResult<String> result = userInfoService.updateUserInfo(userInfo);
        logger.info("userInfoService.updateUserInfo end.. the returnVal:{}", JsonUtils.toJsonString(result));
        if (!result.isSuccess()) {
            throw new ServiceException(result.getErrCode(), result.getErrMsg());
        }
        return result;
    }

    @Override
    public ResponseResult<AddressInfo> queryAddressByUserId(Long userId) {
        return addressInfoService.queryAddressByUserId(userId);
    }

    @Override
    public ResponseResult<ContactInfoQueryDto> queryContactInfoByPage(ContactInfoQueryDto queryDto) {
        return contactInfoService.queryContactInfoByPage(queryDto);
    }

    @Override
    public ResponseResult<UserHistory> queryUserSnapByOrderNo(String orderNo) {
        UserHistory uh = userHistoryService.queryUserHistory(orderNo);
        if (StringUtils.isNotBlank(uh.getCardFaceUrl())) {
            uh.setCardFaceUrl(accessUrlPrefix + uh.getCardFaceUrl());
        }
        if (StringUtils.isNotBlank(uh.getCardBackUrl())) {
            uh.setCardBackUrl(accessUrlPrefix + uh.getCardBackUrl());
        }
        if (StringUtils.isNotBlank(uh.getFaceAuthUrl())) {
            uh.setFaceAuthUrl(accessUrlPrefix + uh.getFaceAuthUrl());
        }
        return ResponseResult.buildSuccessResponse(uh);
    }


}
