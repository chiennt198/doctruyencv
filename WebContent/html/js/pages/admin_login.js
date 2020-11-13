var loginVue = new Vue({
    el: '#main',
    data:{
    	error_message : '',
    	loginInfo:{
    		kaiinId:'', 
    		password:''
    	},
    	GakkaiOptions:[]
    },
    created : function() {
    	this.loginInfo.kaiinId = '';
    	this.loginInfo.password = '';
    	sessionStorage.clear();
    },
    methods: {
    	login: function() {
    		this.error_message = '';
    		if(!this.loginInfo.kaiinId) {
    			this.error_message += '<p>' + 'You must enter a value for 会員番号' + '</p>';
    		}
    		if(!this.loginInfo.password) {
    			this.error_message += '<p>' + 'You must enter a value for パスワード' + '</p>';
    		}
    		if(this.error_message) {
    			return false;
    		}
    		showLoading();
    		var thisItems =  this;
    		var url = '';
    		var param = {
    				"kaiin_code":thisItems.loginInfo.kaiinId,
    				"gakkai_code":$('#societyCd').val(),
    				"password":thisItems.loginInfo.password,
    				"url_key":'13'
    			}
    		$.ajax({
  			  url: API_AUTH_WEB_API + "/login",
  			  type:'post',
  			  async: false,
  			  cache : false,
  			 data : {json:JSON.stringify(param)},
    		}).done(function( jsond ) {

    			var auth_data = JSON.parse(jsond);
    			var result = auth_data.result;
	  			url = API_HTTP_COMMON + "/admin/admin-get-user-login";
				if(result != "true"){
					thisItems.error_message = '認証に失敗しました。';
					hideLoading();
					return;
				}
				sessionStorage.setItem(PARAM_AUTH_DATA,JSON.stringify(auth_data));
			$.ajax({
				  url: url,
				  type:'post',
				  async: false,
					  headers: {'AUTH-DATA' : sessionStorage.getItem(PARAM_AUTH_DATA)},
					  data : thisItems.loginInfo
			}).done(function( jsond ) {
				var data = JSON.parse(jsond);
				if (data.status == STATUS_NORMAL) {
					sessionStorage.setItem("ADMIN_ID", data.dataInfo.id);
					sessionStorage.setItem("ADMIN_ROLE", data.dataInfo.role);
					sessionStorage.setItem("NAME_LOGIN", data.dataInfo.sei + data.dataInfo.mei);
					sessionStorage.setItem("ADMIN_BEAN", JSON.stringify(data.dataInfo));
					sessionStorage.setItem("GAKKAI_CD", data.dataInfo.gakkaiCd);
					sessionStorage.setItem("GAKKAI_NM", data.dataInfo.gakkaiNm);
					sessionStorage.setItem("MULTIPLE_GAKKAI_FLG", data.dataInfo.multipleGakkaiFlg);
					
					if(!data.dataInfo.gakkaiSponsorKbn) {
						data.dataInfo.gakkaiSponsorKbn = '';
					}
					sessionStorage.setItem("GAKKAI_SPONSOR_KBN", data.dataInfo.gakkaiSponsorKbn);
					window.location.href= contextPath + "/html/admin_menu.html";
				} else {
					var objArr = data.dataInfo;

					if(objArr != null && objArr.length > 0){
						var strError ="";
						$.each(objArr,function(index, value) {
							strError += value
						});

						thisItems.error_message = strError;
					}else{
						thisItems.error_message = '<p>' + data.errorMessage + '</p>';
					};
				}
				hideLoading();
			}).fail(function(jqXHR, textStatus, errorThrown) {
				hideLoading();
			    sessionStorage.clear();
			    bootbox.alert("エラー：" + textStatus);
				    location.href=contextPath + "/html/admin_login.html";
				return;
			});
  		}).fail(function(jqXHR, textStatus, errorThrown) {
  			hideLoading();
  		    sessionStorage.clear();
  		    alert("エラー：" + textStatus);
  		    location.href=contextPath + "/html/admin_login.html";
  			return;
  		});
    		
			
        },
        loginEnter:function(event) {
        	if(event.keyCode == 13){
    			this.login();
    		};
        },
    },
});
