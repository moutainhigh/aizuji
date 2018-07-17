package org.gz.oss.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.gz.oss.common.dto.*;
import org.gz.oss.common.entity.Banner;
import org.gz.oss.common.entity.MiniAppBanner;
import org.gz.oss.common.entity.MiniAppShopwindow;

import java.util.List;

/**
 * @author WenMing.Zhou
 * @Description: 小程序Dao
 * @date 2018/3/27 10:45
 */
@Mapper
public interface MiniAppDao {

    public void addBanner(MiniAppBanner banner);

    public List<MiniAppBanner> findAllBanner();

//    public Banner getById(Integer id);

    public void deleteBannerById(Integer id);

    public void updateBannner(MiniAppBanner banner);

    public void batchUpdateBanner(UpdateDto<MiniAppBanner> updateDto);





    public List<MiniAppShopwindow> findShopwindowCount();

    public int addShopwindow(MiniAppShopwindowDto miniAppShopwindowDto);

    public void addShopwindowCommodity(List<MiniAppShopwindowCommodityDto> list);

    public void deleteShopwindow(Integer id);

    public void deleteShopwindowCommodity(Integer id);

    public void updateShopwindow(MiniAppShopwindowDto miniAppShopwindowDto);

    public void batchUpdateShopwindow(MiniAppShopwindowVo miniAppShopwindowVo);

    public List<MiniAppShopwindowDetail> getShopwindowDetailById(Integer id);

    public List<MiniAppShopwindowVo> queryAllShopwindow();





}
