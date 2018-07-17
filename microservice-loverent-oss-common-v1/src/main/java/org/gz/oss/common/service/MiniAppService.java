package org.gz.oss.common.service;

import org.gz.oss.common.dto.MiniAppShopwindowDto;
import org.gz.oss.common.dto.MiniAppShopwindowVo;
import org.gz.oss.common.entity.Banner;
import org.gz.oss.common.entity.MiniAppBanner;
import org.gz.oss.common.entity.MiniAppShopwindow;

import java.util.List;

/**
 * @author WenMing.Zhou
 * @Description: Alipay小程序Service
 * @date 2018/3/27 10:35
 */
public interface MiniAppService {

    void addBanner(MiniAppBanner banner);

    List<MiniAppBanner> findAllBanner();

    void batchUpdateBanner(List<MiniAppBanner> bannerList);

    void updateBannner(MiniAppBanner banner);

    void deleteBannerById(Integer id);





    List<MiniAppShopwindow> findShopwindowCount();

    void addShopwindow(MiniAppShopwindowDto miniAppShopwindowDto);

    void deleteShopwindow(Integer id);

    void updateShopwindow(MiniAppShopwindowDto miniAppShopwindowDto);

    void batchUpdateShopwindow(List<MiniAppShopwindowVo> miniAppShopwindowVoList);

    MiniAppShopwindowDto getShopwindowDetailById(Integer id);

    List<MiniAppShopwindowVo> queryAllShopwindow();


}
