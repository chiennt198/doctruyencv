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
		sql.append(" ,AUTHOR_NAME ");
		sql.append(" ,CHAPTER_COUNT ");
		sql.append(" ,STATUS ");
		sql.append(" ,LINK_IMG ");
		sql.append(" ,PUBLIC_FLG ");
		sql.append(" ,INSERT_DATETIME ");	
		sql.append(" ,UPDATE_DATETIME ");
		sql.append(" ) VALUES ( ");
		sql.append(" ? ");
		sql.append(" ,? ");
		sql.append(" ,? ");
		sql.append(" ,? ");	
		sql.append(" ,? ");
		sql.append(" ,1 ");
		sql.append(" ,? ");	
		sql.append(" ,IFNULL(?,'0')");
		sql.append(" ,NOW() ");
		sql.append(" ,NOW() ");	
		sql.append(" ) ");
		
		KcsPreparedStatement kps = db.getPreparedStatement(sql.toString());
		int index = 1;
		kps.setString(index++,param.get("name"));
		kps.setString(index++,param.get("description"));
		kps.setString(index++,param.get("categoryId"));
		kps.setString(index++,param.get("authorName"));
		kps.setString(index++,param.get("chapterCount"));
		kps.setString(index++,param.get("linkImg"));
		kps.setString(index++,param.get("publicFlg"));
		kps.execute();
		log.info("end");
	}
	
	public void  update(Map<String,String> param, DBAccess db) throws Exception {
		log.info("start");
		StringBuilder sql = null;
		// 請求TBL取得の処理
		sql = new StringBuilder();
		sql.append(" UPDATE T_STORIES SET ");
		sql.append(" NAME = ? ");
		sql.append(" ,DESCRIPTION = ? ");
		sql.append(" ,CATEGORY_ID = ? ");	
		sql.append(" ,AUTHOR_NAME = ? ");
		sql.append(" ,CHAPTER_COUNT = ? ");
		sql.append(" ,STATUS = ? ");
		sql.append(" ,LINK_IMG = ? ");
		sql.append(" ,PUBLIC_FLG = ? ");
		sql.append(" ,UPDATE_DATETIME  = NOW() ");
		sql.append(" WHERE ID = ? ");
		KcsPreparedStatement kps = db.getPreparedStatement(sql.toString());
		int index = 1;
		kps.setString(index++,param.get("name"));
		kps.setStringNoneSqlLiteral(index++,param.get("description"));
		kps.setString(index++,param.get("categoryId"));
		kps.setString(index++,param.get("authorName"));
		kps.setString(index++,param.get("chapterCount"));
		kps.setString(index++,param.get("status"));
		kps.setString(index++,param.get("linkImg"));
		kps.setString(index++,param.get("publicFlg"));
		kps.setString(index++,param.get("id"));
		kps.execute();
		log.info("end");
	}
	
	 public List<Map<String,String>> search(Map<String,String> cond) throws Exception {
			DBAccess db = null;
			ResultSet rs = null;
			StringBuilder sbSql = null;
			//開始ログ出力
			List<Map<String,String>> list = new ArrayList<>();
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
				sbSql.append(" IFNULL(st.ID,'') AS ID ");
				sbSql.append(" ,IFNULL(st.NAME,'') AS NAME ");
				sbSql.append(" ,IFNULL(st.DESCRIPTION,'') AS DESCRIPTION ");
				sbSql.append(" ,IFNULL(st.STATUS,'') AS STATUS ");
				sbSql.append(" ,IFNULL(mw1.NAME,'') AS STATUS_NAME ");
				sbSql.append(" FROM T_STORIES st ");
				sbSql.append(" LEFT JOIN M_WIDE mw1 ");
				sbSql.append(" ON st.STATUS =  mw1.CD ");
				sbSql.append(" AND mw1.IDX =  1 ");
				sbSql.append(" WHERE st.DELETE_FLG IS NULL ");
				if(StringUtils.isNotBlank(cond.get("name"))) {
					sbSql.append(" AND st.NAME like ? ");
				}
				
				if(StringUtils.isNotBlank(cond.get("status"))) {
					sbSql.append(" AND st.STATUS like ? ");
				}
				sbSql.append(" ORDER BY st.ID ");
				
				//SQL実行
	            KcsPreparedStatement kps = db.getPreparedStatement(sbSql.toString());
	            int idx = 1;
	            
	            if(StringUtils.isNotBlank(cond.get("name"))) {
	            	kps.setString(idx++, "%" + cond.get("name") + "%");
				}
				
				if(StringUtils.isNotBlank(cond.get("status"))) {
					kps.setString(idx++, cond.get("status"));
				}
	            rs = kps.executeQuery();
	            if(rs != null) {
	            	while (rs.next()) {
	            		map =  new HashMap<>();
	            		map.put("id",rs.getString("ID"));
	            		map.put("name",rs.getString("NAME"));
	            		map.put("description",rs.getString("DESCRIPTION"));
	            		map.put("status",rs.getString("STATUS"));
	            		map.put("statusName",rs.getString("STATUS_NAME"));
	            		list.add(map);
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

			return list;
		}
	 
	 public Map<String,String> getByKey(String id) throws Exception {
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
				sbSql.append(" IFNULL(st.ID,'') AS ID ");
				sbSql.append(" ,IFNULL(st.NAME,'') AS NAME ");
				sbSql.append(" ,IFNULL(st.DESCRIPTION,'') AS DESCRIPTION ");
				sbSql.append(" ,IFNULL(st.CATEGORY_ID,'') AS CATEGORY_ID ");
				sbSql.append(" ,IFNULL(st.AUTHOR_NAME,'') AS AUTHOR_NAME ");
				sbSql.append(" ,IFNULL(st.STATUS,'') AS STATUS ");
				sbSql.append(" ,IFNULL(mw1.NAME,'') AS STATUS_NAME ");
				sbSql.append(" ,IFNULL(st.LINK_IMG,'') AS LINK_IMG ");
				sbSql.append(" ,IFNULL(st.PUBLIC_FLG,'0') AS PUBLIC_FLG ");
				sbSql.append(" FROM T_STORIES st ");
				sbSql.append(" LEFT JOIN M_WIDE mw1 ");
				sbSql.append(" ON st.STATUS =  mw1.CD ");
				sbSql.append(" AND mw1.IDX =  1 ");
				sbSql.append(" WHERE st.DELETE_FLG IS NULL ");
				sbSql.append(" ORDER BY st.ID ");
				
				//SQL実行
	            KcsPreparedStatement kps = db.getPreparedStatement(sbSql.toString());
	            int idx = 1;
	            rs = kps.executeQuery();
	            if(rs != null && rs.next()) {
        		map =  new HashMap<>();
        		map.put("id",rs.getString("ID"));
        		map.put("name",rs.getString("NAME"));
        		map.put("description",rs.getString("DESCRIPTION"));
        		map.put("categoryId",rs.getString("CATEGORY_ID"));
        		map.put("authorName",rs.getString("AUTHOR_NAME"));
        		map.put("status",rs.getString("STATUS"));
        		map.put("statusName",rs.getString("STATUS_NAME"));
        		map.put("linkImg",rs.getString("LINK_IMG"));
        		map.put("publicFlg",rs.getString("PUBLIC_FLG"));
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
