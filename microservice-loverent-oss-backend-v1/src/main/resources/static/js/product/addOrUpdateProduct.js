/**
 * 新增或修改产品
 */
var addOrUpdatePdt = {
		
	/**
	 * id
	 */
	id:common.getQueryString("id"),	
	
	/**
	 * 多个折旧费
	 */
	depreciateAmouts:null,
	
	/**
	 * 初始化操作
	 */
	initOperator:function(){
		$("#productTypeSel").change(function(){
			var _this = $(this);
			if(_this.val() == 3){
				$("div .leaseDiv").hide();
			}else{
				$("div .leaseDiv").show();
			}
		});
		
		$("#configDepreciateAmout").click(function(){
			var _leaseTeamVal = $("#leaseTerm option:selected").attr("id").split("_")[1];
			var _depreciateAmoutArr = addOrUpdatePdt.depreciateAmouts && addOrUpdatePdt.depreciateAmouts.split(",") || new Array();
			//组装折旧费数组
			var _jsonArr = new Array();
			var _columns = [[
     			{  
     			    title: '期数',// 标题 可不加
     			    formatter: function (value, row, index) {  
     			        return index+1;  
     			    }  
     			},
     			{
 	                field: 'depreciateAmout',
 	                title: '折旧违约金额',
 	                formatter:function(value,row,index){
 	            	   return "<input name='depreciateAmoutIpt' type='text' class='form-control' value='"+value+"'/>";
     				}
     	        }, 
			 ]];
			for(var i = 0;i<_leaseTeamVal;i++){
				var _json = new Object();
				_json["depreciateAmout"] = _depreciateAmoutArr[i] || "";
				_jsonArr.push(_json);
 			}
			$('#integrated-tab').bootstrapTable('destroy');
			common.loadTableByJson("integrated-tab",null,_jsonArr,_columns,null,false);
			$('#myModal').modal('show');
		});
		
		/**
		 * 租期修改的时候将折旧费清空
		 */
		$('.lease').change(function(){
			addOrUpdatePdt.depreciateAmouts = null;
		});
		
		/**
		 * 更新折旧费
		 */
		$("#updateDepreciateAmout").click(function(){
			var _depreciateAmoutIpts = $("input[name='depreciateAmoutIpt']");
			var _arr = new Array();
			for(var i = 0; i < _depreciateAmoutIpts.length; i++){
				var _obj = $(_depreciateAmoutIpts[i]);
				var _val = _obj.val();
				if(!addOrUpdatePdt.validateNumWithFloat(_val)){
					common.alert("折旧违约金额只能为数值,最多包含两位小数");
					_obj.focus();
					return false;
				}
				_arr.push(_val);
			}
			addOrUpdatePdt.depreciateAmouts = _arr.join(",");
			$('#myModal').modal('hide');
		});
		
		$("#back").click(function(){
			window.history.back();
		});
		
		/**
		 * 提交
		 */
		$("#addOrUpdatePdt").click(function(){
			//校验参数
			var _materielClassId = $("#materielClass").val();
			var _materielBrandId = $("#materielBrand").val();
			var _materielModelId = $("#materielModel").val();
			var _specBatchNo = $("#materielSpec").val();
			var _depreciateAmouts = addOrUpdatePdt.depreciateAmouts;
			var _leaseTermId = $("#leaseTerm").val();
			var _leaseAmount = $("#leaseAmount").val();
			var _premium = $("#premium").val();
			var _floatAmount = $("#floatAmount").val();
			var _signContractAmount = $("#signContractAmount").val();
			var _showAmount = $("#showAmount").val();
			var _sesameCredit = $("#sesameCredit").val();
			var _isDeleted = 0;
			var _productType = $("#productTypeSel").val();
			var _materielNewConfigId = $("#materielNewConfigSel").val();
			if(!_materielClassId || !_materielBrandId || !_materielModelId || !_specBatchNo){
				common.alert("请选择相关物料信息");
				return false;
			}
			if(!addOrUpdatePdt.validateNumWithFloat(_signContractAmount)
					   || !addOrUpdatePdt.validateNumWithFloat(_showAmount)){
				common.alert("金额只能为数值,最多包含两位小数");
				return false;
			}
			if(!_materielNewConfigId){
				common.alert("请选择成色");
				return false;
			}
			var _param = {};
			if(_productType == 1 || _productType == 2){
				if(!addOrUpdatePdt.validateNumWithFloat(_leaseAmount) || !addOrUpdatePdt.validateNumWithFloat(_premium)
						|| !addOrUpdatePdt.validateNumWithFloat(_floatAmount) 
				){
					common.alert("金额只能为数值,最多包含两位小数");
					return false;
				}
				if(!addOrUpdatePdt.validateNum(_sesameCredit)){
					common.alert("芝麻信用值只能为整数");
					return false;
				}
				_param['depreciateAmouts'] = _depreciateAmouts || "";
				_param['leaseTermId'] = _leaseTermId;
				_param['leaseAmount'] = _leaseAmount;
				_param['premium'] = _premium;
				_param['floatAmount'] = _floatAmount;
				_param['sesameCredit'] = _sesameCredit;
			}
			_param['materielClassId'] = _materielClassId;
			_param['materielBrandId'] = _materielBrandId;
			_param['materielModelId'] = _materielModelId;
			_param['specBatchNo'] = _specBatchNo;
			_param['materielNewConfigId'] = _materielNewConfigId;
			_param['signContractAmount'] = _signContractAmount;
			_param['showAmount'] = _showAmount;
			_param['isDeleted'] = _isDeleted;
			_param['productType']=_productType;
			var _url = "/oss/product/addProduct";
			if(addOrUpdatePdt.id){
				_param['id'] = addOrUpdatePdt.id;
				_url = "/oss/product/updateProduct";
			}
			common.ajax(_url,_param,function(data){
				location.href = "productConfig.html";
			});
		});
	},
	
	/**
	 * 初始化数据
	 */
	initData:function(){
		if(this.id){
			$("#menuSpan").text("编辑产品");
			//编辑页面
			this.getPdtData(this.id,this.getPdtDataSuccessCb);
		}else{
			//新增
			$("#menuSpan").text("新增产品");
			this.initLeaseTermData();
			this.initMaterielInfo();
			this.initMaterielNewConfig();
		}
	},
	
	//初始化物料信息
	initMaterielInfo:function(classVal,brandVal,modelVal,specBatchNo){
		materiel.init("materielClass","materielBrand","materielModel","materielSpec",classVal,brandVal,modelVal,specBatchNo);
	},
	
	/**
	 * 初始化新旧程度配置下拉框
	 */
	initMaterielNewConfig:function(materielNewConfig){
		var _url = "/oss/product/queryAllNewConfigs";
		common.ajax(_url,null,function(data){
			var _arr = new Array();
			_arr.push("<option value=''>请选择产品成色</option>");
			var _list = data || new Array();
			for(var i = 0;i < _list.length;i++){
				var _obj = _list[i];
				var _check = "";
				if(materielNewConfig && materielNewConfig == _obj.id){
					_check = "selected=selected";
				}
				_arr.push("<option value='"+_obj.id+"' "+_check+">"+_obj.configValue+"</option>");
			}
			$("#materielNewConfigSel").html(_arr.join(""));
		});
	},
	
	/**
	 * 获取产品数据
	 */
	getPdtData:function(id,cb){
		var _url = "/oss/product/getByIdOrPdtNo";
		var _param = {"id":id};
		common.ajax(_url,_param,function(data){
			cb && cb(data);
		});
	},
	
	/**
	 * 获取产品信息成功回调
	 */
	getPdtDataSuccessCb:function(data){
		var _d = data;
		addOrUpdatePdt.initMaterielInfo(_d.materielClassId,_d.materielBrandId,_d.materielModelId,_d.specBatchNo);
		addOrUpdatePdt.depreciateAmouts = _d.depreciateAmouts;
		addOrUpdatePdt.initLeaseTermData(_d.leaseTermId);
		addOrUpdatePdt.initMaterielNewConfig(_d.materielNewConfigId);
		$("#productTypeSel").val(_d.productType);
		$("#leaseAmount").val(_d.leaseAmount);
		$("#premium").val(_d.premium);
		$("#floatAmount").val(_d.floatAmount);
		$("#signContractAmount").val(_d.signContractAmount);
		$("#showAmount").val(_d.showAmount);
		$("#sesameCredit").val(_d.sesameCredit);
	},
	
	/**
	 * 初始化租期数据
	 */
	initLeaseTermData:function(leaseTermVal){
		var _url = "/oss/product/queryAllProductLeaseTerm";
		common.ajax(_url,null,function(data){
			var _list = data;
			var _arr = new Array();
			for(var i = 0;i < _list.length;i++){
				var _check = "";
				if(leaseTermVal && leaseTermVal == _list[i].id){
					_check = "selected=selected";
				}
				_arr.push("<option id='opt_"+_list[i].termValue+"' value="+_list[i].id+" "+_check+">"+_list[i].termDesc+"</option>");
			}
			$("#leaseTerm").append(_arr.join(""));
		});
	},
	
	/**
	 * 校验浮点数字
	 */
	validateNumWithFloat(num){
		return num.match(/^\d+(?:\.\d{1,2})?$/);
	},

	/**
	 * 校验整数
	 */
	validateNum(num){
		return num.match(/^\+?[1-9][0-9]*$/);
	},
	
	initPage:function(){
		this.initOperator();
		this.initData();
	}	
}
addOrUpdatePdt.initPage();