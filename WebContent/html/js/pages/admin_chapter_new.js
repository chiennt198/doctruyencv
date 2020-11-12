var vueItem = new Vue({
    el: '#main',
    data:{
    	error_message : '',
    	categoryList : [],
    	chapterData : {},
    },
    created : function() {
    	if(!sessionStorage.getItem("PARAM_STORY_ID")) {
    		window.location.href= contextPath + "/html/admin_story_search.html";
    		return;
    	}
    	this.chapterData.storyId =  sessionStorage.getItem("PARAM_STORY_ID");
    },
    methods: {
    	createChapter: function(){
    		post(this, contextPath + "/admin-regist-chapter" , {json:JSON.stringify(this.chapterData)}, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				alert("Đã tạo chương mới thành công");
    				this.chapterData.name = '';
    				this.chapterData.content = '';
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


