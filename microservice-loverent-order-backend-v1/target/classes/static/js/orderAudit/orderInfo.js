/**
 * 订单信息
 */
var orderInfo = {
	/**
	 * 订单数据
	 */
	orderData:null,
	
	/**
	 * 初始化数据
	 */
	initData:function(){
		//经纬度数据
		this.initRentCoordinateData();
		
		//订单基础数据
		this.initOrderBasicData();
	},
	
	/**
	 * 初始化订单基础数据
	 */
	initOrderBasicData:function(){
		var _d = this.orderData;
		var _rentRecord = _d.rentRecord;
		var _rentRecordExtends = _d.rentRecordExtends;
		$("#orderInfo_orderNo").html(_rentRecord.rentRecordNo || "");
		$("#orderInfo_userName").html(_rentRecordExtends.realName || "");
		$("#orderInfo_mobile").html(_rentRecordExtends.phoneNum || "");
		$("#orderInfo_IdNo").html(_rentRecordExtends.idNo || "");
		$("#orderInfo_address").html((_rentRecordExtends.prov||"") + (_rentRecordExtends.city||"") + (_rentRecordExtends.area||"") + (_rentRecordExtends.address||""));
		$("#orderInfo_emergencyContact").html(_rentRecordExtends.emergencyContact || "");
		$("#orderInfo_emergencyContactPhone").html(_rentRecordExtends.emergencyContactPhone || "");
		
		
		$("#orderInfo_leaseTerm").html(_rentRecordExtends.leaseTerm && (_rentRecordExtends.leaseTerm + "个月") || "");
		$("#orderInfo_leaseAmount").html(_rentRecordExtends.leaseAmount || "");
		$("#orderInfo_applyTime").html(_rentRecord.applyTime && new Date(_rentRecord.applyTime).Format("yyyy-MM-dd"));
		$("#orderInfo_premium").html(_rentRecordExtends.premium || "");
		$("#orderInfo_signContractAmount").html(_rentRecordExtends.signContractAmount || "");
		$("#orderInfo_floatAmount").html(_rentRecordExtends.floatAmount || "");
		$("#orderInfo_classAndBrandName").html((_rentRecordExtends.materielClassName || "") +">>" +  (_rentRecordExtends.materielBrandName || ""));
		$("#orderInfo_materielModelName").html(_rentRecordExtends.materielModelName || "");
		$("#orderInfo_materielSpecName").html(_rentRecordExtends.materielSpecName || "");
		$("#orderInfo_sn").html(_rentRecord.snCode || "");
		$("#orderInfo_imei").html(_rentRecord.imei || "");
	},
	
	/**
	 * 初始化经纬度数据
	 */
	initRentCoordinateData:function(){
		var _url = "/integration/queryRentCoordinateByRecordNo";
		var _param = {"rentRecordNo":this.orderData.rentRecord.rentRecordNo};
		common.ajax(_url,_param,function(data){
			var _list = data;
			for(var i = 0; i< _list.length; i++){
				var _obj = _list[i];
				var _latLng = (_obj.lat||"") + "," + (_obj.lng||"");
				if(_obj.state == 1){
					//申请
					$("#orderInfo_applyLngLat").html(_latLng);
				}else if(_obj.state == 2){
					//签约
					$("#orderInfo_signLngLat").html(_latLng);
                    $("#orderInfo_phoneModel").html(_obj.phoneModel);
				}
			}
		});
	},
	
	/**
	 * 初始化页面
	 */
	initPage:function(orderData){
		this.orderData = orderData;
		this.initData();
	}	
}