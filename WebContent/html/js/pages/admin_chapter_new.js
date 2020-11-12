var vueItem = new Vue({
    el: '#main',
    data:{
    	error_message : '',
    	categoryList : [],
    	chapterData : {},
    	registType : '0',
    },
    created : function() {
    	if(!sessionStorage.getItem("PARAM_STORY_ID")) {
    		window.location.href= contextPath + "/html/admin_story_search.html";
    		return;
    	}
    	this.chapterData.storyId =  sessionStorage.getItem("PARAM_STORY_ID");
    	if(sessionStorage.getItem("PARAM_CHAPTER_ID")) {
    		this.registType = '1';
    		post(this, contextPath + "/get-chapter-detail" , {storyId :this.chapterData.storyId, chapterId :  sessionStorage.getItem("PARAM_CHAPTER_ID")}, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				this.chapterData = data.dataInfo;
    			} else {
    				this.error_message = data.errorMessage;
    			}
    		});
    	}
    },
    methods: {
    	createChapter: function(){
    		this.chapterData.registType = this.registType;
    		post(this, contextPath + "/admin-regist-chapter" , {json:JSON.stringify(this.chapterData)}, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				alert("Đã tạo chương mới thành công");
    				this.chapterData.name = '';
    				this.chapterData.content = '';
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


