package org.gz.oss.common.entity;

import org.gz.common.entity.QueryPager;

import javax.validation.constraints.NotNull;

/**
 * @author WenMing.Zhou
 * @Description: ${todo}
 * @date 2018/3/23 16:19
 */
public class MaterielModelQuery extends QueryPager {

    private static final long serialVersionUID = 4772240877833004304L;


    @NotNull(message = "品牌ID不能为空")
    private Integer brandId; //品牌ID



    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }


}
