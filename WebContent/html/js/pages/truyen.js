var vueItem = new Vue({
    el: '#main',
    data:{
    	error_message : '',
    	storyInfo:{},
    	chapterList:[],
    	filteredList:[],
    	dataCount:0,
    	currentPage: 0,
		itemsPerPage: ITEMS_PER_PAGE,
    },
    created : function() {
    	
    	if (!sessionStorage.getItem("PARAM_STORY_ID")) {
    		window.location.href= contextPath + "/html/Home.html";
    		return;
    	}
    	
    	getContentMenu();
    	this.getChapterList();
    },
    methods: {
    	getChapterList: function(){
    		this.error_message = '';
    		this.chapterList = [];
    		this.filteredList = [];
    		this.dataCount = 0;
    		var this_ = this;
    		
    		get(this, contextPath + "/get-story-info/" + sessionStorage.getItem("PARAM_STORY_ID") , {}, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				var storyItems = data.dataInfo;
    				this.storyInfo = storyItems.storyInfo;
    				this.chapterList = storyItems.chapterList;
    				
    				if ( this.chapterList != null ) {
    					this.dataCount = this.chapterList.length;
    					this.sortList();
    					
    					if (this.dataCount > 0) {
    						$('#pagination').twbsPagination('destroy');
            				$('#pagination').twbsPagination({
        			            totalPages: this_.totalPages,
        			            visiblePages: 3,
        			            startPage : Number(this_.currentPage) + 1,
        			            onPageClick: function (event, page) {
        			            	this_.setPage(page -1);
        			            }
            				 });
    					}
    					
    				}
    				
    			} else {
    				this.error_message = data.errorMessage;
    			}
    		});
    	},
    	sortList: function(){
    		var _el = this;
    		var index = this.currentPage * this.itemsPerPage;
    		this.filteredList = this.chapterList.slice(index, index + this.itemsPerPage);
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
			return Math.ceil(this.chapterList.length / this.itemsPerPage);
		},
	}, 
});


