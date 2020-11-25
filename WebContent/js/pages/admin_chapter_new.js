var vueItem = new Vue({
    el: '#main',
    data:{
    	error_message : '',
    	categoryList : [],
    	chapterData : {},
    	registType : '0',
    	newestChapterName : '',
    },
    created : function() {
    	if(!sessionStorage.getItem("PARAM_STORY_ID")) {
    		window.location.href= contextPath + "/html/admin_story_search.html";
    		return;
    	}
    	
    	if(sessionStorage.getItem("PARAM_CHAPTER_INFO")) {
    		this.chapterData = JSON.parse(sessionStorage.getItem("PARAM_CHAPTER_INFO"));
    	} else {
    		this.chapterData.storyId =  sessionStorage.getItem("PARAM_STORY_ID");
    		this.newestChapterName = sessionStorage.getItem("PARAM_NEWEST_CHAPTER_NAME");
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
    	}
    	
    },
    methods: {
    	createChapter: function(){
    		this.chapterData.registType = this.registType;
    		sessionStorage.setItem("PARAM_CHAPTER_INFO", JSON.stringify(this.chapterData));
    		window.location.href= contextPath + "/html/admin_chapter_confirm.html";
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


