/**
 * 滞纳金减免记录js
 */
//加载列表数据
var _columns = [[
            {
            	  field: 'id',
  	              title: 'id',
  	              visible:false
            },
  			{
  	              field: 'orderSN',
  	              title: '订单单号'
  	        },
  	        {
  	              field: 'userRealName',
  	              title: '承租人'
  	        },
  	        {
  	              field: 'createOn',
  	              title: '减免时间',
  	              formatter:function(value, row, index){
  	              	if(value){
                        return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
					}else{
  	              		return ''
					}
	 		      }
  	        },
            {
                  field: 'operator',
                  title: '操作人'
            },
            {
                  field: 'amount',
                  title: '减免金额'
            },
            {
                  field: 'remissionDesc',
                  title: '减免原因'
            }
    ]];
function initData(){
	common.tableInit().init("grid-tab",'/remissionLog/queryList',function (pageParam) {
		var temp={};
		temp = searObj;
		temp.currPage = pageParam.pageNumber;
		temp.pageSize = pageParam.pageSize;
		return $.param(temp);
	}
	,_columns);	
}

// 查询
$('#search').click(function(){
	$('#grid-tab').bootstrapTable('destroy');
	initParams();
	initData();
})
var searObj = {};
function cDayFunc (){
    $('.btn-group0 .btn').removeClass('btn-primary')
    setTimeout(function(){
        searObj.startDate = $('#dtp_input1').val();
        searObj.endDate  = $('#dtp_input2').val();
    },200)
}
function initParams(){
	if($('#numberInput').val()){
		 searObj.orderSN = $('#numberInput').val();
	}else{
		searObj.orderSN = '';
	}

    if($('#dtp_input1').val() && !$('#dtp_input2').val() || $('#dtp_input2').val() && !$('#dtp_input1').val()){
        common.error('请正确填写减免开始和结束时间')
    }
    searObj.startDate = $('#dtp_input1').val();
    searObj.endDate = $('#dtp_input2').val();

    if($('#userName').val()){
        searObj.operator = $('#userName').val();
    }else{
        searObj.operator = '';
    }

    if($('#zurenName').val()){
        searObj.userRealName = $('#zurenName').val();
    }else{
        searObj.userRealName = '';
    }
}
