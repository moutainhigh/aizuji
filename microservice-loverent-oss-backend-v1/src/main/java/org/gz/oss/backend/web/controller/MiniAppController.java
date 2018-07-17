package org.gz.oss.backend.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.web.controller.BaseController;
import org.gz.oss.common.dto.MiniAppShopwindowCommodityDto;
import org.gz.oss.common.dto.MiniAppShopwindowDto;
import org.gz.oss.common.entity.MiniAppBanner;
import org.gz.oss.common.entity.MiniAppShopwindow;
import org.gz.oss.common.request.MiniappBannerReq;
import org.gz.oss.common.request.MiniappShopwindowReq;
import org.gz.oss.common.service.MiniAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @author WenMing.Zhou
 * @Description: Alipay小程序对应控制器
 * @date 2018/3/27 10:30
 */
@RestController
@Slf4j
@RequestMapping("/miniapp")
public class MiniAppController extends BaseController {

    @Autowired
    private MiniAppService miniAppService;




    /*====================================================================================相关的Banner的接口=======================================================================================================*/

    /**
     * 添加bannner
     * @param banner
     * @return
     */
    @PostMapping(value = "/addminiBannner")
    public ResponseResult<?> addBanner(@Valid @RequestBody MiniAppBanner banner, BindingResult bindingResult) {
        try {
            ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
            if (validateResult==null){
                Long userId = super.getAuthUserId();
                Date now = new Date();
                banner.setCreateTime(now);
                banner.setLastUpdateTime(now);
                banner.setCreateUserId(userId);
                banner.setUpdateUserId(userId);
                miniAppService.addBanner(banner);
                return ResponseResult.buildSuccessResponse();
            }
            return validateResult;
        } catch (Exception e) {
            log.error("addBanner失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), ResponseStatus.DATA_CREATE_ERROR.getMessage(),
                    e.getMessage());
        }
    }


    /**
     * 删除Banner
     * @param id
     * @return
     */
    @PostMapping(value = "/deleteBannerbyId")
    public ResponseResult<?> deleteBannerById(@RequestParam Integer id){
        try{
            if (id!=null || id>0){
                miniAppService.deleteBannerById(id);
                return ResponseResult.buildSuccessResponse();
            }else {
                return ResponseResult.build(ResponseStatus.PARAMETER_ERROR.getCode(), ResponseStatus.PARAMETER_ERROR.getMessage(),
                        null);
            }
        }catch (Exception e){
            log.error("删除Banner失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATA_DELETED_ERROR.getCode(), ResponseStatus.DATA_DELETED_ERROR.getMessage(),
                    e.getMessage());
        }
    }

    /**
     * 更新banner
     * @param banner
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/updateBanner")
    public ResponseResult<?> updateBannner(@Valid @RequestBody MiniAppBanner banner,BindingResult bindingResult){
        try {
            if (banner.getId()!=null && banner.getId()>0){
                ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
                if (validateResult==null){
                    banner.setLastUpdateTime(new Date());
                    banner.setUpdateUserId(super.getAuthUserId());
                    miniAppService.updateBannner(banner);
                    return ResponseResult.buildSuccessResponse();
                }
                return validateResult;
            }
            return ResponseResult.build(ResponseStatus.PARAMETER_ERROR.getCode(),
                    ResponseStatus.PARAMETER_ERROR.getMessage(), null);
        } catch (Exception e) {
            log.error("修改Banner失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(),
                    ResponseStatus.DATA_UPDATED_ERROR.getMessage(), null);
        }
    }


    /**
     * 查询所有的Banner信息
     * @return
     */
    @GetMapping(value = "/queryAllBanner")
    public ResponseResult<?> queryBannerAllList() {
        try {
            return ResponseResult.buildSuccessResponse(miniAppService.findAllBanner());
        } catch (Exception e) {
            log.error("queryBannerAllList失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(),
                    e.getMessage());
        }
    }

    /**
     * 上下移动
     * @param banner
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/batchUpdateBanner")
    public ResponseResult<?> batchupdateBannner(@Valid @RequestBody MiniappBannerReq banner,BindingResult bindingResult){
        try {
            ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
            if (validateResult==null){
                List<MiniAppBanner> miniappBannerReqList = banner.getMiniappBannerReqList();
                for (MiniAppBanner miniAppBanner:miniappBannerReqList){
                    miniAppBanner.setLastUpdateTime(new Date());
                    miniAppBanner.setUpdateUserId(super.getAuthUserId());
                }
                miniAppService.batchUpdateBanner(miniappBannerReqList);
                return ResponseResult.buildSuccessResponse();
            }
            return validateResult;
        } catch (Exception e) {
            log.error("修改Banner失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(),
                    ResponseStatus.DATA_UPDATED_ERROR.getMessage(), null);
        }
    }


    /*=========================================================================================相关的橱窗位的接口==================================================================================================*/

    /**
     * 查询所有的橱窗位列表，以及对应的商品个数
     * @return
     */
    @GetMapping(value = "/findShopwindowCount")
    public ResponseResult<?> findShopwindowCount(){
        try {
            List<MiniAppShopwindow> shopwindowCount = miniAppService.findShopwindowCount();
            return ResponseResult.buildSuccessResponse(shopwindowCount);
        }catch (Exception e){
            log.error("获取所有的橱窗位失败：",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(), null);
        }
    }


    /**
     * 添加橱窗
     * @param miniAppShopwindowDto
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/addShopwindow")
    public ResponseResult<?> addShopwindow(@Valid @RequestBody MiniAppShopwindowDto miniAppShopwindowDto,BindingResult bindingResult){
        try{
            ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
            if (validateResult==null){
                miniAppService.addShopwindow(miniAppShopwindowDto);
                return ResponseResult.buildSuccessResponse();
            }
            return validateResult;
        }catch (Exception e){
            log.error("addShopwindow失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), ResponseStatus.DATA_CREATE_ERROR.getMessage(),
                    e.getMessage());
        }
    }


    /**
     * 编辑橱窗
     * @param miniAppShopwindowDto
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/updateShopwindow")
    public ResponseResult<?> updateShopwindow(@Valid @RequestBody MiniAppShopwindowDto miniAppShopwindowDto,BindingResult bindingResult){
        try{
            if (miniAppShopwindowDto.getId()!=null && miniAppShopwindowDto.getId()>0){
                ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
                if (validateResult==null){
                    miniAppService.updateShopwindow(miniAppShopwindowDto);
                    return ResponseResult.buildSuccessResponse();
                }
                return validateResult;
            }
            return ResponseResult.build(ResponseStatus.PARAMETER_ERROR.getCode(), ResponseStatus.PARAMETER_ERROR.getMessage(),null);
        }catch (Exception e){
            log.error("更新橱窗失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), ResponseStatus.DATA_CREATE_ERROR.getMessage(),
                    e.getMessage());
        }
    }

    /**
     * 删除橱窗
     * @param id
     * @return
     */
    @PostMapping(value ="/deleteShopwindow")
    public ResponseResult<?> deleteShopwindow(Integer id){
        try{
            if (id!=null && id>0){
                miniAppService.deleteShopwindow(id);
                return ResponseResult.buildSuccessResponse();
            }
            return ResponseResult.build(ResponseStatus.PARAMETER_ERROR.getCode(), ResponseStatus.PARAMETER_ERROR.getMessage(),
                    null);
        }catch (Exception e){
            log.error("删除橱窗失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATA_DELETED_ERROR.getCode(), ResponseStatus.DATA_DELETED_ERROR.getMessage(),
                    e.getMessage());
        }
    }

    /**
     * 橱窗的位置调整
     * @param miniappShopwindowReq
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/batchUpdateShopwindow")
    public ResponseResult<?> batchUpdateShopwindow(@Valid @RequestBody MiniappShopwindowReq miniappShopwindowReq,BindingResult bindingResult){
        try{
            ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
            if (validateResult==null){
                miniAppService.batchUpdateShopwindow(miniappShopwindowReq.getShopwindowList());
                return ResponseResult.buildSuccessResponse();
            }
            return validateResult;
        }catch (Exception e){
            log.error("修改橱窗失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(),
                    ResponseStatus.DATA_UPDATED_ERROR.getMessage(), null);
        }
    }



    @PostMapping(value = "/getShopwindowDetailById")
    public ResponseResult<?> getShopwindowDetailById(@RequestParam Integer id){
        try{
            if (id!=null && id>0){
                MiniAppShopwindowDto shopwindowDetailById = miniAppService.getShopwindowDetailById(id);

                return ResponseResult.buildSuccessResponse(shopwindowDetailById);
            }
            return ResponseResult.build(ResponseStatus.PARAMETER_ERROR.getCode(),
                    ResponseStatus.PARAMETER_ERROR.getMessage(), null);
        }catch (Exception e){
            log.error("根据id拿橱窗详情失败：{}",e.getMessage());
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),
                    ResponseStatus.DATA_REQUERY_ERROR.getMessage(), null);
        }
    }





}
