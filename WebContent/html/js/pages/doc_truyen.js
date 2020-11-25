var vueItem = new Vue({
    el: '#main',
    data:{
    	error_message : '',
    	chapterInfo:{},
    	storyKey:'',
    	chapterKey:'',
    	setting:{
    		fontSize:'20px',
    		background:'',
    		color:''
    	},
    	config:'0',
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
    	openSetting: function(flg){
    		this.config = flg;
    	},
    	settingFun: function(){
    		$('.sonsyu').css("font-size",this.setting.fontSize );
    		$('.sonsyu').css("background-color",this.setting.background );
    		$('.sonsyu').css("color",this.setting.color );
    	},
    	settingColor: function(type, colorType){
    		
    		if ( colorType == '1' ) {
    			if (type == '1') {
        			this.setting.background = '#f5e4e4';
        		} else if (type == '2') {
        			this.setting.background = '#f5ebcd';
        		} else if (type == '3') {
        			this.setting.background = '#e2eee2';
        		} else if (type == '4') {
        			this.setting.background = '#e1e8e8';
        		} else if (type == '5') {
        			this.setting.background = '#eae4d3';
        		} else if (type == '6') {
        			this.setting.background = '#e5e3df';
        		} else if (type == '7') {
        			this.setting.background = '#222';
        		} else if (type == '8') {
        			this.setting.background = '#fff';
        		}
    		} else {
    			if (type == '1') {
        			this.setting.color = '#f5e4e4';
        		} else if (type == '2') {
        			this.setting.color = '#f5ebcd';
        		} else if (type == '3') {
        			this.setting.color = '#e2eee2';
        		} else if (type == '4') {
        			this.setting.color = '#e1e8e8';
        		} else if (type == '5') {
        			this.setting.color = '#eae4d3';
        		} else if (type == '6') {
        			this.setting.color = '#e5e3df';
        		} else if (type == '7') {
        			this.setting.color = '#222';
        		} else if (type == '8') {
        			this.setting.color = '#fff';
        		}
    		}
    		
    		this.settingFun();
    	}
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
