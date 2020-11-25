var protocol = location.protocol;
var host = location.host;
//send the request as application/x-www-form-urlencoded MIME type

//セッションストレージ
// ウェブストレージに対応している
function logout(context) {
	var url = window.location.href;
	if ( url && url.indexOf('admin') > -1) {
		window.location.href = contextPath + '/html/admin_login.html';
	}
}
//++++++++++++++++++++++++++
//HTTP CONNECTTION
//++++++++++++++++++++++++++
function post(context, url, data, successfunc) {
	showLoading();
	var submitData = {
			adminBean: sessionStorage.getItem('ADMIN_BEAN'),
			adminId: sessionStorage.getItem('ADMIN_ID'),
			adminRole: sessionStorage.getItem('ADMIN_ROLE'),
	}
	$.extend(submitData, data);

	setTimeout(function() {
		$.ajax({
			url : url,
			type : 'post',
			async: false,
			headers: {},
			data : submitData,
			success : function (data, status, request) {
				if (successfunc) {
					try {
						data = JSON.parse(data);
					} catch (e) {

					}
					successfunc.call(context, data, status, request);
				} else {
					var doc = document.open("text/html");
					doc.write(data);
					doc.close();
				}
				hideLoading();
			},
			error : function (data, status, request) {
				hideLoading();
				logout(context);
		    }
		});
    }, 1);

}

function put(context, url, data, successfunc) {
	showLoading();
	var submitData = {
			adminBean: sessionStorage.getItem('ADMIN_BEAN'),
			adminId: sessionStorage.getItem('ADMIN_ID'),
			adminRole: sessionStorage.getItem('ADMIN_ROLE'),
	}
	$.extend(submitData, data);
	setTimeout(function() {
		$.ajax({
			url : url,
			type : 'put',
			async: false,
			headers: {},
			data : submitData,
			success : function (data, status, request) {
				if (successfunc) {
					try {
						data = JSON.parse(data);
					} catch (e) {

					}
					successfunc.call(context, data, status, request);
				} else {
					var doc = document.open("text/html");
					doc.write(data);
					doc.close();
				}
				hideLoading();
			},
			error : function (data, status, request) {
				hideLoading();
				logout(context);
		    }
		});
	}, 1);

}

function get(context, url, data, successfunc) {
	showLoading();
	var submitData = {
			adminBean: sessionStorage.getItem('ADMIN_BEAN'),
			adminId: sessionStorage.getItem('ADMIN_ID'),
			adminRole: sessionStorage.getItem('ADMIN_ROLE'),
	}
	$.extend(submitData, data);
	setTimeout(function() {
		$.ajax({
			url : url,
			type : 'get',
			async: false,
			headers: {},
			data : submitData,
			success : function (data, status, request) {
				if (successfunc) {
					try {
						data = JSON.parse(data);
					} catch (e) {

					}
					successfunc.call(context, data, status, request);
				} else {
					var doc = document.open("text/html");
					doc.write(data);
					doc.close();
				}
				hideLoading();
			},
			error : function (data, status, request) {
				hideLoading();
				logout(context);
		    }
		});
	}, 1);
}

function httpsDelete(context, url, data, successfunc) {
	showLoading();
	var submitData = {
		adminBean: sessionStorage.getItem('ADMIN_BEAN'),
		adminId: sessionStorage.getItem('ADMIN_ID'),
		adminRole: sessionStorage.getItem('ADMIN_ROLE'),
	}
	$.extend(submitData, data);
	setTimeout(function() {
		$.ajax({
			url : url,
			type : 'delete',
			async: false,
			headers: {'AUTH-DATA' : sessionStorage.getItem(PARAM_AUTH_DATA)},
			data : submitData,
			success : function (data, status, request) {
				if (successfunc) {
					try {
						data = JSON.parse(data);
					} catch (e) {

					}
					successfunc.call(context, data, status, request);
				} else {
					var doc = document.open("text/html");
					doc.write(data);
					doc.close();
				}
				hideLoading();
			},
			error : function (data, status, request) {
				hideLoading();
				logout(context);
		    }
		});
	}, 1);

}

function downloadCSVFile(pdfData,fileName) {
	var byteArray = new Uint8Array(pdfData);
	var a = window.document.createElement('a');
	if(window.navigator.msSaveOrOpenBlob) {
		blobObject = new Blob([byteArray], { type: 'application/octet-stream' });
		window.navigator.msSaveOrOpenBlob(blobObject, fileName + ".csv");
	} else {
		a.href = window.URL.createObjectURL(new Blob([byteArray], { type: 'application/octet-stream' }));
		a.download = fileName + ".csv";

		// Append anchor to body.
		document.body.appendChild(a)
		a.click();

		// Remove anchor from body
		document.body.removeChild(a)
	}
}
function getCurrentDateTime() {
	var currentdate = new Date();
	var datetime = currentdate.getFullYear() + ''
					+ pad((currentdate.getMonth()+1), 2) + ''
					+ pad(currentdate.getDate(), 2) + ''
	                + pad(currentdate.getHours(), 2) + ''
	                + pad(currentdate.getMinutes(), 2) + ''
	                + pad(currentdate.getSeconds(), 2) + '';
	return datetime;
}
function pad(num, size) {
    var s = num+"";
    while (s.length < size) s = "0" + s;
    return s;
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
