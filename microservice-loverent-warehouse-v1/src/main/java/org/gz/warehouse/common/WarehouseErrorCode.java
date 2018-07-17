/**
 * 
 */
package org.gz.warehouse.common;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月4日 下午4:20:05
 */
public enum WarehouseErrorCode {
	/**
	 * 编码规则：前两位：模块名称,中两位:功能名称,后两位：错误编码
	 */
	MATERIEL_UNIT_REPEAT_ERROR(100001, "物料单位数据重复"), 
	MATERIEL_UNIT_USING_ERROR(100002, "物料单位正被使用"), 
	
	MATERIEL_SPEC_REPEAT_ERROR(100101, "物料规格数据重复"),
	MATERIEL_SPEC_USING_ERROR(100102, "物料规格正被使用"), 
	
	MATERIEL_BRAND_REPEAT_ERROR(100301, "物料品牌数据重复"),
	MATERIEL_BRAND_USING_ERROR(100302, "物料品牌正被使用"), 
	
	MATERIEL_MODEL_REPEAT_ERROR(100303, "物料模型数据重复"),
	MATERIEL_MODELSPEC_REPEAT_ERROR(100304, "物料模型规格数据重复"),
	
	MATERIEL_CLASS_REPEAT_ERROR(100401, "物料分类数据重复"),
	MATERIEL_CLASS_USING_ERROR(100402, "物料分类正被使用"), 
	
	MATERIEL_BASIC_USING_ERROR(100501, "物料信息已使用"), 
	MATERIEL_BASIC_REPEAT_ERROR(100502, "物料名称重复"), 
    MATERIEL_BASIC_NOTEXISTS_ERROR(100503, "物料信息不存在"),
    MATERIEL_BASIC_ALREADY_DISABLED_ERROR(100504, "物料信息不可用"),
	
	/****************************仓库定义*****************************/
	WAREHOUSE_LOCATION_REPEAT_ERROR(101001, "库位编码重复"), 
	WAREHOUSE_LOCATION_USING_ERROR(101002, "库位编码正被使用"), 
	WAREHOUSE_INFO_REPEAT_ERROR(102001, "仓库编码重复"),  
	
	/****************************物料采购申请定义*****************************/
	PURCHASE_APPLY_REPEAT_ERROR(110001, "采购申请物料数据重复"), 
	PURCHASE_APPLY_NOTEXIST_ERROR(110002, "采购申请物料数据不存在"), 
	PURCHASE_APPROVED_STATUS_ERROR(120001, "采购审批状态错误"), 

    /****************************产品管理定义*****************************/
    PRODUCT_APPLY_REPEAT_ERROR(130001, "产品数据重复"),
    
    /****************************外部接口错误码定义*****************************/
    EX_NO_COMMODITY_ERROR(210001, "商品可售库存不足"),
    EX_EXIST_COMMODITY_ERROR(210002, "商品库位已存在"), 
    EX_NO_WAREHOUSELOCATION_ERROR(210003, "新机库冻结库位不存在"),
    EX_SIGN_FAILED_ERROR(210004, "签约失败"),
    EX_PICK_QUERY_ERROR(210005, "拣货查询失败"),
    EX_CONFIRM_SIGN_ERROR(210006, "确认收货失败"),
    EX_BUY_END_ERROR(210007, "买断失败"),
    EX_RENTING_ERROR(210008,"在租调用失败"),
    PICK_EXCEPTION_ERROR(310000,"拣货异常失败"),
    PICK_NOT_SIGN_ERROR(310001,"拣货失败,该订单尚未进行过签约冻结操作"),
    PICK_UNKNOW_COMMODITY_ERROR(310002,"拣货失败,扫描商品与冻结商品不一致"),
    
    PICK_LOCATION_ERROR(310003,"拣货失败，冻结库位暂时无库存"),
    OUT_ORDER_ERROR(310004,"出货失败，未找到拣货相关信息"),
    OUT_STATUS_ERROR(310005,"出货失败，订单不处于待发货状态"),
    OUT_LOCATION_ERROR(310006,"出货失败，待售库位暂时无库存"),
    OUT_EXCEPTION_ERROR(310010,"出货失败，出货异常失败"),
    UNDO_PICK_NOT_ERROR(310011,"撤销拣货失败，该订单尚未进行过拣货操作"),
    UNDO_PICK_UNKNOW_COMMODITY_ERROR(310012,"撤销拣货失败，传入的商品参数与拣货商品不一致"),
    UNDO_PICK_LOCATION_ERROR(310013,"撤销拣货失败，待售库位中暂时无传入商品"),
    UNDO_PICK_EXCEPTION_ERROR(310014,"撤销拣货异常失败"),
    RENTING_IN_LOCATION_ERROR(310015,"入库失败，在租库位暂无库存"),
    TRANSIT_OUT_IN_LOCATION_ERROR(310016,"入库失败，出库在途库位暂无库存"),
    UNDO_OUT_ERROR(310017,"变更商品在租库位失败，该订单尚未进行过出库操作"), 
    UNDO_OUT_UNKNOW_COMMODITY_ERROR(310018,"变更商品在租库位失败，传入的商品参数与出货商品不一致"),
	;

	/**
	 * 错误代码
	 */
	private int code;

	/**
	 * 错误消息
	 */
	private String message;

	WarehouseErrorCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
