package jp.co.kcs_grp.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import jp.co.kcs_grp.base.DBAccess;
import jp.co.kcs_grp.base.KcsPreparedStatement;

public class M_WideDao{

	/**
	 * ログ.
	 */
	private Logger log = Logger.getLogger(M_WideDao.class);
	 public List<Map<String,String>> getWideList(String idx) throws Exception {
			DBAccess db = null;
			ResultSet rs = null;
			StringBuilder sbSql = null;
			//開始ログ出力
			List<Map<String,String>> mWideList = null;
			Map<String,String> mWide = null;
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
				sbSql.append("SELECT CD , NAME ");
				sbSql.append(" FROM M_WIDE ");		//学会マスタ
				sbSql.append(" WHERE IDX = ? ");
				sbSql.append(" ORDER BY CD ");
				
				//SQL実行
	            KcsPreparedStatement kps = db.getPreparedStatement(sbSql.toString());
	            kps.setString(1, idx);
	            rs = kps.executeQuery();

	            if(rs != null) {
	            	mWide = null;
	            	mWideList =  new ArrayList<>();
	            	while (rs.next()) {
	            		mWide =  new HashMap<>();
	            		mWide.put("cd",rs.getString("CD"));
	            		mWide.put("name",rs.getString("NAME"));
	            		mWideList.add(mWide);
					}
	            	
	            	//ResultSetのクローズ
					rs.close();
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

			return mWideList;
		}
}
