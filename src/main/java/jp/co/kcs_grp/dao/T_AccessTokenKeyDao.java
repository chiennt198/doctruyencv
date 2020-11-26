package jp.co.kcs_grp.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import jp.co.kcs_grp.base.DBAccess;
import jp.co.kcs_grp.base.KcsPreparedStatement;

public class T_AccessTokenKeyDao {
	/**
	 * ログ.
	 */
	private Logger log = Logger.getLogger(T_AccessTokenKeyDao.class);
	
	 public String statusAuthentication(String authKey) throws Exception {
		DBAccess db = null;
		ResultSet rs = null;
		StringBuilder sbSql = null;
		//開始ログ出力
		String status = "nothing";
		String keyGenerate = "";
		log.warn("start");
		try {
			//データベース接続
            db = new DBAccess();
            if (!db.dbConnection()) {
                db.DBClose();
                throw new Exception("データベース接続が失敗です。");
            }
            
			//SQL作成
			sbSql = new StringBuilder();
			sbSql.append("SELECT KEY_GENERATE ");
			sbSql.append(" FROM T_ACCESS_TOKEN_KEY ");		
			sbSql.append(" WHERE INSERT_DATETIME > DATE_SUB(CURDATE(), INTERVAL 1 DAY) ");
			
			//SQL実行
            KcsPreparedStatement kps = db.getPreparedStatement(sbSql.toString());
            rs = kps.executeQuery();
            if(rs != null && rs.next()) {
            	keyGenerate = rs.getString("KEY_GENERATE");
            	//ResultSetのクローズ
				rs.close();
            }
            
            if ( StringUtils.isEmpty(keyGenerate) ) {
            	
            	sbSql = new StringBuilder();
            	sbSql.append("SELECT CNT(*) AS cnt ");
    			sbSql.append(" FROM T_ACCESS_TOKEN_KEY ");		
    			sbSql.append(" WHERE KEY_GENERATE = ? ");
            	
    			kps = db.getPreparedStatement(sbSql.toString());
    			kps.setString(1, authKey);
    			rs = kps.executeQuery();
    			
    			if(rs != null && rs.next()) {
    				if ( rs.getInt("cnt") > 0) {
    					status = "expires";
    				}
    			}
            	
            } else {
            	status = "ok";
            }
            	
			
		} catch(Exception e) {
			StringWriter stack = new StringWriter();
        	e.printStackTrace(new PrintWriter(stack));
        	log.error(stack.toString());
            throw e;
		} finally {
			//データベース切断
			db.DBClose();
			//終了ログ出力
			log.warn("end");
		}

		return status;
	}

	 public String newAuthentication(String key, String dateSys) throws Exception {
		DBAccess db = null;
		StringBuilder sbSql = null;
		//開始ログ出力
		log.warn("start");
		String keyGenerate = "";
		try {
			//データベース接続
            db = new DBAccess();
            if (!db.dbConnection()) {
                db.DBClose();
                throw new Exception("データベース接続が失敗です。");
            }
            
			//SQL作成
			sbSql = new StringBuilder();
			sbSql.append("INSERT INTO T_ACCESS_TOKEN_KEY (KEY_GENERATE, INSERT_DATETIME) ");
			sbSql.append(" FROM T_ACCESS_TOKEN_KEY ");		
			sbSql.append(" VALUES (PASSWORD(?), ?);  ");
			
			//SQL実行
            KcsPreparedStatement kps = db.getPreparedStatement(sbSql.toString());
            kps.setString(1, key);
            kps.setString(2, dateSys);
            kps.execute();
            
            //SQL作成
			sbSql = new StringBuilder();
			sbSql.append("SELECT KEY_GENERATE ");
			sbSql.append(" FROM T_ACCESS_TOKEN_KEY ");		
			sbSql.append(" WHERE KEY_GENERATE = PASSWORD(?) ");
			sbSql.append(" AND INSERT_DATETIME > DATE_SUB(CURDATE(), INTERVAL 1 DAY) ");
			kps = db.getPreparedStatement(sbSql.toString());
            kps.setString(1, key);
            
            ResultSet rs = kps.executeQuery();
            
            if(rs != null && rs.next()) {
            	keyGenerate = rs.getString("KEY_GENERATE");
			}
			
		} catch(Exception e) {
			StringWriter stack = new StringWriter();
        	e.printStackTrace(new PrintWriter(stack));
        	log.error(stack.toString());
            throw e;
		} finally {
			//データベース切断
			db.DBClose();
			//終了ログ出力
			log.warn("end");
		}
		
		return keyGenerate;
	}
}
