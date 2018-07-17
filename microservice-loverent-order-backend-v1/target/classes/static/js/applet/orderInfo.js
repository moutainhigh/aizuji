/**
 * 订单信息
 */
var orderInfo = {
	orderNo:common.getQueryString("orderNo"),
	/**
	 * 订单数据
	 */
	orderData:null,
	
	/**
	 * 初始化数据
	 */
	initData:function(){
		
		//订单基础数据
		this.initOrderBasicData();
		
		//流程信息
		this.initRentState();
		
		//物流信息
		this.initLogistics();
		
	},
	initLoad:function(){
		var _url = "/applet/detail";
		var _param = {};
		common.ajax(_url,"rentRecordNo="+this.orderNo,function(data){
			orderInfo.orderData = data;
			orderInfo.initData();
			orderInfo.initOperator();
			orderInfo.initOpeartBtn();
		},null,null,'get');
	},
	
	/**
	 * 初始化订单基础数据
	 */
	initOrderBasicData:function(){
		var _d = this.orderData;
		$("#orderInfo_orderNo").html(_d.rentRecordNo || "");
		$("#orderInfo_zmOrderNo").html(_d.zmOrderNo || "");
		$("#orderInfo_zmGrade").html(_d.zmGrade || "");
		$("#orderInfo_orderState").html(common.orderdetail.rentStateJson[_d.state] || "");
		$("#orderInfo_userName").html(_d.realName || "");
		$("#orderInfo_mobile").html(_d.phoneNum || "");
		$("#orderInfo_IdNo").html(_d.idNo || "");
		$("#orderInfo_address").html((_d.prov||"") + (_d.city||"") + (_d.area||"") + (_d.address||""));
		$("#orderInfo_emergencyContact").html(_d.emergencyContact || "");
		$("#orderInfo_emergencyContactPhone").html(_d.emergencyContactPhone || "");
		
		
		$("#orderInfo_leaseTerm").html(_d.leaseTerm && (_d.leaseTerm + "个月") || "");
		$("#orderInfo_leaseAmount").html(_d.leaseAmount || "");
		$("#orderInfo_applyTime").html(_d.applyTime && new Date(_d.applyTime).Format("yyyy-MM-dd"));
		$("#orderInfo_premium").html(_d.premium || "0");
		$("#orderInfo_signContractAmount").html(_d.signContractAmount || "");
		$("#orderInfo_floatAmount").html(_d.floatAmount || "0");
		$("#orderInfo_creditAmount").html(_d.creditAmount || "0");
		$("#orderInfo_showAmount").html(_d.showAmount || "0");
		$("#orderInfo_classAndBrandName").html((_d.materielClassName || "") +">>" +  (_d.materielBrandName || ""));
		$("#orderInfo_materielModelName").html(_d.materielModelName || "");
		$("#orderInfo_materielSpecName").html(_d.materielSpecName || "");
		$("#orderInfo_sn").html(_d.snCode || "");
		$("#orderInfo_imei").html(_d.imei || "");
		var _fileUrl = _d.sealAgreementUrl;
		$("#orderInfo_hetong").html(_fileUrl && ("<a href='"+_fileUrl+"' download='"+_fileUrl+"' class='compact'>点击下载合同</a>") || "");
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
	            "rentRecordNo":orderInfo.orderData.rentRecordNo
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
		var _url = "/applet/rentState";
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
	            "rentRecordNo":orderInfo.orderData.rentRecordNo
	        };
	        return $.param(temp);
		},_columns,false);
	},
	
	initOpeartBtn:function(){
		var _d = this.orderData;
		if(_d.state==3) // 取消订单
			{
			$(".cancel").show();
			}
		else if(_d.state==4) //取消订单
			{
			$(".cancel").show();
			}
		else if(_d.state==6) //取消订单 
			{
			$(".cancel").show();
			}
		else if(_d.state==9) //确认收货
			{
			$(".confirmreceive").show();
			}
	},
	initOperator:function(){
		var _d = this.orderData;
		$(".confirmreceive").click(function(){
			var _param = {};
			_param.rentRecordNo = _d.rentRecordNo;
	 		common.promt("确认收货？",orderInfo.pay,_param)
		});
		$(".cancel").click(function(){
			var _param = {};
			_param.rentRecordNo = _d.rentRecordNo;
			common.promt("取消订单？",orderInfo.cancel,_param)
		});
	},
	cancel:function(param){
		common.ajax('/applet/cancelOrder',param,function(data){
			window.location.reload();
		}); 
	},
	confirmreceive:function(param){
		common.ajax('/applet/signedOrder',param,function(data){
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
};
orderInfo.initLoad();