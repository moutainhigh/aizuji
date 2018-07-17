/**
 * 通讯录
 */
var addressList = {
	/**
	 * 订单数据
	 */
	orderData:null,
	
	/**
	 * 初始化数据
	 */
	initData:function(){
		var _userId = this.orderData.rentRecord.userId;
		var _url = "/userInfo/queryContactInfoByPage";
		//加载列表数据
		var _columns = [[
		{  
		    title: '姓名',// 标题 可不加
		    field: 'contactName' 
		},
		{
              field: 'contactPhone',
              title: '电话'
          }
	    ]];
		common.tableInit().init("contact_tab",_url,function(pageParam){
			var temp = {   // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				"currPage":pageParam.pageNumber,   // 页面大小
	            "pageSize": pageParam.pageSize,  // 页码
	            "userId":_userId
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