package org.gz.oss.common.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.dao.MiniAppDao;
import org.gz.oss.common.dto.*;
import org.gz.oss.common.entity.MiniAppBanner;
import org.gz.oss.common.entity.MiniAppShopwindow;
import org.gz.oss.common.feign.IMaterielService;
import org.gz.oss.common.feign.IProductService;
import org.gz.oss.common.service.MiniAppService;
import org.gz.warehouse.common.vo.MaterielModelVo;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.ProductInfoQvo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WenMing.Zhou
 * @Description: ${todo}
 * @date 2018/3/27 10:44
 */
@Slf4j
@Service
public class MiniAppServiceImpl implements MiniAppService {



    @Autowired
    private MiniAppDao miniAppDao;

    @Autowired
    private IMaterielService materielService;

    @Autowired
    private IProductService productService;

    @Override
    public void addBanner(MiniAppBanner banner) {
        miniAppDao.addBanner(banner);
    }

    @Override
    public List<MiniAppBanner> findAllBanner() {
        List<MiniAppBanner> allBanner = miniAppDao.findAllBanner();
        if (allBanner!=null && allBanner.size()>0){
            List<Integer> modelId=new ArrayList();
            for (MiniAppBanner banner:allBanner){
                modelId.add(banner.getModelId());
            }
            ResponseResult<List<MaterielModelVo>> materielModelById = materielService.getMaterielModelById(modelId);

            List<MaterielModelVo> data = materielModelById.getData();
            for (MaterielModelVo modelVo:data){
                for (MiniAppBanner banner:allBanner){
                    if (banner.getModelId()==modelVo.getId()){
                        banner.setModelName(modelVo.getMaterielModelName());
                    }
                }
            }
        }
        return allBanner;
    }



    @Override
    @Transactional
    public void batchUpdateBanner(List<MiniAppBanner> bannerList) {
        if (bannerList!=null && bannerList.size()>0){
            for (MiniAppBanner br : bannerList) {
                UpdateDto<MiniAppBanner> updateDto = new UpdateDto<>();
                MiniAppBanner updateWhere  = new MiniAppBanner();
                updateWhere.setId(br.getId());
                updateDto.setUpdateCloumn(br);
                updateDto.setUpdateWhere(updateWhere);
                miniAppDao.batchUpdateBanner(updateDto);
            }
        }
    }

    @Override
    public void updateBannner(MiniAppBanner banner) {
        miniAppDao.updateBannner(banner);
    }

    @Override
    public void deleteBannerById(Integer id) {
        miniAppDao.deleteBannerById(id);

    }

    @Override
    public List<MiniAppShopwindow> findShopwindowCount() {

        return miniAppDao.findShopwindowCount();
    }

    @Override
    @Transactional
    public void addShopwindow(MiniAppShopwindowDto miniAppShopwindowDto) {
        //先添加橱窗信息
        int shopwindowId=miniAppDao.addShopwindow(miniAppShopwindowDto);

        //拿去新增的橱窗生成的ID
        List<MiniAppShopwindowCommodityDto> shopwindowCommodityList = miniAppShopwindowDto.getShopwindowCommodityList();
        for (MiniAppShopwindowCommodityDto miniAppShopwindowCommodityDto:shopwindowCommodityList){
            miniAppShopwindowCommodityDto.setShopwindowId(miniAppShopwindowDto.getId());
        }
        //添加橱窗的商品
        miniAppDao.addShopwindowCommodity(shopwindowCommodityList);
    }

    @Override
    @Transactional
    public void deleteShopwindow(Integer id) {
        //先删除橱窗ID下的所有关联的商品
        miniAppDao.deleteShopwindowCommodity(id);
        //删除橱窗信息
        miniAppDao.deleteShopwindow(id);
    }

    @Override
    @Transactional
    public void updateShopwindow(MiniAppShopwindowDto miniAppShopwindowDto) {
        //根据当前的橱窗ID删除相关联的商品信息
        miniAppDao.deleteShopwindowCommodity(miniAppShopwindowDto.getId());
        //修改橱窗信息
        miniAppDao.updateShopwindow(miniAppShopwindowDto);
        //添加商品信息
        List<MiniAppShopwindowCommodityDto> shopwindowCommodityList = miniAppShopwindowDto.getShopwindowCommodityList();
        for (MiniAppShopwindowCommodityDto miniAppShopwindowCommodityDto:shopwindowCommodityList){
            miniAppShopwindowCommodityDto.setShopwindowId(miniAppShopwindowDto.getId());
        }
        miniAppDao.addShopwindowCommodity(shopwindowCommodityList);

    }

    @Override
    @Transactional
    public void batchUpdateShopwindow(List<MiniAppShopwindowVo> miniAppShopwindowVoList) {
        if (miniAppShopwindowVoList!=null && miniAppShopwindowVoList.size()>0){
            for (MiniAppShopwindowVo miniAppShopwindowVo:miniAppShopwindowVoList){
                miniAppDao.batchUpdateShopwindow(miniAppShopwindowVo);
            }
        }
    }

    @Override
    public MiniAppShopwindowDto getShopwindowDetailById(Integer id) {
        MiniAppShopwindowDto miniAppShopwindowDto=new MiniAppShopwindowDto();

        //更具id查询出对应的橱窗信息
        List<MiniAppShopwindowDetail> shopwindowDetailById = miniAppDao.getShopwindowDetailById(id);
        if (shopwindowDetailById!=null && shopwindowDetailById.size()>0){
            //取出所有的modelID
            List<Integer> modelIdList=new ArrayList<>();
            for (MiniAppShopwindowDetail miniAppShopwindowDetail:shopwindowDetailById){
                modelIdList.add(miniAppShopwindowDetail.getModelId());
            }
            ResponseResult<List<MaterielModelVo>> materielModelById = materielService.getMaterielModelById(modelIdList);
            List<MaterielModelVo> data = materielModelById.getData();
            for (MiniAppShopwindowDetail mo:shopwindowDetailById){
                for (MaterielModelVo modelVo:data){
                    if (mo.getModelId()==modelVo.getId()){
                        mo.setModelName(modelVo.getMaterielModelName());
                    }
                }
            }
            //添加橱窗基本信息
            miniAppShopwindowDto.setId(shopwindowDetailById.get(0).getId());
            miniAppShopwindowDto.setName(shopwindowDetailById.get(0).getName());
            miniAppShopwindowDto.setPosition(shopwindowDetailById.get(0).getPosition());
            miniAppShopwindowDto.setType(shopwindowDetailById.get(0).getType());
            List<MiniAppShopwindowCommodityDto> shopwindowCommodityList=new ArrayList<>();
            for (MiniAppShopwindowDetail miniAppShopwindowDetail:shopwindowDetailById){
                MiniAppShopwindowCommodityDto miniAppShopwindowCommodityDto=new MiniAppShopwindowCommodityDto();
                miniAppShopwindowCommodityDto.setShopwindowId(miniAppShopwindowDetail.getShopwindowId());
                miniAppShopwindowCommodityDto.setImage(miniAppShopwindowDetail.getImage());
                miniAppShopwindowCommodityDto.setPosition(miniAppShopwindowDetail.getImageposition());
                miniAppShopwindowCommodityDto.setModelId(miniAppShopwindowDetail.getModelId());
                miniAppShopwindowCommodityDto.setModelName(miniAppShopwindowDetail.getModelName());
                miniAppShopwindowCommodityDto.setProductType(miniAppShopwindowDetail.getProductType());

                //根据modelId查询商品的最低价
                ProductInfoQvo productInfoQvo=new ProductInfoQvo();
                productInfoQvo.setMaterielModelId(miniAppShopwindowDetail.getModelId());
                productInfoQvo.setProductType(1);  //目前此模块都为租赁商品 所以这里选1
                ResponseResult<ProductInfo> leasePriceLowestProduct = productService.getLeasePriceLowestProducts(productInfoQvo);
                if (leasePriceLowestProduct.getData()!=null){
                    ProductInfo productInfo = leasePriceLowestProduct.getData();
                    miniAppShopwindowCommodityDto.setDisplayLeaseType(productInfo.getDisplayLeaseType());
                    miniAppShopwindowCommodityDto.setLeaseAmount(productInfo.getLeaseAmount());
                    miniAppShopwindowCommodityDto.setDayLeaseAmount(productInfo.getDayLeaseAmount());
                    shopwindowCommodityList.add(miniAppShopwindowCommodityDto);
                }else {
                    log.info("此型号ID：{}，查不到租赁最低价",miniAppShopwindowDetail.getModelId());
                }
            }
            miniAppShopwindowDto.setShopwindowCommodityList(shopwindowCommodityList);
            return miniAppShopwindowDto;
        }
        return null;
    }

    @Override
    public List<MiniAppShopwindowVo> queryAllShopwindow() {

        return miniAppDao.queryAllShopwindow();
    }


}
