package org.gz.oss.server.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.web.controller.BaseController;
import org.gz.oss.common.dto.MiniAppShopwindowDto;
import org.gz.oss.common.dto.MiniAppShopwindowVo;
import org.gz.oss.common.service.MiniAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WenMing.Zhou
 * @Description: ${todo}
 * @date 2018/3/29 14:10
 */
@RestController
@Slf4j
@RequestMapping("/miniapp/api")
public class MiniAppControler extends BaseController {

    @Autowired
    private MiniAppService miniAppService;



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

//    @PostMapping(value = "/queryAllShopwindowDetail")

    /**
     * 查询所有的橱窗列表
     * @return
     */
    @GetMapping(value = "/queryAllShopwindow")
    public ResponseResult<?> queryAllShopwindow() {
        try {
            List<MiniAppShopwindowVo> miniAppShopwindowVos = miniAppService.queryAllShopwindow();
            return ResponseResult.buildSuccessResponse(miniAppShopwindowVos);
        } catch (Exception e) {
            log.error("queryAllShopwindow失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(),
                    e.getMessage());
        }
    }

    /**
     * 根据橱窗iD查询橱窗详情信息
     * @param id
     * @return
     */
    @PostMapping(value = "/getShopwindowDetailById")
    public ResponseResult<?> getShopwindowDetailById(@RequestParam Integer id){
        try{
            if (id!=null && id>0){
                MiniAppShopwindowDto shopwindowDetailById = miniAppService.getShopwindowDetailById(id);
                if (shopwindowDetailById==null){
                    return ResponseResult.build(ResponseStatus.PARAMETER_ERROR.getCode(),"橱窗ID："+id+"，无相关记录",null);
                }
                return ResponseResult.buildSuccessResponse(shopwindowDetailById);
            }
            return ResponseResult.build(ResponseStatus.PARAMETER_ERROR.getCode(),"橱窗ID不能为空", null);
        }catch (Exception e){
            log.error("根据id："+id+"拿橱窗详情失败：{}",e.getMessage());
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),
                    ResponseStatus.DATA_REQUERY_ERROR.getMessage(), e.getMessage());
        }
    }




}
