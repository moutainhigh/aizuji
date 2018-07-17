var loan123RecommendMenuList = {
	initOperator:function(){
		$("#editRecommendBtn").click(function(){
			window.location.href = "../recommend/loan123EditRecommendMenu.html";
		});
	},
	
	initData:function(){
		var _url = "/transmit-web/recommend/queryRecommendList";
		var _param = {"resource":2};
		transmitUtil.ajax(_url,_param,function(data){
			var _infoData = new Array();
         	var _columns = [[
				 {  
				    title: '序号',// 标题 可不加
				    formatter: function (value, row, index) {  
				        return index+1;  
				    }  
				 },
	             {
	                 field: 'remark',
	                 title: '菜单'
	             },
	             {
	            	 field: 'loanAppNum',
	            	 title: '已配置导流数'
	             },
	             {
	                 field: 'operate',
	                 title: '操作',
	                 align: 'center',
	                 events: loan123RecommendMenuList.operateEvents,
	                 formatter: loan123RecommendMenuList.operateFormatter
	             }
 		    ]];
         	transmitUtil.loadTableByJson("loan123_loan_tab","editRecommendToolbar",data,_columns,"id");
		});
	},
	
	/**
	 * 表单操作项格式化
	 */
	operateFormatter:function(value, row, index){
		var _isStart = (row.isDeleted == 1)?"上架":"下架";
		return [
		    '<button type="button" class="edit btn btn-default  btn-sm" style="margin-right:15px;">编辑导流</button>',
		    '<button type="button" class="isStart btn btn-default  btn-sm" style="margin-right:15px;">'+_isStart+'</button>'
		].join('');
	},
	
	/**
	 * 表格操作项绑定事件
	 */
	operateEvents:{
		//绑定上下架事件
		   'click .isStart': function (e, value, row, index) {
			   transmitUtil.updateIsStartPromt(row.isDeleted,"是否确定下架？",function(isDeleted){
				   loan123RecommendMenuList.updateLoanIsEnable(row.id,isDeleted,function(){
					   $('#loan123_loan_tab').bootstrapTable('destroy');
					   loan123RecommendMenuList.initData();
				   });
			   });
		    },
	    //绑定编辑事件
	    'click .edit': function (e, value, row, index) {
	    	window.location.href = "../loan/loanMapping.html?id="+row.id+"&type="+1;           
		}
	},
	
	/**
	 * 推荐位上下架
	 */
	updateLoanIsEnable:function(id,isDeleted,successCb){
		var _url = "/transmit-web/recommend/updateRecommendIsEnable";
		var _param = {};
		_param["id"] = id;
		_param["isDeleted"] = isDeleted;
		transmitUtil.ajax(_url,_param,function(data){
			successCb(data);
		});
	},
	
	initPage:function(){
		$("#menuNav").html(transmitUtil.getMenuHtml("贷款123推荐位"));
		this.initOperator();
		this.initData();
	}	
} 
