var updateProductModel = {
	/**
	 * 文件图片路径
	 */
	filePath:null,
	/**
	 * id
	 */
	id:transmitUtil.getQueryString("id"),
	
	columnId:transmitUtil.getQueryString("columnId"),

	initOperator:function(){
		//绑定提交事件
		$("#save").click(function(){
			if(updateProductModel.filePath == null){
				$("#imgErrorDiv").text('请先上传图片');
				return;
			}
			var _isDeleted = $("#isDeleted").val();
			if(_isDeleted==null || _isDeleted==""){
				$("#isDeletedErrorDiv").text('请选择配置类型');
				return;
			}
			var _priceValueObj = $("#priceValue");
			var _priceValue = _priceValueObj.val().trim();
			if(_priceValue==null || _priceValue.length==0 ||!updateProductModel.isMatchReg(_priceValue,/^[1-9]\d*$/)){
				$("#priceValueErrorDiv").text("文案为整型。");
				return;
			}
			var _param = {
				"isDeleted":_isDeleted,
				"photoUrl":updateProductModel.filePath,
				"price":_priceValue,
		    };
			if(updateProductModel.id){
				_param["id"] = updateProductModel.id;
				_url = "/oss/productColumn/updateRelation";
			}
			transmitUtil.ajax(_url,_param,function(data){
				updateProductModel.back();
			});
		});
		
		
		//绑定返回事件
		$("#cancel").click(function(){
			updateProductModel.back();
		});
	},
	
	isMatchReg:function(jqObj,reg){
    	var _val = jqObj;
    	if(_val && reg.test(_val)){
    		return true;
    	}
    	return false;
    },
	
	back:function(){
		window.location.href = "productModelList.html?id="+updateProductModel.columnId;
	},
	
	initData:function(){
		var _fileUpload = transmitUtil.fileUpload();
		var _fileUploadUrl = "/oss/productColumn/uploadImg";
		var _fileUploadContain ="imageUrl";
		var _fileUploadSuccessCb = function(fileData){
			updateProductModel.filePath = fileData.response.data;
		};
		//初始化文件上传
		_fileUpload.init(_fileUploadContain,_fileUploadUrl ,null,_fileUploadSuccessCb);
		if(this.id){
			//根据id取数据
			var _url = "/oss/productColumn/getRelation";
			var _param = {"id":this.id};
			transmitUtil.ajax(_url,_param,function(data){
				$("#priceValue").val(data.price || "");
				updateProductModel.filePath = data.photoUrl;
				//选中默认的配置类型
				$("#isDeleted").children().each(function(){
				    if($(this).val()==data.isDeleted){
				    	$(this).attr("selected",true);
				    }
				  });
				//初始化文件上传
				$("#imageUrl").fileinput('destroy');
				_fileUpload.init(_fileUploadContain,_fileUploadUrl,updateProductModel.filePath,_fileUploadSuccessCb);
			});
		}
	},
	initPage:function(){
		this.initOperator();
		this.initData();
	}	
}
updateProductModel.initPage();