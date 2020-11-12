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

public class T_ChaptersDao {

	/**
	 * ログ.
	 */
	private Logger log = Logger.getLogger(T_ChaptersDao.class);
	public void  insert(Map<String,String> param, DBAccess db) throws Exception {
		log.info("start");
		StringBuilder sql = null;
		// 請求TBL取得の処理
		sql = new StringBuilder();
		sql.append(" INSERT INTO T_CHAPTERS ( ");
		sql.append(" ID ");
		sql.append(" ,NAME ");
		sql.append(" ,STORY_ID ");
		sql.append(" ,CONTENT ");	
		sql.append(" ,SORT_KEY ");
		sql.append(" ,INSERT_DATETIME ");	
		sql.append(" ,UPDATE_DATETIME ");
		sql.append(" ) VALUES ( ");
		sql.append(" ? ");
		sql.append(" ,? ");
		sql.append(" ,? ");
		sql.append(" ,? ");
		sql.append(" ,? ");	
		sql.append(" ,NOW() ");
		sql.append(" ,NOW() ");	
		sql.append(" ) ");
		
		KcsPreparedStatement kps = db.getPreparedStatement(sql.toString());
		int index = 1;
		kps.setString(index++,param.get("id"));
		kps.setString(index++,param.get("name"));
		kps.setString(index++,param.get("storyId"));
		kps.setStringNoneSqlLiteral(index++,param.get("content"));
		kps.setString(index++,param.get("sortKey"));
		kps.execute();
		log.info("end");
	}
	
	public void  update(Map<String,String> param, DBAccess db) throws Exception {
		log.info("start");
		StringBuilder sql = null;
		// 請求TBL取得の処理
		sql = new StringBuilder();
		sql.append(" UPDATE  T_CHAPTERS SET ");
		sql.append(" NAME = ? ");
		sql.append(" ,CONTENT = ? ");	
		sql.append(" ,SORT_KEY = ? ");
		sql.append(" ,UPDATE_DATETIME = NOW() ");
		sql.append(" WHERE ID =  ?");
		sql.append(" AND STORY_ID =  ? ");
		KcsPreparedStatement kps = db.getPreparedStatement(sql.toString());
		int index = 1;
		kps.setString(index++,param.get("name"));
		kps.setStringNoneSqlLiteral(index++,param.get("content"));
		kps.setString(index++,param.get("sortKey"));
		kps.setString(index++,param.get("id"));
		kps.setString(index++,param.get("storyId"));
		kps.execute();
		log.info("end");
	}
	
	 public String getNewChapterId(String storyId) throws Exception {
			DBAccess db = null;
			ResultSet rs = null;
			StringBuilder sbSql = null;
			//開始ログ出力
			String result = "1";
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
				sbSql.append(" IFNULL(MAX(ID),'0') AS MAX_ID ");
				sbSql.append(" FROM T_CHAPTERS ");
				sbSql.append(" WHERE STORY_ID = ? ");
				
				//SQL実行
	            KcsPreparedStatement kps = db.getPreparedStatement(sbSql.toString());
	            kps.setString(1, storyId);
	            rs = kps.executeQuery();
	            if(rs != null && rs.next()) {
	            	result = String.valueOf(rs.getInt("MAX_ID") + 1);
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

			return result;
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
				sbSql.append(" ID ");
				sbSql.append(" ,IFNULL(NAME,'') AS NAME ");
				sbSql.append(" ,IFNULL(STORY_ID,'') AS STORY_ID ");
				sbSql.append(" ,IFNULL(CONTENT,'') AS CONTENT ");
				sbSql.append(" FROM T_CHAPTERS st ");
				sbSql.append(" WHERE st.DELETE_FLG IS NULL ");
				if(StringUtils.isNotBlank(cond.get("storyId"))) {
					sbSql.append(" AND st.STORY_ID = ? ");
				}
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
	            if(StringUtils.isNotBlank(cond.get("storyId"))) {
					kps.setString(idx++, cond.get("storyId"));
				}
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
	 
	 	public Map<String,String> getByKey(String storyId, String chapterId) throws Exception {
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
				sbSql.append(" IFNULL(ID,'') AS ID ");
				sbSql.append(" ,IFNULL(STORY_ID,'') AS STORY_ID ");
				sbSql.append(" ,IFNULL(NAME,'') AS NAME ");
				sbSql.append(" ,IFNULL(CONTENT,'') AS CONTENT ");
				sbSql.append(" FROM T_CHAPTERS  ");
				sbSql.append(" WHERE DELETE_FLG IS NULL ");
				sbSql.append(" AND ID = ? ");
				sbSql.append(" AND STORY_ID = ? ");
				//SQL実行
	            KcsPreparedStatement kps = db.getPreparedStatement(sbSql.toString());
	            kps.setString(1, chapterId);
	            kps.setString(2, storyId);
	            rs = kps.executeQuery();
	            if(rs != null && rs.next()) {
	            		map =  new HashMap<>();
	            		map.put("id",rs.getString("ID"));
	            		map.put("storyId",rs.getString("STORY_ID"));
	            		map.put("name",rs.getString("NAME"));
	            		map.put("content",rs.getString("CONTENT"));
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
