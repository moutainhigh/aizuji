var userInfo = {
	
	degNum:null,
	
	/**
	 * 订单数据
	 */
	orderData:null,

	/**
	 * 用户id
	 */
	userId:null,
		
	/**
	 * 初始化数据
	 */
	initData:function(orderData){
		this.userId = orderData.rentRecord.userId;
		this.initUserData();
	},
	
	/**
	 * 初始化用户基础数据
	 */
	initUserData:function(){
		var _param = {"orderNo":this.orderData.rentRecord.rentRecordNo};
		var _url = "/userInfo/queryUserSnapByOrderNo";
		common.ajax(_url,_param,function(data){
			//填充用户基础数据
			userInfo.fillUserBaseInfoData(data);
			
			//填充用户扩展数据
			userInfo.fillUserExtendData(data);
		});
	},
	
	/**
	 * 填充用户基础数据
	 */
	fillUserBaseInfoData:function(data){
		var _d = data;
		var _user_realnameCertState = (_d.realnameCertState == 1)?"已实名认证":"未实名认证";
		//基本信息
		$("#userInfo_realnameCertState").html(_user_realnameCertState);
		$("#userInfo_userName").html(_d.realName || "");
		$("#userInfo_sex").html(_d.gender && common.orderAudit.sexJson[_d.gender] || "");
		$("#userInfo_idNo").html(_d.idNo || "");
		// $("#userInfo_job").html(_d.job && common.orderAudit.jobJson[_d.job] || "");
        $("#userInfo_marriage").html(_d.marital && common.orderAudit.marriageJson[_d.marital] || "");
		$("#userInfo_resource").html(_d.channelType  || "");
        $("#userInfo_zhifubao").html(_d.authAlipayUserId  || "");
		$("#userInfo_mobile").html(_d.phoneNum || "");
		$("#userInfo_birthday").html(_d.birthday && new Date(_d.birthday).Format("yyyy-MM-dd") || "");
		var _st = _d.effectiveStartDate && new Date(_d.effectiveStartDate).Format("yyyy-MM-dd");
		var _et = _d.effectiveEndDate && new Date(_d.effectiveEndDate).Format("yyyy-MM-dd");
		$("#userInfo_IdEffectiveTime").html("自    "+(_st || "") + "至    "+ (_et||""));
		// $("#userInfo_industry").html(_d.industry && common.orderAudit.industryJson[_d.industry] || "");
        $("#userInfo_level").html(_d.education && common.orderAudit.courselevelJson[_d.education] || "");
		$("#userInfo_zhimaScore").html(_d.zhimaScore);
        var _fileUrl = this.orderData.rentRecord.sealAgreementUrl;
		$("#userInfo_hetong").html(_fileUrl && ("<a href='"+_fileUrl+"' download='"+_fileUrl+"' class='compact'>点击下载合同</a>") || "");
		$("#userInfo_age").html(_d.age);
        if(_d.birthday != ""){
            var date = new Date();
            var birthday = '${_d.birthday}';
            var startDate = new Date(birthday);
            var newDate = date.getTime() - startDate.getTime();
            var age = Math.ceil(newDate / 1000 / 60 / 60 / 24 /365);
            if (isNaN(age)){
                age = "";
            }
            $("#userInfo_ages").html(age);
		}
		$("#userInfo_residenceAddress").html(_d.residenceAddress || "");
		$("#userInfo_issuingAuthority").html(_d.issuingAuthority || "");
		// $("#userInfo_monthIncome").html(_d.monthIncome && common.orderAudit.revenueJson[_d.monthIncome] || "");
		// $("#userInfo_addrDetail").html(_d.addrDetail || "");
        $("#userInfo_phoneUse").html(_d.phoneUserTime || "");
        $("#userInfo_email").html(_d.userEmail || "");

		//单位/学校信息
        $("#userInfo_industry").html(_d.industry && common.orderAudit.industryJson[_d.industry] || "");
        $("#userInfo_entryTime").html(_d.entryTime && new Date(_d.entryTime).Format("yyyy-MM-dd") || "");
        $("#userInfo_unitAddress").html(_d.companyAddress || "");
        $("#userInfo_livingMoney").html(_d.livingExpenses || "");
        $("#userInfo_occupation").html(_d.job && common.orderAudit.jobJson[_d.job] || "");
        $("#userInfo_post").html(_d.position || "");
        $("#userInfo_workIncome").html(_d.monthIncome || "");
        $("#userInfo_company").html(_d.companyName || "");
        $("#userInfo_contactNumber").html(_d.companyContactNumber || "");
        $("#userInfo_schoolname").html(_d.schoolName || "");
        $("#userInfo_schoolAddress").html(_d.schoolAddress || "");

        //贷款信息
        $("#userInfo_loanRecord").html(_d.hasLoanRecord && common.orderAudit.loanJson[_d.hasLoanRecord] || "");
        $("#userInfo_loanMoney").html(_d.loanAmount || "");
        $("#userInfo_repaymentMoney").html(_d.monthRepayment || "");

        //认证照
		var _cardFaceUrl =  _d.cardFaceUrl;
		var _cardBackUrl =  _d.cardBackUrl;
		var _faceAuthUrl =  _d.faceAuthUrl;
		_cardFaceUrl && $("#userInfo_cardFaceUrl").attr("src", _cardFaceUrl);
		_cardBackUrl && $("#userInfo_cardBackUrl").attr("src", _cardBackUrl);
		_faceAuthUrl && $("#userInfo_faceAuthUrl").attr("src", _faceAuthUrl);
	},
	
	/**
	 * 填充用户扩展数据
	 */
	fillUserExtendData:function(data){
		var _d = data;
		//$("input[type='radio'][name='sex'][value="+_d.gender+"]").attr("checked","checked");
		//$("#dtp_input1").val(_d.birthday && new Date(_d.birthday).Format("yyyy-MM-dd"));
		
		//邮箱
		$("#dtp_inputsz1").val(_d.userEmail || "");
		//户籍地址
		//$("#dtp_inputsz2").val(_d.residenceAddress || "");
		//支付宝账号
		//$("#dtp_inputsz3").val(_d.authAlipayUserId || "");
		//手机使用时长
		$("#dtp_inputsz4").val(_d.phoneUserTime || "");
		//入职时间
		$("#dtp_inputsz5").val(_d.entryTime && new Date(_d.entryTime).Format("yyyy-MM-dd"));
		//单位名称
		$("#dtp_inputsz6").val(_d.companyName || "");
		//联系电话
		$("#dtp_inputsz7").val(_d.companyContactNumber || "");
		//职务
		$("#dtp_inputsz8").val(_d.position || "");
		//工作收入
		$("#monthIncome").val(_d.monthIncome || "");
		//单位住址
		$("#dtp_inputsz9").val(_d.companyAddress || "");
		//学校名称
		$("#dtp_inputsz10").val(_d.schoolName || "");
		//月生活费
		$("#dtp_inputsz11").val(_d.livingExpenses || "");
		//学校地址
		$("#dtp_inputsz12").val(_d.schoolAddress || "");
		//贷款金额
		$("#dtp_inputsz13").val(_d.loanAmount || "");
		//月还款
		$("#dtp_inputsz14").val(_d.monthRepayment || "");
		
		//初始化职业、行业、月收入基础数据
		common.initJsonNomalBtn("jobDiv",common.orderAudit.jobJson,_d.job);
        common.initJsonNomalBtn("marriageDiv",common.orderAudit.marriageJson,_d.marital);
        common.initJsonNomalBtn("courselevelDiv",common.orderAudit.courselevelJson,_d.education);
		common.initJsonNomalBtn("industryDiv",common.orderAudit.industryJson,_d.industry);
		common.initJsonNomalBtn("loanDiv",common.orderAudit.loanJson,_d.hasLoanRecord);
		
		//如果有无贷款选择无  隐藏贷款明细
		if(_d.hasLoanRecord == 1){
			$("#loanDetailDiv").show();
		}else{
			$("#loanDetailDiv").hide();
		}
		
	},
	
	/**
	 * 初始化操作
	 */
	initOperator:function(){
		$('.occupation-list,.trade-list,.income').delegate('.btn','click',function(){
			var _this =	$(this); 
			_this.toggleClass('btn-primary').siblings().removeClass('btn-primary');
			if(_this.parent().attr("id") == "loanDiv"){
				//如果有无贷款选择无  隐藏贷款明细
				if(_this.val() == 1){
					$("#loanDetailDiv").show();
				}else{
					$("#loanDetailDiv").hide();
				}
			}
		});
		
		$("img[name='image']").click(function(){
			var _this = $(this);
			switch (userInfo.degNum){
				case '90' : userInfo.degNum = '180'; break;
				case '180' : userInfo.degNum = '270'; break;
				case '270' : userInfo.degNum = '0'; break;
				default: userInfo.degNum = '90';
			}
			_this.css("transform","rotate(" +userInfo.degNum+ "deg)");
		});
		
		//绑定补充用户信息接口
		$("#updateUserBtn").click(function(){
			var _url = "/userInfo/updateUser";
			var _param = {};
			_param['rentRecordNo'] = userInfo.orderData.rentRecord.rentRecordNo;
			_param['userId'] = userInfo.userId;
//			var _genderObj = $("input[name='sex']:checked");
//			var _gender = null;
//			if(_genderObj && _genderObj.length > 0){
//				_gender = _genderObj.val();
//				_param['gender'] = _gender;
//			}
            var _email = $("#dtp_inputsz1").val();
            //var _residenceAddress = $("#dtp_inputsz2").val();
            //var _zhifubao = $("#dtp_inputsz3").val();
            var _phoneUse = $("#dtp_inputsz4").val();
            var _entryTime = $("#dtp_inputsz5").val();
            var _company = $("#dtp_inputsz6").val();
            var _contactNumber = $("#dtp_inputsz7").val();
            var _post = $("#dtp_inputsz8").val();
            var _unitAddress = $("#dtp_inputsz9").val();
            var _schoolname = $("#dtp_inputsz10").val();
            var _livingMoney = $("#dtp_inputsz11").val();
            var _schoolAddress = $("#dtp_inputsz12").val();
            var _loanMoney = $("#dtp_inputsz13").val();
            var _repaymentMoney = $("#dtp_inputsz14").val();
			var _job = userInfo.getSelectedBtnByDivId("jobDiv");
            var _marriage = userInfo.getSelectedBtnByDivId("marriageDiv");
            var _courselevel = userInfo.getSelectedBtnByDivId("courselevelDiv");
			var _industry = userInfo.getSelectedBtnByDivId("industryDiv");
			var _revenue = $("#monthIncome").val();
            var _loan = userInfo.getSelectedBtnByDivId("loanDiv");
			//_param['birthday'] = _birthday;
            _param['userEmail'] = _email;
			_param['job'] = _job;
            _param['hasLoanRecord'] = _loan;
            _param['marital'] = _marriage;
            _param['education'] = _courselevel;
            //_param['residenceAddress'] = _residenceAddress;
            //_param['authAlipayUserId'] = _zhifubao;
            _param['phoneUserTime'] = _phoneUse;
			_param['industry'] = _industry;
            _param['companyName'] = _company;
            _param['entryTime'] = _entryTime;
            _param['companyContactNumber'] = _contactNumber;
            _param['position'] = _post;
            _param['companyAddress'] = _unitAddress;
            _param['schoolName'] = _schoolname;
            _param['livingExpenses'] = _livingMoney;
            _param['schoolAddress'] = _schoolAddress;
            _param['monthIncome'] = _revenue;
            if(_loan == 1){
            	_param['loanAmount'] = _loanMoney;
            	_param['monthRepayment'] = _repaymentMoney;
            }
			// if(!_gender && !_birthday && !_job && !_industry && !_revenue){
			// 	common.error("请至少更新一项");
			// 	return false;
			// }
            if(!(_marriage&&_courselevel&&_email&&_phoneUse&&_industry&&_job&&_entryTime&&_company&&_contactNumber&&_post&&_revenue&&_unitAddress&&_loan)){
            	common.alert("补充信息不完整");
            	return;
            }
            if(_loan == 1)
        		if(!(_loanAmount&&_monthRepayment)){
        			common.alert("补充信息不完整");
        			return;
        		}
			common.ajax(_url,_param,function(data){
				$("#addUserInfo").modal("hide");
				common.alert("更新成功");
				//$("#userInfo_ages").html(age);
				//$("#userInfo_sex").html(_gender && common.orderAudit.sexJson[_gender] || "");
				//$("#userInfo_birthday").html(_birthday && new Date(_birthday).Format("yyyy-MM-dd") || "");
				$("#userInfo_workIncome").html(_revenue || "");
				$("#userInfo_occupation").html(_job && common.orderAudit.jobJson[_job] || "");
                $("#userInfo_marriage").html(_marriage && common.orderAudit.marriageJson[_marriage] || "");
                $("#userInfo_level").html(_courselevel && common.orderAudit.courselevelJson[_courselevel] || "");
                $("#userInfo_email").html(_email || "");
                //$("#userInfo_residenceAddress").html(_residenceAddress || "");
                //$("#userInfo_zhifubao").html(_zhifubao || "");
                $("#userInfo_phoneUse").html(_phoneUse || "");
                $("#userInfo_entryTime").html(_entryTime && new Date(_entryTime).Format("yyyy-MM-dd") || "");
                $("#userInfo_company").html(_company || "");
                $("#userInfo_contactNumber").html(_contactNumber || "");
                $("#userInfo_post").html(_post || "");
                $("#userInfo_unitAddress").html(_unitAddress || "");
                $("#userInfo_schoolname").html(_schoolname || "");
                $("#userInfo_livingMoney").html(_livingMoney || "");
                $("#userInfo_schoolAddress").html(_schoolAddress || "");
                $("#userInfo_loanMoney").html(_loanMoney || "");
                $("#userInfo_repaymentMoney").html(_repaymentMoney || "");
				$("#userInfo_industry").html(_industry && common.orderAudit.industryJson[_industry] || "");
				$("#userInfo_monthIncome").html(_revenue && common.orderAudit.revenueJson[_revenue] || "");
                $("#userInfo_loanRecord").html(_loan && common.orderAudit.loanJson[_loan] || "");
			});
		});
	},
	
	/**
	 * 从div里面找选择的类型并返回值
	 */
	getSelectedBtnByDivId:function(divId){
		var _val = null;
		var _objs = $("#"+divId).find("button");
		$.each(_objs,function(){
			var _this = $(this);
			if(_this.hasClass("btn-primary")){
				_val =  _this.val();
				return;
			}
		});
		return _val;
	},
		
	/**
	 * 初始化页面
	 */
	initPage:function(orderData){
		this.orderData = orderData;
		this.initOperator();
		this.initData(orderData);
	}
}