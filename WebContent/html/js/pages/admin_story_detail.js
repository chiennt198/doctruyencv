var vueItem = new Vue({
    el: '#main',
    data:{
    	error_message : '',
    	categoryList : [],
    	storyData : {},
    	categoryList : [],
    },
    created : function() {
    	get(this, contextPath + "/get-category-list" , {}, function(data) {
			if (data.status == STATUS_NORMAL) {
				this.categoryList = data.dataInfo;
			}
		});
    	this.loadStory();
    },
    methods: {
    	loadStory: function(){
    		if(!sessionStorage.getItem("PARAM_STORY_ID")) {
    			window.location.href= contextPath + "/html/admin_story_search.html";
        		return;
    		}
    		var id = sessionStorage.getItem("PARAM_STORY_ID");
    		get(this, contextPath + "/admin-get-story-detail/" + id , {}, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				this.storyData = data.dataInfo;
    			}
    		});
    	},
    	update : function() {
    		this.storyData.registType = '1';
    		post(this, contextPath + "/admin-regist-story" , {json:JSON.stringify(this.storyData)}, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				alert("Đã tạo truyện mới thành công");
    			} else {
    				this.error_message = data.errorMessage;
    			}
    		});
    	},
    	createChapter : function() {
    		sessionStorage.setItem("PARAM_STORY_ID",this.storyData.id);
    		window.location.href= contextPath + "/html/admin_chapter_new.html";
    	}
    },
	watch: {
		
	},
});


