var vueItem = new Vue({
    el: '#main',
    data:{
    	error_message : '',
    	resultList:[],
    	filteredList:[],
    	dataCount:0,
    	currentPage: 0,
		itemsPerPage: ITEMS_PER_PAGE,
    },
    created : function() {
    	getContentMenu();
    	this.getList();
    },
    methods: {
    	getList: function(){
    		
    	},
    },
    computed : {
		totalPages : function() {
			return Math.ceil(this.resultList.length / this.itemsPerPage);
		},
	}, 
	watch: {
		
	},
});


