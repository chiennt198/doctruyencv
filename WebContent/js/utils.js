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
				get(this, contextPath + "/api/get-category-list" , {}, function(data) {
	    			if (data.status == STATUS_NORMAL) {
	    				this.contentList = data.dataInfo;
	    			}
	    		});
			},
			loadList: function(item){
				sessionStorage.setItem("PARAM_CATEGORY_ITEM", JSON.stringify(item));
				$('#' + item.categoryId).attr('class', 'active');
				window.location.href= contextPath + "/danh_sach_truyen.html";
				
			},
			isActive: function(item){
				
				if ( sessionStorage.getItem("PARAM_CATEGORY_ITEM") ) {
					var categoryItem = JSON.parse(sessionStorage.getItem("PARAM_CATEGORY_ITEM"));
					if ( categoryItem.categoryId == item.categoryId) {
						return true;
					}
				}
				
				return false
			},
		},
	});
}

function contentMenuTemplate(){
	var html  = '<ul>';
		html += '<li>';
		html += '<a href="./trang_chu.html">Trang chủ</a>';
		html += '</li>';
	
		html += '<li v-for="item in contentList" :class="{ active: isActive(item) }">';
		html += '<a style="cursor: pointer;" v-on:click="loadList(item)"><span v-html="item.categoryName"></span></a>';
		html += '</li>';
		html += '</ul>';
	return html;
}

function getURLParameter(name) {
	return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1].replace(/\+/g, '%20')) || null;
}
