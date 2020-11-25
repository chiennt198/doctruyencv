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
import jp.co.kcs_grp.utils.AppParams;

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
				sbSql.append(" ,IFNULL(st.NAME,'') AS NAME ");
				sbSql.append(" ,IFNULL(st.STORY_ID,'') AS STORY_ID ");
				sbSql.append(" ,IFNULL(st.CONTENT,'') AS CONTENT ");
				sbSql.append(" ,IFNULL(st.KEY_SEARCH,'') AS KEY_SEARCH ");
				
				sbSql.append(" ,CASE   ");
				sbSql.append("	WHEN TIMESTAMPDIFF(YEAR, st.UPDATE_DATETIME, NOW()) > 0 THEN  CONCAT(TIMESTAMPDIFF(YEAR, UPDATE_DATETIME, NOW()),' năm trước')  ");
				sbSql.append("	WHEN TIMESTAMPDIFF(MONTH, st.UPDATE_DATETIME, NOW()) > 0 THEN CONCAT(TIMESTAMPDIFF(MONTH, UPDATE_DATETIME, NOW()),' tháng trước')  ");
				sbSql.append("	WHEN TIMESTAMPDIFF(DAY, st.UPDATE_DATETIME, NOW()) > 0 THEN CONCAT(TIMESTAMPDIFF(DAY, UPDATE_DATETIME, NOW()),' ngày trước')  ");
				sbSql.append("	WHEN TIMESTAMPDIFF(HOUR, st.UPDATE_DATETIME, NOW()) > 0 THEN  CONCAT(TIMESTAMPDIFF(HOUR, UPDATE_DATETIME, NOW()),' tiếng trước')  ");
				sbSql.append("	WHEN TIMESTAMPDIFF(MINUTE, st.UPDATE_DATETIME, NOW()) > 0 THEN CONCAT(TIMESTAMPDIFF(MINUTE, UPDATE_DATETIME, NOW()),' phút trước')  ");
				sbSql.append("	ELSE '1 phút trước'  ");
				sbSql.append(" END AS UPDATE_CHAPTER_TIME ");
				
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
				
				if ( StringUtils.equals("1", cond.get("orderBy")) ) {
					sbSql.append(" ORDER BY st.ID DESC ");
				} else if ( StringUtils.equals("0", cond.get("orderBy")) ) {
					sbSql.append(" ORDER BY st.ID ASC ");
				} else {
					sbSql.append(" ORDER BY st.ID DESC ");
				}

				if ( !StringUtils.equals("-1", cond.get("currentPage")) ) {
					if ( StringUtils.isNotEmpty(cond.get("currentPage")) ) {
						sbSql.append(" LIMIT ?, ? ");
					} else {
						sbSql.append(" LIMIT 0, ? ");
					}
				}
				
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
				
				if ( !StringUtils.equals("-1", cond.get("currentPage")) ) {
					if ( StringUtils.isNotEmpty(cond.get("currentPage")) ) {
						kps.setInt(idx++, Integer.valueOf(cond.get("currentPage")));
					} 
					
					kps.setInt(idx++, Integer.valueOf(AppParams.getValue("parameterpath", "ITEMS_PER_PAGE")));
				}
				
	            rs = kps.executeQuery();
	            if(rs != null) {
	            	while (rs.next()) {
	            		map =  new HashMap<>();
	            		map.put("id",rs.getString("ID"));
	            		map.put("name",rs.getString("NAME"));
	            		map.put("updateChapterTime",rs.getString("UPDATE_CHAPTER_TIME"));
	            		map.put("content",rs.getString("CONTENT"));
	            		map.put("chapterKey",rs.getString("KEY_SEARCH"));
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
	 
	 	public Map<String,String> getByKey(String storyKey, String chapterKey) throws Exception {
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
				sbSql.append(" IFNULL(tc.ID,'') AS ID ");
				sbSql.append(" ,IFNULL(ts.NAME,'') AS STORY_NAME ");
				sbSql.append(" ,IFNULL(tc.STORY_ID,'') AS STORY_ID ");
				sbSql.append(" ,IFNULL(tc.NAME,'') AS NAME ");
				sbSql.append(" ,IFNULL(tc.CONTENT,'') AS CONTENT ");
				sbSql.append(" ,IFNULL(MAX(tcp.ID),'') AS CHAPTER_ID_PRE ");
				sbSql.append(" ,IFNULL(MIN(tcn.ID),'') AS CHAPTER_ID_NEXT ");
				sbSql.append(" FROM T_CHAPTERS as tc ");
				
				sbSql.append(" INNER JOIN T_STORIES as ts ");
				sbSql.append(" ON ts.ID = tc.STORY_ID ");
				
				sbSql.append(" LEFT JOIN T_CHAPTERS AS tcp ");
				sbSql.append(" ON tcp.STORY_ID = tc.STORY_ID ");
				sbSql.append(" AND tcp.ID < tc.ID ");
				sbSql.append(" AND tcp.DELETE_FLG IS NULL ");
						
				sbSql.append(" LEFT JOIN T_CHAPTERS AS tcn  ");
				sbSql.append(" ON tcn.STORY_ID = tc.STORY_ID  ");
				sbSql.append(" AND tcn.ID > tc.ID ");
				sbSql.append(" AND tcn.DELETE_FLG IS NULL ");
				
				sbSql.append(" WHERE tc.DELETE_FLG IS NULL ");
				sbSql.append(" AND ts.KEY_SEARCH = ? ");
				sbSql.append(" AND tc.KEY_SEARCH = ? ");
				//SQL実行
	            KcsPreparedStatement kps = db.getPreparedStatement(sbSql.toString());
	            kps.setString(1, storyKey);
	            kps.setString(2, chapterKey);
	            rs = kps.executeQuery();
	            if(rs != null && rs.next()) {
            		map =  new HashMap<>();
            		map.put("id",rs.getString("ID"));
            		map.put("storyId",rs.getString("STORY_ID"));
            		map.put("storyName",rs.getString("STORY_NAME"));
            		map.put("name",rs.getString("NAME"));
            		map.put("content",rs.getString("CONTENT"));
            		map.put("chapterIdPre",rs.getString("CHAPTER_ID_PRE"));
            		map.put("chapterIdNext",rs.getString("CHAPTER_ID_NEXT"));
	            }
	            
	            if (map !=null && !map.isEmpty()) {
	            	
	            	if ( StringUtils.isNotEmpty(map.get("chapterIdPre")) ) {
	            		sbSql = new StringBuilder();
		            	sbSql.append("SELECT KEY_SEARCH FROM T_CHAPTERS ");
		            	sbSql.append(" WHERE DELETE_FLG IS NULL ");
		            	sbSql.append(" AND ID = ? ");
		            	kps = db.getPreparedStatement(sbSql.toString());
		            	kps.setString(1, map.get("chapterIdPre"));
		            	rs = kps.executeQuery();
			            if(rs != null && rs.next()) {
			            	map.put("keySearchPre",rs.getString("KEY_SEARCH"));
			            }
	            	}
	            	
	            	if ( StringUtils.isNotEmpty(map.get("chapterIdNext")) ) {
	            		sbSql = new StringBuilder();
		            	sbSql.append("SELECT KEY_SEARCH FROM T_CHAPTERS ");
		            	sbSql.append(" WHERE DELETE_FLG IS NULL ");
		            	sbSql.append(" AND ID = ? ");
		            	kps = db.getPreparedStatement(sbSql.toString());
		            	kps.setString(1, map.get("chapterIdNext"));
		            	rs = kps.executeQuery();
			            if(rs != null && rs.next()) {
			            	map.put("keySearchNext",rs.getString("KEY_SEARCH"));
			            }
	            	}
		            
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
	 	
	 	public String countChapter(String storyId) throws Exception {
			DBAccess db = null;
			ResultSet rs = null;
			StringBuilder sbSql = null;
			//開始ログ出力
			String result = "0";
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
				sbSql.append(" COUNT(*) chCount ");
				sbSql.append(" FROM T_CHAPTERS ");
				sbSql.append(" WHERE STORY_ID = ? ");
				sbSql.append(" AND DELETE_FLG IS NULL ");
				
				//SQL実行
	            KcsPreparedStatement kps = db.getPreparedStatement(sbSql.toString());
	            kps.setString(1, storyId);
	            rs = kps.executeQuery();
	            if(rs != null && rs.next()) {
	            	result = rs.getString("chCount");
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
	 	
	 	
 	 public long totalChapterInStories(String storyId) throws Exception {
		DBAccess db = null;
		ResultSet rs = null;
		StringBuilder sbSql = null;
		//開始ログ出力
		long totalChapter = 0;
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
			sbSql.append("SELECT COUNT(*) as CNT ");
			sbSql.append(" FROM T_CHAPTERS st ");
			sbSql.append(" WHERE st.DELETE_FLG IS NULL ");
			sbSql.append(" AND st.STORY_ID = ? ");
			
			//SQL実行
            KcsPreparedStatement kps = db.getPreparedStatement(sbSql.toString());
            int idx = 1;
            kps.setString(idx++, storyId);
            rs = kps.executeQuery();
            if(rs != null  && rs.next()) {
            	totalChapter = rs.getLong("CNT");
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

		return totalChapter;
	}
}
