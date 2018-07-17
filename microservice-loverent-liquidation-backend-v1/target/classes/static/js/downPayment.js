/**
 * 
 */
//加载列表数据
var _columns = [[
            {
            	  field: 'id',
  	              title: '流水Id',
  	              visible:false
            },
  			{
  	              field: 'orderSN',
  	              title: '单号'
  	        },
  	        {
  	              field: 'transactionTypeDesc',
  	              title: '交易类型'
  	        },
  	        {
  	              field: 'transactionWayDesc',
  	              title: '交易方式'
  	        },
  	        {
  	              field: 'transactionSourceDesc',
  	              title: '交易入口'
  	        },
  	        {
  	              field: 'realName',
  	              title: '交易发起人'
  	        },
  	        {
  	              field: 'fromAccount',
  	              title: '交易账号'
  	        },
  	        {
  	              field: 'transactionAmount',
  	              title: '支付金额'
  	        },
  	        {
  	              field: 'createOn',
  	              title: '交易发起时间',
  	              formatter:function(value, row, index){
	 		          return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
	 		      }
  	        },
  	        {
  	              field: 'finishTime',
  	              title: '交易完成时间',
  	              formatter:function(value, row, index){
  	            	  if(value){
  	            		return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
  	            	  }
  	            	  return '';
	 		      }
  	        },
  	      	{
  	              field: 'stateDesc',
  	              title: '交易状态'
  	        },
  	      	{
  	              field: 'failureDesc',
  	              title: '失败原因'
  	        },
  	      	{
  	              field: 'remark',
  	              title: '交易备注'
  	        },
  	        {
  	              field: 'operate',
  	              title: '操作',
  	              events: downPayment.operateEvents,
  	              formatter:function(value,row,index){
  	                  var btn = "<button class='detail btn btn-default btn-sm' id='btn-payDetail' data-id="+row.id+" data-toggle=\"modal\" data-target=\"#payDetail\">查看明细</button>";
	  	              if(row.state == 1){
	  	            	  btn += "<button class='manual btn btn-default btn-sm' id='btn-pay' data-id="+row.id+" data-transactionsn="+row.transactionSN+
	  	                  " data-transactionamount="+row.transactionAmount+" >手动完成支付</button>";
	  	              }
	  	              return btn
     			  }
  	        }
 		]];
initData();
function initData(){
	common.tableInit().init("grid-tab",'/downPayment/transaction/list',function (pageParam) {
		var temp={};
		temp.currPage=pageParam.pageNumber;
		temp.pageSize=pageParam.pageSize;
		return $.param(temp);
	}
	,_columns);	
}

// 支付明细弹出框
/*$('#payDetail').on('show.bs.modal',function(e){
   var id = $('#btn-payDetail').data('id');
   transactionDetail(id);
});*/

// 查询首期款统计
queryDownpaymentStatistics();
queryDownpaymentStatisticsToday();
queryDownpaymentStatisticsYestoday();


// 查询首期款统计
function queryDownpaymentStatistics(){
	common.ajax('/downPayment/downpaymentStatistics',{},function(data){
		$('#span-totalTransactions').html(data.totalTransactions+"笔");
    	$('#span-totalDownpayment').html(data.totalDownpayment+"元");
    	$('#span-totalFirstRent').html(data.totalFirstRent+"元");
    	// 保障金
    	$('#span-totalInsurance').html(data.totalInsurance+"元");
    	$('#span-totalPremium').html(data.totalPremium+"元");
    	//$('#span-pendingDownpayment').html(data.pendingDownpayment+"元");
    	//$('#span-pendingDownpaymentPayments').html(data.pendingDownpaymentPayments+"笔");
	});
}
// 查询今日首期款统计
function queryDownpaymentStatisticsToday(){
	common.ajax('/downPayment/downpaymentStatistics/today',{},function(data){
		$('#span-totalTransactions-today').html(data.totalTransactions+"笔");
		$('#span-totalDownpayment-today').html(data.totalDownpayment+"元");
		$('#span-totalFirstRent-today').html(data.totalFirstRent+"元");
		// 保障金
		$('#span-totalInsurance-today').html(data.totalInsurance+"元");
		$('#span-totalPremium-today').html(data.totalPremium+"元");
		$('#span-receivableDownpayment-today').html(data.receivableDownpayment+"元");
	});
}
// 查询昨日日首期款统计
function queryDownpaymentStatisticsYestoday(){
	common.ajax('/downPayment/downpaymentStatistics/yestoday',{},function(data){
		$('#span-totalTransactions-yestoday').html(data.totalTransactions+"笔");
		$('#span-totalDownpayment-yestoday').html(data.totalDownpayment+"元");
		$('#span-totalFirstRent-yestoday').html(data.totalFirstRent+"元");
		// 保障金
		$('#span-totalInsurance-yestoday').html(data.totalInsurance+"元");
		$('#span-totalPremium-yestoday').html(data.totalPremium+"元");
		$('#span-receivableDownpayment-yestoday').html(data.receivableDownpayment+"元");
	});
}
