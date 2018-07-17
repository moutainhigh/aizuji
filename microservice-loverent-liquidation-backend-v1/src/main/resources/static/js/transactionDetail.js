/**
 * 交易明细
 */
//表格标题
var _columns = [[
	{
		  field: 'id',
          title: '流水Id',
          visible:false
    },
	{
          field: 'orderSN',
          title: '订单单号'
    },
    {
        field: 'transactionTypeDesc',
        title: '交易类型'
    },
    {
          field: 'realName',
          title: '客户姓名'
    },
    {
    	field: 'phone',
    	title: '客户手机号'
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
        	  }else {
        		  return '';
        	  }
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

//common.loadTableByJson("integrated-tab",null,jsonArray,_columns,null,true)

// 实际开发请调用一下方法
function initData(){
	common.tableInit().init("grid-tab",'/downPayment/transaction/list',function (pageParam) {
		var temp={};
		temp = searObj;
		temp.currPage = pageParam.pageNumber;
		temp.pageSize = pageParam.pageSize;
		return $.param(temp);
		
	 },_columns);
}	
var searObj = {};
function cDayFunc (){
	$('.btn-group0 .btn').removeClass('btn-primary')
	setTimeout(function(){
    	searObj.startDate = $('#dtp_input1').val()
    	searObj.endDate  = $('#dtp_input2').val()
	},200)
}
$('.btn-group0,.btn-group1').delegate('.btn','click',function(){
	$(this).toggleClass('btn-primary').siblings().removeClass('btn-primary');
	if($(this).hasClass('btn-primary')){
		//下单时间
		if($(this).parent().attr('class') == 'btn-group0'){
			searObj.startDate = common.timeTemplate($(this).index())[0]
			searObj.endDate = common.timeTemplate($(this).index())[1]
			$('#dtp_input1').val(searObj.startDate)
			$('#dtp_input2').val(searObj.endDate)
		}
		
		// if($(this).parent().attr('class') == 'btn-group1'){
		// 	// 交易状态
		// 	searObj.state = $(this).val();
		// }
	}else{
		if($(this).parent().attr('class') == 'btn-group0'){
			searObj.startDate = ''
			searObj.endDate = ''
			$('#dtp_input1').val('')
			$('#dtp_input2').val('')
		}
		
		// if($(this).parent().attr('class') == 'btn-group1'){
		// 	// 交易状态
		// 	searObj.state = '';
		// }
	}
})

$('#search').click(function(){
	$('#grid-tab').bootstrapTable('destroy');
	initParams();
	initData();
	// 重置表单
	//searObj = {};
})

function initParams(){
	if($('#numberInput').val()){
		 searObj.orderSN = $('#numberInput').val();
	}else{
		searObj.orderSN = '';
	}	
	
	if($('#userName').val()){
		searObj.realName = $('#userName').val();
	}else{
		searObj.realName = '';
	}
	if(!/^1[34578]\d{9}$/.test($('#phoneNumber').val()) && $('#phoneNumber').val()){
		common.error('手机号码格式错误，请重新输入')
		$('#idCard').val('')
	}
	if($('#phoneNumber').val()){
		searObj.phone = $('#phoneNumber').val();
	}else{
		searObj.phone = '';
	}
	/*if(!/^[0-9]*$/.test($('#cardNmumber').val()) && $('#cardNmumber').val()){
		common.error('交易账号格式错误，请重新输入')
		$('#cardNmumber').val('')
	}*/
	if($("#ipt-fromAccount").val()){
		searObj.fromAccount = $("#ipt-fromAccount").val();
	}else{
		searObj.fromAccount = '';
	}
	if($('#dtp_input1').val() && !$('#dtp_input2').val() || $('#dtp_input2').val() && !$('#dtp_input1').val()){
		common.error('请正确填写下单开始和结束时间')
	}
    searObj.startDate = $('#dtp_input1').val();
    searObj.endDate = $('#dtp_input2').val();
	
	var options = $("#select-transactionWay option:selected"); //获取交易状态选中的项 
	searObj.transactionWay = options.val();
	
	var transactionTypeOptions = $("#select-transactionType option:selected"); //获取交易状态选中的项 
	searObj.transactionType = transactionTypeOptions.val();

    var stateTypeOptions = $("#select-stateTypeOptions option:selected"); //获取交易状态选中的项
    searObj.state = stateTypeOptions.val();
	
}

//总计
queryDownpaymentTotal();
function queryDownpaymentTotal(){
    common.ajax('/rentCollection/transactionRecord/statistics',{},function(data){
        $('#totalNum').html(data.count);
        $('#totalMoney').html(data.amount);
    });
}


