var transmitUtil = {
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
                cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true,                   //是否显示分页（*）
                sortable: false,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                queryParams: queryParamCb || this.queryParams,//传递参数（*）
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                pageNumber:1,                       //初始化加载第一页，默认第一页
                pagination:(isPage === false)?false:true,
                pageSize: 10,                       //每页的记录行数（*）
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
                	//兼容不同的数据结构
                	 if(data && data.errCode == 0){
                		 if(data.data.data){
                			 data['rows'] = data.data.data;
                          	data['total'] = data.datatotalNum;
                		 }else{
                			 data['rows'] = data.data;
                          	data['total'] = data.totalNum;
                		 }
                     	return data;
                     }else{
                    	transmitUtil.error('系统异常,请稍后重试.');
                     	return false;
                     }
                },
                onLoadSuccess:function(data){
                	successCb && successCb(data);
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
	loadTableByJson:function(containsId,toolbarId,json,columns,uniqueId){
		$("#"+containsId).bootstrapTable({
			"data":json,
			"striped": true,
			"toolbar":"#"+toolbarId,
			"columns":columns,
            "uniqueId":uniqueId||"ID"
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
            	if(d && d.errCode == 0){
            		successfn(d.data);
            	}else{
            		transmitUtil.error(d.message);
            	}
            },
            error: function(e){
    			transmitUtil.error('系统异常,请稍后重试.');
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
    	},timeout || 3000);
    },
    /**
     * 错误弹出框 默认三秒关闭
     */
    error:function(msg,timeout){
    	var _errorDiolog =BootstrapDialog.show({
            message: msg,
            title: '错误',
            type:BootstrapDialog.TYPE_DANGER
        });
    	setTimeout(function(){
    		_errorDiolog && _errorDiolog.close();
    	},timeout || 3000);
    },
    
    /**
     * 确认弹出框 
     */
    promt:function(msg,yesSuccFun){
    	BootstrapDialog.show({
            message: msg,
            type: BootstrapDialog.TYPE_DEFAULT,
            buttons: [{
                label: '确定',
                cssClass: 'btn-primary',
                autospin: true,
                action: function(dialogRef){
                	dialogRef.close();
                	yesSuccFun && yesSuccFun();
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
     * 上下架弹出框操作
     * @param 上下架标识，下架提示语，用户选择确定回调函数
     */
    updateIsStartPromt:function(isDeleted,unableMsg,yesSuccFun){
    	var _msg = "";
		   var _isDeleted = isDeleted;
		   if(_isDeleted == 1){
			   _isDeleted = 0;
			   yesSuccFun && yesSuccFun(_isDeleted);
		   }else if(_isDeleted === 0){
			   _isDeleted = 1;
			   _msg = unableMsg;
			   transmitUtil.promt(unableMsg,function(){
				   yesSuccFun && yesSuccFun(_isDeleted);
			   });
		   }
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
    
    /**
     * 获取菜单html 名称一样表示选中
     */
    getMenuHtml:function(menuName){
    	var _arr = new Array();
    	_arr.push('<nav class="navbar-default navbar-static-side" role="navigation">');
    	_arr.push(    '<div class="sidebar-collapse">');
    	_arr.push(        '<ul class="nav metismenu" id="side-menu">');
    	_arr.push(           '<li>');
    	_arr.push(                '<a href="javascript:void(0)" style="padding-left:15px;">');
    	_arr.push(                    '<i class="fa fa-th-large"></i>');
    	_arr.push(                    '<span class="nav-label">导流管理</span>');
    	_arr.push(                    '<span class="fa arrow"></span>');
    	_arr.push(                 '</a>');
    	_arr.push(                 '<ul class="nav">');
    	_arr.push(                     '<li>');
    	_arr.push(                         '<a href="../company/companyList.html" style="padding-left:30px;">');
    	_arr.push(                             '<span class="nav-label" name="合作方配置">合作方配置</span>');
    	_arr.push(                             '<span class="fa arrow"></span>');
    	_arr.push(                         '</a>');
    	_arr.push(                     '</li>');
    	_arr.push(                     '<li>');
    	_arr.push(                         '<a href="../loan/loanList.html" style="padding-left:30px;">');
    	_arr.push(                             '<span class="nav-label" name="导流配置">导流配置</span>');
    	_arr.push(                             '<span class="fa arrow"></span>');
    	_arr.push(                         '</a>');
    	_arr.push(                     '</li>');
    	_arr.push(                     '<li>');
    	_arr.push(                         '<a href="../recommend/loanCloudRecommend.html?nav=1" style="padding-left:30px;">');
    	_arr.push(                             '<span class="nav-label" name="贷款云推荐位">贷款云推荐位</span>');
    	_arr.push(                             '<span class="fa arrow"></span>');
    	_arr.push(                         '</a>');
    	_arr.push(                     '</li>');
    	_arr.push(                     '<li>');
    	_arr.push(                         '<a href="../recommend/loan123Recommend.html?nav=1" style="padding-left:30px;">');
    	_arr.push(                             '<span class="nav-label" name="贷款123推荐位">贷款123推荐位</span>');
    	_arr.push(                             '<span class="fa arrow"></span>');
    	_arr.push(                         '</a>');
    	_arr.push(                     '</li>');
    	_arr.push(                     '<li>');
    	_arr.push(                         '<a href="../recommend/loanH5Recommend.html?nav=1" style="padding-left:30px;">');
    	_arr.push(                             '<span class="nav-label" name="H5推荐位">H5推荐位</span>');
    	_arr.push(                             '<span class="fa arrow"></span>');
    	_arr.push(                         '</a>');
    	_arr.push(                     '</li>');
    	_arr.push(                     '<li>');
    	_arr.push(                         '<a href="../monitor/monitorList.html" style="padding-left:30px;">');
    	_arr.push(                             '<span class="nav-label" name="导出监控">导出监控</span>');
    	_arr.push(                             '<span class="fa arrow"></span>');
    	_arr.push(                         '</a>');
    	_arr.push(                     '</li>');
    	_arr.push(                  '</ul>');
    	_arr.push(            '</li>');
    	_arr.push(        '</ul>');
    	_arr.push(    '</div>');
    	_arr.push('</nav>');
    	var _menu = $(_arr.join(""));
    	_menu.find("span[name="+menuName+"]").parent().parent().addClass("active").siblings().removeClass("active");
    	return _menu.html();
    },
    
    /**
	 * 获取前N天到今天的日期
	 */
	getDayArr:function(num){
	    var myDate = new Date();
	    myDate.setDate(myDate.getDate() - num +1);
	    var dateArray = []; 
	    var dateTemp; 
	    var flag = 1; 
	    for (var i = 0; i < num; i++) {
	    	var month = myDate.getMonth() + 1;
		    var strDate = myDate.getDate();
		    if (month >= 1 && month <= 9) {
		        month = "0" + month;
		    }
		    if (strDate >= 0 && strDate <= 9) {
		        strDate = "0" + strDate;
		    }
	        dateTemp = myDate.getFullYear() +'-'+month+"-"+strDate;
	        dateArray.push(dateTemp);
	        myDate.setDate(myDate.getDate() + flag);
	    }
	    return dateArray;
	 },
	 
	 /**
	  * 获取当前日期
	  */
	 getCurrentDay:function(){
	    var date = new Date();
	    var seperator1 = "-";
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
	    return currentdate;
	 },
	 
	 getTime:function(n){
			var now=new Date();
			var year=now.getFullYear();
			//因为月份是从0开始的,所以获取这个月的月份数要加1才行
			var month=now.getMonth()+1;	
			var date=now.getDate();
			var day=now.getDay();
			//判断是否为周日,如果不是的话,就让今天的day-1(例如星期二就是2-1)
			if(day!==0){
				n=n+(day-1);
			}
			else{
				n=n+day;
			}
			if(day){
				//这个判断是为了解决跨年的问题
				if(month>1){
					month=month;
				}
				//这个判断是为了解决跨年的问题,月份是从0开始的
				else{
					year=year-1;
					month=12;
				}
			}
			now.setDate(now.getDate()-n);	
			year=now.getFullYear();
			month=now.getMonth()+1;
			date=now.getDate();
			s=year+"-"+(month<10?('0'+month):month)+"-"+(date<10?('0'+date):date);
			return s;
		}
}
//全局的ajax访问，处理ajax清求时sesion超时 
$.ajaxSetup({
    contentType : "application/x-www-form-urlencoded;charset=utf-8",
    complete : function(XMLHttpRequest, textStatus) {
        var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus，
        if (sessionstatus == "timeout") {
            // 如果超时就处理 ，指定要跳转的页面
            window.location.href="/transmit-web/login.html";
        }
    }
});