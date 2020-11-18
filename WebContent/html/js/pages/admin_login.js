var loginVue = new Vue({
    el: '#main',
    data:{
    	error_message : '',
    	loginInfo:{
    		userId:'', 
    		password:''
    	},
    },
    created : function() {
    	this.loginInfo.userId = '';
    	this.loginInfo.password = '';
    	sessionStorage.clear();
    },
    methods: {
    	login: function() {
    		this.error_message = '';
    		if(!this.loginInfo.userId) {
    			this.error_message += '<p>' + 'You must enter a value for 会員番号' + '</p>';
    		}
    		if(!this.loginInfo.password) {
    			this.error_message += '<p>' + 'You must enter a value for パスワード' + '</p>';
    		}
    		if(this.error_message) {
    			return false;
    		}
    		showLoading();

  			url = API_HTTP_COMMON + "/admin-login";
			$.ajax({
				  url: url,
				  type:'post',
				  async: false,
					  headers: {},
					  data : loginVue.loginInfo
			}).done(function( jsond ) {
				var data = JSON.parse(jsond);
				if (data.status == STATUS_NORMAL) {
					
					sessionStorage.setItem("ADMIN_ID", data.dataInfo.userID);
					sessionStorage.setItem("ADMIN_ROLE", data.dataInfo.role);
					sessionStorage.setItem("ADMIN_NAME", data.dataInfo.name);
					window.location.href= contextPath + "/html/admin_story_search.html";
				} else if (data.status == STATUS_NO_DATA) {
					loginVue.error_message = "Tên Đăng Nhập Hoặc Mật Khẩu bị sai";
				} else {
					loginVue.error_message = data.errorMessage;
				}
				
				hideLoading();
			}).fail(function(jqXHR, textStatus, errorThrown) {
				hideLoading();
			    sessionStorage.clear();
			    bootbox.alert("エラー：" + textStatus);
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
