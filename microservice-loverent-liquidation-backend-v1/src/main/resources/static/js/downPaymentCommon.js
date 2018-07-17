/**
 * 首期收款公共js
 */

// 手动完成支付窗口打开之前事件
/*$('#manuaPay').on('show.bs.modal',function(e){
    var id = $('#btn-pay').data('id');
    var transactionSN = $('#btn-pay').data('transactionsn');
    var transactionAmount = $('#btn-pay').data('transactionamount');
    $("#ipt-hidden-transaction-id").val(id);
    $("#ipt-hidden-transactionSN").val(transactionSN);
    
    $("#div-orderSN").html(transactionSN);
    $("#div-amount").html(transactionAmount);
});*/
// 手动完成支付		
function submit(){
	var param = {};
	param.id = $("#ipt-hidden-transaction-id").val();
	param.transactionSN =  $("#ipt-hidden-transactionSN").val();
	param.remark =  $("#textarea-remark").val();
	 //debugger;
	if(!$('#ipt-endDate').val()){
		common.error('交易完成日期不能空');
		return false;
	}
	param.endDate = $('#ipt-endDate').val();
	 
	common.ajax('/downPayment/payCorrected',param,function(data){
		$('#manuaPay').modal('hide');
		$('#grid-tab').bootstrapTable('refresh');  
		/*BootstrapDialog.show({
            message: '更新成功！',
            title: '操作提示'
        });*/
	});
}
var downPayment = {
		/**
		 * 打开手动完成支付弹框
		 */
		openManualPayDialog:function(title,id,transactionSN,transactionAmount){
		  //console.info("********************** "+id+" transactionSN"+transactionSN+" transactionAmount:"+transactionAmount);
		  $("#ipt-hidden-transaction-id").val(id);
		  $("#ipt-hidden-transactionSN").val(transactionSN);
		  $("#div-orderSN").html(transactionSN);
		  $("#div-amount").html(transactionAmount);
		  $('#manuaPay').modal('show');
		},

        tuikuanShow:function (id) {
            common.ajax('/refund/refundImg/'+id,{},function(data){

			})
        },
		
		transactionDetail:function(id){
			common.ajax('/downPayment/transaction/detail/'+id,{},function(data){
				var _record = data.data;
				//console.log("查看明细："+JSON.stringify(_record));
				$('#span-orderSN').html(_record.orderSN||"");
		    	$('#span-category').html(_record.sourceTypeDesc||"");
		    	$('#span-realName').html(_record.realName||"");
		    	$('#span-periods').html((_record.periods && _record.periods+"个月")||"");
		    	$('#span-rent').html((_record.rent && _record.rent+"元")||"0元");
		    	$('#span-premium').html(_record.premium && (_record.premium+"元")||"0元");
		    	$('#span-phone').html(_record.phone||"");
		    	$('#span-transactionSN').html(_record.transactionSN);
		    	$('#span-transactionWay').html(_record.transactionWayDesc);
		    	$('#span-transactionType').html(_record.transactionTypeDesc);
		    	$('#span-transactionSource').html(_record.transactionSourceDesc||"");
		    	$('#span-fromAccount').html(_record.fromAccount||"");
		    	$('#span-transactionAmount').html(_record.transactionAmount&&(_record.transactionAmount+"元")||"0元");
		    	$('#span-state').html(_record.stateDesc);
		    	$('#span-failure').html(_record.failureDesc||"");
		    	$('#span-createRealName').html(_record.realName||"");
		    	$('#span-creareOn').html(new Date(_record.createOn).Format('yyyy-MM-dd hh:mm:ss')||"");
		    	$('#span-finishTime').html(_record.finishTime && new Date(_record.finishTime).Format('yyyy-MM-dd hh:mm:ss')||"");
		    	$('#span-remark').html(_record.remark||"");
			},null,null,'post');
			$('#payDetail').show();
		},
		
		operateEvents:{
		    //绑定打开手动完成支付事件
		    'click .manual': function (e, value, row, index) {
		    	downPayment.openManualPayDialog("打开手动完成支付窗口",row.id,row.transactionSN,row.transactionAmount);
			},
			 //绑定删除事件
		    'click .detail': function (e, value, row, index) {
		    	downPayment.transactionDetail(row.id);
			},
			//退款截图
            'click .tuikuan': function (e, value, row, index) {
                downPayment.tuikuanShow(row.id);
            },
		},
		
}