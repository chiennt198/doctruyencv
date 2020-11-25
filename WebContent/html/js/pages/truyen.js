var vueItem = new Vue({
    el: '#main',
    data:{
    	error_message : '',
    	storyInfo:{},
    	chapterList:[],
    	dataCount:0,
    	currentPage: 0,
		itemsPerPage: ITEMS_PER_PAGE,
		storyKey: '',
		totalPages:0,
		viewFlg:1,
		orderBy:1,
    },
    created : function() {
    	this.storyKey = getURLParameter('storyKey');
    	
    	if (!this.storyKey ) {
    		window.location.href= contextPath + "/html/trang_chu.html";
    		return;
    	}
    	
    	getContentMenu();
    	this.getChapterList();
    },
    methods: {
    	getChapterList: function(){
    		this.error_message = '';
    		this.chapterList = [];
    		this.dataCount = 0;
    		var this_ = this;
    		
    		get(this, contextPath + "/api/get-story-info/" + this.storyKey , {}, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				var storyItems = data.dataInfo;
    				this.storyInfo = storyItems.storyInfo;
    				this.totalPages = storyItems.totalPages;
    				this.dataCount = Number(storyItems.dataCnt);
    				
    				$('#pagination').twbsPagination({
    		            totalPages: this_.totalPages,
    		            visiblePages: 3,
    		            startPage : 1,
    		            onPageClick: function (event, page) {
    		            	this_.getPagingList(page - 1)
    		            }
    				});
    				
    			} else {
    				$('#pagination').twbsPagination('destroy');
    				this.error_message = data.errorMessage;
    			}
    		});
    	},
    	getPagingList: function(page){
    		this.error_message = '';
    		this.chapterList = [];
    		
    		if (page == -1) {
    			$('#pagination').twbsPagination('destroy');
    			this.viewFlg = 0;
    		}
    		
    		this.currentPage = page;
    		get(this, contextPath + "/api/get-story-info/" + this.storyKey , {currentPage: page, pagingFlg:'1', orderBy: this.orderBy}, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				this.chapterList = data.dataInfo;
    			} else {
    				this.error_message = data.errorMessage;
    			}
    		});
    	},
    	getChapter: function(chapterKey){
    		window.location.href= contextPath + "/html/doc_truyen.html?storyKey=" + this.storyKey + "&chapterKey=" + chapterKey;
    	},
    	orderByPaging: function(order){
    		this.orderBy = order;
    		this.getPagingList(this.currentPage);
    	},
    },
});


