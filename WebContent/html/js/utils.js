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
				sessionStorage.setItem("PARAM_CATEGORY_ID", index);
				$('#' + index).attr('class', 'active');
				window.location.href= contextPath + "/html/danh_sach_truyen.html";
			
				
			},
			isActive: function(index){
				if ( sessionStorage.getItem("PARAM_CATEGORY_ID") == index) {
					return true;
				}
				return false
			},
		},
	});
}

function contentMenuTemplate(){
	var html  = '<ul>';
		html += '<li v-for="item in contentList" :class="{ active: isActive(item.categoryId) }">';
		html += '<a style="cursor: pointer;" v-on:click="loadList(item.categoryId)"><span v-html="item.categoryName"></span></a>';
		html += '</li>';
		html += '</ul>';
	return html;
}

