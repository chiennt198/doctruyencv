var vueItem = new Vue({
    el: '#main',
    data:{
    	error_message : '',
    	chapterInfo:{},
    	storyKey:'',
    	chapterKey:'',
    },
    created : function() {
    	
    	this.storyKey = getURLParameter('storyKey');
    	this.chapterKey = getURLParameter('chapterKey');
    	
    	if (!this.storyKey || !this.chapterKey) {
    		window.location.href= contextPath + "/html/trang_chu.html";
    		return;
    	}
    	
    	getContentMenu();
    	this.getChapter();
    },
    methods: {
    	getChapter: function(){
    		this.error_message = '';
    		this.chapterInfo = {};
    		
    		get(this, contextPath + "/api/get-chapter-details/" + this.storyKey + "/" + this.chapterKey, {}, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				this.chapterInfo = data.dataInfo;
    			}
    		});
    	},
    	loadPage: function(page){
    		var chapterKey = '';
    		if ( page == 'next' ) {
    			chapterKey = this.chapterInfo.keySearchNext;
    		} else {
    			chapterKey = this.chapterInfo.keySearchPre;
    		}
    		
    		if (  chapterKey ) {
    			window.location.href= contextPath + "/html/doc_truyen.html?storyKey=" + this.storyKey + "&chapterKey=" + chapterKey;
    		}
    		
    	},
    	back: function(){
    		window.location.href= contextPath + "/html/truyen.html?storyKey=" + this.storyKey;
    	},
    },
});

$(function() {
	document.addEventListener("keydown", keyDownTextField, false);
	
	function keyDownTextField(e) {
		var keyCode = e.keyCode;
		if(keyCode == 37) {
			vueItem.loadPage('pre');
		} else if(keyCode== 39) {
			vueItem.loadPage('next');
		}
	}
})
