package org.gz.warehouse.web.controller.warehouse;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.AssertUtils;
import org.gz.common.utils.JsonUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.order.common.dto.OrderDetailRespForWare;
import org.gz.order.common.dto.RentRecordQuery;
import org.gz.order.common.entity.RentCoordinate;
import org.gz.warehouse.entity.warehouse.WarehousePickingRecord;
import org.gz.warehouse.entity.warehouse.WarehousePickingRecordQuery;
import org.gz.warehouse.feign.AliOrderService;
import org.gz.warehouse.feign.IThirdPartService;
import org.gz.warehouse.feign.OrderService;
import org.gz.warehouse.feign.ResultCode;
import org.gz.warehouse.mapper.warehouse.WarehousePickingRecordMapper;
import org.gz.warehouse.service.warehouse.WarehouseOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Title: 库存出货相关控制器
 * @author hxj
 * @date 2017年12月22日 上午10:55:56
 */
@RestController
@RequestMapping("/warehouseOut")
@Slf4j
public class WarehouseOutController extends BaseController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private AliOrderService aliOrderService;
	
	@Autowired
	private WarehouseOutService outService;

	@Autowired
	private WarehousePickingRecordMapper pickMapper;

	@Autowired
	private IThirdPartService thirdPartService;

	/**
	 * 
	 * @Description: 获取待出货订单，根据orderSource(1：APP,2:小程序)作切分
	 * @param q
	 */
	@PostMapping(value = "/queryPendingOrderPage")
	public ResponseResult<ResultPager<OrderDetailRespForWare>> queryPendingOrderPage(@RequestBody RentRecordQuery q) {
		try {
			log.info("请求参数：{}",JsonUtils.toJsonString(q));
			ResponseResult<ResultPager<OrderDetailRespForWare>> result =null;
			if(q.getOrderSource().intValue()==1) {
				result= this.orderService.queryRentRecordList(q);
			}
		    if(q.getOrderSource().intValue()==2) {
		    	result= this.aliOrderService.queryPageRentRecordList(q);
		    }
			if (result != null && result.getData() == null) {
				result.setData(new ResultPager<OrderDetailRespForWare>(0, q.getCurrPage(), q.getPageSize(),new ArrayList<OrderDetailRespForWare>(0)));
			}
			return result;
		} catch (Exception e) {
			log.error("库存系统->订单系统，获取订单数据失败：{}", e.getLocalizedMessage());
			return ResponseResult.build(10000, "库存系统->订单系统，获取订单数据失败", null);
		}
	}

	/**
	 * 
	 * @Description: 查询经纬度坐标
	 * @param rentCoordinate
	 *            传递rentRecordNo:销售订单号参数
	 * @return ResponseResult<List<RentCoordinate>>
	 */
	@PostMapping(value = "/queryOrderCoordinate")
	public ResponseResult<List<RentCoordinate>> queryOrderCoordinate(@RequestBody RentCoordinate rentCoordinate) {
		return this.orderService.queryOrderCoordinate(rentCoordinate);
	}

	/**
	 * 
	 * @Description: 拣货
	 * @param q
	 */
	@PostMapping(value = "/pick")
	public ResponseResult<WarehousePickingRecord> pick(@Valid @RequestBody WarehousePickingRecord q,
			BindingResult bindingResult) {
		// 验证数据
		ResponseResult<String> vr = super.getValidatedResult(bindingResult);
		if (vr == null) {
			try {
				return this.outService.pick(q);
			} catch (Exception e) {
				log.error("拣货异常：{}", e.getLocalizedMessage());
				return ResponseResult.build(1000, "", null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(),
				ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}

	/**
	 * 
	 * @Description: 撤销拣货
	 * @param q
	 */
	@PostMapping(value = "/undoPick")
	public ResponseResult<WarehousePickingRecord> undoPick(@Valid @RequestBody WarehousePickingRecord q,BindingResult bindingResult) {
		// 验证数据
		ResponseResult<String> vr = super.getValidatedResult(bindingResult);
		if (vr == null) {
			try {
				return this.outService.undoPick(q);
			} catch (Exception e) {
				log.error("撤销拣货异常：{}", e.getLocalizedMessage());
				return ResponseResult.build(1000, "", null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(),
				ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}

	/**
	 * 
	 * @Description: 出货
	 * @param q
	 */
	@PostMapping(value = "/out")
	public ResponseResult<WarehousePickingRecord> out(@Valid @RequestBody WarehousePickingRecord q,
			BindingResult bindingResult) {
		// 验证数据
		ResponseResult<String> vr = super.getValidatedResult(bindingResult);
		if (vr == null) {
			try {
				return this.outService.out(q);
			} catch(ServiceException e) {
				return ResponseResult.build(e.getErrorCode(), e.getErrorMsg(), null);
			}catch (Exception e) {
				log.error("出货异常：{}", e.getLocalizedMessage());
				return ResponseResult.build(1000, "", null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(),
				ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}

	/**
	 * 
	 * @Description: 获取出货记录
	 * @param q
	 */
	@PostMapping(value = "/queryPickedPage")
	public ResponseResult<ResultPager<WarehousePickingRecord>> queryPickedPage(
			@RequestBody WarehousePickingRecordQuery q) {
		try {
			q.setStatusFlag(9);
			if (StringUtils.hasText(q.getPickStartDate()) && q.getPickStartDate().length() == 10) {
				q.setPickStartDate(q.getPickStartDate() + " 00:00:00");
			}
			if (StringUtils.hasText(q.getPickEndDate()) && q.getPickEndDate().length() == 10) {
				q.setPickEndDate(q.getPickEndDate() + " 23:59:59");
			}
			if (StringUtils.hasText(q.getApplyStartTime()) && q.getApplyStartTime().length() == 10) {
				q.setApplyStartTime(q.getApplyStartTime() + " 00:00:00");
			}
			if (StringUtils.hasText(q.getApplyEndTime()) && q.getApplyEndTime().length() == 10) {
				q.setApplyEndTime(q.getApplyEndTime() + " 23:59:59");
			}
			return this.outService.queryByPage(q);
		} catch (Exception e) {
			log.error("库存系统->订单系统，获取订单数据失败：{}", e.getLocalizedMessage());
			return ResponseResult.build(10000, "库存系统->订单系统，获取订单数据失败", null);
		}
	}


	/**
	 * 
	 * @Description: 下载顺丰快速下单时生成的运单图片
	 * @param sourceOrderNo
	 *            原订单号
	 * @param response
	 */
	@GetMapping(value = "/downWaybill/{sourceOrderNo}")
	public void downWaybill(@PathVariable("sourceOrderNo") String sourceOrderNo, HttpServletResponse response) {
		boolean downloadResult = false;
		ResponseResult<String> errorResult = ResponseResult.build(1000, "下载运单图片须先传订单号", null);
		if (StringUtils.hasText(sourceOrderNo)) {
			ResultCode resultCode = thirdPartService.orderDownload2(sourceOrderNo);
			if (resultCode.getCode() == 0) {
				@SuppressWarnings("unchecked")
				List<String> decodeDataList = (List<String>) resultCode.getData();
				if (CollectionUtils.isNotEmpty(decodeDataList)) {
					try {
						response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
						response.setHeader("content-disposition", "attachment;filename=" + sourceOrderNo + ".zip");
						ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
						BufferedOutputStream bos = new BufferedOutputStream(zos);
						int index = 1;
						for (String imageData : decodeDataList) {
							byte[] decodeData = generateImageByte(imageData);// 解码
							BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(decodeData));
							zos.putNextEntry(new ZipEntry(sourceOrderNo + "_" + (index++) + ".jpg"));
							int len = 0;
							byte[] buf = new byte[10 * 1024];
							while ((len = bis.read(buf, 0, buf.length)) != -1) {
								bos.write(buf, 0, len);
							}
							bis.close();
							bos.flush();
						}
						bos.close();
						downloadResult = true;
					} catch (Exception e) {
						log.error("生成压缩包失败：{}", e.getLocalizedMessage());
						errorResult = ResponseResult.build(1000, "服务正忙，请稍候下载！", null);
					}
				}
			} else {
				errorResult = ResponseResult.build(1000, resultCode.getMessage(), null);
			}
		}
		if (downloadResult == false) {
			response.reset();
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.write(errorResult.toString());
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public byte[] generateImageByte(String imgStr) {
		if (imgStr == null) // 图像数据为空
			return null;
		Base64 base64 = new Base64();

		// Base64解码
		byte[] b = base64.decode(imgStr.getBytes());
		for (int i = 0; i < b.length; ++i) {
			if (b[i] < 0) {// 调整异常数据
				b[i] += 256;
			}
		}
		return b;
	}

	@GetMapping(value = "/exportSignConfirm/{id}")
	public void exportSignConfirm(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
		log.info("id:{}", id);
		if (AssertUtils.isPositiveNumber4Long(id)) {
			ServletOutputStream out = null;
			try {
				WarehousePickingRecord p = this.pickMapper.selectByPrimaryKey(id);
				if(p!=null) {
					response.setContentType(MediaType.APPLICATION_PDF_VALUE);
					response.setHeader("Content-Disposition", "attachment;fileName=" + id + ".pdf");
					byte[] datas = buildSignConfirmPDF(p);
					out = response.getOutputStream();
					out.write(datas);
					out.flush();
				}
			} catch (Exception e) {
				log.error("打印签收确认单失败：{}", e.getLocalizedMessage());
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
	}
	
	private byte[] buildSignConfirmPDF(WarehousePickingRecord record) {
		Document document = null;
		PdfWriter writer = null;
		ByteArrayOutputStream bos=null;
		try {
			document = new Document(PageSize.A4);
			bos = new ByteArrayOutputStream();
			writer = PdfWriter.getInstance(document, bos);
			// 设置字体
			BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			Font FontChinese16 = new Font(bfChinese, 16, Font.BOLD);
			Font FontChinese12 = new Font(bfChinese, 12, Font.NORMAL);
			document.open();//打开文档
			
			//设置标题
			Paragraph title = new Paragraph("爱租机产品签收确认单",FontChinese16);
			title.setAlignment(Element.ALIGN_CENTER);//居中对齐
			document.add(title);
			
			//设置
			StringBuilder content = new StringBuilder();
			content.append("本人：").append(record.getRealName()).append("，");
			content.append("身份证号：").append(record.getIdNo()).append("，");
			content.append("在深圳市国智互联网科技有限公司旗下“爱租机”平台租赁一台").append(record.getMaterielModelName()+" "+record.getMaterielSpecValue()).append("，");
			content.append("合同编号：").append(record.getSourceOrderNo()).append("，");
			content.append("本人确认合同有效性并已签收全部协议中约定的设备(设备SN/IMEI号为").append(record.getSnNo()+"/"+record.getImieNo()).append(")。");
			Paragraph contentParagraph = new Paragraph(content.toString(),FontChinese12);
			contentParagraph.setSpacingBefore(50f);//设置上方空白区域
			contentParagraph.setLeading(30f);//设置行间距
			contentParagraph.setFirstLineIndent(20f);//设置首行缩进
			document.add(contentParagraph);
			
			Paragraph clientSignParagraph = new Paragraph("客户签字:",FontChinese12);
			clientSignParagraph.setSpacingBefore(150f);//设置上方空白区域
			document.add(clientSignParagraph);
			
			Paragraph clientIdParagraph = new Paragraph("客户身份证号:",FontChinese12);
			clientIdParagraph.setSpacingBefore(30f);//设置上方空白区域
			document.add(clientIdParagraph);
			
			Paragraph signDateParagraph = new Paragraph("签署日期:",FontChinese12);
			signDateParagraph.setSpacingBefore(30f);//设置上方空白区域
			document.add(signDateParagraph);
		} catch (Exception e) {
			log.error("生成PDF失败：{}",e.getLocalizedMessage());
		}finally {
			 // 关闭文档
			 document.close();
			// 关闭书写器
			 writer.close();
		}
		return bos.toByteArray();
	}
}
