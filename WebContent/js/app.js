var protocol = location.protocol;
var host = location.host;
//send the request as application/x-www-form-urlencoded MIME type
var img3 = new Image();
img3.src = "./img/ajax_loader.gif";
//セッションストレージ
// ウェブストレージに対応している
function logout() {
	location.href=contextPath + "/login.html";
}
//++++++++++++++++++++++++++
//HTTP CONNECTTION
//++++++++++++++++++++++++++
function post(context, url, data, successfunc) {
	showLoading();
	var submitData = {
		userId: sessionStorage.getItem("KAIIN_ID"),
	}
	$.extend(submitData, data);

	setTimeout(function() {
		$.ajax({
			url : url,
			type : 'post',
			async: false,
			headers: {'AUTH-DATA' : AUTH-DATA},
			data : submitData,
			cache: false,
			success : function (data, status, request) {
				if (successfunc) {
					try {
						data = JSON.parse(data);
					} catch (e) {

					}
					successfunc.call(context, data, status, request);
				} else {
					var doc = document.open("text");
					doc.write(data);
					doc.close();
				}
				hideLoading();
			},
			error : function (data, status, request) {
				hideLoading();
		    }
		});
    }, 1);

}

function get(context, url, data, successfunc) {
	showLoading();
	var submitData = {
			userId: sessionStorage.getItem("KAIIN_ID")
	}
	$.extend(submitData, data);
	setTimeout(function() {
		$.ajax({
			url : url,
			type : 'get',
			async: false,
			headers: {'AUTH-DATA' : AUTH-DATA},
			data : submitData,
			cache: false,
			success : function (data, status, request) {
				if (successfunc) {
					try {
						data = JSON.parse(data);
					} catch (e) {

					}
					successfunc.call(context, data, status, request);
				} else {
					var doc = document.open("text");
					doc.write(data);
					doc.close();
				}
				hideLoading();
			},
			error : function (data, status, request) {
				hideLoading();
		    }
		});
	}, 1);
}
//++++++++++++++++++++++++++
//SHOW LOADING
//++++++++++++++++++++++++++
function showLoading(hideId) {
	hideId = '';
	if ((hideId && document.getElementById('div_overlay' + hideId)) ||
			   (!hideId && document.getElementById('div_overlay'))) {
			  var element = document.getElementById('loading');
			  if (element) {
			   return false;
			  }
	}

	var body = document.body,
	html = document.documentElement;

	var height = Math.max( body.scrollHeight, body.offsetHeight,
                  html.clientHeight, html.scrollHeight, html.offsetHeight );
	var overlayId = 'div_overlay';
	if (hideId) {
		overlayId = 'div_overlay' + hideId;
	}
	// Adds a overlay
	var oDiv = document.createElement('div');
	oDiv.setAttribute('id', overlayId);
	oDiv.setAttribute("class", "black_overlay");
	oDiv.style.display='block';
//	oDiv.style.height= height +  'px';
	oDiv.style.height= '8000px';
//	oDiv.style.height= '100%';
	body.appendChild(oDiv);

	// Adds loading
	var lDiv = document.createElement('div');
	lDiv.setAttribute('id','loading');
	lDiv.setAttribute("class", "loading");
	lDiv.style.display='block';
	body.appendChild(lDiv);

}

function hideLoading(hideId) {
	setTimeout(function() {
	var body = document.body;
	// Removes loading
	if ((hideId && document.getElementById('div_overlay' + hideId)) ||
			(!hideId && document.getElementById('div_overlay'))) {
		var element = document.getElementById('loading');
		if (element) {
			body.removeChild(element);
		}
	}

	// Removes a overlay box
	var overlayId = 'div_overlay';
	if (hideId) {
		overlayId = 'div_overlay' + hideId;
	}
	var element = document.getElementById(overlayId);
	if (element) {
		body.removeChild(element);
	}
	},1)
}

$(function(){
	$('#header').load("header.html");
	$('#footer').load("footer.html");
});
