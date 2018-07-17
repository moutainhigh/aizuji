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
		
		//流程信息
		this.initRentState();
		
		//物流信息
		this.initLogistics();
		
	},
	
	/**
	 * 初始化订单基础数据
	 */
	initOrderBasicData:function(){
		var _d = this.orderData;
		var _rentRecord = _d.rentRecord;
		var _rentRecordExtends = _d.rentRecordExtends;
		$("#orderInfo_orderNo").html(_rentRecord.rentRecordNo || "");
		$("#orderInfo_orderState").html(common.orderdetail.rentStateJson[_rentRecord.state] || "");
		$("#orderInfo_userName").html(_rentRecordExtends.realName || "");
		$("#orderInfo_mobile").html(_rentRecordExtends.phoneNum || "");
		$("#orderInfo_IdNo").html(_rentRecordExtends.idNo || "");
		$("#orderInfo_address").html((_rentRecordExtends.prov||"") + (_rentRecordExtends.city||"") + (_rentRecordExtends.area||"") + (_rentRecordExtends.address||""));
		$("#orderInfo_emergencyContact").html(_rentRecordExtends.emergencyContact || "");
		$("#orderInfo_emergencyContactPhone").html(_rentRecordExtends.emergencyContactPhone || "");
		
		
		$("#orderInfo_leaseTerm").html(_rentRecordExtends.leaseTerm && (_rentRecordExtends.leaseTerm + "个月") || "");
		$("#orderInfo_leaseAmount").html(_rentRecordExtends.leaseAmount || "");
		$("#orderInfo_applyTime").html(_rentRecord.applyTime && new Date(_rentRecord.applyTime).Format("yyyy-MM-dd"));
		$("#orderInfo_premium").html(_rentRecordExtends.premium || "0");
		$("#orderInfo_signContractAmount").html(_rentRecordExtends.signContractAmount || "");
		$("#orderInfo_floatAmount").html(_rentRecordExtends.floatAmount || "0");
		$("#orderInfo_classAndBrandName").html((_rentRecordExtends.materielClassName || "") +">>" +  (_rentRecordExtends.materielBrandName || ""));
		$("#orderInfo_materielModelName").html(_rentRecordExtends.materielModelName || "");
		$("#orderInfo_materielSpecName").html(_rentRecordExtends.materielSpecName || "");
		$("#orderInfo_sn").html(_rentRecord.snCode || "");
		$("#orderInfo_imei").html(_rentRecord.imei || "");
		var _fileUrl = _rentRecord.sealAgreementUrl;
		$("#orderInfo_hetong").html(_fileUrl && ("<a href='"+_fileUrl+"' download='"+_fileUrl+"' class='compact'>点击下载合同</a>") || "");
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
					$("#orderInfo_applyModel").html(_obj.phoneModel);
				}else if(_obj.state == 2){
					//签约
					$("#orderInfo_signLngLat").html(_latLng);
                    $("#orderInfo_signModel").html(_obj.phoneModel);
				}
			}
		});
	},
	
	
	initLogistics:function(){
		var _url = "/distribute/logistics";
		//加载列表数据
		var _columns = [[
			{
			    field: 'logisticsNo',
			    title: '物流运单号'
			},
			{
			    field: 'businessNo',
			    title: '物流商户号'
			},
			{
			    field: 'createMan',
			    title: '下单人'
			},
		  {
              field: 'createOn',
              title: '下单时间',
              formatter:function(value, row, index){
 		          return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
 		      }
          }, 
          {
              field: 'type',
              title: '物流类型',
              formatter:function(value, row, index){
 		          return common.orderdetail.logisticsTypeJson[value];
 		      }
          },
          {
              field: 'state',
              title: '物流状态',
              formatter:function(value, row, index){
 		          return common.orderdetail.logisticsStateJson[value];
 		      }
          },
          {
              field: 'operate',
              title: '操作',
              align: 'center',
              events: orderInfo.operateEvents,
              formatter: orderInfo.operateFormatter
          }
	    ]];
		common.tableInit().init("logistics_tab",_url,function(pageParam){
			var temp = {   // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				"currPage":pageParam.pageNumber,   // 页面大小
	            "pageSize": pageParam.pageSize,  // 页码
	            "rentRecordNo":orderInfo.orderData.rentRecord.rentRecordNo
	        };
	        return $.param(temp);
		},_columns,false);
	},
	
	/**
	 * 操作项格式化
	 */
	operateFormatter:function(value, row, index){
		return '<button type="button" class="logisticsNodes btn btn-default  btn-sm" style="margin-right:15px;">点击查看</button>';
	},
	/**
	 * 操作项绑定事件
	 */
	operateEvents:{
	    //绑定编辑事件
	    'click .logisticsNodes': function (e, value, row, index) {
	    	orderInfo.openLogisticsNodesDialog("物流节点信息",row.logisticsNodes);
		}
	},
	
	/**
	 * 打开物流节点弹窗
	 */
	openLogisticsNodesDialog:function(title,value){
		if(value){
			var ihtml="";
			$.each(value, function(){
				ihtml+='<div class="wl-list">';
				ihtml+="<span class='time'>";
				ihtml+=new Date(this.acceptTime).Format("yyyy-MM-dd hh:mm:ss");
				ihtml+="</span>";
				ihtml+="<span class='text'>";
				ihtml+=this.remark;
				ihtml+="</span>";
				ihtml+="</div>";
			});
			$("#logisticsBox").html(ihtml);
		}
		$("#logisticsModal").modal("show");
	},
	
	
	initRentState:function(){
		var _url = "/integration/rentState";
		//加载列表数据
		var _columns = [[
			{
			    field: 'createOn',
			    title: '时间',
			    formatter:function(value, row, index){
	 		          return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
	 		      }
			},
			{
			    field: 'state',
			    title: '事件',
			    formatter:function(value, row, index){
	 		          return value && common.orderdetail.rentStateJson[value];
	 		      }
			},
			{
			    field: 'createMan',
			    title: '操作人'
			}
	    ]];
		common.tableInit().init("rentState_tab",_url,function(pageParam){
			var temp = {   // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				"currPage":pageParam.pageNumber,   // 页面大小
	            "pageSize": pageParam.pageSize,  // 页码
	            "rentRecordNo":orderInfo.orderData.rentRecord.rentRecordNo
	        };
	        return $.param(temp);
		},_columns,false);
	},
	
	initOpeartBtn:function(){
		var _d = this.orderData;
		var _rentRecord = _d.rentRecord;
		if(_rentRecord.state==3) //完成支付 取消订单
			{
			$(".pay").show();
			$(".cancel").show();
			}
		else if(_rentRecord.state==4) //取消订单
			{
			$(".cancel").show();
			}
		else if(_rentRecord.state==6) //取消订单 下发签约通知
			{
			$(".cancel").show();
			$(".signnotice").show();
			}
		else if(_rentRecord.state==9) //确认收货
			{
			$(".confirmreceive").show();
			}
	},
	initOperator:function(){
		var _d = this.orderData;
		var _rentRecord = _d.rentRecord;
		$(".pay").click(function(){
			var _param = {};
			_param.rentRecordNo = _rentRecord.rentRecordNo;
	 		common.promt("完成支付？",orderInfo.pay,_param)
		});
		$(".cancel").click(function(){
			var _param = {};
			_param.rentRecordNo = _rentRecord.rentRecordNo;
			common.promt("取消订单？",orderInfo.cancel,_param)
		});
		$(".signnotice").click(function(){
			var _param = {};
			_param.rentRecordNo = _rentRecord.rentRecordNo;
			common.promt("下发签约通知？",orderInfo.signnotice,_param)
		});
		$(".confirmreceive").click(function(){
			var _param = {};
			_param.rentRecordNo = _rentRecord.rentRecordNo;
			common.promt("确认收货？",orderInfo.confirmreceive,_param)
		});
	},
	pay:function(param){
		common.ajax('/signpay/pay',param,function(data){
			window.location.reload();
		});
	},
	cancel:function(param){
		common.ajax('/signpay/cancel',param,function(data){
			window.location.reload();
		}); 
	},
	signnotice:function(param){
		common.ajax('/distribute/signnotice',param,function(data){
			window.location.reload();
		});
	},
	confirmreceive:function(param){
		common.ajax('/distribute/confirmreceive',param,function(data){
			window.location.reload();
		});
	},
	/**
	 * 初始化页面
	 */
	initPage:function(orderData){
		this.orderData = orderData;
		this.initData();
		this.initOperator();
		this.initOpeartBtn();
	}	
}