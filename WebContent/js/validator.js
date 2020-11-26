function isValidDate(strY, strM, strD) {
        var valid = true;

        var month = parseInt(strM,10);
        var day   = parseInt(strD,10);
        var year  = parseInt(strY,10);

        // Check the ranges of month and year
        if(year < 1000 || year > 3000 || month == 0 || month > 12)
            return false;

        var monthLength = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];

        // Adjust for leap years
        if(year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))
            monthLength[1] = 29;

        // Check the range of the day
        return day > 0 && day <= monthLength[month - 1];
}

function isNumeric(val) {
	if (!val) {
		return true;
	}
	return /^[-+]?[0-9]+$/.test(val);
}

function isRequired (val) {
	if (val) {
		return true;
	} else {
		return false;
	}
}

function checkPhone(val) {
	if (!val) {
		return true;
	}
	return /^\d{1,4}-\d{1,4}-\d{4}$/.test(val);
};

function checkAlphanumeric(val) {
	if (!val) {
		return true;
	}
	return /^[a-zA-Z0-9-]*$/.test(val);
};


/**
 * nullまたは空文字か
 * @author nagashima 2018.01.10
 * @param val 精査する文字列
 */
function isempty(val) {
	if(val == null || val.length<1){
		return true;
	}
	return false;
}

/**
 * nullでも空文字でもないか
 * @author nagashima 2018.01.10
 * @param val 精査する文字列
 */
function isnotempty(val) {
	return !isempty(val);
}

/**
 * 文字列がすべて半角数字のみであるか
 * @author nagashima 2018.01.10
 * @param val 精査する文字列
 * @returns
 */
function isNumericOnly(val) {
	if (val == null || val.length < 1) {
		// 文字列がない場合はNGとする
		return false;
	}
	for (var i = 0; i < val.length; i++) {
		var c = val[i];
		if (c == '0') {
			//「0」
			continue;
		}
		if (c == '1') {
			//「1」
			continue;
		}
		if (c == '2') {
			//「2」
			continue;
		}
		if (c == '3') {
			//「3」
			continue;
		}
		if (c == '4') {
			//「4」
			continue;
		}
		if (c == '5') {
			//「5」
			continue;
		}
		if (c == '6') {
			//「6」
			continue;
		}
		if (c == '7') {
			//「7」
			continue;
		}
		if (c == '8') {
			//「8」
			continue;
		}
		if (c == '9') {
			//「9」
			continue;
		}
		return false;
	}
	return true;
}

/**
 * メアドとして適切か。値なしはチェックNGとする
 * @author nagashima 2018.01.15
 * @param val 精査する文字列
 */
function isMail(val) {
	if (isempty(val)) {
		// 値なし
		return false;
	}
	// @で分割
	var mail_address_arr = val.split('@');
	if (mail_address_arr == null || mail_address_arr.length != 2) {
		// 要素が2つでない場合エラー
		return false;
	}
	// @の前
	var mail_address1 = mail_address_arr[0];
	if (isempty(mail_address1)) {
		// @の前 値なし
		return false;
	}
	for (var i = 0; i < mail_address1.length; i++) {
		var c = mail_address1[i];
		if (LIST_MAIL_ADDRESS_CHARS.indexOf(c) == INDEX_NOT_FOUND) {
			// @の前 不正文字あり
			return false;
		}
	}

	// @の後
	var mail_address2 = mail_address_arr[1];
	if (isempty(mail_address2)) {
		// @の後 値なし
		return false;
	}
	for (var i = 0; i < mail_address2.length; i++) {
		var c = mail_address2[i];
		if (LIST_MAIL_ADDRESS_CHARS.indexOf(c) == INDEX_NOT_FOUND) {
			// @の後 不正文字あり
			return false;
		}
	}
	return true;
}

/**
 * 電話番号にふさわしい文字であるか。値なしはチェックNGとする
 * @author nagashima 2018.01.15
 * @param val 精査する文字列
 */
function isTelFax(val) {
	if (isempty(val)) {
		return false;
	}
	for (var i = 0; i < val.length; i++) {
		var c = val[i];
		if (LIST_TEL_FAX_CHARS.indexOf(c) == INDEX_NOT_FOUND) {
			return false;
		}
	}
	return true;
}

/**
 * すべて半角数字であるか。値なしはチェックNGとする
 * @author nagashima 2018.01.15
 * @param val 精査する文字列
 */
function isHankakuDigits(val) {
	if (isempty(val)) {
		return false;
	}
	for (var i = 0; i < val.length; i++) {
		var c = val[i];
		if (LIST_HANKAKU_DIGITS.indexOf(c) == INDEX_NOT_FOUND) {
			return false;
		}
	}
	return true;
}

/**
 * すべて半角ローマ字であるか。値なしはチェックNGとする
 * @param val 精査する文字列
 */
function isHankakuUpperAlphabet(val) {
	if (isempty(val)) {
		return false;
	}
	for (var i = 0; i < val.length; i++) {
		var c = val[i];
		if (LIST_HANKAKU_UPPER_ALPHABET.indexOf(c) == INDEX_NOT_FOUND) {
			return false;
		}
	}
	return true;
}

/**
 * 年月日にふさわしい文字であるか。値なしはチェックNGとする
 * @param val 精査する文字列
 */
function isYYYYMMDD(val) {
	if (isempty(val)) {
		// 値なし
		return false;
	}
	if (!isHankakuDigits(val)) {
		// 半角数字以外の文字がある
		return false;
	}
	if (val.length != 8) {
		// 8文字でない
		return false;
	}
	// 年月日を取り出す
	var y = Number(val.substring(0,4));
	var m = Number(val.substring(4,6));
	var d = Number(val.substring(6,8));

	if (y < 1900 || y > 2100) {
		// 年が許容の範囲外はエラー
		return false;
	}

	// Dateオブジェ作成
	var dt = new Date(y,m-1,d);

	// 妥当性チェック（13月とか、2月30日とかはここでチェックNGにできる）
	if (dt.getFullYear() == y && dt.getMonth() == m-1 && dt.getDate() == d) {
		// 妥当性チェックOK
		return true;
	}
	// 妥当性チェックNG
	return false;
}

function checkDate(year, month, day, strName, isReq, yearS, yearE) {
	var errorMsg = ""
	var checkFlg = true;
	//check required
	if (isReq == true) {
		if (!(isRequired(year) && isRequired(month) && isRequired(day))) {
			errorMsg = '<p>' + strName  + REQUIRED_MSG + '</p>';
			return errorMsg;
		}
	}

	// check year
	errorMsg += checkValidYear(year, strName + '(年)', yearS, yearE);
	if (errorMsg) {
		checkFlg = false;
	}

	if (month) {
		if (!isNumeric(month)) {
			errorMsg += '<p>' + strName+ '(月)' + FIELD_INPUT_ERROR_MSG + '</p>';
			checkFlg = false;
		}
	}

	if (day) {
		if (!isNumeric(day)) {
			errorMsg += '<p>' + strName+ '(日)' + FIELD_INPUT_ERROR_MSG + '</p>';
			checkFlg = false;
		}
	}

	if ((!year || !month || !day) && (year || month || day)) {
		errorMsg += '<p>' + strName  + VALID_DATE_CHK + '</p>';
	} else if (year && month && day && checkFlg) {
		if(!isValidDate(year, month, day) ){
			errorMsg += '<p>' + strName + '(年月日)' + VALID_DATE_CHK + '</p>';
		}
	}

	return errorMsg;
};

function checkValidYear(year, strName, yearS, yearE) {
	var errorMsg = "";
	if (year) {
		if (!isNumeric(year)) {
			errorMsg += '<p>' + strName  + FIELD_INPUT_ERROR_MSG + '</p>';
		} else {
			if (year.length < 4) {
				errorMsg += '<p>' + strName  + ' 4桁で入力ください。' + '</p>';
			} else {
				if (yearS) {
					if ( year < yearS || year > yearE) {
						errorMsg += '<p>' + strName  + ' ' + yearS + '-' + yearE + ' 内で入力ください。' + '</p>';
					}
				}
			}
		}
	}

	return errorMsg;
}
