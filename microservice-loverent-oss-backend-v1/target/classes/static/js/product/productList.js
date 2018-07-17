/**
 * 产品列表
 */
var productList = {
		
	tabHelper:common.tableInit(),	
    
	/**
     * 初始化操作
     */
	initOperator:function(){
		// 绑定查询事件
		$("#btn_query").click(function(){
			$('#product_tab').bootstrapTable('destroy');
			productList.initTabData();
		});	
	},
	
	/**
	 * 刷新表格
	 */
	refreshTab:function(){
		$('#product_tab').bootstrapTable('refresh');
	},
	
	/**
	 * 初始化数据
	 */
	initData:function(){
		this.initMaterielInfo();
		
		this.initMaterielNewConfig();
		
		this.initTabData();
	},
	
	/**
	 * 初始化新旧程度配置下拉框
	 */
	initMaterielNewConfig:function(){
		var _url = "/oss/product/queryAllNewConfigs";
		common.ajax(_url,null,function(data){
			var _arr = new Array();
			_arr.push("<option value=''>请选择产品成色</option>");
			var _list = data || new Array();
			for(var i = 0;i < _list.length;i++){
				var _obj = _list[i];
				_arr.push("<option value='"+_obj.id+"'>"+_obj.configValue+"</option>");
			}
			$("#materielNewConfigSel").html(_arr.join(""));
		});
	},
	
	/**
	 * 初始化表格数据
	 */
	initTabData:function(){
		var _columns = [[
			{
	              field: 'productNo',
	              title: '产品编号'
	          }, 
	          {
	              field: 'modelName',
	              title: '产品型号'
	          },
	          {
	              field: 'className',
	              title: '所属分类'
	          },
	          {
	              field: 'termValue',
	              title: '租期',
	              formatter:function(value, row, index){
		              return value && (value+"个月");
		          }
	          },
	          {
	        	  field: 'leaseAmount',
	        	  title: '月租金'
	          },
	          {
	        	  field: 'showAmount',
	        	  title: '价值'
	          },
	          {
	        	  field: 'signContractAmount',
	        	  title: '签约价值'
	          },
	          {
	        	  field: 'floatAmount',
	        	  title: '产品溢价'
	          },
	          {
	        	  field: 'premium',
	        	  title: '保险费用'
	          },
	          {
	        	  field: 'isDeleted',
	        	  title: '状态',
	        	  formatter:function(value, row, index){
		              return (value == 1) && "下架" || "上架";
		          }
	          },
	          {
	        	  field: 'specBatchNoValues',
	        	  title: '规格',
	        	  formatter:function(value, row, index){
		              return value && value.replace(new RegExp(",","gm"),"&nbsp;&nbsp;");
		          }
	          },
	          {
	        	  field: 'productType',
	        	  title: '产品类型',
	        	  formatter:function(value, row, index){
	        		  var _val = "";
	        		  switch (value) {
						case 1:
							_val = "租赁"
							break;
						case 2:
							_val = "以租代购"
							break;
						case 3:
							_val = "出售"
								break;
						default:
							break;
					  }
		              return _val;
		          }
	          },
	          {
	        	  field: 'configValue',
	        	  title: '产品成色'
	          },
	          {
	              field: 'operate',
	              title: '操作',
	              align: 'center',
	              events: this.operateEvents,
	              formatter: this.operateFormatter
	          }
		]];
		productList.tabHelper.init("product_tab","/oss/product/queryProductList",function(pageParam){
			var temp = {   // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	            "currPage":pageParam.pageNumber,   // 页面大小
	            "pageSize": pageParam.pageSize,  // 页码
	            "materielClassId":$("#materielClass").val(),
	            "materielBrandId":$("#materielBrand").val(),
			    "materielModelId":$("#materielModel").val(),
			    "specBatchNo":$("#materielSpec").val(),
			    "productType":$("#productTypeSel").val(),
			    "materielNewConfig":$("#materielNewConfigSel").val(),
				"isDeleted":$("#statue").val()
	        };
	        return $.param(temp);
		},_columns);
	},
	
	/*
	 * 操作项格式化
	 */
	operateFormatter:function(value, row, index){
		var _isStart = (row.isDeleted == 1)?"上架":"下架";
		return [
		    '<button type="button" class="edit btn btn-default  btn-sm" style="margin-right:15px;">编辑</button>',
		    '<button type="button" class="isStart btn btn-default  btn-sm" style="margin-right:15px;">'+_isStart+'</button>'
		].join('');
	},
	
	/**
	 * 操作项绑定事件
	 */
	operateEvents:{
		//绑定上下架事件
		   'click .isStart': function (e, value, row, index) {
			   common.updateIsStartPromt(row.isDeleted,"是否确定下架该产品",function(isDeleted){
				   productList.updatePdtIsEnable(row.id,isDeleted,row.companyId,function(){
					   productList.refreshTab();
				   });
			   });
		    },
		    //绑定编辑事件
		    'click .edit': function (e, value, row, index) {
		    	window.location.href = "productEdit.html?id="+row.id;           
			}
	},
	
	/**
	 * 修改产品上下架
	 */
	updatePdtIsEnable:function(id,isDeleted,companyId,successCb){
		var _url = "/oss/product/updateProductIsEnable";
		var _param = {};
		_param["id"] = id;
		_param["isDeleted"] = isDeleted;
		common.ajax(_url,_param,function(data){
			successCb(data);
		});
	},
	
	/**
	 * 初始化物料
	 */
	initMaterielInfo:function(){
		materiel.init("materielClass","materielBrand","materielModel","materielSpec");
	},
	
	initPage:function(){
		this.initOperator();
		this.initData();
	}	
}
productList.initPage();