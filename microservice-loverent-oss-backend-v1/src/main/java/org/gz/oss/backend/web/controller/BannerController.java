package org.gz.oss.backend.web.controller;

import java.util.Date;
import java.util.List;

import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.web.controller.BaseController;
import org.gz.oss.common.entity.Banner;
import org.gz.oss.common.feign.OSSUploadAliService;
import org.gz.oss.common.request.BannerReq;
import org.gz.oss.common.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/banner")
public class BannerController extends BaseController {

	@Autowired
	private BannerService iBannerService;

	@Autowired
	private OSSUploadAliService uploadAliService;

	/**
	 * 添加banner
	 * 
	 * @param banner
	 * @return
	 * @throws @createBy:zhangguoliang
	 *             createDate:2017年12月16日
	 */
	@PostMapping(value = "/add")
	public ResponseResult<?> addBanner(Banner banner) {
		try {
			log.info("banner:{}", banner.toString());
			Long userId = super.getAuthUserId();
			banner.setCreateTime(new Date());
			banner.setLastUpdateTime(new Date());
			banner.setCreateUserId(userId);
			banner.setUpdateUserId(userId);
			log.info("iBannerService.add...");
			iBannerService.add(banner);
			return ResponseResult.buildSuccessResponse();
		} catch (ServiceException e) {
			log.error("addBanner失败：{}", e.getErrorMsg());
			return ResponseResult.build(e.getErrorCode(), e.getErrorMsg(), null);
		} catch (Exception e) {
			log.error("addBanner失败：{}", e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.FALL_BACK.getCode(), ResponseStatus.FALL_BACK.getMessage(),
					e.getMessage());
		}
	}

	/**
	 * 查询所有banner列表
	 * 
	 * @return
	 * @throws @createBy:zhangguoliang
	 *             createDate:2017年12月16日
	 */
	@PostMapping(value = "/queryAllList")
	public ResponseResult<?> queryBannerAllList() {
		try {
			return ResponseResult.buildSuccessResponse(iBannerService.findAll());
		} catch (Exception e) {
			log.error("queryBannerAllList失败：{}", e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.FALL_BACK.getCode(), ResponseStatus.FALL_BACK.getMessage(),
					e.getMessage());
		}
	}

	/**
	 * id查询banner
	 * 
	 * @param id
	 * @return
	 * @throws @createBy:zhangguoliang
	 *             createDate:2017年12月18日
	 */
	@PostMapping(value = "/getById")
	public ResponseResult<?> getById(Integer id) {
		try {
			return ResponseResult.buildSuccessResponse(iBannerService.getById(id));
		} catch (Exception e) {
			log.error("getById失败：{}", e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.FALL_BACK.getCode(), ResponseStatus.FALL_BACK.getMessage(),
					e.getMessage());
		}
	}

	/**
	 * 更新banner信息
	 * 
	 * @param banner
	 * @return
	 * @throws @createBy:zhangguoliang
	 *             createDate:2017年12月21日
	 */
	@PostMapping(value = "/update")
	public ResponseResult<?> update(Banner banner) {
		try {
			banner.setLastUpdateTime(new Date());
			banner.setUpdateUserId(super.getAuthUserId());
			iBannerService.update(banner);
			return ResponseResult.buildSuccessResponse();
		} catch (Exception e) {
			log.error("update失败：{}", e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
					ResponseStatus.DATABASE_ERROR.getMessage(), null);
		}
	}

	/**
	 * 批量更新banner信息
	 * 
	 * @param
	 * @return
	 * @throws @createBy:zhangguoliang
	 *             createDate:2017年12月19日
	 */
	@PostMapping(value = "/batchUpdate")
	public ResponseResult<?> batchUpdate(BannerReq req) {
		try {
			List<Banner> list = req.getBannerReqList();
			for (Banner banner : list) {
				banner.setLastUpdateTime(new Date());
				banner.setUpdateUserId(super.getAuthUserId());
			}
			iBannerService.batchUpdate(req.getBannerReqList());
			return ResponseResult.buildSuccessResponse();
		} catch (Exception e) {
			log.error("batchUpdate失败：{}", e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.FALL_BACK.getCode(), ResponseStatus.FALL_BACK.getMessage(),
					e.getMessage());
		}
	}

	/**
	 **
	 */
	@PostMapping(value = "/delete")
	public ResponseResult<?> delete(Integer id) {
		try {
			iBannerService.delete(id);
			return ResponseResult.buildSuccessResponse();
		} catch (Exception e) {
			log.error("delete失败：{}", e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.FALL_BACK.getCode(), ResponseStatus.FALL_BACK.getMessage(),
					e.getMessage());
		}
	}
}
