/*
* 租后收款 订单详情页js
*
*/

queryOrderBasicDetail();

// 查询订单基本信息详情
function queryOrderBasicDetail(){
    var orderSN = common.getQueryString("rentRecordNo");
    common.ajax('/rentCollection/queryOrderDetail/'+orderSN,{},function(data){
        var _record = data;
        $('#rentRecordNo').text(_record.orderSN||"");
        $('#realName').html(_record.realName||"");
        $('#idNo').html(_record.identityCard||"");
        $('#backTime').html(new Date(_record.rentEndDate).Format("yyyy-MM-dd hh:mm:ss")||"");
        $('#stateDesc').html(_record.stateDesc||"");
        $('#leaseAmount').html(new Date(_record.repaymentDate).Format("dd")||"");
        $('#leaseTerm').html("已结清"+_record.periodOf+"/总期数"+_record.periods||"");
        $('#userid').html(_record.userId||"");
        $('#userealname').html(_record.userRealName||"");
    },null,null,'post');
}

// 订单交易信息
var _columns = [[
    {
        field: 'id',
        title: 'id',
        visible:false
    },
    {
        field: 'transactionSN',
        title: '流水号'
    },
    {
        field: 'transactionWayDesc',
        title: '交易方式'
    },
    {
        field: 'transactionTypeDesc',
        title: '交易类型'
    },
    {
        field: 'transactionAmount',
        title: '交易金额'
    },
    {
        field: 'createOn',
        title: '交易申请时间',
        formatter:function(value, row, index){
            if(value){
                return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
            }else{
                return '';
            }
        }
    },
    {
        field: 'realName',
        title: '发起人'
    },
    {
        field: 'finishTime',
        title: '交易完成时间',
        formatter:function(value, row, index){
            if(value){
                return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
            }else{
                return '';
            }
        }
    }
]];

function queryRentSchedule(){
    var orderSN = common.getQueryString("rentRecordNo");
    common.tableInit().init("grid-tab",'/downPayment/transaction/list',function (pageParam) {
            var temp={};
            temp.currPage = pageParam.pageNumber;
            temp.pageSize = pageParam.pageSize;
            temp.orderSN = orderSN;
            return $.param(temp);
        }
        ,_columns);
}
queryRentSchedule();

// 订单清偿记录
var repay_columns = [[
    {
        field: 'id',
        title: 'id',
        visible:false
    },
    {
        field: 'transactionSN',
        title: '对应交易流水号'
    },
    {
        field: 'createOn',
        title: '清偿时间',
        formatter:function(value, row, index){
            if(value){
                return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
            }else{
                return '';
            }
        }
    },
    {
        field: 'amount',
        title: '清偿金额'
    },
    {
        field: 'accountDesc',
        title: '清偿科目'
    },
]];
function queryRepayDetail(){
    var orderSN = common.getQueryString("rentRecordNo");
    common.tableInit().init("repay-grid-tab",'/rentCollection/chargeOff/page',
        function (pageParam) {
            var temp={};
            temp.currPage = pageParam.pageNumber;
            temp.pageSize = pageParam.pageSize;
            temp.orderSN = orderSN;
            return $.param(temp);
        }
        ,repay_columns);
}
queryRepayDetail();

// 退款
$(function() {
    $("#upload1").on("change",function () {
        $(this).addClass("hover");
        var objUrl = getObjectURL(this.files[0]);
        if (objUrl) {
            $("#pic1").attr("src", objUrl);
        }
    });
});
function getObjectURL(file) {
    var url = null ;
    if (window.createObjectURL!=undefined) {
        url = window.createObjectURL(file) ;
    } else if (window.URL!=undefined) {
        url = window.URL.createObjectURL(file) ;
    } else if (window.webkitURL!=undefined) {
        url = window.webkitURL.createObjectURL(file) ;
    }
    return url ;
}
$("#uploaderId1").on("click",function () {
    var fileVal = document.getElementById("upload1").value;
    if(fileVal){
        var formData = new FormData($( "#uploadForm1" )[0]);
        $.ajax({
            //url: 'http://192.168.1.115:8012/upload/uploadImage',
            url: '/upload/uploadImage',
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (response) {
                var imgURL = response.data.attaUrl;
            }
        });
    }else{
        alert("请先选择图片进行上传！")
    }
});
$("#deluploaderId1").on("click",function () {
    $("#pic1").attr("src", "../image/imgDefault.png") ;
    var imgURL = "";
    $("#upload1").removeClass("hover");
});
function doLateFeeRemission(){
    var orderSN = common.getQueryString("rentRecordNo");
    var param = {};
    if(!$('#ipt_remission_lateFee').val()){
        common.error('退款金额不能空');
        return false;
    }
    if(!$('#ipt_remission_snNo').val()){
        common.error('交易流水号不能空');
        return false;
    }
    var accountCode = $('input[name="radio_transactionType"]:checked').val();
    if(!$('#tta_desc').val()){
        common.error('退款原因不能空');
        return false;
    }
    param.orderSN = orderSN;
    param.accountCode =  accountCode;
    param.imgURL = imgURL;
    //param.imgURL =  "https://osstest-01.oss-cn-beijing.aliyuncs.com/materiel/pic//7509033040031910_194cc1b7fd1342bba8cb846edac4751d.jpg";
    param.amount = $('#ipt_remission_lateFee').val();
    param.transactionSN = $('#ipt_remission_snNo').val();
    param.refundDesc = $('#tta_desc').val();
    param.userId = $('#userid').html();
    param.userRealName = $('#realName').html();
    common.ajax('/refund/addRefundLog',param,function(data){
        $('#div_lateFeeRemission').modal('hide');
        $('#divsure_lateFeeRemission').modal('show');
    });
}

//查看租赁计划
var repaysee_columns = [[
    {
        field: 'id',
        title: 'id',
        visible:false
    },
    {
        field: 'periodOf',
        title: '期数'
    },
    {
        field: 'paymentDueDate',
        title: '应还租日',
        formatter:function(value, row, index){
            if(value){
                return new Date(value).Format("yyyy-MM-dd");
            }else{
                return '';
            }
        }
    },
    {
        field: 'due',
        title: '应还租金'
    },
    {
        field: 'actualRepayment',
        title: '实还租金'
    },
    {
        field: 'currentBalance',
        title: '未还租金'
    },
    {
        field: 'settleDesc',
        title: '本期是否结清'
    },
    {
        field: 'settleWayDesc',
        title: '本期结清方式'
    },
    {
        field: 'settleDate',
        title: '本期结清时间',
        formatter:function(value, row, index){
            if(value){
                return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
            }else{
                return '';
            }
        }
    },
    {
        field: 'overdueDay',
        title: '本期逾期天数'
    }
]];
function seequeryRepayDetail(){
    var orderSN = common.getQueryString("rentRecordNo");
    common.tableInit().init("grid-tab-see",'/rentCollection/rentSchedule/' + orderSN,
        function (pageParam) {
            var temp={};
            temp.currPage = pageParam.pageNumber;
            temp.pageSize = pageParam.pageSize;
            temp.rentRecordNo = orderSN;
            return $.param(temp);
        }
        ,repaysee_columns);
}
seequeryRepayDetail();

// function goonList() {
//     $('#divsure_lateFeeRemission').modal('hide');
//     //window.location.href();
//
// }