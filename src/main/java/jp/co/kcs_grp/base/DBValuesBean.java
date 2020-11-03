/*
 * $DBValuesBean.java	$
 * $Id: DBValuesBean.java,v 1.1 2017/02/08 01:39:32 yoshizumi Exp $
 * $Revision: 1.1 $
 * $Date: 2017/02/08 01:39:32 $
 *
 * DBValuesBeanクラス.
 *
 * 修正履歴		修正者氏名　	修正内容
 * 2016/01/05	？？？			新規
 *
 * Copyright (C) KCS
 */
package jp.co.kcs_grp.base;

/*#### DBの格納型
## String型
COL_TYPE_STRING=1
## int型
COL_TYPE_INT=2
## long型
COL_TYPE_LONG=3
## byte型
COL_TYPE_BYTE=4
## float型
COL_TYPE_FLOAT=5
## double型
COL_TYPE_DOUBLE=6
## java.sql.Date型
COL_TYPE_DATE=7
## java.sql.Time型
COL_TYPE_TIME=8
## boolean型
COL_TYPE_BOOLEAN=9
## java.sql.Timestamp型
COL_TYPE_TIMESTAMP=10

## String型（名称）
COL_TYPE_STRING_NAME=String
## int型（名称）
COL_TYPE_INT_NAME=int
## long型（名称）
COL_TYPE_LONG_NAME=long
## byte型（名称）
COL_TYPE_BYTE_NAME=byte
## float型（名称）
COL_TYPE_FLOAT_NAME=float
## double型（名称）
COL_TYPE_DOUBLE_NAME=double
## java.sql.Date型（名称）
COL_TYPE_DATE_NAME=java.sql.Date
## java.sql.Time型（名称）
COL_TYPE_TIME_NAME=java.sql.Time
## boolean型（名称）
COL_TYPE_BOOLEAN_NAME=boolean
## java.sql.Timestamp型（名称）
COL_TYPE_TIMESTAMP_NAME=java.sql.Timestamp
*/


/**
 * DB処理で使用する値を格納するBean
 * @author nagashima
 */
public class DBValuesBean {
	public static final int COL_TYPE_STRING = 1;
	public static final int COL_TYPE_INT = 2;
	public static final int COL_TYPE_LONG = 3;
	public static final int COL_TYPE_BYTE = 4;
	public static final int COL_TYPE_FLOAT = 5;
	public static final int COL_TYPE_DOUBLE = 6;
	public static final int COL_TYPE_DATE = 7;
	public static final int COL_TYPE_TIME = 8;
	public static final int COL_TYPE_BOOLEAN = 9;
	public static final int COL_TYPE_TIMESTAMP = 10;
	public static final String COL_TYPE_STRING_NAME = "String";
	public static final String COL_TYPE_INT_NAME = "int";
	public static final String COL_TYPE_LONG_NAME = "long";
	public static final String COL_TYPE_BYTE_NAME = "byte";
	public static final String COL_TYPE_FLOAT_NAME = "float";
	public static final String COL_TYPE_DOUBLE_NAME = "double";
	public static final String COL_TYPE_DATE_NAME = "java.sql.Date";
	public static final String COL_TYPE_TIME_NAME = "java.sql.Time";
	public static final String COL_TYPE_BOOLEAN_NAME = "boolean";
	public static final String COL_TYPE_TIMESTAMP_NAME = "java.sql.Timestamp";
	public static final String DATETIME_JAVA = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_JAVA = "yyyy-MM-dd";
	public static final String TIME_JAVA = "HH:mm:ss";


	/**
	 * データタイプ
	 */
	int col_type = 0;

	/**
	 * データ名称
	 */
	String col_value = null;

	public int getCol_type() {
		return col_type;
	}

	public void setCol_type(int col_type) {
		this.col_type = col_type;
	}

	public String getCol_value() {
		return col_value;
	}

	public void setCol_value(String col_value) {
		this.col_value = col_value;
	}

}
