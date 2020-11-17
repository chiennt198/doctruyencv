var vueItem = new Vue({
    el: '#main',
    data:{
    	error_message : '',
    	storyList:{},
    	filteredList:[],
    	dataCount:0,
    	currentPage: 0,
		itemsPerPage: ITEMS_PER_PAGE,
		categoryList:[],
		statusList:[],
		condInfo:{
			orderStori: '1',
		},
    },
    created : function() {
    	
    	getContentMenu();
    	this.loadDefault();
    	this.getStoryList();
    	
    },
    methods: {
    	loadDefault: function(){
    		get(this, contextPath + "/get-category-list" , {}, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				this.categoryList = data.dataInfo;
    			}
    		});
    		
    		get(this, contextPath + "/get-m-wide-list/1" , {}, function(data) {
    			
    			if (data.status == STATUS_NORMAL) {
    				this.statusList = data.dataInfo;
    			} else {
    				this.error_message = data.errorMessage;
    			}
    		});
    	},
    	getStoryList: function(){
    		this.error_message = '';
    		this.storyList = [];
    		this.filteredList = [];
    		this.dataCount = 0;
    		var this_ = this;

    		if (sessionStorage.getItem("PARAM_CATEGORY_ID")) {
    			this.condInfo.categoryId = sessionStorage.getItem("PARAM_CATEGORY_ID");
    		}
    		
//    		get(this, contextPath + "/get-story-info/" + sessionStorage.getItem("PARAM_STORY_ID") , {}, function(data) {
//    			if (data.status == STATUS_NORMAL) {
//    				var storyItems = data.dataInfo;
//    				this.storyInfo = storyItems.storyInfo;
//    				this.chapterList = storyItems.chapterList;
//    				
//    				if ( this.storyList != null ) {
//    					this.dataCount = this.chapterList.length;
//    					this.sortList();
//    					$('#pagination').twbsPagination('destroy');
//        				$('#pagination').twbsPagination({
//    			            totalPages: this_.totalPages,
//    			            visiblePages: 3,
//    			            startPage : Number(this_.currentPage) + 1,
//    			            onPageClick: function (event, page) {
//    			            	this_.setPage(page -1);
//    			            }
//        				 });
//    				}
//    				
//    			} else {
//    				this.error_message = data.errorMessage;
//    			}
//    		});
    	},
    	sortList: function(){
    		var _el = this;
    		var index = this.currentPage * this.itemsPerPage;
    		this.filteredList = this.storyList.slice(index, index + this.itemsPerPage);
    	},
    	setPage: function(pageNumber) {
    		this.currentPage = pageNumber;
			this.sortList();
    	},
    	getChapter: function(chapterId){
    		sessionStorage.setItem("PARAM_CHAPTER_ID", chapterId);
    		window.location.href= contextPath + "/html/doc_truyen.html";
    	},
    },
    computed : {
		totalPages : function() {
			return Math.ceil(this.storyList.length / this.itemsPerPage);
		},
	}, 
});


