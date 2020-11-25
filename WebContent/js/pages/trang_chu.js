var vueItem = new Vue({
    el: '#main',
    data:{
    	error_message : '',
    	storyList:[],
    	dataCount:0,
		randomLst:[],
		randomCnt:0,
		totalPages:0
    },
    created : function() {
    	this.removeSes();
    	getContentMenu();
    	this.loadDefault();
    },
    methods: {
    	loadDefault: function(){
    		this.error_message = '';
    		this.randomCnt = 0;
    		this.randomLst = [];
    		this.dataCount = [];
    		var this_ = this;
    		
    		get(this, contextPath + "/api/get-story-items" , {}, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				var storyItems = data.dataInfo;
    				
    				this.totalPages = storyItems.totalPages;
    				this.dataCount = Number(storyItems.storyCnt);
    				
    				this.randomLst = storyItems.randomLst;
    				if (this.randomLst != null) {
    					this.randomCnt = this.randomLst.length;
    				}
    				
    				$('#pagination').twbsPagination('destroy');
    				$('#pagination').twbsPagination({
    		            totalPages: this_.totalPages,
    		            visiblePages: 3,
    		            startPage : 1,
    		            onPageClick: function (event, page) {
    		            	this_.getPagingList(page - 1)
    		            }
    				 });
    				
    				
    			} else {
    				this.error_message = data.errorMessage;
    			}
    		});
    	},
    	getPagingList: function(page){
    		this.error_message = '';
    		this.storyList = [];
    		get(this, contextPath + "/api/get-story-items" , {currentPage: page, pagingFlg:'1'}, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				this.storyList = data.dataInfo;
    			} else {
    				this.error_message = data.errorMessage;
    			}
    		});
    	},
    	getStory: function(keySearch){
    		window.location.href = contextPath + "/html/truyen.html?storyKey=" + keySearch;
    	},
    	removeSes: function(){
    		sessionStorage.removeItem("PARAM_CATEGORY_ITEM");
    	},
    },
});


