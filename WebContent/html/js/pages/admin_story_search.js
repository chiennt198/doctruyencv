var movieList = new Vue({
    el: '#main',
    data:{
    	error_message : '',
    	storyList : [],
    	dataCount : 0,
    	currentPage: 0,
		itemsPerPage: ITEMS_PER_PAGE,
		filteredList:[],
		displayResultText : '',
		searchCond : {},
		statusList : []
    },
    created : function() {
    	$('#kekka_list').hide();
    	
    	get(this, contextPath + "/get-m-wide-list/1" , this.searchCond, function(data) {
			
			if (data.status == STATUS_NORMAL) {
				this.statusList = data.dataInfo;
			} else {
				this.error_message = data.errorMessage;
			}
		});
    },
    methods: {
    	storyDetails: function(movieId){
    		window.location.href= contextPath + "/html/admin_movie_detail.html";
    	},
    	
    	search: function(type){
    		var this_ = this;
    		this.error_message = '';
    		this_.storyList = [];
    		this_.filteredList = [];
    		post(this_, contextPath + "/admin-get-list-story" , {json: JSON.stringify(movieList.searchCond)}, function(data) {
    			
    			if (data.status == STATUS_NORMAL) {
    	    		var offset = $('#topScroll').offset();
    	    		var topPosition = offset.top;
    	    		$('body,html').animate({
    	    			scrollTop: topPosition
    	    		}, 300);
    				
    	    		this_.storyList = data.dataInfo;
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
    				this_.error_message = data.errorMessage;
    			}
    		});
    	},
    	createStory : function() {
    		window.location.href= contextPath + "/html/admin_story_new.html";
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
    	back : function() {
    		sessionStorage.removeItem("PARAM_MOVIE_COURSE_DETAILS");
    		window.location.href= contextPath + back();
    	}
    },computed : {
		totalPages : function() {
			return Math.ceil(this.storyList.length / this.itemsPerPage);
		},
	}
});
