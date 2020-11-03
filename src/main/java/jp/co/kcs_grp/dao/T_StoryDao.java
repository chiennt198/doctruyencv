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

public class T_StoryDao{

	/**
	 * ログ.
	 */
	private Logger log = Logger.getLogger(T_StoryDao.class);
	public void  insert(Map<String,String> param, DBAccess db) throws Exception {
		log.info("start");
		StringBuilder sql = null;
		// 請求TBL取得の処理
		sql = new StringBuilder();
		sql.append(" INSERT INTO T_STORIES ( ");
		sql.append(" NAME ");
		sql.append(" ,DESCRIPTION ");
		sql.append(" ,CATEGORY_ID ");	
		sql.append(" ,AUTHOR_ID ");
		sql.append(" ,CHAPTER_COUNT ");
		sql.append(" ,STATUS ");
		sql.append(" ,INSERT_DATETIME ");	
		sql.append(" ,UPDATE_DATETIME ");
		sql.append(" ) VALUES ( ");
		sql.append(" ? ");
		sql.append(" ,? ");
		sql.append(" ,? ");
		sql.append(" ,? ");	
		sql.append(" ,? ");
		sql.append(" ,? ");
		sql.append(" ,NOW() ");
		sql.append(" ,NOW() ");	
		sql.append(" ) ");
		
		KcsPreparedStatement kps = db.getPreparedStatement(sql.toString());
		int index = 1;
		kps.setString(index++,param.get("name"));
		kps.setString(index++,param.get("description"));
		kps.setString(index++,param.get("categoryId"));
		kps.setString(index++,param.get("authorId"));
		kps.setString(index++,param.get("chapterCount"));
		kps.setString(index++,param.get("status"));
		kps.execute();
		log.info("end");
	}
	
	 public List<Map<String,String>> search(Map<String,String> cond) throws Exception {
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
				sbSql.append("SELECT ");
				sbSql.append(" IFNULL(st.NAME,'') AS NAME ");
				sbSql.append(" ,IFNULL(st.DESCRIPTION,'') AS NAME ");
				sbSql.append(" ,IFNULL(st.STATUS,'') AS STATUS ");
				sbSql.append(" ,IFNULL(mw1.NAME,'') AS STATUS_NAME ");
				sbSql.append(" FROM T_STORY st ");
				sbSql.append(" LEFT JOIN M_WIDE mw1 ");
				sbSql.append(" ON st.STATUS =  mw1.CD ");
				sbSql.append(" AND mw1.IDX =  1 ");
				
				sbSql.append(" ORDER BY st.ID ");
				
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
