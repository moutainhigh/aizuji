/*
* 租后收款 订单详情页js
* 
*/

queryOrderBasicDetail();

// 查询订单基本信息详情
function queryOrderBasicDetail(){
	
	var orderSN = common.getQueryString("rentRecordNo");
	
	common.ajax('/rentCollection/queryOrderDetail/'+orderSN,{},function(data){
		var _record = data;
		$('#td-orderSN').text(_record.orderSN||"");
    	$('#td-realName').html(_record.realName||"");
    	$('#td-identityCard').html(_record.identityCard||"");
    	$('#td-rentEndDate').html(_record.rentEndDate && new Date(_record.rentEndDate).Format("yyyy-MM-dd hh:mm:ss")||"");
    	$('#td-stateDesc').html(_record.stateDesc||"");
    	$('#td-repaymentDate').html(_record.repaymentDate||"");
    	$('#td-performance').html("已结清"+_record.periodOf+"/总期数"+_record.periods||"");
    	
    	// 还款统计
    	$('#span_totalLateFees').text(_record.totalLateFees||"0");
    	$('#span_totalRepaymentRent').text(_record.totalRepaymentRent||"0");
    	$('#span_currentLateFees').text(_record.currentLateFees||"0");
    	$('#span_currentRent').text(_record.currentRent||"0");
   
	},null,null,'post');
}

// 还款计划

var _columns = [[
    {
	    field: 'id',
        title: 'id',
        visible:false
    },
	{
        field: 'periodOf',
        title: '期数'
    },
    {
        field: 'paymentDueDate',
        title: '应还租日',
        formatter:function(value, row, index){
        	 if(value){
         		 return new Date(value).Format("yyyy-MM-dd");
             }else{
           	  	 return '';
             }
        }
    },
    {
        field: 'due',
        title: '应还租金'
    },
    {
    	field: 'actualRepayment',
    	title: '实还租金'
    },
    {
    	field: 'currentBalance',
    	title: '未还租金'
    },
    {
    	field: 'settleDesc',
    	title: '本期是否结清'
    },
    {
        field: 'settleWayDesc',
        title: '本期结清方式'
    },
    {
            field: 'settleDate',
            title: '本期结清时间',
            formatter:function(value, row, index){
            	 if(value){
             		 return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                 }else{
               	  	 return '';
                 }
		    }
    },
    {
    	field: 'overdueDay',
    	title: '本期逾期天数'
    }
]];

// 查询租赁计划
function queryRentSchedule(){
	var orderSN = common.getQueryString("rentRecordNo");
	common.tableInit().init("lease-tab",'/rentCollection/rentSchedule/'+orderSN,function (pageParam) {
		var temp={};
		temp.currPage = pageParam.pageNumber;
		temp.pageSize = pageParam.pageSize;
		return $.param(temp);
	}
	,_columns);	
}
// 自动查询租赁计划
queryRentSchedule();

var repay_columns = [[
    {
	    field: 'id',
        title: 'id',
        visible:false
    },
	{
        field: 'transactionSN',
        title: '对应交易流水号'
    },
    {
        field: 'createOn',
        title: '还款时间',
        formatter:function(value, row, index){
        	 if(value){
         		 return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
             }else{
           	  	 return '';
             }
        }
    },
    {
        field: 'amount',
        title: '还款额(元)'
    },
    {
    	field: 'accountDesc',
    	title: '还款科目'
    },
]];

function queryRepayDetail(){
	var orderSN = common.getQueryString("rentRecordNo");
	common.tableInit().init("repay-grid-tab",'/rentCollection/repmayment/detail/'+orderSN,
		function (pageParam) {
			var temp={};
			temp.currPage = pageParam.pageNumber;
			temp.pageSize = pageParam.pageSize;
			return $.param(temp);
	}
	,repay_columns);	
}

//查询还款明细
queryRepayDetail();

// 手动清偿
function manualSettle(){
	
	var orderSN = common.getQueryString("rentRecordNo");
	
	var param = {};
	
	if(!$('#ipt_transactionSN').val()){
		common.error('交易流水号不能空');
		return false;
	}
	
	if(!$('#ipt_transactionAmount').val()){
		common.error('交易金额不能空');
		return false;
	}
	
	// 交易方式
	var transactionWay = $('input[name="radio_transactionWay"]:checked').val();
	var transactionType = $('input[name="radio_transactionType"]:checked').val();
	
	if(!$('#dtp_input1').val()){
		common.error('交易发起时间不能空');
		return false;
	}
	
	if(!$('#ipt_finishTime').val()){
		common.error('交易完成时间不能空');
		return false;
	}
	
	param.orderSN = orderSN;
	param.transactionSN =  $("#ipt_transactionSN").val();
	param.amount =  $("#ipt_transactionAmount").val();
	param.transactionWay =  transactionWay;
	param.transactionType =  transactionType;
	param.startTime = $('#dtp_input1').val();
	param.finishTime = $('#ipt_finishTime').val();
	common.ajax('/rentCollection/manualSettle',param,function(data){
        // 清空表单
        $("#ipt_transactionSN").val('');
        $("#ipt_transactionAmount").val('');
        $('#dtp_input1').val('');
        $('#ipt_finishTime').val('');
		$('#div_manualSettle').modal('hide');
        queryRepayDetail();
	});
}

// 弹出框
$('#div_lateFeeRemission').on('show.bs.modal',function(e){
	var orderSN = common.getQueryString("rentRecordNo");
	common.ajax('/rentCollection/lateFees/detail/'+orderSN,null,function(data){
		//console.log("查询滞纳金: "+JSON.stringify(data))
		// 总滞纳金
		totalLateFess = data.unclearedLateFees+data.clearedLateFees;
		$('#ipt_total_lateFee').val(totalLateFess);
		// 已还滞纳金
		$('#ipt_clearedLateFees').val(data.clearedLateFees);
	});
});

// 滞纳金减免
function doLateFeeRemission(){
	var totalLateFess = $('#ipt_total_lateFee').val();
	var clearedLateFees = $('#ipt_clearedLateFees').val();
	
	var unclearedLateFees = totalLateFess - clearedLateFees;
	if(unclearedLateFees == 0){
		if(!$('#ipt_transactionAmount').val()){
			common.error('无未还滞纳金,无需减免!');
			return false;
		}
	}
	
	// 减免滞纳金不能超过未还滞纳金
	var remissionAmount = $('#ipt_remission_lateFee').val();
	if(!isNumber(remissionAmount) ){
		common.error('减免金额输入错误!');
		return false;
	}
	
	// 减免原因不能为空
	var desc = $('#tta_desc').val();
	if(!desc ){
		common.error('减免原因不能为空!');
		return false;
	}
	
	var param = {};
	param.orderSN = common.getQueryString("rentRecordNo");
	param.remissionDesc= desc;
	param.remissionAmount= remissionAmount;
	
	common.ajax('/rentCollection/lateFee/remission',param,function(data){
		//console.log("滞纳金减免: "+JSON.stringify(data));
		// 清空表单
		$('#tta_desc').val('');
		$('#ipt_remission_lateFee').val('');
		$('#div_lateFeeRemission').modal('hide');
        queryOrderBasicDetail();

	});
}

function isNumber(val){
    // isNaN()函数 把空串 空格 以及NUll 按照0来处理 所以先去除
    if(val == "" || val ==null){
        return false;
    }
    if(!isNaN(val)){
        return true;
    }else{
        return false;
    }
} 