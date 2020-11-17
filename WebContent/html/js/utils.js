function getContentMenu() {
	Vue.component('content-menu', {
		template: contentMenuTemplate(),
		data : function () {
			return {
				contentList : [],
			}
		},
		created : function () {
			this.loadPage();
		},
		methods: {
			loadPage: function(index, html){
				get(this, contextPath + "/get-category-list" , {}, function(data) {
	    			if (data.status == STATUS_NORMAL) {
	    				this.contentList = data.dataInfo;
	    			}
	    		});
			},
			loadList: function(index){
				
			},
		},
	});
}

function contentMenuTemplate(){
	var html  = '<ul>';
		html += '<li v-for="item in contentList">';
		html += '<a style="cursor: pointer;" v-on:click="loadList(item.categoryId)"><span v-html="item.categoryName"></span></a>';
		html += '</li>';
		html += '</ul>';
	return html;
}

