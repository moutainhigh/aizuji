package org.gz.order.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.gz.order.common.dto.UpdateDto;
import org.gz.order.common.entity.RentInvoice;
import org.gz.order.server.dao.RentInvoiceDao;
import org.gz.order.server.service.RentInvoiceService;
import org.springframework.stereotype.Service;

@Service
public class RentInvoiceServiceImpl implements RentInvoiceService {
	@Resource
	RentInvoiceDao rentInvoiceDao;

	@Override
	public List<RentInvoice> queryList(RentInvoice dto) {
		return rentInvoiceDao.queryList(dto);
	}

  @Override
  public int update(RentInvoice rentInvoice) {
    UpdateDto<RentInvoice> updateDto = new UpdateDto<RentInvoice>();
    updateDto.setUpdateCloumn(rentInvoice);
    RentInvoice whereCloumn = new RentInvoice();
    whereCloumn.setRentRecordNo(rentInvoice.getRentRecordNo());
    updateDto.setUpdateWhere(whereCloumn);
    return rentInvoiceDao.update(updateDto);
  }

}
