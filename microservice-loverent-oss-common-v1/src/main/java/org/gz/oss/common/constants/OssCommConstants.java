package org.gz.oss.common.constants;

/**
 * 常量工具类
 * Author	Version		Date		Changes
 * zhangguoliang 	1.0  		2017年12月18日 	Created
 */
public class OssCommConstants {

    public final static String  LOGSIGN_STRING   = "oss";

    /**
     * 是否演示数据
     */
    public final static Byte    IS_DEMO_DATA_YES = 1;
    /**
     * 单位 1：天 2：月
     */
    public final static Byte   UNIT_DAY         = 1;
    /**
     * 单位 1：天 2：月
     */
    public final static Byte   UNIT_MONTH       = 2;

    /**
     * 是否常量(0为否，1为是)
     */
    public final static int   IS_COMMON_YES                 = 1;
    public final static int   IS_COMMON_NO                  = 0;

    /**
     * 应用平台是否支持常量  0为不支持,1为支持
     */
    public final static String  APPLY_PLATFORM_IS_SUPPORT_NO  = "0";
    public final static String  APPLY_PLATFORM_IS_SUPPORT_YES = "1";

    /**
     * 上传图片类型  1：banner 2：橱窗位   3：其他  4:广告位
     */
    public final static int    UPLOAD_IMG_TYPE_BANNER        = 1;
    public final static int    UPLOAD_IMG_TYPE_SHOPWINDOW    = 2;
    public final static int    UPLOAD_IMG_TYPE_OTHER         = 3;
    public final static int    UPLOAD_IMG_TYPE_ADVERTISING   = 4;
    public final static int    UPLOAD_IMG_TYPE_RECOMMEND     = 5;
    public final static int    UPLOAD_IMG_TYPE_ALIPAYAPP     = 6;


    /**
     * 关联常量（1：推荐类型关联，2.拒绝锁定页关联）
     */
    public final static Byte   MAPPING_FLAG_RECOMMEND        = 1;
    public final static Byte   MAPPING_FLAG_OTHER            = 2;


    /**
     * 推荐位或广告位上移/下移类型
     */
    public final static int MOVE_UP = 0;
    public final static int MOVE_DOWN = 1;

    /**
     * banner配置类型（10：链接 20：售卖商品 30：租赁商品）
     */
    public final static int    BANNER_TYPE_LINKED            = 10;
    public final static int    BANNER_TYPE_SALE              = 20;
    public final static int    BANNER_TYPE_LEASE             = 30;

    /**
     * 推荐位类型（10：售卖  20：租赁）
     */
    public final static int    RECOMMEND_TYPE_SALE           = 10;
    public final static int    RECOMMEND_TYPE_LEASE          = 20;
}
