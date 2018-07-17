package org.gz.oss.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.constants.OssCommConstants;
import org.gz.oss.common.dao.BannerDao;
import org.gz.oss.common.dto.UpdateDto;
import org.gz.oss.common.entity.Banner;
import org.gz.oss.common.feign.IMaterielService;
import org.gz.oss.common.feign.IProductService;
import org.gz.oss.common.service.BannerService;
import org.gz.warehouse.common.vo.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BannerServiceImpl implements BannerService {

	@Autowired
	private BannerDao bannerDao;
	
    @Resource
    private IProductService productService;

    @Resource
    private IMaterielService materielService;

	@Override
	public void add(Banner banner) {
		try {
			bannerDao.add(banner);
		} catch (Exception e) {
			log.error("add failed:{}",e.getLocalizedMessage());
		}
		log.info("...finish add...");
	}

	@Override
	public List<Banner> findAll() {
        List<Banner> list = bannerDao.findAll();
        for (Banner banner : list) {
            this.settleMaterielInfo(banner);
        }
        return list;
	}

	@Override
	public Banner getById(Integer id) {
        Banner banner = bannerDao.getById(id);
        this.settleMaterielInfo(banner);
        return banner;
	}

	@Override
    @Transactional
	public void batchUpdate(List<Banner> bannerList) {
		if(bannerList!=null && bannerList.size()>0){
			for (Banner br : bannerList) {
				UpdateDto<Banner> updateDto = new UpdateDto<>();
				Banner updateWhere  = new Banner();
				updateWhere.setId(br.getId());
				updateDto.setUpdateCloumn(br);
				updateDto.setUpdateWhere(updateWhere);
				bannerDao.update(updateDto);
			}
		}
	}

	@Override
	public void update(Banner banner) {
		UpdateDto<Banner> updateDto = new UpdateDto<>();
		Banner updateWhere  = new Banner();
		updateWhere.setId(banner.getId());
		updateDto.setUpdateCloumn(banner);
		updateDto.setUpdateWhere(updateWhere);
		bannerDao.update(updateDto);
	}

    /**
     * 设置物料信息
     * 
     * @throws
     * @createBy:liuyt
     * createDate:2018年3月15日
     */
	private void settleMaterielInfo(Banner banner) {
        switch (banner.getType().intValue()) {
            case OssCommConstants.BANNER_TYPE_SALE:
                // 如果是关联售卖商品 根据产品id获取产品信息
                ProductInfo pInfo = new ProductInfo();
                pInfo.setId(banner.getProductId());
                ResponseResult<ProductInfo> result = productService.getByIdOrPdtNo(pInfo);
                if (result.isSuccess() && null != result.getData()) {
                    ProductInfo info = result.getData();
                    banner.setModelName(info.getModelName());
                    banner.setConfigValue(info.getConfigValue());
                    banner.setSpecBatchNoValues(info.getSpecBatchNoValues());
                }
                break;
            case OssCommConstants.BANNER_TYPE_LEASE:
                // 如果关联租赁商品 根据型号id获取型号名称
                ResponseResult<?> res = materielService.getMaterielModelDetail(banner.getModelId().intValue());
                if (res.isSuccess() && null != res.getData()) {
					Map<String, Object> map = (HashMap) res.getData();
                    banner.setModelName(map.get("materielModelName").toString());
                    Object materielClassIdObj = map.get("materielClassId");
                    Object materielBrandIdObj = map.get("materielBrandId");
                    banner.setMaterielClassId(materielClassIdObj == null ? null : (Integer) (materielClassIdObj));
                    banner.setMaterielBrandId(materielBrandIdObj == null ? null : (Integer) (materielBrandIdObj));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void delete(Integer id) {
        bannerDao.delete(id);
    }

}
