/**
 * 租后清算记录js
 */

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
          field: 'transactionSN',
          title: '交易流水号'
    },
    {
    	  field: 'accountDesc',
    	  title: '清算类型'
    },
    {
          field: 'createOn',
          title: '清算时间',
          formatter:function(value, row, index){
	          return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
	      }
    },
    {
          field: 'amount',
          title: '清算金额',
    }
]];

function initData(){
	common.tableInit().init("grid-tab",'/repayment/accoutRecord/pageList',function (pageParam) {
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
    	searObj.startDate = $('#dtp_input1').val();
    	searObj.endDate  = $('#dtp_input2').val();
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
        //     // 交易状态
        //     searObj.state = $(this).val();
        // }
    }else{
        if($(this).parent().attr('class') == 'btn-group0'){
            searObj.startDate = ''
            searObj.endDate = ''
            $('#dtp_input1').val('')
            $('#dtp_input2').val('')
        }

        // if($(this).parent().attr('class') == 'btn-group1'){
        //     // 交易状态
        //     searObj.state = '';
        // }
    }
})

function initParams(){
	// 订单号
	if($('#numberInput').val()){
		 searObj.orderSN = $('#numberInput').val();
	}else{
		searObj.orderSN = '';
	}
	
	if($('#dtp_input1').val() && !$('#dtp_input2').val() || $('#dtp_input2').val() && !$('#dtp_input1').val()){
		common.error('请正确填写清算开始和结束时间')
	}
	
	searObj.startDate = $('#dtp_input1').val();
	searObj.endDate = $('#dtp_input2').val();

    var stateTypeOptions = $("#select-stateTypeOptions option:selected"); //获取交易状态选中的项
    searObj.state = stateTypeOptions.val();
	
}


$('#search').click(function(){
	$('#grid-tab').bootstrapTable('destroy');
	initParams();
	initData();
	
})

queryDownpaymentStatistics();
queryDownpaymentStatisticsToday();

function queryDownpaymentStatistics(){
	var sum = 0;
    common.ajax('/rentCollection/revenueStatistics/all',{},function(data){
        $('#span-totalTransactions').html(data.rent+"元");
        $('#span-totalFirstRent').html(data.lateFees+"元");
        $('#span-totalPremium').html(data.buyoutAmount+"元");
        $('#span-totalzhejiu').html(data.depreciationExpense+"元");
        $('#span-totalsale').html(data.saleAmount+"元");
		$("#total-total").html(parseFloat(data.rent+data.lateFees+data.buyoutAmount+data.depreciationExpense+data.saleAmount).toFixed(2)+"元")
    });
}
// 查询今日首期款统计
function queryDownpaymentStatisticsToday(){
    common.ajax('/rentCollection/revenueStatistics/today',{},function(data){
        $('#span-totalTransactions_today').html(data.rent+"元");
        $('#span-totalFirstRent_today').html(data.lateFees+"元");
        $('#span-totalPremium_today').html(data.buyoutAmount+"元");
        $('#span-totalzhejiu_today').html(data.depreciationExpense+"元");
        $('#span-totalsale_today').html(data.saleAmount+"元");
        $("#total-total-today").html(parseFloat(data.rent+data.lateFees+data.buyoutAmount+data.depreciationExpense+data.saleAmount).toFixed(2)+"元")
    });
}

$('.btn-group0,.btn-group1').delegate('.btn','click',function(){
	$(this).toggleClass('btn-primary').siblings().removeClass('btn-primary');
	
	if($(this).hasClass('btn-primary')){
		
		if($(this).parent().attr('class') == 'btn-group1'){
			// 清算类型
			searObj.accountCode = $(this).val();
		}
		
	}else{
		
		if($(this).parent().attr('class') == 'btn-group0'){
			searObj.startDate = ''
			searObj.endDate = ''
			$('#dtp_input1').val('')
			$('#dtp_input2').val('')
		}
		
		if($(this).parent().attr('class') == 'btn-group1'){
			// 清算类型
			searObj.accountCode = '';
		}
	}
	
});