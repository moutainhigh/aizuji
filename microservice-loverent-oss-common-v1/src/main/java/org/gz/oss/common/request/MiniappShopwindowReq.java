package org.gz.oss.common.request;

import org.gz.common.entity.BaseEntity;
import org.gz.oss.common.dto.MiniAppShopwindowVo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author WenMing.Zhou
 * @Description: ${todo}
 * @date 2018/3/28 10:09
 */
public class MiniappShopwindowReq extends BaseEntity {

    private static final long serialVersionUID = -8084535817705413449L;


    @NotNull(message = "请参数属性不能为空")
    @Valid
    private List<MiniAppShopwindowVo> shopwindowList;

    public List<MiniAppShopwindowVo> getShopwindowList() {
        return shopwindowList;
    }

    public void setShopwindowList(List<MiniAppShopwindowVo> shopwindowList) {
        this.shopwindowList = shopwindowList;
    }
}
