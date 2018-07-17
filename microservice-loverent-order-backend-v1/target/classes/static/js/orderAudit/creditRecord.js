/**
 * 信用记录
 */
var creditRecord = {
	/**
	 * 订单数据
	 */
	orderData:null,
	
	/**
	 * 来源json
	 */
	channelnosJson:{},
	
	/**
	 * 状态json
	 */
	stateJson:{},
	
	/**
	 * 初始化数据
	 */
	initData:function(){
		//初始化订单统计数据
		this.initOrderStatisticData();
		
		//初始化订单审核通过数量
		this.initAuditPassCountData();
		
		//初始化订单来源和状态
		this.initChannelNos();
		this.initState();
	
		//初始化订单列表数据
		this.initOrderListData();
	},
	
	/**
	 * 初始化订单审核通过数量
	 */
	initAuditPassCountData:function(){
		var _url = "/orderCreditDetail/queryCountByCustomerCreditList";
		var _param = {"userId":creditRecord.orderData.rentRecord.userId};
		_param['creditType'] = 3;
		_param['creditResult'] = 2;
		common.ajax(_url,_param,function(data){
			$("#creditRecord_pass_count").html(data || 0);
		});
	},
	
	/**
	 * 初始化订单统计数据
	 */
	initOrderStatisticData:function(){
		var _url = "/integration/queryRentRecordStatusStatistics";
		var _param = {"userId":creditRecord.orderData.rentRecord.userId};
		common.ajax(_url,_param,function(data){
			var _list = data;
			var _total = 0;
			var _map = {};
			for(var i = 0; i < _list.length; i++){
				var _obj = _list[i];
				_total += (_obj.orderCount || 0);
				_map[_obj.state] = _obj.orderCount;
			}
			$("#creditRecord_apply_count").html(_total || 0);
			$("#creditRecord_refusal_count").html(_map[2] || 0);
			$("#creditRecord_recover_count").html(_map[20] || 0);
			$("#creditRecord_buyOut_count").html((_map[17] || 0) + (_map[19] || 0));
			$("#creditRecord_rescind_contract_count").html((_map[11] || 0) + (_map[18] || 0));
			$("#creditRecord_coming_count").html(_map[10] || 0);
		});
	},

	/**
	 * 初始化订单列表数据
	 */
	initOrderListData:function(){
		//加载列表数据
		var _columns = [[
  			{
  	              field: 'rentRecord.rentRecordNo',
  	              title: '订单编号'
  	        },
  	        {
  	        	field: 'rentRecordExtends.phoneNum',
  	        	title: '手机号'
  	        },
  	        {
  	              field: 'rentRecordExtends.materielClassName',
  	              title: '商品类别'
  	        },
  	        {
  	              field: 'rentRecordExtends.materielModelName',
  	              title: '商品型号'
  	        },
  	        {
	              field: 'rentRecordExtends.materielSpecName',
	              title: '规格',
	              formatter:function(value, row, index){
		              return value && value.replace(new RegExp(",","gm"),"&nbsp;&nbsp;");
		          }
	        },
  	        {
  	              field: 'rentRecordExtends.leaseTerm',
  	              title: '租期',
  	              formatter:function(value, row, index){
 		              return value && (value+"个月");
 		          }
  	        },
  	        {
  	              field: 'rentRecordExtends.leaseAmount',
  	              title: '月租金',
            	  formatter:function(value, row, index){
 		              return value && (value+"元");
 		          }
  	        },
  	        {
	              field: 'rentRecordExtends.floatAmount',
	              title: '产品溢价'
	        },
	        {
	        	field: 'rentRecordExtends.premium',
	        	title: '保险费用'
	        },
	        {
	        	field: 'rentRecordExtends.signContractAmount',
	        	title: '签约价值'
	        },
  	        {
  	              field: 'rentRecord.applyTime',
  	              title: '申请时间',
  	              formatter:function(value, row, index){
	 		          return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
	 		      }
  	        },
  	        {
  	              field: 'rentRecord.state',
  	              title: '订单状态',
  	              formatter:function(value, row, index){
	 		          return creditRecord.stateJson[value];
	 		      }
  	        },
  	        {
  	              field: 'rentRecord.channelNo',
  	              title: '订单来源',
  	              formatter:function(value, row, index){
	 		          return creditRecord.channelnosJson[value];
	 		      }
  	        },
  	        {
	        	field: 'performanceCount',
	        	title: '履约次数'
	        },
	        {
	        	field: 'breachCount',
	        	title: '违约次数'
	        },
 		]];
		common.tableInit().init("creditRecord_tab",'/integration/queryRentListAndUserContract',function (pageParam) {
    		var temp={};
    		temp['rentRecord.userId']=creditRecord.orderData.rentRecord.userId;
    		temp.currPage=pageParam.pageNumber;
    		temp.pageSize=pageParam.pageSize;
    		return $.param(temp);
		}
    	,_columns);	
	},
	
	/**
	 * 初始化订单来源 （同步方法）
	 */
	initChannelNos:function(){
		common.ajax('/common/channelNos',{},function(data){
        	var _list = data;
			for(var i = 0;i < _list.length;i++){
				creditRecord.channelnosJson[_list[i].code]=_list[i].message;
			}
        },null,false);
	},
	
	/**
	 * 初始化订单状态（同步方法）
	 */
	initState:function(){
		common.ajax('/common/backRentStates',{},function(data){
			var _list = data;
			for(var i = 0;i < _list.length;i++){
				creditRecord.stateJson[_list[i].code]=_list[i].message;
			}
		},null,false);
	},
	
	/**
	 * 初始化页面
	 */
	initPage:function(orderData){
		this.orderData = orderData;
		this.initData();
	}		
}