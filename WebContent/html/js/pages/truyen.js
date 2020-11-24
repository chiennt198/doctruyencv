var vueItem = new Vue({
    el: '#main',
    data:{
    	error_message : '',
    	storyInfo:{},
    	chapterList:[],
    	dataCount:0,
    	currentPage: 0,
		itemsPerPage: ITEMS_PER_PAGE,
		keySearch: '',
    },
    created : function() {
    	debugger;
    	var url = window.location.href.split('/');
    	var keySearch = url[url.length - 1];
    	this.keySearch = keySearch ? keySearch : sessionStorage.getItem("PARAM_STORY_ID");
    	
    	if (!this.keySearch ) {
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
    		this.filteredList = [];
    		this.dataCount = 0;
    		var this_ = this;
    		
    		get(this, contextPath + "/api/get-story-info/" + this.keySearch , {}, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				var storyItems = data.dataInfo;
    				this.storyInfo = storyItems.storyInfo;
    				this.totalPages = storyItems.totalPages;
    				this.dataCount = Number(storyItems.dataCnt);
    				
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
    		get(this, contextPath + "/api/get-story-info/" + this.keySearch , {currentPage: page, pagingFlg:'1'}, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				this.chapterList = data.dataInfo.storyList;
    			} else {
    				this.error_message = data.errorMessage;
    			}
    		});
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

function getURLParameter(name) {
	return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1].replace(/\+/g, '%20')) || null;
}
