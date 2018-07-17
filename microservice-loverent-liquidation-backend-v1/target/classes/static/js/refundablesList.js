/**
 * 订单退款处理记录js
 */

var _columns = [[
	{
          field: 'orderSN',
          title: '订单单号'
    },
    {
        field: 'createOn',
        title: '退款时间',
        formatter:function(value, row, index){
            return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
        }
    },
    {
          field: 'transactionSN',
          title: '操作人'
    },
    {
    	  field: 'accountDesc',
    	  title: '退款科目'
    },
    {
          field: 'amount',
          title: '退款金额'
    },
    {
        field: 'amount',
        title: '退款原因'
    },
    {
        field: 'operate',
        title: '退款截图',
        events: downPayment.operateEvents,
        formatter:function(value,row,index){
            var btn = "<button class='tuikuan btn btn-default btn-sm' id='btn-payDetail' data-id="+row.id+" data-toggle=\"modal\" data-target=\"#divsure_lateFeeRemission\">预览</button>";
            return btn
        }
    }
]];

function initData(){
	common.tableInit().init("grid-tab",'/refund/page/list',function (pageParam) {
		var temp={};
		temp = searObj;
		temp.currPage = pageParam.pageNumber;
		temp.pageSize = pageParam.pageSize;
		$("#pingzheng").src = pageParam.imgURL;
        //document.getElementById("pingzheng").src = pageParam.imgURL;
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
    searObj.realName = $('#userName').val();

    var stateTypeOptions = $("#select-stateTypeOptions option:selected"); //获取交易状态选中的项
    searObj.accountCode = stateTypeOptions.val();
}


$('#search').click(function(){
	$('#grid-tab').bootstrapTable('destroy');
	initParams();
	initData();
	
});

// $('.btn-group0,.btn-group1').delegate('.btn','click',function(){
// 	$(this).toggleClass('btn-primary').siblings().removeClass('btn-primary');
//
// 	if($(this).hasClass('btn-primary')){
//
// 		if($(this).parent().attr('class') == 'btn-group1'){
// 			// 清算类型
// 			searObj.accountCode = $(this).val();
// 		}
//
// 	}else{
//
// 		if($(this).parent().attr('class') == 'btn-group0'){
// 			searObj.startDate = ''
// 			searObj.endDate = ''
// 			$('#dtp_input1').val('')
// 			$('#dtp_input2').val('')
// 		}
//
// 		if($(this).parent().attr('class') == 'btn-group1'){
// 			// 清算类型
// 			searObj.accountCode = '';
// 		}
// 	}
//
// });