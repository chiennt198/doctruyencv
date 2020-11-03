var protocol = location.protocol;
var host = location.host;
//send the request as application/x-www-form-urlencoded MIME type

//セッションストレージ
// ウェブストレージに対応している
function logout(context) {
	var prokind = '';
	var userFlg = '';
	if (sessionStorage)
	{
		prokind = sessionStorage.getItem("PROC_KIND");
		userFlg = sessionStorage.getItem("USER_SCREEN_FLG");
		sessionStorage.clear();
	}
	if(prokind && prokind == C_ADMIN_PROC_KIND_CONFIRM_BY_KAKUNINSYA) {
		location.href=contextPath + "/html/kakuninsya_login.html";
	} else if(userFlg =='1') {
		location.href=contextPath + "/html/login.html";
	} else {
		location.href=contextPath + "/html/admin_login.html";
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

function getBunyaMaster(context, listObj,listObjName) {
	get(context, contextPath + '/common/get-mbunya-list', {gakkaiCd : sessionStorage.getItem("GAKKAI_CD")}, function(data) {
		if (data.status == STATUS_NORMAL) {
			var masterData = data.dataInfo;
			for(var i = 0; i < masterData.length; i++){
				listObj.push({
					text : masterData[i].bunyaNm,
					value : masterData[i].bunyaCd
				});
				if (listObjName) {
					listObjName[masterData[i].bunyaCd] = masterData[i].bunyaNm;
				};
			};
		}  else {
			if (context.error_message) {
				context.error_message = data.errorMessage;
			} else if (context.errorMsg) {
				context.error_message = data.errorMessage;
			};
		};
	});
};

function getTaikaiMaster(context, listObj,categoryCd,listObjName) {
	get(context, contextPath + '/common/get-mtaikai-list', {categoryCd : categoryCd, gakkaiCd : sessionStorage.getItem("GAKKAI_CD")}, function(data) {
		if (data.status == STATUS_NORMAL) {
			var masterData = data.dataInfo;
			for(var i = 0; i < masterData.length; i++){
				listObj.push({
					text : masterData[i].taikaiNm,
					value : masterData[i].taikaiCd
				});
				if (listObjName) {
					listObjName[masterData[i].taikaiCd] = masterData[i].taikaiNm;
				};
			};
		}  else {
			if (context.error_message) {
				context.error_message = data.errorMessage;
			} else if (context.errorMsg) {
				context.error_message = data.errorMessage;
			};
		};
	});
};

function getNaiyouSyubetsuMaster(context, listObj,listObjName) {
	get(context, contextPath + '/common/get-mnaiyousyubetsu-list', {gakkaiCd : sessionStorage.getItem("GAKKAI_CD")}, function(data) {
		if (data.status == STATUS_NORMAL) {
			var masterData = data.dataInfo;
			for(var i = 0; i < masterData.length; i++){
				listObj.push({
					text : masterData[i].typeNm,
					value : masterData[i].typeCd
				});
				if (listObjName) {
					listObjName[masterData[i].typeCd] = masterData[i].typeNm;
				};
			};
		}  else {
			if (context.error_message) {
				context.error_message = data.errorMessage;
			} else if (context.errorMsg) {
				context.error_message = data.errorMessage;
			};
		};
	});
};
function getCategoryMaster(context, listObj,listObjName) {
	get(context, contextPath + '/common/get-mcategory-list', {}, function(data) {
		if (data.status == STATUS_NORMAL) {
			var masterData = data.dataInfo;
			for(var i = 0; i < masterData.length; i++){
				listObj.push({
					text : masterData[i].categoryNm,
					value : masterData[i].categoryCd
				});
				if (listObjName) {
					listObjName[masterData[i].categoryCd] = masterData[i].categoryNm;
				};
			};
		}  else {
			if (context.error_message) {
				context.error_message = data.errorMessage;
			} else if (context.errorMsg) {
				context.error_message = data.errorMessage;
			};
		};
	});
};


function getBasicRealmMaster(context, listObj,listObjName) {
	get(context, contextPath + '/common/get-basic-realm-list', {}, function(data) {
		if (data.status == STATUS_NORMAL) {
			var masterData = data.dataInfo;
			for(var i = 0; i < masterData.length; i++){
				listObj.push({
					text : masterData[i].realmNm,
					value : masterData[i].realmCd
				});
				if (listObjName) {
					listObjName[masterData[i].realmCd] = masterData[i].realmNm;
				};
			};
		}  else {
			if (context.error_message) {
				context.error_message = data.errorMessage;
			} else if (context.errorMsg) {
				context.error_message = data.errorMessage;
			};
		};
	});
};

function getMovieKindsList(listObj,listObjName,gSKbn) {
	if(gSKbn && gSKbn == '1') {
		listObj.push({
			text : '学術動画',
			value : C_MOVIE_KIND_GAKUJUTSU
		});
		listObj.push({
			text : 'コース',
			value : C_MOVIE_KIND_COURSE
		});
		if (listObjName) {
			listObjName[C_MOVIE_KIND_GAKUJUTSU] = '学術動画';
			listObjName[C_MOVIE_KIND_COURSE] = 'コース';
		};
	} else {
		listObj.push({
			text : '学術動画',
			value : C_MOVIE_KIND_GAKUJUTSU_SPONSOR
		});
		listObj.push({
			text : 'コース',
			value : C_MOVIE_KIND_COURSE_SPONSOR
		});
		listObj.push({
			text : 'CM動画',
			value : C_MOVIE_KIND_CM
		});
		if (listObjName) {
			listObjName[C_MOVIE_KIND_GAKUJUTSU_SPONSOR] = '学術動画';
			listObjName[C_MOVIE_KIND_COURSE_SPONSOR] = 'コース';
			listObjName[C_MOVIE_KIND_CM] = 'CM動画';
		};
	}
};

function getAreaMaster(context, listObj,listObjName) {
	get(context, contextPath + '/common/get-area-list', {gakkaiCd : sessionStorage.getItem("GAKKAI_CD")}, function(data) {
		if (data.status == STATUS_NORMAL) {
			var masterData = data.dataInfo;
			for(var i = 0; i < masterData.length; i++){
				listObj.push({
					text : masterData[i].area,
					value : masterData[i].areaCd
				});
				if (listObjName) {
					listObjName[masterData[i].areaCd] = masterData[i].area;
				};
			};
		}  else {
			if (context.error_message) {
				context.error_message = data.errorMessage;
			} else if (context.errorMsg) {
				context.error_message = data.errorMessage;
			};
		};
	});
};

function getMAdmin(context, listObj,listObjName) {
	get(context, contextPath + '/common/get-m-admin-list', {gakkaiCd : sessionStorage.getItem("GAKKAI_CD")}, function(data) {
		if (data.status == STATUS_NORMAL) {
			var masterData = data.dataInfo;
			for(var i = 0; i < masterData.length; i++){
				listObj.push({
					text : masterData[i].type,
					value : masterData[i].cd
				});
				if (listObjName) {
					listObjName[masterData[i].cd] = masterData[i].type;
				};
			};
		}  else {
			if (context.error_message) {
				context.error_message = data.errorMessage;
			} else if (context.errorMsg) {
				context.error_message = data.errorMessage;
			};
		};
	});
};
function getWideMaster(context, listObj, dataIndex, listObjName) {
	get(context, contextPath + '/common/get-master-wide-list', {idx : dataIndex}, function(data) {
		if (data.status == STATUS_NORMAL) {
			if (data.status == STATUS_NORMAL) {
				var masterData = data.dataInfo;
				for(var i = 0; i < masterData.length; i++){
					listObj.push({
						text : masterData[i].name,
						value : masterData[i].cd
					});
					if (listObjName) {
						listObjName[masterData[i].cd] = masterData[i].name;
					};
				};
			} else {
				sessionStorage.clear();
				logout(context);
			};
		}  else {
			if (context.error_message) {
				context.error_message = data.errorMessage;
			} else if (context.errorMsg) {
				context.error_message = data.errorMessage;
			};
		};
	});
};

function getMSponsor(context, listObj, listObjName) {
	get(context, contextPath + '/common/get-sponsor-list', {}, function(data) {
		if (data.status == STATUS_NORMAL) {
			if (data.status == STATUS_NORMAL) {
				var masterData = data.dataInfo;
				for(var i = 0; i < masterData.length; i++){
					listObj.push({
						text : masterData[i].sponsorNm,
						value : masterData[i].sponsorCd
					});
					if (listObjName) {
						listObjName[masterData[i].sponsorCd] = masterData[i].sponsorNm;
					};
				};
			} else {
				sessionStorage.clear();
				location.href=logout(context);
			};
		}  else {
			if (context.error_message) {
				context.error_message = data.errorMessage;
			} else if (context.errorMsg) {
				context.error_message = data.errorMessage;
			};
		};
	});
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

function getMEnqCagory(context, listObj, listObjName) {
	get(context, contextPath + '/common/get-enq-category-list', {gakkaiCd : sessionStorage.getItem("GAKKAI_CD")}, function(data) {
		if (data.status == STATUS_NORMAL) {
			if (data.status == STATUS_NORMAL) {
				var masterData = data.dataInfo;
				for(var i = 0; i < masterData.length; i++){
					listObj.push({
						text : masterData[i].categoryNm,
						value : masterData[i].categoryCd
					});
					if (listObjName) {
						listObjName[masterData[i].categoryCd] = masterData[i].categoryNm;
					};
				};
			} else {
				sessionStorage.clear();
				location.href=logout(context);
			};
		}  else {
			if (context.error_message) {
				context.error_message = data.errorMessage;
			} else if (context.errorMsg) {
				context.error_message = data.errorMessage;
			};
		};
	});
}