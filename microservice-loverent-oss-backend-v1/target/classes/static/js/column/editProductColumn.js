var addOrUpdateBanner = {

	/**
	 * id
	 */
	id:transmitUtil.getQueryString("id"),

	/**
	 * resource
	 */
//	resource:transmitUtil.getQueryString("resource"),
	
	
	initOperator:function(){
		//绑定提交事件
		$("#confirm").click(function(){
			var _columnName = $("#columnName").val().trim();
			if(_columnName==null || _columnName.length==0 || _columnName.length>20){
				$(".Error").text("栏目名称不能为空且小于20个字符。");
				return false; 
			}else{
				$(".Error").text();
			}
			var _param = {
				"columnName":_columnName,
		    };
			if(addOrUpdateBanner.id){
				_param["id"] = addOrUpdateBanner.id;
				_url = "/oss/productColumn/updateColumn";
			}else{
				_param["sortNum"] = 0;
				_url = "/oss/productColumn/addColumn";
			}
			transmitUtil.ajax(_url,_param,function(data){
				addOrUpdateBanner.back();
			});
		});
		
		//绑定返回事件
		$("#cancel").click(function(){
			addOrUpdateBanner.back();
		});
	},
	
	back:function(){
		window.location.href = "productColumnList.html";
	},
	
	initData:function(){
		if(this.id){
			//根据id取数据
			var _url = "/oss/productColumn/getColumnById";
			var _param = {"id":this.id};
			transmitUtil.ajax(_url,_param,function(data){
				$("#columnName").val(data.columnName || "");
			});
		}
	},
	
	initPage:function(){
		this.initOperator();
		this.initData();
	}	
}
addOrUpdateBanner.initPage();