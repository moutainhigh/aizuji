package org.gz.workorder.backend.service;


import org.apache.commons.lang.StringUtils;
import org.gz.common.entity.AuthUser;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.BeanConvertUtil;
import org.gz.order.common.Enum.OrderResultCode;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.RentRecordQuery;
import org.gz.order.common.dto.WorkOrderRsp;
import org.gz.user.dto.UserInfoDto;
import org.gz.user.entity.AppUser;
import org.gz.user.service.UserService;
import org.gz.workorder.backend.dto.AppUserExtendDto;
import org.gz.workorder.backend.feign.OrderClient;
import org.gz.workorder.backend.feign.LiquidationClient;
import org.gz.workorder.commons.WorkOrderConstants;
import org.gz.workorder.dto.SearchRecordQueryDto;
import org.gz.workorder.entity.SearchRecord;
import org.gz.workorder.entity.UpdateRecord;
import org.gz.workorder.service.SearchRecordService;
import org.gz.workorder.service.UpdateRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InfoSearchService {

	Logger logger = LoggerFactory.getLogger(InfoSearchService.class);
	
	@Autowired
    private UserService userService;
    @Autowired
    private SearchRecordService searchRecordService;
    @Autowired
    private OrderClient rentRecordClient;
    @Autowired
    private LiquidationClient repaymentClient;
    @Autowired
    private UpdateRecordService updateRecordService;
    
	public ResponseResult<SearchRecordQueryDto> queryPageSearchRecord(SearchRecordQueryDto searchRecordQueryDto) {
		return searchRecordService.queryPageSearchRecord(searchRecordQueryDto);
	}
	public ResponseResult<AppUser> queryUserById(Long userId) {
		return userService.queryUserById(userId);
	}
	public ResponseResult<OrderDetailResp> queryOrderDetailForWork(String rentRecordNo) {
		return rentRecordClient.queryOrderDetailForWork(rentRecordNo);
	}
	public ResponseResult<?> repaymentSchedule(String rentRecordNo) {
		return repaymentClient.repaymentSchedule(rentRecordNo);
	}
	public ResponseResult<?> transactionRecordLatest(String rentRecordNo) {
		return repaymentClient.latest(rentRecordNo);
	}
	public ResponseResult<ResultPager<WorkOrderRsp>> orderlist(RentRecordQuery rentRecordQuery, AuthUser authUser) {
		SearchRecord searchRecord=new SearchRecord();
		searchRecord.setOperator(authUser.getUserName());
		String content="";
		if (StringUtils.isNotEmpty(rentRecordQuery.getIdNo())) {
			content=rentRecordQuery.getIdNo();
		}
		if (StringUtils.isNotEmpty(rentRecordQuery.getPhoneNum())) {
			content=rentRecordQuery.getPhoneNum();
		}
		if (StringUtils.isNotEmpty(rentRecordQuery.getRentRecordNo())) {
			content=rentRecordQuery.getRentRecordNo();
		}
		searchRecord.setContent(content);
		if (StringUtils.isNotEmpty(content)) {
			searchRecordService.add(searchRecord);
		}
		return rentRecordClient.queryPageWokerOrderList(rentRecordQuery);
	}
	public ResponseResult<String> resetPassword(AppUser appUser,AuthUser authUser) {
		appUser.setLoginPassword("abcd1234");
		ResponseResult<String> result= userService.resetPassword(appUser);
		if (result!=null&&result.isSuccess()) {
			UpdateRecord updateRecord=new UpdateRecord();
			updateRecord.setOperator(authUser.getUserName());
			updateRecord.setType(WorkOrderConstants.UPDATE_RECORD_TYPE_RESETPWD);
			updateRecordService.add(updateRecord);
		}
		return ResponseResult.buildSuccessResponse();
	}
	public ResponseResult<UserInfoDto> queryByCondition(AppUserExtendDto appUserExtendDto) {
		AppUser appUser=BeanConvertUtil.convertBean(appUserExtendDto, AppUser.class);
		if (StringUtils.isNotEmpty(appUserExtendDto.getRentRecordNo())) {
			ResponseResult<OrderDetailResp> result= rentRecordClient.queryOrderDetailForWork(appUserExtendDto.getRentRecordNo());
			if (null!=result) {
				if (result.isSuccess()) {
					appUser.setUserId(result.getData().getUserId());
				}else if (result.getErrCode()==OrderResultCode.NOT_RENT_RECORD.getCode()) {
					return ResponseResult.buildSuccessResponse();
				}
			}
		}
		return userService.queryByCondition(appUser);
	}
    
}