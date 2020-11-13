package jp.co.kcs_grp.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import jp.co.kcs_grp.base.DBAccess;
import jp.co.kcs_grp.base.KcsPreparedStatement;

public class M_AdminDao {

	/**
	 * ログ.
	 */
	private Logger log = Logger.getLogger(M_AdminDao.class);

	 
 	public Map<String,String> login(String userId, String password) throws Exception {
		DBAccess db = null;
		ResultSet rs = null;
		StringBuilder sbSql = null;
		//開始ログ出力
		Map<String,String> map = null;
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
			sbSql.append("SELECT ");
			sbSql.append(" IFNULL(USER_ID,'') AS USER_ID ");
			sbSql.append(" ,IFNULL(NAME,'') AS NAME ");
			sbSql.append(" ,IFNULL(EMAIL,'') AS EMAIL ");
			sbSql.append(" ,IFNULL(ROLE,'') AS ROLE ");
			sbSql.append(" FROM M_ADMIN  ");
			sbSql.append(" WHERE DELETE_FLG IS NULL ");
			sbSql.append(" AND USER_ID = ? ");
			sbSql.append(" AND PASSWORD = PASSWORD(?) ");
			//SQL実行
            KcsPreparedStatement kps = db.getPreparedStatement(sbSql.toString());
            kps.setString(1, userId);
            kps.setString(2, password);
            rs = kps.executeQuery();
            if(rs != null && rs.next()) {
            		map =  new HashMap<>();
            		map.put("userId",rs.getString("USER_ID"));
            		map.put("name",rs.getString("NAME"));
            		map.put("email",rs.getString("EMAIL"));
            		map.put("role",rs.getString("ROLE"));
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

		return map;
	}
}
