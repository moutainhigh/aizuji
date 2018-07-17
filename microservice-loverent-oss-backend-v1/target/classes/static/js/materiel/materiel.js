/**
 * 物料信息（处理四级联动）
 */
var materiel = {
	/**
	 * 初始化物料大类信息列表
	 */
	initMaterielClass:function(classObjId,materielClassVal,cb){
		var _url = "/oss/materiel/queryAllMaterielClassList";
		common.ajax(_url,null,function(data){
			var _obj = data;
			var _arr = new Array();
			_arr.push("<option value=''>"+_obj.typeName+"</option>");
			var _list = _obj.childClassList || new Array();
			for(var i = 0;i < _list.length;i++){
				var _check = "";
				if(materielClassVal && materielClassVal == _list[i].id){
					_check = "selected=selected";
				}
				_arr.push("<option value="+_list[i].id+" "+_check+">"+_list[i].typeName+"</option>");
				var _childList = _list[i].childClassList;
				for(var j = 0; j < _childList.length; j++){
					if(materielClassVal && materielClassVal == _childList[i].id){
						_check = "selected=selected";
					}
					_arr.push("<option value="+_childList[j].id+" "+_check+">"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+_childList[j].typeName+"</option>");
				}
			}
			$("#"+classObjId).html("").append(_arr.join(""));
			cb && cb();
		});
	},
	
	/**
	 * 初始化物料品牌
	 */
	initMaterielBrand:function(brandObjId,materielClassVal,brandVal,cb){
		if(materielClassVal){
			var _url = "/oss/materiel/queryMaterielBrandListByClassId";
			var _param = {"classId":materielClassVal};
			common.ajax(_url,_param,function(data){
				var _list = data;
				var _arr = new Array();
				_arr.push("<option value=''>请选择品牌</option>");
				for(var i = 0;i < _list.length;i++){
					var _check = "";
					if(brandVal && brandVal == _list[i].id){
						_check = "selected=selected";
					}
					_arr.push("<option value="+_list[i].id+" "+_check+">"+_list[i].brandName+"</option>");
				}
				$("#"+brandObjId).html("").append(_arr.join(""));
				cb && cb();
			});
		}
	},
	
	/**
	 * 初始化物料型号
	 */
	initMaterielModel:function(modelObjId,materielBrandVal,modelVal,cb){
		if(materielBrandVal){
			var _url = "/oss/materiel/queryMaterielModelListByBrandId";
			var _param = {"brandId":materielBrandVal};
			common.ajax(_url,_param,function(data){
				var _list = data;
				var _arr = new Array();
				_arr.push("<option value=''>请选择型号</option>");
				for(var i = 0;i < _list.length;i++){
					var _check = "";
					if(modelVal && modelVal == _list[i].id){
						_check = "selected=selected";
					}
					_arr.push("<option value="+_list[i].id+" "+_check+">"+_list[i].materielModelName+"</option>");
				}
				$("#"+modelObjId).html("").append(_arr.join(""));
				cb && cb();
			});
		}
	},
	
	/**
	 * 初始化物料规格
	 */
	initMaterielSpec:function(specObjId,modelVal,specBatchNo,cb){
		if(modelVal){
			var _url = "/oss/materiel/queryMaterielSpecListByModelId";
			var _param = {"modelId":modelVal};
			common.ajax(_url,_param,function(data){
				var _list = data;
				var _arr = new Array();
				_arr.push("<option value=''>请选择规格</option>");
				for(var i = 0;i < _list.length;i++){
					var _check = "";
					if(specBatchNo && specBatchNo == _list[i].specBatchNo){
						_check = "selected=selected";
					}
					_arr.push("<option value="+_list[i].specBatchNo+" "+_check+">"+_list[i].specValues.replace(new RegExp(",","gm"),"&nbsp;&nbsp;")+"</option>");
				}
				$("#"+specObjId).html("").append(_arr.join(""));
				cb && cb();
			});
		}
	},
   
	/**
	 * @param classObjId 物料分类控件id
	 * @param brandObjId 物料品牌控件id
	 * @param modelObjId 物料模型控件id
	 * @param specObjId 物料规格控件id
	 * @param classVal 物料分类默认显示值
	 * @param brandVal 物料品牌默认显示值
	 * @param modelVal 物料型号默认显示值
	 * @param specBatchNo 物料规格批次默认显示值
	 */
	init:function(classObjId,brandObjId,modelObjId,specObjId,classVal,brandVal,modelVal,specBatchNo){
		//初始化数据
		this.initData(classObjId,brandObjId,modelObjId,specObjId,classVal,brandVal,modelVal,specBatchNo);
	
		//绑定change事件
		$("#"+classObjId).change(function(){
			var _classVal = $(this).val();
			materiel.initMaterielBrand(brandObjId, _classVal);
		});
		
		$("#"+brandObjId).change(function(){
			var _brandVal = $(this).val();
			materiel.initMaterielModel(modelObjId, _brandVal);
		});
		
		$("#"+modelObjId).change(function(){
			var _modelVal = $(this).val();
			materiel.initMaterielSpec(specObjId, _modelVal);
		});
	},
	
	/**
	 * 初始化数据
	 */
	initData:function(classObjId,brandObjId,modelObjId,specObjId,classVal,brandVal,modelVal,specBatchNo){
		this.initMaterielClass(classObjId, classVal);
		this.initMaterielBrand(brandObjId, classVal, brandVal);
		this.initMaterielModel(modelObjId, brandVal, modelVal);
		this.initMaterielSpec(specObjId, modelVal, specBatchNo);
	}
	
}