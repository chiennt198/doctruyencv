var vueItem = new Vue({
    el: '#main',
    data:{
    	error_message : '',
    	storyList:{},
    	dataCount:0,
    	currentPage: 0,
		categoryList:[],
		statusList:[],
		condInfo:{
			orderKey: '2',
		},
		categoryItem:{},
		totalPages:0,
		orderKey:'2',
    },
    created : function() {
    	
    	getContentMenu();
    	if (!sessionStorage.getItem("PARAM_CATEGORY_ITEM")) {
    		window.location.href= contextPath + "/trang_chu.html";
    		return;
    	}
    	
    	this.categoryItem = JSON.parse(sessionStorage.getItem("PARAM_CATEGORY_ITEM"));
    	this.loadDefault();
    	
    	this.currentPage = 0;
    	this.getStoryList();
    	
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
    	getStoryList: function(){
    		this.error_message = '';
    		this.storyList = [];
    		this.dataCount = 0;
    		var this_ = this;

    		this.condInfo.categoryId = this.categoryItem.categoryId;
    		this.condInfo.key = this.categoryItem.key;
    		get(this, contextPath + "/api/get-story-list", this.condInfo, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				var storyItems = data.dataInfo;
    				this.totalPages = storyItems.totalPages;
    				this.dataCount = Number(storyItems.dataCnt);
    				
    				$('#pagination').twbsPagination('destroy');
    				
    				if ( this.totalPages == 1 ) {
    					this_.getPagingList();
    				} else {
    					$('#pagination').twbsPagination({
        		            totalPages: this_.totalPages,
        		            visiblePages: 3,
        		            startPage : 1,
        		            onPageClick: function (event, page) {
        		            	this_.currentPage = page - 1;
        		            	this_.getPagingList()
        		            }
        				});
    				}
    			} else {
    				$('#pagination').twbsPagination('destroy');
    				this.error_message = data.errorMessage;
    			}
    		});
    	},
    	getPagingList: function(){
    		this.error_message = '';
    		this.storyList = [];
    		this.condInfo.orderKey = this.orderKey;
    		this.condInfo.currentPage = this.currentPage;
    		this.condInfo.pagingFlg = '1';
    		this.condInfo.key = this.categoryItem.key;
    		get(this, contextPath + "/api/get-story-list", this.condInfo, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				this.storyList = data.dataInfo;
    			} else {
    				this.error_message = data.errorMessage;
    			}
    		});
    	},
    	sortBy: function(orderKey) {
    		this.orderKey = orderKey;
    		this.getPagingList(this.currentPage);
    	},
    	getStory: function(keySearch){
    		window.location.href = contextPath + "/truyen.html?storyKey=" + keySearch;
    	},
    },
});


