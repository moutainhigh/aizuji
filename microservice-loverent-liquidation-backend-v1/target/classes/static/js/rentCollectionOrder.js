/**
 * 收款处理js
 */
//加载列表数据
var _columns = [[
            {
            	  field: 'id',
  	              title: 'id',
  	              visible:false
            },
  			{
  	              field: 'rentRecordNo',
  	              title: '订单单号'
  	        },
  	        {
  	              field: 'realName',
  	              title: '客户姓名'
  	        },
  	        {
  	              field: 'idNo',
  	              title: '客户身份证号'
  	        },
  	        {
  	              field: 'signTime',
  	              title: '签约日期',
  	              formatter:function(value, row, index){
  	            	  if(value){
 	            		  return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
	   	              }else{
	   	            	  return '';
	   	              }
	 		      }
  	        },
            {
                  field: 'leaseTerm',
                  title: '期数'
            },
            {
                  field: 'leaseAmount',
                  title: '月租金'
            },
            {
                  field: 'stateDesc',
                  title: '订单状态'
            },
  	        {
  	              field: 'backTime',
  	              title: '租期截止日',
  	              formatter:function(value, row, index){
  	            	  if(value){
  	            		  return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
    	              }else{
    	            	  return '';
    	              }
    	              
	 		      }
  	        },
      	    {
                  field: 'operate',
                  title: '操作',
                //  events: downPayment.operateEvents,
                  formatter:function(value,row,index){
                      var btn = "<a href='./rentCollectionOrderDetail.html?rentRecordNo="+row.rentRecordNo+"'>查看详情</a>";
                      return btn
              }
            }	
    ]];
function initData(){
	common.tableInit().init("grid-tab",'/rentCollection/order/pageList',function (pageParam) {
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
function initParams(){
	if($('#orderSN').val()){
		 searObj.orderSN = $('#orderSN').val();
	}else{
		searObj.orderSN = '';
	}	
	
	if($('#userName').val()){
		 searObj.realName = $('#userName').val();

	}else{
		searObj.realName = '';
	}	
	
	if($('#identityCard').val()){
		searObj.identityCard = $('#identityCard').val();
	}else{
		searObj.identityCard = '';
	}
	
}
