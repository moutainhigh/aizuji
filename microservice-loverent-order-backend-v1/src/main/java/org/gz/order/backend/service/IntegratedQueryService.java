package org.gz.order.backend.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.RepaymentStatisticsReq;
import org.gz.liquidation.common.dto.RepaymentStatisticsResp;
import org.gz.order.backend.feign.IMaterielService;
import org.gz.order.backend.feign.IliquidationService;
import org.gz.order.common.dto.RecordAndExtends;
import org.gz.order.common.dto.RecordAndExtendsQuery;
import org.gz.order.common.dto.RentOrderStateStatistics;
import org.gz.order.common.dto.RentStateDtoPage;
import org.gz.order.common.entity.RentCoordinate;
import org.gz.order.common.entity.RentInvoice;
import org.gz.order.common.entity.RentState;
import org.gz.order.server.service.RentInvoiceService;
import org.gz.order.server.service.RentRecordExtendsService;
import org.gz.order.server.service.RentRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.discovery.converters.Auto;

@Service
public class IntegratedQueryService {

	Logger logger = LoggerFactory.getLogger(IntegratedQueryService.class);
	@Autowired
    RentRecordService rentRecordService;
    @Autowired
    RentRecordExtendsService rentRecordExtendsService;
    @Autowired
    IMaterielService materielService;
    @Autowired
    RentInvoiceService rentInvoiceService;
    
    @Autowired
    IliquidationService        repaymentService;

	public ResponseResult<?> list(RecordAndExtendsQuery recordAndExtendsQuery) {
		ResultPager<RecordAndExtends> resultPager= rentRecordService.queryPageRecordAndExtends(recordAndExtendsQuery);
		return ResponseResult.buildSuccessResponse(resultPager);
	}

	public ResponseResult<?> detail(Long id) {
		RecordAndExtends recordAndExtends= rentRecordService.getRecordAndExtends(id);
		return ResponseResult.buildSuccessResponse(recordAndExtends);
	}

	public ResponseResult<?> rentState(String rentRecordNo) {
		List<RentState> rentStates = rentRecordService.selectStateByRecordNo(rentRecordNo);
		RentStateDtoPage rsdp=new RentStateDtoPage();
		rsdp.setData(rentStates);
		return ResponseResult.buildSuccessResponse(rsdp);
	}

	public ResponseResult<?> allMaterielClassList() {
		return materielService.queryAllMaterielClassList();
	}
	
    public List<RentCoordinate> queryRentCoordinateByRecordNo(String rentRecordNo) {
        RentCoordinate dto = new RentCoordinate();
        dto.setValid(true);
        dto.setRentRecordNo(rentRecordNo);
        return this.rentRecordService.queryRentCoordinateByRecordNo(dto);
    }

    public List<RentOrderStateStatistics> queryRentRecordStatusStatistics(Long userId) {
        return this.rentRecordService.queryRentRecordStatusStatistics(userId);
    }

    public ResponseResult<?> queryRentListAndUserContract(RecordAndExtendsQuery recordAndExtendsQuery) {
        ResultPager<RecordAndExtends> resultPager = rentRecordService.queryPageRecordAndExtends(recordAndExtendsQuery);
        RepaymentStatisticsReq req = new RepaymentStatisticsReq();
        List<String> paramList = new ArrayList<>();
        List<RecordAndExtends> list = resultPager.getData();
        Map<String, RecordAndExtends> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (RecordAndExtends r : list) {
                paramList.add(r.getRentRecord().getRentRecordNo());
                map.put(r.getRentRecord().getRentRecordNo(), r);
            }
            req.setOrderSN(paramList);

            // 查询清算数据并赋值到订单实体
            ResponseResult<List<RepaymentStatisticsResp>> result = repaymentService.queryRepaymentStatistics(req);
            if (result.isSuccess()) {
                List<RepaymentStatisticsResp> statisticsRespList = result.getData();
                if (statisticsRespList != null) {
                    for (RepaymentStatisticsResp resp : statisticsRespList) {
                        map.get(resp.getOrderSN()).setBreachCount(resp.getBreachCount());
                        map.get(resp.getOrderSN()).setPerformanceCount(resp.getPerformanceCount());
                    }
                }
            }
        }
        return ResponseResult.buildSuccessResponse(resultPager);
    }

	public ResponseResult<RentInvoice> invoice(String rentRecordNo) {
		RentInvoice ri=new RentInvoice();
		ri.setRentRecordNo(rentRecordNo);;
		List<RentInvoice> invoices= rentInvoiceService.queryList(ri);
		if (null!=invoices&&invoices.size()>0) {
			return ResponseResult.buildSuccessResponse(invoices.get(0));
		}
		return ResponseResult.buildSuccessResponse();
	}
}