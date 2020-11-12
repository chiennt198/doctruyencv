var vueItem = new Vue({
    el: '#main',
    data:{
    	error_message : '',
    	categoryList : [],
    	storyData : {},
    },
    created : function() {
    	get(this, contextPath + "/get-category-list" , {}, function(data) {
			if (data.status == STATUS_NORMAL) {
				this.categoryList = data.dataInfo;
			}
		});
    },
    methods: {
    	createStory: function(){
    		this.error_message = '';
    		if(!this.storyData.name) {
    			this.error_message = '<p>Vui lòng nhập tên truyện</p>';
    		}
    		if(!this.storyData.authorName) {
    			this.error_message += '<p>Vui lòng nhập tác giả</p>';
    		}
    		if(!this.storyData.description) {
    			this.error_message += '<p>Vui lòng nhập tóm tắt truyện </p>';
    		}
    		
    		if(!this.storyData.categoryId) {
    			this.error_message += '<p>Vui lòng chọn thể loại truyện </p>';
    		}
    		if(this.error_message) {
    			return;
    		}
    		post(this, contextPath + "/admin-regist-story" , {json:JSON.stringify(this.storyData)}, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				alert("Đã tạo truyện mới thành công");
    				window.location.href= contextPath + "/html/admin_story_search.html";
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


