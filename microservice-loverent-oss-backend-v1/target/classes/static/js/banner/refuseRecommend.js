var refuseRecommend = {
	initOperator:function(){
		/**
		 * 绑定编辑推荐位关联导流事件
		 */
		$("button[id='editLoanMappingBtn']").click(function(){
			var _mappingFlag = 2;
			var _type = 3;
			window.location.href = "../loan/loanMapping.html?mappingFlag="+_mappingFlag+"&type="+_type;
		});
	},	
		
	/**
	 * 初始化数据
	 */
	initData:function(param){
		var _url = "/transmit-web/recommend/queryRecommendMappingInfo";
		var _param = {"mappingFlag":2};
		transmitUtil.ajax(_url,_param,function(data){
            var _loanListData = data.loanRespList;
            _columns = [[
         	 			{  
         	 			    title: '序号',// 标题 可不加
         	 			    formatter: function (value, row, index) {  
         	 			        return index+1;  
         	 			    }  
         	 			},
         	 			{
         	 	              field: 'name',
         	 	              title: '名称'
         	 	        },
         	 	        {
         	 	              field: 'linkUrl',
         	 	              title: '链接'
         	 	        }
         	 	    ]];
            transmitUtil.loadTableByJson("loan_tab","refuseRecommendToolbar",_loanListData,_columns,"id");
		});
	},
	
	/**
	 * 初始化页面
	 */
	initPage:function(){
		$("#menuNav").html(transmitUtil.getMenuHtml("贷款123推荐位"));
		this.initOperator();
		this.initData();
	}	
}