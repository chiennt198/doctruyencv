var vueItem = new Vue({
    el: '#main',
    data:{
    	error_message : '',
    	categoryList : [],
    	storyData : {},
    	categoryList : [],
    	chaptersList : [],
    	dataCount : 0,
    	currentPage: 0,
		itemsPerPage: ITEMS_PER_PAGE,
		filteredList:[],
		displayResultText : '',
		statusList : []
    },
    created : function() {
    	get(this, contextPath + "/api/get-category-list" , {}, function(data) {
			if (data.status == STATUS_NORMAL) {
				this.categoryList = data.dataInfo;
			}
		});
    	this.loadStory();
    },
    methods: {
    	loadStory: function(){
    		if(!sessionStorage.getItem("PARAM_STORY_ID")) {
    			window.location.href= contextPath + "/admin_story_search.html";
        		return;
    		}
    		sessionStorage.removeItem("PARAM_NEWEST_CHAPTER_NAME");
    		sessionStorage.removeItem("PARAM_CHAPTER_INFO");
    		var id = sessionStorage.getItem("PARAM_STORY_ID");
    		get(this, contextPath + "/api/admin-get-story-detail/" + id , {}, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				this.storyData = data.dataInfo;
    			}
    		});
    		get(this, contextPath + "/api/get-m-wide-list/1" , {}, function(data) {
    			
    			if (data.status == STATUS_NORMAL) {
    				this.statusList = data.dataInfo;
    			} else {
    				this.error_message = data.errorMessage;
    			}
    		});
    		this.search();
    	},
    	update : function() {
    		this.error_message = '';
    		if(!this.storyData.name) {
    			this.error_message = '<p>Vui lòng nhập tên truyện</p>';
    		}
    		if(!this.storyData.keySearch) {
    			this.error_message = '<p>Vui lòng nhập tên đánh dấu</p>';
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
    		this.storyData.registType = '1';
    		post(this, contextPath + "/api/admin-regist-story" , {json:JSON.stringify(this.storyData)}, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				alert("Đã tạo truyện mới thành công");
    			} else {
    				this.error_message = data.errorMessage;
    			}
    		});
    	},
    	chapterDetails : function(id) {
    		sessionStorage.setItem("PARAM_STORY_ID",this.storyData.id);
    		sessionStorage.setItem("PARAM_CHAPTER_ID",id);
    		sessionStorage.setItem("PARAM_NEWEST_CHAPTER_NAME",this.storyData.chapterName);
    		window.location.href= contextPath + "/admin_chapter_new.html";
    	},
    	createChapter : function() {
    		sessionStorage.setItem("PARAM_STORY_ID",this.storyData.id);
    		sessionStorage.setItem("PARAM_CHAPTER_ID",'');
    		sessionStorage.setItem("PARAM_NEWEST_CHAPTER_NAME",this.storyData.chapterName);
    		window.location.href= contextPath + "/admin_chapter_new.html";
    	},search: function(){
    		var this_ = this;
    		this.error_message = '';
    		this_.chaptersList = [];
    		this_.filteredList = [];
    		post(this_, contextPath + "/api/admin-get-list-chapters" , {json: JSON.stringify({storyId:sessionStorage.getItem("PARAM_STORY_ID")})}, function(data) {
    			
    			if (data.status == STATUS_NORMAL) {
    	    		var offset = $('#topScroll').offset();
    	    		var topPosition = offset.top;
    	    		$('body,html').animate({
    	    			scrollTop: topPosition
    	    		}, 300);
    				
    	    		this_.chaptersList = data.dataInfo;
    	    		this_.dataCount = data.dataInfo.length;
    	    		this_.sortList();
    	    		
    	    		this.currentPage= 0;
    	        	if ( sessionStorage.getItem("PARAM_CURRENT_PAGE")) {
    	        		this.currentPage = sessionStorage.getItem("PARAM_CURRENT_PAGE");
    	        		sessionStorage.removeItem("PARAM_CURRENT_PAGE");
    	        	}
    	    		
                    $('#pagination').twbsPagination('destroy');
    				$('#pagination').twbsPagination({
			            totalPages: this_.totalPages,
			            visiblePages: this_.itemsPerPage,
			            startPage : Number(this_.currentPage) + 1,
			            onPageClick: function (event, page) {
			            	this_.setPage(page -1);
			            	
			            	var index = this_.currentPage * this_.itemsPerPage;
			            	var indexFirst = index + 1;
			            	var lastIndex = index + this_.itemsPerPage;
			            	if(lastIndex > this_.dataCount) {
			            		lastIndex = this_.dataCount;
			            	}
			            	this_.displayResultText = 'displaying ' + indexFirst  + ' to ' + '' + lastIndex;
			            }
    				       
    				 });
     				$('#kekka_list').show();
    			} else {
    				$('#kekka_list').hide();
    				if(data.status != STATUS_NO_DATA) {
    					this_.error_message = data.errorMessage;
    				}
    			}
    		});
    	},
    	sortList: function(){
    		var _el = this;
    		var index = this.currentPage * this.itemsPerPage;
    		this.filteredList = this.chaptersList.slice(index, index + this.itemsPerPage);
    	},
    	setPage: function(pageNumber) {
    		this.currentPage = pageNumber;
			this.sortList();
    	},
    },
	watch: {
		
	},
	computed : {
		totalPages : function() {
			return Math.ceil(this.chaptersList.length / this.itemsPerPage);
		},
	}
});


