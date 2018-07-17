package org.gz.order.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.gz.common.constants.SmsType;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.DateUtils;
import org.gz.order.common.Enum.BackRentState;
import org.gz.order.common.Enum.OrderResultCode;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.RecordAndExtends;
import org.gz.order.common.dto.RecordAndExtendsQuery;
import org.gz.order.common.dto.RentOrderStateStatistics;
import org.gz.order.common.dto.RentRecordQuery;
import org.gz.order.common.dto.UpdateDto;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.gz.order.common.entity.RentCoordinate;
import org.gz.order.common.entity.RentLogistics;
import org.gz.order.common.entity.RentRecord;
import org.gz.order.common.entity.RentRecordExtends;
import org.gz.order.common.entity.RentState;
import org.gz.order.common.utils.StateConvert;
import org.gz.order.server.dao.RentCoordinateDao;
import org.gz.order.server.dao.RentLogisticsDao;
import org.gz.order.server.dao.RentRecordDao;
import org.gz.order.server.dao.RentRecordExtendsDao;
import org.gz.order.server.dao.RentStateDao;
import org.gz.order.server.service.RentRecordService;
import org.gz.sms.constants.SmsChannelType;
import org.gz.sms.dto.SmsDto;
import org.gz.sms.service.SmsSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RentRecordServiceImpl implements RentRecordService {

  public static final Logger logger = LoggerFactory.getLogger(RentRecordServiceImpl.class);
    @Resource
    private RentRecordDao rentRecordDao;
    @Resource
    private RentRecordExtendsDao rentRecordExtendsDao;
    @Resource
    private RentStateDao         rentStateDao;
    @Resource
    private RentCoordinateDao    rentCoordinateDao;
  @Resource
  private RentLogisticsDao rentLogisticsDao;

  @Autowired
  private SmsSendService smsSendService;

    @Override
	public RentRecord getById(Long id) {
		return rentRecordDao.getById(id);
	}
    
    @Override
	public RecordAndExtends getRecordAndExtends(Long id) {
		return rentRecordDao.getRecordAndExtends(id);
	}
    
	@Override
	public ResultPager<RecordAndExtends> queryPageRecordAndExtends(RecordAndExtendsQuery recordAndExtendsQuery) {
		int totalNum = this.rentRecordDao.countRecordAndExtends(recordAndExtendsQuery);
		List<RecordAndExtends> list = new ArrayList<RecordAndExtends>(0);
		if(totalNum>0) {
			list = this.rentRecordDao.queryPageRecordAndExtends(recordAndExtendsQuery);
		}
		ResultPager<RecordAndExtends> data = new ResultPager<RecordAndExtends>(totalNum, recordAndExtendsQuery.getCurrPage(), recordAndExtendsQuery.getPageSize(), list);
		return data;
	}
    


	@Override
	public List<RentState> selectStateByRecordNo(String rentRecordNo) {
		RentState rentState=new RentState();
		rentState.setRentRecordNo(rentRecordNo);
		return rentStateDao.queryList(rentState);
	}
	
	@Override
	public RentRecord getByRentRecordNo(String rentRecordNo) {
		return rentRecordDao.getByRentRecordNo(rentRecordNo);
	}


	@Override
	public void updateStockFlag(RentRecord req) {
		RentRecord rentRecord = new RentRecord();
        rentRecord.setRentRecordNo(req.getRentRecordNo());
        if (req.getStockFlag() != null) {
            rentRecord.setStockFlag(req.getStockFlag());
        }

        UpdateDto<RentRecord> updateDto = new UpdateDto<RentRecord>();
        updateDto.setUpdateCloumn(rentRecord);

        RentRecord whereCloumn = new RentRecord();
        whereCloumn.setRentRecordNo(rentRecord.getRentRecordNo());
        updateDto.setUpdateWhere(whereCloumn);
        rentRecordDao.update(updateDto);
	}

    @Override
    public List<RentCoordinate> queryRentCoordinateByRecordNo(RentCoordinate dto) {
        return rentCoordinateDao.queryList(dto);
    }

    @Override
    public List<RentOrderStateStatistics> queryRentRecordStatusStatistics(Long userId) {
        return rentRecordDao.queryRentRecordStatusStatistics(userId);
    }

  @Override
  public ResponseResult<String> updateCreditState(HashMap<String, Object> map) {
    String rentRecordNo = (String) map.get("rentRecordNo");
    int creditState = (int) map.get("creditState");
    RentRecord rr = rentRecordDao.getByRentRecordNo(rentRecordNo);
    // 如果当前订单状态不为待审批则不能更新审批状态
    if (BackRentState.ApprovalPending.getCode() != rr.getState()) {
      return ResponseResult.build(OrderResultCode.STATE_FALSE.getCode(),
        OrderResultCode.STATE_FALSE.getMessage(),
        null);
    }
    RentRecord rentRecord = new RentRecord();
    rentRecord.setCreditState(creditState);
    UpdateDto<RentRecord> updateDto = new UpdateDto<RentRecord>();
    updateDto.setUpdateCloumn(rentRecord);

    RentRecord whereCloumn = new RentRecord();
    whereCloumn.setRentRecordNo(rentRecordNo);;
    updateDto.setUpdateWhere(whereCloumn);
    rentRecordDao.update(updateDto);
    return ResponseResult.buildSuccessResponse("");
  }

  private void sendMsg(String rentRecordNo, SmsType smsType, String phoneNum, List<String> datas) {
    // 调用接口发送通知短信
    SmsDto dto = new SmsDto();
    dto.setChannelType(SmsChannelType.SMS_CHANNEL_TYPE_Y_T_X);
    dto.setPhone(phoneNum);
    dto.setSmsType(smsType.getType());
    dto.setTemplateId(smsType.getType());
    dto.setDatas(datas);
    ResponseResult<String> result = smsSendService.sendSmsStockSignInform(dto);
    if (!result.isSuccess()) {
      logger.error(" 下发短信失败 rentRecordNo={},templateId={},ErrMsg={},ErrCode={}",
        rentRecordNo,
        smsType.getType(),
        result.getErrMsg(),
        result.getErrCode());
    }

  }

  @Override
  public List<OrderDetailResp> queryOrderList(RentRecordQuery rentRecordQuery) {
    return rentRecordDao.queryOrderList(rentRecordQuery);
  }

  @Override
  public ResponseResult<String> sureReturned(UpdateOrderStateReq req) {
    RentRecord byRentRecordNo = rentRecordDao.getByRentRecordNo(req.getRentRecordNo());
    int state = req.getState();

    if (byRentRecordNo == null) {
      return ResponseResult.build(OrderResultCode.NOT_RENT_RECORD.getCode(),
        OrderResultCode.NOT_RENT_RECORD.getMessage(),
        null);
    }
    if (req.getState() != byRentRecordNo.getState()) {
      return ResponseResult.build(OrderResultCode.STATE_FALSE.getCode(),
        OrderResultCode.STATE_FALSE.getMessage(),
        null);
    }

    RentRecord rentRecord = new RentRecord();
    int type = 1;

    // @description 从（归还中”、“提前解约中”、“强制归还中”、“退货中 16，30，11，33）变为（27定损完成”、“12
    // * 提前解约”、“31 强制定损完成”、“34已退货 ）
    if (state == BackRentState.Recycling.getCode()) {
      rentRecord.setDiscountFee(req.getDiscountFee());
      rentRecord.setState(BackRentState.DamageCheck.getCode());
    } else if (state == BackRentState.PrematureTerminating.getCode()) {
      rentRecord.setState(BackRentState.PrematureTermination.getCode());
    } else if (state == BackRentState.ForceRecycleIng.getCode()) {
      rentRecord.setDiscountFee(req.getDiscountFee());
      rentRecord.setState(BackRentState.ForceDamageCheck.getCode());
    } else if (state == BackRentState.ReturnGoodIng.getCode()) {
      type = 0;
      rentRecord.setState(BackRentState.ReturnGoodEnd.getCode());
    }
    // 需要更新rent_logistics表物流状态为已归还
    if (state != BackRentState.PrematureTerminating.getCode()) {
      rentRecord.setRentRecordNo(req.getRentRecordNo());
      RentLogistics rentLogistics = new RentLogistics();
      rentLogistics.setState(1);
      UpdateDto<RentLogistics> updateDto = new UpdateDto<RentLogistics>();
      updateDto.setUpdateCloumn(rentLogistics);
      RentLogistics whereCloumn = new RentLogistics();
      whereCloumn.setRentRecordNo(req.getRentRecordNo());
      whereCloumn.setType(type);
      updateDto.setUpdateWhere(whereCloumn);
      rentLogisticsDao.update(updateDto);
    }

    if (UpdateOrder(rentRecord) > 0) {
      // 更新订单成功之后添加rent_state表信息
      RentState rentState = new RentState();
      rentState.setCreateOn(new Date());
      rentState.setCreateBy(req.getCreateBy());
      rentState.setRentRecordNo(req.getRentRecordNo());
      rentState.setState(rentRecord.getState());
      rentState.setCreateMan(req.getCreateMan());
      rentState.setRemark(req.getRemark());
      rentStateDao.add(rentState);
    }


    return ResponseResult.buildSuccessResponse("");
  }

  private int UpdateOrder(RentRecord rentRecord) {
    UpdateDto<RentRecord> updateDto = new UpdateDto<RentRecord>();
    updateDto.setUpdateCloumn(rentRecord);
    RentRecord whereCloumn = new RentRecord();
    whereCloumn.setRentRecordNo(rentRecord.getRentRecordNo());
    updateDto.setUpdateWhere(whereCloumn);
    int i = rentRecordDao.update(updateDto);
    return i;
  }

  @Override
  public ResponseResult<String> alreadReturned(UpdateOrderStateReq req) {
    RentRecord byRentRecordNo = rentRecordDao.getByRentRecordNo(req.getRentRecordNo());
    int state = req.getState();
    if (byRentRecordNo == null) {
      return ResponseResult.build(OrderResultCode.NOT_RENT_RECORD.getCode(),
        OrderResultCode.NOT_RENT_RECORD.getMessage(),
        null);
    }
    if (req.getState() != byRentRecordNo.getState()) {
      return ResponseResult.build(OrderResultCode.STATE_FALSE.getCode(),
        OrderResultCode.STATE_FALSE.getMessage(),
        null);
    }
    RentRecord rentRecord = new RentRecord();
    if (state == BackRentState.ForceDamageCheck.getCode()) {
      rentRecord.setState(BackRentState.ForceRecycleEnd.getCode());
    } else if (state == BackRentState.DamageCheck.getCode()) {
      rentRecord.setState(BackRentState.Recycle.getCode());
    }

    if (UpdateOrder(rentRecord) > 0) {
      // 更新订单成功之后添加rent_state表信息
      RentState rentState = new RentState();
      rentState.setCreateOn(new Date());
      rentState.setCreateBy(req.getCreateBy());
      rentState.setRentRecordNo(req.getRentRecordNo());
      rentState.setState(rentRecord.getState());
      rentState.setCreateMan(req.getCreateMan());
      rentState.setRemark(req.getRemark());
      rentStateDao.add(rentState);
    }
    return ResponseResult.buildSuccessResponse("");
  }

}
