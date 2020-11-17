var vueItem = new Vue({
    el: '#main',
    data:{
    	error_message : '',
    	chapterInfo:{},
    },
    created : function() {
    	
    	if (!sessionStorage.getItem("PARAM_STORY_ID") || !sessionStorage.getItem("PARAM_CHAPTER_ID")) {
    		window.location.href= contextPath + "/html/Home.html";
    		return;
    	}
    	
    	getContentMenu();
    	this.getChapter();
    },
    methods: {
    	getChapter: function(){
    		this.error_message = '';
    		this.chapterInfo = {};
    		
    		get(this, contextPath + "/get-chapter-details/" + sessionStorage.getItem("PARAM_STORY_ID") + "/" + sessionStorage.getItem("PARAM_CHAPTER_ID"), {}, function(data) {
    			if (data.status == STATUS_NORMAL) {
    				this.chapterInfo = data.dataInfo;
    			}
    		});
    	},
    	loadPage: function(page){
    		var chapterId = '';
    		if ( page == 'next' ) {
    			chapterId = this.chapterInfo.chapterIdNext;
    		} else {
    			chapterId = this.chapterInfo.chapterIdPre;
    		}
    		
    		if (  chapterId ) {
    			this.chapterInfo = {};
        		get(this, contextPath + "/get-chapter-details/" + sessionStorage.getItem("PARAM_STORY_ID") + "/" + chapterId, {}, function(data) {
        			
        			if (data.status == STATUS_NORMAL) {
        				this.chapterInfo = data.dataInfo;
        				$('body,html').animate({scrollTop: 0}, 300);
        			}
        		});
    		}
    		
    	},
    },
    computed : {
    	
	}, 
});

$(function() {
	document.addEventListener("keydown", keyDownTextField, false);
	
	function keyDownTextField(e) {
		alert(e.keyCode);
		var keyCode = e.keyCode;
		if(keyCode == 37) {
			vueItem.loadPage('pre');
		} else if(keyCode== 39) {
			vueItem.loadPage('next');
		}
	}
})
