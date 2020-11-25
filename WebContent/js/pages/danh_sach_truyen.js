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
			orderKey: '2',
		},
		categoryItem:{},
    },
    created : function() {
    	
    	getContentMenu();
    	
    	if (!sessionStorage.getItem("PARAM_CATEGORY_ITEM")) {
    		window.location.href= contextPath + "/Home.html";
    		return;
    	}
    	
    	this.categoryItem = JSON.parse(sessionStorage.getItem("PARAM_CATEGORY_ITEM"));
    	this.loadDefault();
    	this.getStoryList('2');
    	
    },
    methods: {
    	loadDefault: function(){
    		get(this, contextPath + "/api/get-category-list" , {}, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				this.categoryList = data.dataInfo;
    			}
    		});
    		
    		get(this, contextPath + "/api/get-m-wide-list/1" , {}, function(data) {
    			
    			if (data.status == STATUS_NORMAL) {
    				this.statusList = data.dataInfo;
    			} else {
    				this.error_message = data.errorMessage;
    			}
    		});
    	},
    	getStoryList: function(orderKey){
    		this.error_message = '';
    		this.storyList = [];
    		this.filteredList = [];
    		this.dataCount = 0;
    		var this_ = this;

    		this.condInfo.categoryId = this.categoryItem.categoryId;
    		this.condInfo.orderKey = orderKey;
    		this.condInfo.currentPage = this.currentPage;
    		this.condInfo.itemsPerPage = this.itemsPerPage;
    		
    		get(this, contextPath + "/get-story-list", this.condInfo, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				this.storyList = data.dataInfo;
    				
    				if ( this.storyList != null ) {
    					this.dataCount = this.storyList.length;
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
    		this.filteredList = this.storyList.slice(index, index + this.itemsPerPage);
    	},
    	setPage: function(pageNumber) {
    		this.currentPage = pageNumber;
			this.sortList();
    	},
    	getStory: function(storyId){
    		debugger;
    		sessionStorage.setItem("PARAM_STORY_ID",storyId);
    		window.location.href= contextPath + "/truyen.html";
    	},
    },
    computed : {
		totalPages : function() {
			return Math.ceil(this.storyList.length / this.itemsPerPage);
		},
	}, 
});


