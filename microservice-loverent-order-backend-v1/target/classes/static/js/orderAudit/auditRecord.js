/**
 * 审核记录
 */
var auditRecord = {
	/**
	 * 订单数据
	 */
	orderData:null,
	
	/**
	 * 初始化数据
	 */
	initData:function(){
		var _url = "/orderCreditDetail/queryCustomerCreditList";
		//加载列表数据
		var _columns = [[
		{  
		    title: '序号',// 标题 可不加
		    formatter: function (value, row, index) {  
		        return index+1;  
		    }  
		},
		{
			field: 'orderNo',
			title: '订单号'
		},
		{
			field: 'creditOn',
			title: '审核日期',
			formatter:function(value, row, index){
				return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
			}
		}, 
		{
			field: 'updateName',
			title: '审核人'
		},
          {
              title: '审核方式',
              field: 'creditType',
              formatter:function(value, row, index){
 		          return value && common.orderAudit.creditTypeJson[value];
 		      }
          },
          {
              field: 'creditResult',
              title: '审核结果',
              formatter:function(value, row, index){
 		          return value && common.orderAudit.auditResultJson[value];
 		      }
          },
          {
        	  field: 'refusalReason',
        	  title: '拒绝理由',
              formatter:function(value, row, index){
 		          return value && common.orderAudit.refusalReasonJson[value];
 		      }
          },
          {
        	  field: 'remark',
        	  title: '备注'
          }
	    ]];
		common.tableInit().init("auditRecord_tab",_url,function(pageParam){
			var temp = {   // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				"currPage":pageParam.pageNumber,   // 页面大小
	            "pageSize": pageParam.pageSize,  // 页码
	            "userId":auditRecord.orderData.rentRecord.userId
	        };
	        return $.param(temp);
		},_columns);
	},
	
	/**
	 * 初始化页面
	 */
	initPage:function(orderData){
		this.orderData = orderData;
		this.initData();
	}	
}