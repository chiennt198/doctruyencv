var vueItem = new Vue({
    el: '#main',
    data:{
    	error_message : '',
    	storyList:[],
    	filteredList:[],
    	dataCount:0,
    	currentPage: 0,
		itemsPerPage: ITEMS_PER_PAGE,
		storyNominationsList:[],
		storyNominationsCnt:0,
    },
    created : function() {
    	getContentMenu();
    	this.getList();
    },
    methods: {
    	getList: function(){
    		this.error_message = '';
    		this.storyNominationsCnt = 0;
    		this.storyList = [];
    		this.filteredList = [];
    		this.storyNominationsList = [];
    		this.dataCount = [];
    		var this_ = this;
    		
    		get(this, contextPath + "/get-story-items" , {}, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				var storyItems = data.dataInfo;
    				this.storyList = storyItems.storyList;
    				this.storyNominationsList = storyItems.storyNominationsList;
    				this.sortList();
    				
    				if (this.storyList != null) {
    					this.dataCount = this.storyList.length;
    				}
    				
    				if (this.storyNominationsList != null) {
    					this.storyNominationsCnt = this.storyNominationsList.length;
    				}
    				
    				$('#pagination').twbsPagination('destroy');
    				$('#pagination').twbsPagination({
			            totalPages: this_.totalPages,
			            visiblePages: this_.itemsPerPage,
			            startPage : Number(this_.currentPage) + 1,
			            onPageClick: function (event, page) {
			            	this_.setPage(page -1);
			            }
    				 });
    				
    				
    				
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
    },
    computed : {
		totalPages : function() {
			return Math.ceil(this.storyList.length / this.itemsPerPage);
		},
	}, 
	watch: {
		
	},
});


