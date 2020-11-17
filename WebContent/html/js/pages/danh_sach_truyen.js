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
			orderStory: '1',
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
    		
    		get(this, contextPath + "/get-story-list", {}, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				this.storyList = data.dataInfo;
    				
    				if ( this.storyList != null ) {
    					this.dataCount = this.storyList.length;
    					this.sortList();
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
    				
    			} else {
    				this.error_message = data.errorMessage;
    			}
    		});
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
    	getStory: function(storyId){
    		sessionStorage.setItem("PARAM_STORY_ID",storyId);
    		window.location.href= contextPath + "/html/truyen.html";
    	},
    },
    computed : {
		totalPages : function() {
			return Math.ceil(this.storyList.length / this.itemsPerPage);
		},
	}, 
});


