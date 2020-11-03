/*
 * $PreparedDBAccess.java	$
 * $Id: PreparedDBAccess.java,v 1.1 2017/02/08 01:39:32 yoshizumi Exp $
 * $Revision: 1.1 $
 * $Date: 2017/02/08 01:39:32 $
 *
 * PreparedDBAccessクラス.
 *
 * 修正履歴		修正者氏名　	修正内容
 * 2016/01/06	？？？			新規
 *
 * Copyright (C) KCS
 */
package jp.co.kcs_grp.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

/**
 * DB管理を行うクラス（PreparedStatementを使用）
 * @author nagashima
 */
public class PreparedDBAccess {

	InitialContext ic;
	DataSource ds;
	Connection conn = null;

	/** ロガー */
	private static final Logger log = Logger.getLogger(PreparedDBAccess.class);

	/**
	 * コネクションの確立（本システム）
	 * @throws Exception 例外
	 */
	public void dbConnection() throws Exception {

		log.warn("dbConnection() start");

		try {
			ic = new InitialContext();
			ds = (DataSource)ic.lookup("java:comp/env/jdbc/MySQL");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw e;
		}

		log.warn("dbConnection() end");
	}

	/**
	 * コネクションの確立（各学会）
	 * @param society_code 学会コード
	 * @throws Exception 例外
	 */
	public void societyDBConnection(String society_code) throws Exception {

		log.warn("societyDBConnection(String) start");

		try {
			ic = new InitialContext();
			ds = (DataSource)ic.lookup("java:comp/env/jdbc/MySQL" + society_code);
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw e;
		}

		log.warn("societyDBConnection(String) end");
	}
    /**
     * DBに接続する
     * @throws Exception 例外
     */
    public boolean dbConnectionWithResource(String resname) throws Exception {
        log.debug("start");
        boolean flg = false;
        try {
        	ic = new InitialContext();
            ds = (DataSource)ic.lookup("java:comp/env/" + resname);
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = ds.getConnection();
            flg = true;
        }
        catch (Exception e) {
            e.printStackTrace();
			log.error(e.toString());
			throw e;
        }
        log.debug("end");
        return flg;

    }

	/**
	 * SQLの実行（結果を返すSQLのみ）
	 * @param sql SQL文
	 * @param dbvlist 値のかたまり
	 * @return ResultSet 結果
	 * @throws Exception 例外
	 */
	public ResultSet dbQuery(String sql, List<DBValuesBean> dbvlist) throws Exception {
		// 返却値
		ResultSet rs = null;
		log.warn("dbQuery start");

		try {
			if (conn.isClosed()) {
				//コネクションがない
				log.error("connection isClosed");
				throw new Exception("コネクションがない");
			}

			log.info(sql);
			// SQLの、?の数を求める
			int question_count = StringUtils.countMatches(sql, "?");
			if (question_count != dbvlist.size()) {
				// ?の数と、値数が一致しない場合はエラー
				throw new Exception("値数ミスマッチ");
			}
			// PreparedStatementを取得
			PreparedStatement ps = singleStatement(sql, dbvlist);
			// いざSQL実行
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("【\nsql:" + sql + "】\n" + e.toString());
			throw e;
		}

		log.warn("dbQuery end");
		return rs;
	}

	/**
	 * SQLの実行（結果を返さないSQLのみ）
	 * @param sql SQL文
	 * @param clsname 呼び出し先のクラス＆メソッド名（ログ収集用）
	 * @param dbvlist 値のかたまり
	 * @throws Exception 例外
	 */
	public void dbAction(String sql, List<DBValuesBean> dbvlist) throws Exception {

		log.warn("dbAction start");

		try {
			if (conn.isClosed()){
				//コネクションがない
				log.error("connection isClosed");
				throw new Exception("コネクションがない");
			}

			log.info(sql);
			// SQLの、?の数を求める
			int question_count = StringUtils.countMatches(sql, "?");
			if (question_count != dbvlist.size()) {
				// ?の数と、値数が一致しない場合はエラー
				throw new Exception("値数ミスマッチ");
			}
			// PreparedStatementを取得
			PreparedStatement ps = singleStatement(sql, dbvlist);
			// いざSQL実行
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("【\nsql:" + sql + "】\n" + e.toString());
			throw e;
		}
		log.warn("dbAction end");
	}

	/**
	 * コネクションの開放
	 * @throws Exception 例外
	 */
	public void dbClose() throws Exception {

		log.warn("dbClose() start");
		try {
			if (conn != null && !conn.isClosed()){
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw e;
		}
		log.warn("dbClose() end");
	}

	/**
	 * トランザクションの開始（connectionのオートコミットをoffにする）
	 * @throws Exception 例外
	 */
	public void dbTranStart() throws Exception {

		log.warn("dbTranStart() start");
		try {
			if (conn.isClosed()){
				//コネクションがない
				log.error("connection isClosed");
				throw new Exception("コネクションがない");
			}
			// コミットをきらないようにする
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw e;
		}
		log.warn("dbTranStart() end");
	}


	/**
	 * トランザクションの終了（connectionのオートコミットをoffにする）
	 * @param b commitする場合はtrue、rollbackする場合はfalse
	 * @throws Exception 例外
	 */
	public void dbTranEnd(boolean b) throws Exception {

		log.warn("dbTranEnd() start");
		try {
			if (conn.isClosed()){
				// コネクションが閉じられている場合は終了してよし
				return;
			}

			if (conn.getAutoCommit()){
				// オートコミットがOFFの場合は終了
				return;
			}
			if (b){
				// コミットをきる
				conn.commit();
			} else{
				// ロールバックをする
				conn.rollback();
			}

			// オートコミットをONに
			conn.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw e;
		}
		log.warn("dbTranEnd() end");
	}

	/**
	 * コネクションの確認
	 * @return	コネクションが切れてる場合はtrue、切れてない場合はfalse
	 * @throws Exception 例外
	 */
	public boolean dbisClosed() throws Exception {

		boolean flg = true;
		log.warn("dbisClosed() start");
		try {

			if (conn != null){
				flg = conn.isClosed();
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			throw e;
		}
		log.warn("dbisClosed() end");
		return flg;
	}


	/**
	 * SQL処理で必要なPreparedStatementを作成
	 * @param sql SQL文
	 * @param dbvlist 値のかたまり
	 * @return PreparedStatement
	 * @throws Exception 例外
	 */
	private PreparedStatement singleStatement(String sql, List<DBValuesBean> dbvlist) throws Exception {
		// 返却値
		PreparedStatement ps = conn.prepareStatement(sql);
		if (dbvlist == null || dbvlist.size() < 1) {
			// パラメータを１つも設定しない場合
			return ps;
		}

		setParamFromDbvList(ps, dbvlist);

		return ps;
	}

	private void setParamFromDbvList(PreparedStatement ps, List<DBValuesBean> dbvlist) throws Exception {
		for (int i = 0; i < dbvlist.size(); i++) {
			// 値格納クラス呼び出し
			DBValuesBean bean = dbvlist.get(i);
			// typeを取得
			int col_type = bean.getCol_type();
			// valueを取得
			String col_value = bean.getCol_value();
			// 日付形式（使用しない場合もある、というか使用しない場合が圧倒的に多い）
			String[] ptn = null;
			// ログを出す
//			StringBuilder logmes = new StringBuilder();
//			logmes.append("count：");
//			logmes.append(i+1);
//			logmes.append("、type：");
//			logmes.append(getDBTypeCodeToName(col_type));
//			logmes.append("、value：");
//			logmes.append(col_value);
			// log.warn(logmes.toString());

			if (col_type == DBValuesBean.COL_TYPE_STRING) {
				// String型
				ps.setString(i+1, col_value);
			}
			if (col_type == DBValuesBean.COL_TYPE_INT) {
				// int型
				ps.setInt(i+1, NumberUtils.toInt(col_value));
			}
			if (col_type == DBValuesBean.COL_TYPE_LONG) {
				// long型
				ps.setLong(i+1, NumberUtils.toLong(col_value));
			}
			if (col_type == DBValuesBean.COL_TYPE_BYTE) {
				// byte型
				ps.setByte(i+1, NumberUtils.toByte(col_value));
			}
			if (col_type == DBValuesBean.COL_TYPE_FLOAT) {
				// float型
				ps.setFloat(i+1, NumberUtils.toFloat(col_value));
			}
			if (col_type == DBValuesBean.COL_TYPE_DOUBLE) {
				// double型
				ps.setDouble(i+1, NumberUtils.toDouble(col_value));
			}
			if (col_type == DBValuesBean.COL_TYPE_DATE) {
				// java.sql.Date型
				ptn = new String[1];
				ptn[0] = DBValuesBean.DATE_JAVA;
				java.util.Date date1 = DateUtils.parseDate(col_value, ptn);
				ps.setDate(i+1, new java.sql.Date(date1.getTime()));
			}
			if (col_type == DBValuesBean.COL_TYPE_TIME) {
				// java.sql.Time型
				ptn = new String[1];
				ptn[0] = DBValuesBean.TIME_JAVA;
				java.util.Date date2 = DateUtils.parseDate(col_value, ptn);
				ps.setTime(i+1, new Time(date2.getTime()));
			}
			if (col_type == DBValuesBean.COL_TYPE_BOOLEAN) {
				// boolean型
				ps.setBoolean(i+1, BooleanUtils.toBoolean(col_value));
			}
			if (col_type == DBValuesBean.COL_TYPE_TIMESTAMP) {
				// java.sql.Timestamp型
				ptn = new String[1];
				ptn[0] = DBValuesBean.DATETIME_JAVA;
				java.util.Date date3 = DateUtils.parseDate(col_value, ptn);
				ps.setTimestamp(i+1, new Timestamp(date3.getTime()));
			}
		}

	}

	private String getDBTypeCodeToName(int col_type) throws Exception {
		// 返却値
		String ret = "";

		if (col_type == DBValuesBean.COL_TYPE_STRING) {
			// String型
			ret = "String";
		}
		if (col_type == DBValuesBean.COL_TYPE_INT) {
			// int型
			ret = "int";
		}
		if (col_type == DBValuesBean.COL_TYPE_LONG) {
			// long型
			ret = "long";
		}
		if (col_type == DBValuesBean.COL_TYPE_BYTE) {
			// byte型
			ret = "byte";
		}
		if (col_type == DBValuesBean.COL_TYPE_FLOAT) {
			// float型
			ret = "float";
		}
		if (col_type == DBValuesBean.COL_TYPE_DOUBLE) {
			// double型
			ret = "double";
		}
		if (col_type == DBValuesBean.COL_TYPE_DATE) {
			// java.sql.Date型
			ret = "java.sql.Date";
		}
		if (col_type == DBValuesBean.COL_TYPE_TIME) {
			// java.sql.Time型
			ret = "java.sql.Time";
		}
		if (col_type == DBValuesBean.COL_TYPE_BOOLEAN) {
			// boolean型
			ret = "boolean";
		}
		if (col_type == DBValuesBean.COL_TYPE_TIMESTAMP) {
			// java.sql.Timestamp型
			ret = "java.sql.Timestamp";
		}
		return ret;
	}

	public PreparedStatement getPreparedStatement(String sql) throws SQLException {
		return conn.prepareStatement(sql);
	}

	public ResultSet queryPreparedStatement(PreparedStatement ps, List<DBValuesBean> dbvlist) throws Exception {
		setParamFromDbvList(ps, dbvlist);
		ResultSet rs = ps.executeQuery();

		return rs;
	}

}
