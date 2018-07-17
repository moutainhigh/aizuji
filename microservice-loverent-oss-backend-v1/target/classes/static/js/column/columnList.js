var bannerList = {
		tabHelper:transmitUtil.tableInit(),
		
		/**
		param {
		   "tabId":"tab容器Id",
		   "addBtnId":"添加按钮id",
		   "sortBtnId":"排序按钮id",
		   "resource":"来源 1:贷款云；2：贷款123借款首页；3：贷款123贷款超市页",
		}*/
		param:null,
		
		/**
		 * 初始化操作
		 */
		initOperator:function(){
			$("#"+this.param.sortBtnId).click(function(){
				var _param = new Object();
				var _trs = $("#"+bannerList.param.tabId+" tbody").find("tr");
				$.each(_trs,function(index){
					var _this = $(this);
					_param["columnReqList["+index+"].id"] = _this.find("input[name='id']").val();
					_param["columnReqList["+index+"].sortNum"] = index+1;
				});
				var _url = "/oss/productColumn/batchUpdateColumn";
				transmitUtil.ajax(_url,_param,function(data){
					transmitUtil.alert("保存排序成功");
				});
			});
			
			$("#"+bannerList.param.addBtnId).click(function(){
				window.location.href = "../column/editProductColumn.html";
			});
		},
		
		/**
		 * 初始化数据
		 */
		initData:function(){
			// 初始化表格控件
			this.initTabData();
		},
		
		/**
		 * 初始化表格数据
		 */
		initTabData:function(){
			var _columns = [[
	 			{  
	 			    title: '序号',// 标题 可不加
	 			    formatter: function (value, row, index) {  
	 			        return index+1;  
	 			    }  
	 			},
	 			{
	 	              field: 'columnName',
	 	              title: '栏目名称',
	 	              formatter: function (value, row, index) {
	            	       var _hiddenIpt = "<input name='id' type='hidden' value="+row.id+">"  
	     			       return value + _hiddenIpt;  
	     			  } 
	 	        },
	 	        {
	              field: 'operate',
	              title: '操作',
	              align: 'center',
	              events: this.operateEvents,
	              formatter: this.operateFormatter
	           }
	 	    ]];
			bannerList.tabHelper.init(bannerList.param.tabId,"/oss/productColumn/queryColumnList",null,_columns,false,function(){
				$("#"+bannerList.param.tabId+" tbody").dragsort({ dragSelector: "tr", dragEnd: function() {
					//拖拽结束将序号更新
					bannerList.updateSortNum(bannerList.param.tabId);
				}, dragBetween: false, placeHolderTemplate: "<tr><td></td><td></td><td></td><td></td></tr>" });
			});
		},
		
		/**
		 * 更新排序号
		 */
		updateSortNum:function(tabId){
			var _trs = $("#"+tabId+" tbody").find("tr");
			$.each(_trs,function(index){
				var _this = $(this);
				_this.find("td").eq(0).html(index+1);
			});
		},
		
		/**
		 * 操作项格式化
		 */
		operateFormatter:function(value, row, index){
			return [
			    '<button type="button" class="del btn btn-danger  btn-sm" style="margin-right:15px;">删除</button>',
			    '<button type="button" class="edit btn btn-default  btn-sm" id="bianji" style="margin-right:15px;">编辑</button>',
			    '<button type="button" class="edit btn btn-default  btn-sm" id="guanlian" style="margin-right:15px;">关联产品</button>',
			    '<button type="button" class="edit btn btn-default  btn-sm" id="btnproductModelList" style="margin-right:15px;">型号列表</button>'
			].join('');
		},
		
		/**
		 * 操作项绑定事件
		 */
		operateEvents:{
			//绑定上下架事件
			   'click .del': function (e, value, row, index) {
				   transmitUtil.promt("确定删除该栏目吗？",function(){
					   var _delUrl = "/oss/productColumn/batchUpdateColumn";
					   var _delParam = new Object();
					   _delParam["columnReqList["+0+"].id"] = row.id;
					   _delParam["columnReqList["+0+"].isDeleted"] = 1;
					   transmitUtil.ajax(_delUrl,_delParam,function(data){
						   $('#'+bannerList.param.tabId).bootstrapTable('refresh');
						});
				   });
			    },
			    //绑定编辑事件
			    'click #bianji': function (e, value, row, index) {
			    	window.location.href = "../column/editProductColumn.html?id="+row.id;           
				},
				//绑定关联产品事件
			    'click #guanlian': function (e, value, row, index) {
			    	window.location.href = "../column/jointProduct.html?id="+row.id;           
				},
			    'click #btnproductModelList': function (e, value, row, index) {
			    	window.location.href = "../column/productModelList.html?id="+row.id;           
				}
		},
		
		/**
		 * 初始化页面 
		 *
		 */
		initPage:function(param){
			this.param = param;
			this.initOperator();
			this.initData();
		}	
};