var addOrUpdateBanner = {
	/**
	 * 文件图片路径
	 */
	filePath:null,
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
			if(addOrUpdateBanner.filePath != null){
		        $("#imgErrorDiv").text('');
			}else{
				$("#imgErrorDiv").text('请先上传图片');
				return;
			}
			var _linkUrlObj = $("#linkUrl");
			var _setResource = $("#setResource").val();
			if(_setResource==null || _setResource==""){
				$("#reErrorDiv").text('请选择配置类型');
				return;
			}else{
				$("#reErrorDiv").text('');
			}
			var _linkUrl = _linkUrlObj.val().trim();
			if(_linkUrl==null || _linkUrl.length==0 ||_linkUrl.length>200){
				$("#linkUrlError").text($("#configValue").text()+"不能为空且小于200个字符。");
				return false;
			}else{
				$("#linkUrlError").text();
			}
			if(_setResource==1 && _linkUrl && (/http[s]?:\/\/[^\s'"]+$/.test(_linkUrl) == false)){
				_linkUrlObj.next('div').text("链接地址必须http://或者https://开头");
				_linkUrlObj.focus();
		        return false;
			}else{
				_linkUrlObj.next('div').text('');
			}
			var _param = {
				"resource":_setResource,
				"bannerImgUrl":addOrUpdateBanner.filePath,
				"linkValue":_linkUrl,
		    };
			if(addOrUpdateBanner.id){
				_param["id"] = addOrUpdateBanner.id;
				_url = "/oss/banner/update";
			}else{
				_param["sortNum"] = 0;
				_url = "/oss/banner/add";
			}
			transmitUtil.ajax(_url,_param,function(data){
				addOrUpdateBanner.back();
			});
		});
		
		//绑定select选中事件
		var $select = $("#setResource");
		$select.change(function(){
			switch ($(this).val()) {
			case "1":
				$("#configValue").text("配置链接");
				$("#linkUrl").attr("placeholder","如：http://www.aizuji.com");
				break;
			case "2":
				$("#configValue").text("配置商品");
				$("#linkUrl").attr("placeholder","");
				break;
			case "3":
				$("#configValue").text("配置商品");
				$("#linkUrl").attr("placeholder","");
				break;
			}
		});
		
		//绑定返回事件
		$("#cancel").click(function(){
			addOrUpdateBanner.back();
		});
	},
	
	back:function(){
		window.location.href = "bannerList.html";
	},
	
	initData:function(){
		var _fileUpload = transmitUtil.fileUpload();
		var _fileUploadUrl = "/oss/banner/uploadImg";
		var _fileUploadContain ="imageUrl";
		var _fileUploadSuccessCb = function(fileData){
			addOrUpdateBanner.filePath = fileData.response.data;
		};
		//初始化文件上传
		_fileUpload.init(_fileUploadContain,_fileUploadUrl ,null,_fileUploadSuccessCb);
		if(this.id){
			//根据id取数据
			var _url = "/oss/banner/getById";
			var _param = {"id":this.id};
			transmitUtil.ajax(_url,_param,function(data){
				$("#linkUrl").val(data.linkValue || "");
				addOrUpdateBanner.filePath = data.bannerImgUrl;
				//选中默认的配置类型
				$("#setResource").children().each(function(){
				    if($(this).val()==data.resource){
				    	$(this).attr("selected",true);
				    	switch ($(this).val()) {
						case "1":
							$("#configValue").text("配置链接");
							$("#linkUrl").attr("placeholder","如：http://www.aizuji.com");
							break;
						case "2":
							$("#configValue").text("配置商品");
							$("#linkUrl").attr("placeholder","");
							break;
						case "3":
							$("#configValue").text("配置商品");
							$("#linkUrl").attr("placeholder","");
							break;
						}
				    }
				  });
				//初始化文件上传
				$("#imageUrl").fileinput('destroy');
				_fileUpload.init(_fileUploadContain,_fileUploadUrl,addOrUpdateBanner.filePath,_fileUploadSuccessCb);
			});
		}
	},
	initPage:function(){
		/*$("#menuNav").html(transmitUtil.getMenuHtml("贷款123推荐位"));*/
		this.initOperator();
		this.initData();
	}	
}
addOrUpdateBanner.initPage();