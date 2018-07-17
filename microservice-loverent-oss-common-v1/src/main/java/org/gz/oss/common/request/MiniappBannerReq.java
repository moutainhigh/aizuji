package org.gz.oss.common.request;

import org.gz.common.entity.BaseEntity;
import org.gz.oss.common.entity.MiniAppBanner;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author WenMing.Zhou
 * @Description: ${todo}
 * @date 2018/3/27 15:39
 */
public class MiniappBannerReq  extends BaseEntity {

    private static final long serialVersionUID = 6785301473523473891L;

    @NotNull(message = "对象属性缺少必填参数")
    @Valid
    private List<MiniAppBanner> miniappBannerReqList;

    public List<MiniAppBanner> getMiniappBannerReqList() {
        return miniappBannerReqList;
    }

    public void setMiniappBannerReqList(List<MiniAppBanner> miniappBannerReqList) {
        this.miniappBannerReqList = miniappBannerReqList;
    }
}
