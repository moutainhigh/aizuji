Date.prototype.Format = function (fmt) { //author: meizz 
        var o = {
            "M+": this.getMonth() + 1, //月份 
            "d+": this.getDate(), //日 
            "h+": this.getHours(), //小时 
            "m+": this.getMinutes(), //分 
            "s+": this.getSeconds(), //秒 
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
           "S": this.getMilliseconds() //毫秒 
        };
       if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
           for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
var common = {
    /**
     * 表格组件
     * @param 
	 */
		/**
	     * 表格组件
	     * @param 
	     */
	    tableInit:function(){
	        var _oTable = new Object();
	        //初始化Table
	        _oTable.init = function (containId,url,queryParamCb,columns,isPage,successCb) {
	            $('#'+containId).bootstrapTable({
	                url: url,         //请求后台的URL（*）
	                method: 'post',                      //请求方式（*）
	                toolbar: '#toolbar',                //工具按钮用哪个容器
	                striped: true,                      //是否显示行间隔色
	                queryParamsType:"",
	                contentType:"application/x-www-form-urlencoded; charset=UTF-8",
	                cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
	                pagination: true,                   //是否显示分页（*）
	                sortable: false,                     //是否启用排序
	                sortOrder: "asc",                   //排序方式
	                queryParams: queryParamCb || this.queryParams,//传递参数（*）
	                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
	                pageNumber:1,                       //初始化加载第一页，默认第一页
	                pagination:(isPage === false)?false:true,
	                pageSize: 20,                       //每页的记录行数（*）
	                pageList: [10, 15, 20, 25],        //可供选择的每页的行数（*）
	                //search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
	                //strictSearch: true,
	                //showColumns: true,                  //是否显示所有的列
	                //showRefresh: true,                  //是否显示刷新按钮
	                minimumCountColumns: 2,             //最少允许的列数
	                clickToSelect: true,                //是否启用点击选中行
	                //height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
	                uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
	                //showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
	                cardView: false,                    //是否显示详细视图
	                detailView: false,                   //是否显示父子表
	                columns: columns,
	                responseHandler: function (data) {
	                	 if(data && data.errCode == 0 && data.success === true){
	                     	data['rows'] = data.data.data;
	                     	data['total'] = data.data.totalNum;
	                     	return data;
	                     }else{
	                    	common.error('系统异常,请稍后重试.');
	                     	return false;
	                     }
	                },
	                onLoadSuccess:function(data){
	                    successCb && successCb(data);
	                },
	                formatLoadingMessage:function(){
	                    
	                }
	            });
	        };

	        //得到查询的参数
	        _oTable.queryParams = function (params) {
	            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	                limit: params.limit,   //页面大小
	                offset: params.offset  //页码
	            };
	            return temp;
	        };
	        return _oTable;
	    },
	
	/**
	 * 通过json加载table
	 */
	loadTableByJson:function(containsId,toolbarId,json,columns,uniqueId,isPage){
		$("#"+containsId).bootstrapTable({
			"data":json,
			"striped": true,
			"toolbar":"#"+toolbarId,
			"columns":columns,
            "uniqueId":uniqueId||"ID",
            // pagination: true,                   //是否显示分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pagination:(isPage === false) ? false :true,
            pageSize: 20,                       //每页的记录行数（*）
            pageList: [10, 15, 20, 25],        //可供选择的每页的行数（*）
            formatLoadingMessage:function(){

            }
		});
	},
	
	/**
     * ajax封装
     * url 发送请求的地址
     * data 发送到服务器的数据，数组存储，如：{"date": new Date().getTime(), "state": 1}
     * async 默认值: true。默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为 false。
     *       注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
     * type 请求方式("POST" 或 "GET")， 默认为 "GET"
     * dataType 预期服务器返回的数据类型，常用的如：xml、html、json、text
     * successfn 成功回调函数
     * errorfn 失败回调函数
     */
    ajax:function(url, data,successfn, errorfn,async, type, dataType) {
        async = (async==null || async=="" || typeof(async)=="undefined")? "true" : async;
        type = (type==null || type=="" || typeof(type)=="undefined")? "post" : type;
        dataType = (dataType==null || dataType=="" || typeof(dataType)=="undefined")? "json" : dataType;
        data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
        $.ajax({
            type: type,
            async: async,
            data: data,
            url: url,
            dataType: dataType,
            success: function(d){
            	if(d && d.errCode == 0 && d.success === true){
            		successfn(d.data);
            	}else{
            		common.error(d.errMsg);
            	}
            },
            error: function(e){
    			common.error('系统异常,请稍后重试.');
            }
        });
    },
    
    /**
     *通过参数名获取Url里参数值
     */
    getQueryString:function(name) {
    	var reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
        var result = window.location.search.substr(1).match(reg);
        return result?decodeURIComponent(result[2]):null;
    },
    
    /**
     * 提示弹出框 默认三秒关闭
     */
    alert:function(msg,timeout){
    	var _alertDiolog = BootstrapDialog.show({
            message: msg,
            title: '提示'
        });
    	setTimeout(function(){
    		_alertDiolog && _alertDiolog.close();
    	},timeout || 2000);
    },
    /**
     * 错误弹出框 默认三秒关闭
     */
    error:function(msg,timeout){
    	var _errorDiolog =BootstrapDialog.show({
            message: msg,
            title: '错误提示',
            type:BootstrapDialog.TYPE_DANGER
        });
    	setTimeout(function(){
    		_errorDiolog && _errorDiolog.close();
    	},timeout || 2000);
    },
    
    /**
     * 确认弹出框 
     */
    promt:function(msg,yesSuccFun,param){
    	BootstrapDialog.show({
    		title:'提示',
            message: msg,
            type: BootstrapDialog.TYPE_DEFAULT,
            buttons: [{
                label: '确定',
                cssClass: 'btn-primary',
                autospin: true,
                action: function(dialogRef){
                	dialogRef.close();
                	yesSuccFun && yesSuccFun(param);
                }
            }, {
                label: '关闭',
                action: function(dialogRef){
                    dialogRef.close();
                }
            }]
        });
    },
    
    /**
     * 文件上传
     */
    fileUpload:function(){
    	var oFile = new Object();
	    //初始化fileinput控件（第一次初始化）
	    oFile.init = function(ctrlName, uploadUrl,img,successCb) {
	    	var _param = {
		            language: 'zh', //设置语言  
		            uploadUrl: uploadUrl,  //上传地址  
		            showUpload: true, //是否显示上传按钮  
		            showRemove:false,  
		            dropZoneEnabled: false,  
		            showCaption: true,//是否显示标题  
		            allowedPreviewTypes: ['image'],  
	                allowedFileTypes: ['image'],  
	                allowedFileExtensions:  ['png'],
	                maxFileSize : 2000,
		            maxFileCount: 1,
		            validateInitialCount:true,
		            browseClass : "btn btn-success",
	                previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
	                msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
		    };
	    	if(img){
	    		_param['initialPreview'] = "<img src='" + img + "' class='file-preview-image' alt='肖像图片' title='肖像图片'>";
	    	}
	        var control = $('#' + ctrlName);
	        //如果控制器不存在 则直接返回
	        if(control.length == 0){
	        	return;
	        }
	        //初始化上传控件的样式
	        control.fileinput(_param).on("fileuploaded", function(event, data) {  
            	$(".kv-upload-progress").addClass("hide");
            	successCb && successCb(data);
            }); 
	    }
	    return oFile;
    },
    
    timeTemplate:function(type){
        var timeArr = []
        const nowTime = new Date();
        const weekStart = new Date();
        const monthStart = new Date();
        const yesterday = new Date();
        yesterday.setTime(weekStart.getTime() - 3600 * 1000 * 24 * 1);
        weekStart.setTime(weekStart.getTime() - 3600 * 1000 * 24 * 7);
        monthStart.setTime(monthStart.getTime() - 3600 * 1000 * 24 * 30);
        // 昨日
        if(type == 0 ){
            timeArr = [yesterday.Format("yyyy-MM-dd")+' 00:00:00',yesterday.Format("yyyy-MM-dd") + ' 23:59:59']
        }
        // 今日
        if(type == 1 ){
            timeArr = [nowTime.Format("yyyy-MM-dd")+' 00:00:00',nowTime.Format("yyyy-MM-dd") + ' 23:59:59']
        }
        // 最近一周
        if(type == 2 ){
            timeArr = [weekStart.Format("yyyy-MM-dd hh:mm:ss"),nowTime.Format("yyyy-MM-dd hh:mm:ss")]
        }
        // 最近一月
        if(type == 3 ){
            timeArr = [monthStart.Format("yyyy-MM-dd hh:mm:ss"),nowTime.Format("yyyy-MM-dd hh:mm:ss")]
        }
        return timeArr
    },
    
    /**
     * 订单审核相关
     */
    orderAudit:{
    	//电核结果常量
    	phoneAuditResultJson:{
    		"1":"身份不符",
    		"2":"联系方式不符",
    		"3":"地址不符",
    		"4":"无人接听",
    		"5":"信息属实",
    		"6":"其他信息不符",
    		"7":"其他"
    	},
    	
    	//审核结果常量
    	auditResultJson:{
    		"1":"审核拒绝",
    		"2":"电核通过"
    	},
    	
    	
    	//审核建议常量
    	suggestJson:{
    		"1":"建议通过",
    		"2":"建议拒绝",
    		"3":"建议再电联"
    	},
    	
    	//拒绝理由常量
    	refusalReasonJson:{
    		"1":"未满18岁",
    		"2":"严重逾期",
    		"3":"恶意刷单",
    		"4":"地址伪造",
    		"5":"身份伪造",
    		"6":"联系方式不符",
    		"7":"购买信息不符",
    		"8":"其他"
    	},

        //婚姻状况常量
		marriageJson:{
            "0":"未婚",
            "1":"已婚",
            "2":"离婚"
		},

        //教育程度常量
        courselevelJson:{
            "6":"博士及以上",
            "5":"硕士",
            "4":"本科",
            "3":"大专",
            "2":"高中/中专",
            "1":"初中及以下"
		},

    	//职业常量
    	jobJson:{
    		"1":"学生",
    		"2":"工程师",
    		"3":"行政人员",
    		"4":"自由职业",
    		"5":"公务员",
    		"6":"销售",
    		"7":"个体户",
    		"8":"教师",
    		"9":"医生",
    		"10":"护士",
    		"11":"导游",
    		"12":"教练",
            "13":"翻译",
            "14":"产品经理",
            "15":"会计师",
            "16":"商务",
            "17":"快递",
            "18":"企业高管",
            "19":"项目经理",
            "20":"其他"
    	},
    	
    	//行业常量
    	industryJson:{
    		"1":"政府机关",
    		"2":"医院",
    		"3":"教育",
    		"4":"培训",
    		"5":"IT相关",
    		"6":"金融",
    		"7":"保险",
    		"8":"家政",
    		"9":"餐饮",
    		"10":"旅游",
    		"11":"商业",
    		"12":"农业",
    		"13":"其他"
    	},
    	
    	//收入常量
    	revenueJson:{
    		"1":"3K-6K",
    		"2":"6K-10K",
    		"3":"10K-15K",
    		"4":"15K-20K",
    		"5":"20K-30K",
    		"6":"30K以上"
    	},

        //性别常量
    	sexJson:{
    		"1":"男",
    		"2":"女"
    	},

        //有无贷款常量
        loanJson:{
            "1":"有",
            "0":"无"
        },
        
        //审核类型常量
        creditTypeJson:{
        	"1":"一审",
        	"2":"二审",
        	"3":"三审"
        }
    },
    orderdetail:{
    	//物流类型
    	logisticsTypeJson:{
    		"0":"我司发货",
    		"1":"用户归还",
    		"2":"用户维修"
    	},
    	//物流状态
    	logisticsStateJson:{
    		"0":"发货中",
    		"1":"已签收"
    	},
    	rentStateJson:{
    		"0":"提交",
    		"1":"待审批",
    		"2":"已拒绝",
    		"3":"待支付",
    		"4":"待签约",
    		"5":"已取消",
    		"6":"待配货",
    		"7":"待拣货",
    		"8":"待发货",
    		"9":"发货中",
    		"10":"正常履约（已经签收）",
    		"11":"提前解约中",
    		"12":"提前解约",
    		"13":"换机订单状态",
    		"14":"维修订单状态",
    		"15":"已逾期",
    		"16":"归还中",
    		"17":"提前买断",
    		"18":"提前归还（废弃）",
    		"19":"正常买断",
    		"20":"已归还",
    		"21":"提前买断中",
    		"22":"正常买断中",
    		"23":"提前归还中（废弃）",
    		"24":"履约完成",
    		"25":"强制买断中",
    		"26":"强制买断",
    		"27":"定损完成",
    		"28":"强制履约中",
    		"29":"强制履约完成",
    		"30":"强制归还中",
    		"31":"强制定损完成",
    		"32":"强制归还完成",
    		"33":"退货中",
    		"34":"已退货",
    	}
    },
    applet:{
    	//信用级别
    	zmGrade:{
    		"0":"较差",
    		"1":"中等",
    		"2":"良好",
    		"3":"优秀",
    		"4":"极好",
    	},
    },
    
    //初始化Json下拉框
	initJsonSel:function(containId,json,defaultVal){
		var _json = json;
		var _arr = new Array();
		for(var i in _json){
			var _check = "";
			if(defaultVal && defaultVal == i){
				_check = "selected=selected";
			}
			_arr.push("<option value="+i+" "+_check+">"+_json[i]+"</option>");
		}
		$("#"+containId).html("").append(_arr.join(""));
	},

	//初始化Json单选按钮
	initJsonNomalBtn:function(containId,json,defaultVal){
		var _json = json;
		var _arr = new Array();
		for(var i in _json){
			var _css = "";
			if(defaultVal && defaultVal == i){
				_css = "btn-primary";
			}else{
				_css = "btn-default";
			}
			_arr.push('<button type="button" value='+i+' class="btn '+_css+' btn-sm">' + _json[i] +'</button>');
		}
		$("#"+containId).html("").append(_arr.join(""));
	}
}
//全局的ajax访问，处理ajax清求时sesion超时 
$.ajaxSetup({
	contentType : "application/x-www-form-urlencoded;charset=utf-8",
    complete : function(XMLHttpRequest, textStatus) {
    	var data=JSON.parse(XMLHttpRequest.responseText);
        if (data.errCode == 20001) {
            // 如果超时就处理 ，指定要跳转的页面
            window.location.href="/login.html";
        }
    }
});

//获取地址栏参数
function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

function allMaterielClassList(){
	common.ajax('/integration/allMaterielClassList',{},function(data){
		var _obj = data;
		var _arr = new Array();
		_arr.push('<button type="button" class="btn btn-default" value="">'+_obj.typeName+'</button>');
		var _list = _obj.childClassList;
		for(var i = 0;i < _list.length;i++){
			_arr.push('<button type="button" class="btn btn-default" value="'+_list[i].id+'">'+_list[i].typeName+'</button>');
			var _childList = _list[i].childClassList;
			for(var j = 0; j < _childList.length; j++){
				_arr.push('<button type="button" class="btn btn-default" value="'+(_childList[i].id==0?"":_childList[i].id)+'">'+_childList[i].typeName+'</button>');
			}
		}
		$('.btn-group2').html(_arr.join(""));
	});
}


