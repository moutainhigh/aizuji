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
		
		//性别
		$("#gender").val(_d.gender || "");
		//婚姻状况
		$("#marital").val(_d.marital || "");
		//教育程度
		$("#education").val(_d.education || "");
		//出生日期
		$("#birthday").val(_d.birthday && new Date(_d.birthday).Format("yyyy-MM-dd") || "");
		//电子邮箱
		$("#userEmail").val(_d.userEmail || "");
		//支付宝账号
		$("#authAlipayUserId").val(_d.authAlipayUserId || "");
		//手机使用时长
		$("#phoneUserTime").val(_d.phoneUserTime || "");
		//户籍详细地址
		$("#residenceAddress").val(_d.residenceAddress || "");
		//行业
		$("#industry").val(_d.industry || "");
		//职业
		$("#job").val(_d.job || "");
		//入职/入学时间
		$("#entryTime").val(_d.entryTime && new Date(_d.entryTime).Format("yyyy-MM-dd"));
		//单位/学校名称
		$("#companyName").val(_d.companyName || "");
		//联系电话
		$("#companyContactNumber").val(_d.companyContactNumber || "");
		//职务
		$("#position").val(_d.position || "");
		//工作收入/月生活费
		$("#monthIncome").val(_d.monthIncome || "");
		//现单位/学校地址
		$("#companyAddress").val(_d.companyAddress || "");
		//有无贷款
		$("#hasLoanRecord").val(_d.hasLoanRecord || "");
		//贷款金额
		$("#loanAmount").val(_d.loanAmount || "");
		//月还款
		$("#dtp_inputsz14").val(_d.monthRepayment || "");
		
		/*//初始化职业、行业、月收入基础数据
		common.initJsonNomalBtn("jobDiv",common.orderAudit.jobJson,_d.job);
        common.initJsonNomalBtn("marriageDiv",common.orderAudit.marriageJson,_d.marital);
        common.initJsonNomalBtn("courselevelDiv",common.orderAudit.courselevelJson,_d.education);
		common.initJsonNomalBtn("industryDiv",common.orderAudit.industryJson,_d.industry);
		common.initJsonNomalBtn("loanDiv",common.orderAudit.loanJson,_d.hasLoanRecord);
		*/
		//如果有无贷款选择无  隐藏贷款明细
		if(_d.hasLoanRecord==0){
			$("#loanDetailDiv").hide();
		}else{
			$("#loanDetailDiv").show();
		}
		
	},
	
	/**
	 * 初始化操作
	 */
	initOperator:function(){
		$("#hasLoanRecord").change(function(){
			if($("#hasLoanRecord").val()==1){
				$("#loanDetailDiv").show();
			}else{
				$("#loanDetailDiv").hide();
			}
		});
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
			var _gender = $("#gender").val();
            var _marital = $("#marital").val();
            var _education = $("#education").val();
            var _birthday = $("#birthday").val();
            var _userEmail = $("#userEmail").val();
            var _authAlipayUserId = $("#authAlipayUserId").val();
            var _phoneUserTime = $("#phoneUserTime").val();
            var _residenceAddress = $("#residenceAddress").val();
            var _industry = $("#industry").val();
            var _job = $("#job").val();
            var _entryTime = $("#entryTime").val();
            var _companyName = $("#companyName").val();
            var _companyContactNumber = $("#companyContactNumber").val();
			var _position = $("#position").val();
            var _monthIncome = $("#monthIncome").val();
            var _companyAddress = $("#companyAddress").val();
			var _hasLoanRecord = $("#hasLoanRecord").val();
			var _loanAmount = $("#loanAmount").val();
            var _monthRepayment = $("#monthRepayment").val();
            _param['gender'] = _gender;
			_param['marital'] = _marital;
            _param['education'] = _education;
            _param['birthday'] = _birthday;
            _param['userEmail'] = _userEmail;
            _param['authAlipayUserId'] = _authAlipayUserId;
			_param['phoneUserTime'] = _phoneUserTime;
            _param['residenceAddress'] = _residenceAddress;
            _param['industry'] = _industry;
            _param['job'] = _job;
            _param['entryTime'] = _entryTime;
            _param['companyName'] = _companyName;
            _param['companyContactNumber'] = _companyContactNumber;
            _param['position'] = _position;
            _param['monthIncome'] = _monthIncome;
            _param['companyAddress'] = _companyAddress;
            _param['hasLoanRecord'] = _hasLoanRecord;
            _param['loanAmount'] = _loanAmount;
            _param['monthRepayment'] = _monthRepayment;
            if(!(_gender&&_marital&&_education&&_birthday&&_userEmail&&_authAlipayUserId&&_phoneUserTime&&_residenceAddress&&_industry&&_job&&_entryTime&&_companyName&&_companyContactNumber&&_position&&_monthIncome&&_companyAddress&&_hasLoanRecord)){
            	common.alert("补充信息不完整");
            	return;
            }
            if(_hasLoanRecord!=0)
        		if(!(_loanAmount&&_monthRepayment)){
        			common.alert("补充信息不完整");
        			return;
        		}
			common.ajax(_url,_param,function(data){
				$("#addUserInfo").modal("hide");
				common.alert("更新成功");
				$("#userInfo_sex").html(_gender && common.orderAudit.sexJson[_gender] || "");
				$("#userInfo_marriage").html(_marriage && common.orderAudit.marriageJson[_marriage] || "");
				$("#userInfo_level").html(_d.education && common.orderAudit.courselevelJson[_education] || "");
				$("#userInfo_birthday").html(_birthday && new Date(_birthday).Format("yyyy-MM-dd") || "");
				$("#userInfo_email").html(_email || "");
				$("#userInfo_zhifubao").html(_authAlipayUserId || "");
				$("#userInfo_phoneUse").html(_phoneUserTime || "");
				$("#userInfo_residenceAddress").html(_residenceAddress || "");
				$("#userInfo_industry").html(_industry && common.orderAudit.industryJson[_industry] || "");
				$("#userInfo_occupation").html(_job && common.orderAudit.jobJson[_job] || "");
				$("#userInfo_entryTime").html(_entryTime && new Date(_entryTime).Format("yyyy-MM-dd") || "");
				$("#userInfo_company").html(_companyName || "");
                $("#userInfo_contactNumber").html(_companyContactNumber || "");
                $("#userInfo_post").html(_position || "");
                $("#userInfo_workIncome").html(_monthIncome || "");
                $("#userInfo_unitAddress").html(_companyAddress || "");
                $("#userInfo_loanRecord").html(_hasLoanRecord && common.orderAudit.loanJson[_loan] || "");
                $("#userInfo_loanMoney").html(_loanAmount || "");
                $("#userInfo_repaymentMoney").html(_monthRepayment || "");
               
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