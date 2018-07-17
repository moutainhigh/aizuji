/**
 * 订单审核
 */
var orderAudit = {

	/**
	 * 订单id
	 */
	orderId:common.getQueryString("id"),
	
	/**
	 * 订单信息
	 */
	orderInfo:null,
	
	/**
	 * 电审id
	 */
	phoneAuditId:null,
	
	/**
	 * 初始化操作
	 */
	initOperator:function(){
		//切换对应tab
		$('.order-tab li').click(function(){
			var _this = $(this);
			_this.addClass('active').siblings().removeClass('active');
			var _index = _this.index();
			var _id = _this.attr("id").split("_")[0];
			$('.tab-content').hide().eq(_index).show();
			eval(_id+".initPage(orderAudit.orderInfo);");
		});
		
		$("#addPhoneAuditRecord").click(function(){
			orderAudit.phoneAuditId = null;
			orderAudit.openPhoneAuditDialog("新增电核记录");
		});
		
		//审批拒绝点击
		$("#auditRefusalBtn").click(function(){
			var _refusalReason = $("#refusal_reason_sel").val();
			if(!_refusalReason){
				common.error("审批拒绝理由不能为空");
				return false;
			}
			var _param = {};
			_param['creditType'] = 2;
			_param['creditResult'] = 1;
			_param['userId'] = orderAudit.orderInfo.rentRecord.userId;
			_param['refusalReason'] = $("#refusal_reason_sel").val();
			_param['remark'] = $.trim($("#auditRefusalRemark").val());
			_param['orderNo'] = orderAudit.orderInfo.rentRecord.rentRecordNo;
			orderAudit.orderAuditHandle(_param);
		});

		//审批通过点击
		$("#auditPassBtn").click(function(){
			var _param = {};
			_param['creditType'] = 2;
			_param['creditResult'] = 2;
			_param['userId'] = orderAudit.orderInfo.rentRecord.userId;
			_param['remark'] = $.trim($("#auditPassRemark").val());
			_param['orderNo'] = orderAudit.orderInfo.rentRecord.rentRecordNo;
			orderAudit.orderAuditHandle(_param);
		});
		
		//新增或修改电核记录
		$("#addOrUpdatePhoneAuditBtn").click(function(){
			var _param = {};
			_param['phoneVerifyResult'] = $("#phone_audit_result_sel").val();
			_param['suggest'] = $("#phone_audit_suggest_sel").val();
			_param['remark'] = $.trim($("#phone_audit_remark").val());
			_param['orderNo'] = orderAudit.orderInfo.rentRecord.rentRecordNo;
			var _url = "/orderPhoneVerifyInfo/add";
			if(orderAudit.phoneAuditId){
				_param['id'] = orderAudit.phoneAuditId;
				_url = "/orderPhoneVerifyInfo/update";
			}
			common.ajax(_url,_param,function(){
				$("#addVerify").modal("hide");
				$('#phone_audit_tab').bootstrapTable('refresh');
			});
		});
	},
	
	/**
	 * 订单审核接口
	 */
	orderAuditHandle:function(param){
		var _url = "/orderCreditDetail/orderAudit";
		var _param = param;
		common.ajax(_url,_param,function(){
			common.alert("操作成功");
			$("#checkPass").modal("hide");
			$("#checkrefuse").modal("hide");
			window.location.href = "./approvalManage.html";
		});
	},

	/**
	 * 初始化数据
	 */
	initData:function(){
		$('.order-tab li').eq(0).trigger("click");
		
		//初始化电核列表
		this.initAuditList();
		
		//初始化审核结果、审核建议、拒绝理由下拉框
		common.initJsonSel("phone_audit_result_sel",common.orderAudit.phoneAuditResultJson);
		common.initJsonSel("phone_audit_suggest_sel",common.orderAudit.suggestJson);
		common.initJsonSel("refusal_reason_sel",common.orderAudit.refusalReasonJson);
	},
	
	
	initAuditList:function(){
		var _url = "/orderPhoneVerifyInfo/queryList";
		//加载列表数据
		var _columns = [[
		{  
		    title: '序号',// 标题 可不加
		    formatter: function (value, row, index) {  
		        return index+1;  
		    }  
		},
		{
              field: 'updateOn',
              title: '更新时间',
              formatter:function(value, row, index){
 		          return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
 		      }
          }, 
          {
              field: 'createName',
              title: '提交人'
          },
          {
              field: 'updateName',
              title: '更新人'
          },
          {
              field: 'phoneVerifyResult',
              title: '电核结果',
              formatter:function(value, row, index){
 		          return value && common.orderAudit.phoneAuditResultJson[value];
 		      }
          },
          {
        	  field: 'suggest',
        	  title: '建议',
              formatter:function(value, row, index){
 		          return value && common.orderAudit.suggestJson[value];
 		      }
          },
          {
        	  field: 'remark',
        	  title: '备注'
          },
          {
              field: 'operate',
              title: '操作',
              align: 'center',
              events: orderAudit.operateEvents,
              formatter: orderAudit.operateFormatter
          }
	    ]];
		common.tableInit().init("phone_audit_tab",_url,function(pageParam){
			var temp = {   // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				"currPage":pageParam.pageNumber,   // 页面大小
	            "pageSize": pageParam.pageSize,  // 页码
	            "orderNo":orderAudit.orderInfo.rentRecord.rentRecordNo
	        };
	        return $.param(temp);
		},_columns);
	},
	
	/**
	 * 操作项格式化
	 */
	operateFormatter:function(value, row, index){
		return [
		    '<button type="button" class="edit btn btn-default  btn-sm" style="margin-right:15px;">编辑</button>',
		    '<button type="button" class="del btn btn-default  btn-sm" style="margin-right:15px;">删除</button>',
		].join('');
	},
	
	/**
	 * 打开电联审核弹框
	 */
	openPhoneAuditDialog:function(title,phoneVerifyResult,suggest,remark){
		$("#phone_audit_span").val(title);
		$("#phone_audit_remark").val(remark || "");
		$("#phone_audit_result_sel").val(phoneVerifyResult || 1);
		$("#phone_audit_suggest_sel").val(suggest || 1);
		$("#addVerify").modal("show");
	},
	
	/**
	 * 操作项绑定事件
	 */
	operateEvents:{
	    //绑定编辑事件
	    'click .edit': function (e, value, row, index) {
	    	orderAudit.phoneAuditId = row.id;
	    	orderAudit.openPhoneAuditDialog("编辑电联记录",row.phoneVerifyResult,row.suggest,row.remark);
		},
		 //绑定删除事件
	    'click .del': function (e, value, row, index) {
	    	common.promt("是否确定删除",function(){
	    	   var _url = "/orderPhoneVerifyInfo/delete";
	    	   var _param = {"id":row.id};
	    	   common.ajax(_url,_param,function(){
	    		   $('#phone_audit_tab').bootstrapTable('refresh');           
	    	   });
		    });
		}
	},
		
	/**
	 * 初始化加载
	 */
	initLoad:function(){
		var _url = "/integration/detail/" + this.orderId;
		var _param = {};
		common.ajax(_url,_param,function(data){
			orderAudit.orderInfo = data;
			orderAudit.initOperator();
			orderAudit.initData();
		},null,null,'get');
	},
	
	/**
	 * 初始化页面
	 */
	initPage:function(){
		this.initLoad();
	}	
};
orderAudit.initPage();