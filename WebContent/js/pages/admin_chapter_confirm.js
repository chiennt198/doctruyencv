var vueItem = new Vue({
    el: '#main',
    data:{
    	error_message : '',
    	categoryList : [],
    	chapterData : {},
    	registType : '0',
    },
    created : function() {
    	if(!sessionStorage.getItem("PARAM_CHAPTER_INFO")) {
    		window.location.href= contextPath + "/admin_story_search.html";
    		return;
    	}
    	this.chapterData = JSON.parse(sessionStorage.getItem("PARAM_CHAPTER_INFO"));
    },
    methods: {
    	createChapter: function(){
    		post(this, contextPath + "/api/admin-regist-chapter" , {json:JSON.stringify(this.chapterData)}, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				sessionStorage.removeItem("PARAM_CHAPTER_INFO");
    				if(this.chapterData.registType = '0') {
    					alert("Đã tạo chương mới thành công");
    					window.location.href= contextPath + "/admin_story_detail.html";
    				} else {
    					alert("Đã chỉnh sửa chương thành công");
    					window.location.href= contextPath + "/admin_story_detail.html";
    				}
    			} else {
    				this.error_message = data.errorMessage;
    			}
    		});
    	},
    },
    computed : {
		totalPages : function() {
			return Math.ceil(this.resultList.length / this.itemsPerPage);
		},
	}, 
	watch: {
		
	},
});


