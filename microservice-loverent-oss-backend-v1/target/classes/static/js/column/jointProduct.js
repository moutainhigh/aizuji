 var loanMapping = {
		//id
		tabUniqueId:null,
		
		//关联id
		id:transmitUtil.getQueryString("id"),
		
		initData:function(){
			this.initMaterielData();
			this.initLeftTabData();
			this.initRightTabData();
		},
		
		/**
		 * 初始化操作
		 */
		initOperator:function(){
			/**
			 * 绑定移入操作
			 */
			$("#into").click(function(){
				var _sourceList =  $("#basic_loan_tab").bootstrapTable("getSelections");
				if(_sourceList && _sourceList.length > 0){
					for(var i = 0;i<_sourceList.length;i++){
						//判断要添加的产品是否已经绑定
						var isBind = false;
						var bindIds = "";
						$("[name='materielModelId']").each(function(index){
							if(_sourceList[i].id==$(this).val()){
								isBind = true;
								bindIds = $(this).val();
								return;
							}
						});
						if(isBind){
							transmitUtil.alert("ID为 "+bindIds+" 的产品已经绑定过该栏目了。");
							return false;
						}
					}
					var _tds = $("#selected_loan_tab tbody").find("tr").last().find("td");
					var _lastNum =  (_tds.length == 1)?0:_tds.first().html();
					_lastNum = parseInt(_lastNum);
					var _url = "";
					var _param = {};
					for(var i = 0;i<_sourceList.length;i++){
						var _index=_lastNum++;
						/*$("#selected_loan_tab").bootstrapTable("insertRow",{
							"index":_index, 
							"row":{"materielModelName":_sourceList[i].materielModelName,"materielModelId":_sourceList[i].id}
						});*/
						_param['relationReqList['+i+'].columnId'] = loanMapping.id;
						_param['relationReqList['+i+'].materielModelId'] =  _sourceList[i].id;
						_param['relationReqList['+i+'].materielModelName'] =  _sourceList[i].materielModelName;
						_param['relationReqList['+i+'].sortNum'] = _index;
					}
					_url = "/oss/productColumn/batchAddRelation";
					transmitUtil.ajax(_url,_param,function(data){
						loanMapping.initRightTabData();
					});
					
				}else{
					transmitUtil.alert("请先选中左侧的产品");
					return false;
				}
			});
			
			/**
			 * 绑定回退操作
			 */
			$("#cancel").click(function(){
				loanMapping.back();
			});
		},
		
		/**
		 * 返回操作
		 */
		back:function(){
			_url = "../column/productColumnList.html";
			window.location.href = _url;
		},
		
		/**
		 * 初始化物料数据
		 */
		initMaterielData:function(){
			materiel.initMaterielClass("materielClass");
			materiel.initMaterielBrand("materielBrand");
			//绑定change事件
			$("#materielClass").change(function(){
				var _classVal = $(this).val();
				materiel.initMaterielBrand("materielBrand", _classVal);
			});
			
			$("#materielBrand").change(function(){
				var _brandVal = $(this).val();
				if(_brandVal){
					$('#basic_loan_tab').bootstrapTable('destroy');
					loanMapping.initLeftTabData();
				}
			});
		},
		
		/**
		 * 初始化左边导流库数据
		 */
		initLeftTabData:function(){
			var _columns = [[
                 {  
                	 checkbox:true,
                 },
     			{  
     			    title: '序号',// 标题 可不加
     			    formatter: function (value, row, index) {  
     			        return index+1;  
     			    }  
     			},
     			{
     	              field: 'materielModelName',
     	              title: '物料型号名称',
     	              formatter: function (value, row, index) {  
     	            	 var _hiddenIpt = "<input name='materielModelName' type='hidden' value="+row.materielModelName+">";
	     			     return value + _hiddenIpt;
      			      } 
     	        },
     			{
   	              field: 'id',
   	              title: '型号id',
   	              formatter: function (value, row, index) {  
   	            	  	 var _hiddenIpt = "<input name='id' type='hidden' value="+row.id+">";
	     			     return value + _hiddenIpt;
    			      } 
     			},
			]];
			transmitUtil.tableInit().init("basic_loan_tab","/oss/product/queryAllMaterielModel",function(pageParam){
				var temp = {   // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			            "materielClassId":$("#materielClass").val(),
			            "materielBrandId":$("#materielBrand").val()
			        };
			        return temp;
				},_columns,false);
		},
		
		/**
		 * 初始化右边产品栏目关联关系
		 */
		initRightTabData:function(){
			var _url = "/oss/productColumn/queryRelationList";
			var _param = {};
			_param["columnId"] = this.id;
			transmitUtil.ajax(_url,_param,function(data){
				loanMapping.queryMappingSuccessCb(data);
			});
		},
		
		/**
		 * 产品栏目关联关系回调
		 */
		queryMappingSuccessCb:function(data){
			var _columns = [[
				{  
				    title: '序号',// 标题 可不加
				    formatter: function (value, row, index) {  
				        return index+1;  
				    }  
				},
                 {
                	 field: 'materielModelName',
                	 title: '物料型号名称',
                	 formatter: function (value, row, index) {  
                		 var _hiddenIpt = "<input name='materielModelName' type='hidden' value="+row.materielModelName+">";  
	     			     return value + _hiddenIpt;   
 				    } 
                 },
                 {
                	 field: 'materielModelId',
                	 title: '型号id',
                	 formatter: function (value, row, index) {  
                		 var _hiddenIpt = "<input name='materielModelId' type='hidden' value="+row.materielModelId+">";  
	     			     return value + _hiddenIpt;   
 				    } 
                 }
			]];
			$("#selected_loan_tab").bootstrapTable('destroy');
			transmitUtil.loadTableByJson("selected_loan_tab",null,data,_columns,"id");
		},
		
		
		initPage:function(){
		   $("#menuNav").html(transmitUtil.getMenuHtml("导流配置"));
		   this.initOperator();
		   this.initData();
		}
}
loanMapping.initPage();
 
 
