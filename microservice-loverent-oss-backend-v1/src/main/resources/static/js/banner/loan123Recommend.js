/**
 * 贷款云推荐位js
 */
var loan123Recommend = {
	
	/**
	 * 初始化操作
	 */
	initOperator:function(){
		// 绑定导航点击事件
		$(".nav").find("li").click(function(){
			var _this = $(this);
			// 如果当前是选中的样式 页面不改变
			if(!_this.hasClass("active")){
				_this.addClass("active").siblings().removeClass("active");
				var _id = _this.attr("id").split("_")[1];
				$("#fourthDiv").show().siblings().hide();
				var _param = {
					"tabId":"loan123MarkingBanner_tab",
					"addBtnId":"loan123MarkingBanner_addBtn",
					"sortBtnId":"loan123MarkingBanner_sortBtn",
					"resource":"3"
				};
				bannerList.initPage(_param);
			}
		});
	},
	
	/**
	 * 初始化数据
	 */
	initData:function(){
		var _id = this.nav || 1;
		$("#nav_"+4).trigger("click");
	},
	
	/**
	 * 初始化页面
	 */
	initPage:function(){
		this.initOperator();
		this.initData();
	}	
}
loan123Recommend.initPage();