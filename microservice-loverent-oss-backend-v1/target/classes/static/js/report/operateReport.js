var operateReport = {
    tabHelper:common.tableInit(),
    initPage:function(){
        this.bindDateInput();
        this.bindQuicklySetDate();
        this.initTabData();
    },
    //绑定时间控件
    bindDateInput:function(){
        var dateConfig = {
            format: 'yyyy-mm-dd',
            autoclose:true,
            startView:2,
            minView:2,
            maxView:2,
            language:'zh-CN'
        }
        $('#startDate').datetimepicker(dateConfig);
        $('#endDate').datetimepicker(dateConfig);
    },
    //绑定昨天今天本周上周事件
    bindQuicklySetDate:function(){
        $("#quicklySetDateGroup").on('click',function(ev){
            var ev = ev || window.event;
            var target = ev.target || ev.srcElement;
            var type = $(target).attr('item-type');
            var startDate = '';
            var endDate = '';

            switch (type){
                case 'yesterday':
                    var yesterday = common.getDayArr(2)[0];
                    startDate = yesterday;
                    endDate = yesterday;
                    break;
                case 'today':
                    var currentDay = common.getCurrentDay();
                    startDate = currentDay;
                    endDate = currentDay;
                    break;
                case 'week':
                    var weekDays = common.getDayArr(new Date().getDay());
                    startDate = weekDays[0];
                    endDate = common.getCurrentDay();
                    break;
                case 'lastWeek':
                    var lastWeekDays = common.getDayArr(new Date().getDay() + 7);
                    startDate = lastWeekDays[0];
                    endDate = lastWeekDays[6];
                    break;
            }

            $("#startDate").val(startDate);
            $("#endDate").val(endDate);
        })
    },
    //初始化表格
    initTabData:function(){
        var _columns = [[
            {field: 'productNo',title: '报表时间'},
            {field: 'productNo',title: '渠道标识'},
            {field: 'productNo',title: '应用类型'},
            {field: 'productNo',title: '注册数'},
            {field: 'productNo',title: '申请数'},
            {field: 'productNo',title: '拒绝数'},
            {field: 'productNo',title: '通过数'},
            {field: 'productNo',title: '签约数'},
            {field: 'productNo',title: '出机数'}
		]];
		operateReport.tabHelper.init("opreateReportTable","/oss/product/queryProductList",function(pageParam){
            debugger
			var temp = {   // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	            "currPage":pageParam.pageNumber,   // 页面大小
	            "pageSize": pageParam.pageSize,  // 页码
	            
	        };
	        return $.param(temp);
		},_columns);
    }
}

$(function(){
    operateReport.initPage()
})