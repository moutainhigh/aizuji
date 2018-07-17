var productModelList = {
		tabHelper:transmitUtil.tableInit(),
		
		id:transmitUtil.getQueryString("id"),
		
		param:{
			"tabId":"productModelList_tab",
			"sortBtnId":"productModelList_sortBtn"
		},
		
		/**
		 * 初始化操作
		 */
		initOperator:function(){
			$("#"+this.param.sortBtnId).click(function(){
				var _param = new Object();
				var _trs = $("#"+productModelList.param.tabId+" tbody").find("tr");
				$.each(_trs,function(index){
					var _this = $(this);
					_param["relationReqList["+index+"].id"] = _this.find("td").children("input[type = hidden]").eq(0).val();
					_param["relationReqList["+index+"].sortNum"] = index+1;
				});
				var _url = "/oss/productColumn/batchUpdateRelation";
				transmitUtil.ajax(_url,_param,function(data){
					transmitUtil.alert("保存排序成功");
				});
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
	 			    	 var _hiddenIpt = "<input name='id' type='hidden' value="+row.id+">";
	 			        return index+1+_hiddenIpt;  
	 			    }  
	 			},
	 	        {
	 	              field: 'materielModelName',
	 	              title: '型号'
	 	        },
	 	       {
	 	              field: 'isDeleted',
	 	              title: '状态',
	 	              formatter: function (value, row, index) {
	            	      return value==0?"已启用":"已停用";
	     			  } 
	 	        },
	 	       {
	 	              field: 'price',
	 	              title: '价格'
	 	        },
	 	       {
	 	              field: 'photoUrl',
	 	              title: '配图',
	 	              formatter: function (value, row, index) {
	 	            	    var _val = "<img src='"+value+"'  class='img-rounded' style='width:100px'>"  
	     			        return _val;  
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
			productModelList.tabHelper.init(productModelList.param.tabId,"/oss/productColumn/queryRelationList?columnId="+productModelList.id,function(){},_columns,false,function(){
				$("#"+productModelList.param.tabId+" tbody").dragsort({ dragSelector: "tr", dragEnd: function() {
					//拖拽结束将序号更新
					productModelList.updateSortNum(productModelList.param.tabId);
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
				_this.find("td").eq(0).html();
			});
		},
		
		/**
		 * 操作项格式化
		 */
		operateFormatter:function(value, row, index){
			return [
			    '<button type="button" class="del btn btn-danger  btn-sm" style="margin-right:15px;">删除</button>',
			    '<button type="button" class="edit btn btn-default  btn-sm" style="margin-right:15px;">编辑</button>'
			].join('');
		},
		
		/**
		 * 操作项绑定事件
		 */
		operateEvents:{
		   'click .del': function (e, value, row, index) {
			   transmitUtil.promt("确定删除吗？",function(){
				   var _delUrl = "/oss/productColumn/deleteRelation?id="+row.id;
				   transmitUtil.ajax(_delUrl,null,function(data){
					   $('#'+productModelList.param.tabId).bootstrapTable('refresh');
					});
			   });
		    },
		    'click .edit': function (e, value, row, index) {
		    	window.location.href = "../column/updateProductModel.html?id="+row.id+"&columnId="+productModelList.id;           
			}
		},
		
		/**
		 * 初始化页面 
		 *
		 */
		initPage:function(){
			this.initOperator();
			this.initData();
		}	
};
productModelList.initPage();