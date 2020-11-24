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
		sql.append(" ,KEY_SEARCH ");
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
		kps.setString(index++,param.get("keySearch"));
		kps.setStringNoneSqlLiteral(index++,param.get("description"));
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
		sql.append(" ,KEY_SEARCH = ? ");
		sql.append(" ,DESCRIPTION = ? ");
		sql.append(" ,CATEGORY_ID = ? ");	
		sql.append(" ,AUTHOR_NAME = ? ");
		sql.append(" ,CHAPTER_COUNT = ? ");
		sql.append(" ,STATUS = ? ");
		sql.append(" ,LINK_IMG = ? ");
		sql.append(" ,PUBLIC_FLG = ? ");
		if("1".equals(param.get("publicFlg"))) {
			sql.append(" ,PUBLIC_DATETIME = NOW() ");
		} else {
			sql.append(" ,PUBLIC_DATETIME = NULL ");
		}
		sql.append(" ,UPDATE_DATETIME  = NOW() ");
		sql.append(" WHERE ID = ? ");
		KcsPreparedStatement kps = db.getPreparedStatement(sql.toString());
		int index = 1;
		kps.setString(index++,param.get("name"));
		kps.setString(index++,param.get("keySearch"));
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
				sbSql.append(" ,IFNULL(st.KEY_SEARCH,'') AS KEY_SEARCH ");
				sbSql.append(" ,IFNULL(st.DESCRIPTION,'') AS DESCRIPTION ");
				sbSql.append(" ,IFNULL(st.CATEGORY_ID,'') AS CATEGORY_ID ");
				sbSql.append(" ,IFNULL(mc.CATEGORY_NAME,'') AS CATEGORY_NAME ");
				sbSql.append(" ,IFNULL(st.AUTHOR_NAME,'') AS AUTHOR_NAME ");
				sbSql.append(" ,IFNULL(st.STATUS,'') AS STATUS ");
				sbSql.append(" ,IFNULL(mw1.NAME,'') AS STATUS_NAME ");
				sbSql.append(" ,IFNULL(st.LINK_IMG,'') AS LINK_IMG ");
				sbSql.append(" ,IFNULL(st.PUBLIC_FLG,'0') AS PUBLIC_FLG ");
				sbSql.append(" ,IFNULL(tc.ID,'') AS CHAPTER_ID ");
				sbSql.append(" ,IFNULL(tc.NAME,'') AS CHAPTER_NAME ");
				sbSql.append(" FROM T_STORIES st ");
				sbSql.append(" LEFT JOIN T_CHAPTERS as tc ");
				sbSql.append(" ON tc.STORY_ID = st.ID  ");
				sbSql.append(" AND tc.ID = st.NEWEST_CHAPTER_ID  ");
				
				sbSql.append(" LEFT JOIN M_WIDE mw1 ");
				sbSql.append(" ON st.STATUS =  mw1.CD ");
				sbSql.append(" AND mw1.IDX =  1 ");
				
				sbSql.append(" LEFT JOIN M_CATEGORY mc ");
				sbSql.append(" ON mc.CATEGORY_ID = st.CATEGORY_ID ");
				
				sbSql.append(" WHERE st.DELETE_FLG IS NULL ");
				sbSql.append(" AND ( ");
				sbSql.append("   st.ID = ? ");
				sbSql.append(" OR st.KEY_SEARCH = ? ");
				sbSql.append(" ) ");
				sbSql.append(" ORDER BY st.ID ");
				
				//SQL実行
	            KcsPreparedStatement kps = db.getPreparedStatement(sbSql.toString());
	            kps.setString(1, id);
	            kps.setString(2, id);
	            rs = kps.executeQuery();
	            if(rs != null && rs.next()) {
	        		map =  new HashMap<>();
	        		map.put("id",rs.getString("ID"));
	        		map.put("name",rs.getString("NAME"));
	        		map.put("keySearch",rs.getString("KEY_SEARCH"));
	        		map.put("description",rs.getString("DESCRIPTION"));
	        		map.put("categoryId",rs.getString("CATEGORY_ID"));
	        		map.put("categoryName",rs.getString("CATEGORY_NAME"));
	        		map.put("authorName",rs.getString("AUTHOR_NAME"));
	        		map.put("status",rs.getString("STATUS"));
	        		map.put("statusName",rs.getString("STATUS_NAME"));
	        		map.put("linkImg",rs.getString("LINK_IMG"));
	        		map.put("publicFlg",rs.getString("PUBLIC_FLG"));
	        		map.put("chapterName",rs.getString("CHAPTER_NAME"));
	        		map.put("chapterId",rs.getString("CHAPTER_ID"));
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
	 
	 
	 public List<Map<String,String>> getList(Map<String,String> cond) throws Exception {
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
				sbSql.append(" SELECT ");
				sbSql.append("  st.ID AS STORY_ID ");
				sbSql.append(" ,IFNULL(st.NAME, '') AS STORY_NAME ");
				sbSql.append(" ,IFNULL(tc.ID,'') AS CHAPTERS_ID ");
				sbSql.append(" ,IFNULL(tc.NAME,'') AS CHAPTER_NAME ");
				sbSql.append(" ,IFNULL(st.LINK_IMG, '') AS LINK_IMG ");
				sbSql.append(" ,CASE  ");
				sbSql.append("	WHEN TIMESTAMPDIFF(YEAR, st.NEWEST_UPDATE_DATETIME, NOW()) > 0 THEN  CONCAT(TIMESTAMPDIFF(YEAR, st.NEWEST_UPDATE_DATETIME, NOW()),' năm trước')  ");
				sbSql.append("	WHEN TIMESTAMPDIFF(MONTH, st.NEWEST_UPDATE_DATETIME, NOW()) > 0 THEN CONCAT(TIMESTAMPDIFF(MONTH, st.NEWEST_UPDATE_DATETIME, NOW()),' tháng trước')  ");
				sbSql.append("	WHEN TIMESTAMPDIFF(DAY, st.NEWEST_UPDATE_DATETIME, NOW()) > 0 THEN CONCAT(TIMESTAMPDIFF(DAY, st.NEWEST_UPDATE_DATETIME, NOW()),' ngày trước')  ");
				sbSql.append("	WHEN TIMESTAMPDIFF(HOUR, st.NEWEST_UPDATE_DATETIME, NOW()) > 0 THEN  CONCAT(TIMESTAMPDIFF(HOUR, st.NEWEST_UPDATE_DATETIME, NOW()),' tiếng trước')  ");
				sbSql.append("	WHEN TIMESTAMPDIFF(MINUTE, st.NEWEST_UPDATE_DATETIME, NOW()) > 0 THEN CONCAT(TIMESTAMPDIFF(MINUTE, st.NEWEST_UPDATE_DATETIME, NOW()),' phút trước')  ");
				sbSql.append("	ELSE '1 phút trước'  ");
				sbSql.append(" END AS UPDATE_CHAPTER_TIME ");
				sbSql.append(" ,IFNULL(mw1.NAME,'') AS STATUS_NAME ");
				sbSql.append(" ,IFNULL(st.CHAPTER_COUNT,0) AS CHAPTER_COUNT ");
				sbSql.append(" ,IFNULL(st.KEY_SEARCH, '') AS KEY_SEARCH ");
				
				sbSql.append(" FROM T_STORIES as st ");
				
				sbSql.append(" LEFT JOIN M_WIDE mw1 ");
				sbSql.append(" ON st.STATUS = mw1.CD ");
				sbSql.append(" AND mw1.IDX = 1 ");
				
				sbSql.append(" LEFT JOIN T_CHAPTERS as tc ");
				sbSql.append(" ON tc.STORY_ID = st.ID  ");
				sbSql.append(" AND tc.ID = st.NEWEST_CHAPTER_ID  ");
				
				sbSql.append(" WHERE st.DELETE_FLG IS NULL ");

				sbSql.append(" AND st.PUBLIC_FLG = '1' ");
				
				sbSql.append(" AND st.PUBLIC_DATETIME <= NOW() ");
				
				if ( StringUtils.isNotEmpty(cond.get("categoryId")) ) {
					sbSql.append(" AND st.CATEGORY_ID = ? ");
				}
				
				if ( StringUtils.isNotEmpty(cond.get("status")) ) {
					sbSql.append(" AND st.STATUS = ? ");		
				}
				
				if ( StringUtils.isNotEmpty(cond.get("authorName")) ) {
					sbSql.append(" AND st.AUTHOR_NAME like ? ");		
				}
				
				if ( StringUtils.isNotEmpty(cond.get("name")) ) {
					sbSql.append(" AND st.NAME like ? ");		
				}
				
				if ( StringUtils.equals("1", cond.get("randomType")) ) {
					sbSql.append(" ORDER BY RAND() LIMIT 4 ");
				} else {
					if ( StringUtils.equals("1", cond.get("orderKey"))) {
						sbSql.append(" ORDER BY STORY_ID DESC ");
					} else if ( StringUtils.equals("2", cond.get("orderKey"))) {
						sbSql.append(" ORDER BY tc.UPDATE_DATETIME  DESC ");
					} else if ( StringUtils.equals("3", cond.get("orderKey"))) {
						sbSql.append(" ORDER BY IFNULL(st.CHAPTER_COUNT,0) DESC ");
					} else if ( StringUtils.equals("4", cond.get("orderKey"))) {
						sbSql.append(" ORDER BY IFNULL(st.WATCH_COUNT,0) DESC ");
					} else {
						sbSql.append(" ORDER BY STORY_ID DESC ");
					}
				}
				
				if ( StringUtils.isNotEmpty(cond.get("currentPage")) ) {
					sbSql.append(" LIMIT ?, ? ");
				}
				

				KcsPreparedStatement kps = db.getPreparedStatement(sbSql.toString());
	            int index = 1;
				if ( StringUtils.isNotEmpty(cond.get("categoryId")) ) {
					kps.setString(index++, cond.get("categoryId"));
				}
				
				if ( StringUtils.isNotEmpty(cond.get("status")) ) {
					kps.setString(index++, cond.get("status"));
				}
				
				if ( StringUtils.isNotEmpty(cond.get("authorName")) ) {
					kps.setString(index++, "%" + cond.get("authorName") + "%");
					
				}
				
				if ( StringUtils.isNotEmpty(cond.get("name")) ) {
					kps.setString(index++, "%" + cond.get("name") + "%");
				}
				
				if ( StringUtils.isNotEmpty(cond.get("currentPage")) ) {
					kps.setInt(index++, Integer.valueOf(cond.get("currentPage")));
					kps.setInt(index++, Integer.valueOf(AppParams.getValue("parameterpath", "ITEMS_PER_PAGE")));
				}
				
				rs = kps.executeQuery();
	            if(rs != null) {
	            	while (rs.next()) {
	            		map =  new HashMap<>();
	            		map.put("storyId",rs.getString("STORY_ID"));
	            		map.put("storyName",rs.getString("STORY_NAME"));
	            		map.put("chaptersId",rs.getString("CHAPTERS_ID"));
	            		map.put("chapterName",rs.getString("CHAPTER_NAME"));
	            		map.put("linkImg",rs.getString("LINK_IMG"));
	            		map.put("updateChapterTime",rs.getString("UPDATE_CHAPTER_TIME"));
	            		map.put("statusName",rs.getString("STATUS_NAME"));
	            		map.put("chapterCount",rs.getString("CHAPTER_COUNT"));
	            		map.put("keySearch",rs.getString("KEY_SEARCH"));
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
	 
	 
	 public long getTotalStory() throws Exception {
			DBAccess db = null;
			ResultSet rs = null;
			StringBuilder sbSql = null;
			//開始ログ出力
			log.warn("start");
			long totalStory = 0;
			try {
				//データベース接続
	            db = new DBAccess();
	            if (!db.dbConnection()) {
	                db.DBClose();
	                throw new Exception("データベース接続が失敗です。");
	            }
	            
				//SQL作成
				sbSql = new StringBuilder();
				sbSql.append(" SELECT COUNT(ID) AS STORY_CNT ");
				sbSql.append(" FROM T_STORIES WHERE ");
				sbSql.append(" DELETE_FLG IS NULL ");
				sbSql.append(" AND PUBLIC_FLG = '1' ");
				sbSql.append(" AND PUBLIC_DATETIME <= NOW() ");

				KcsPreparedStatement kps = db.getPreparedStatement(sbSql.toString());
				rs = kps.executeQuery();
	            if(rs != null && (rs.next()) ) {
	            	totalStory = rs.getLong("STORY_CNT");
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

			return totalStory;
		}
	 
	 public void  updateNewInfo(Map<String,String> param, DBAccess db) throws Exception {
			log.info("start");
			StringBuilder sql = null;
			// 請求TBL取得の処理
			sql = new StringBuilder();
			sql.append(" UPDATE T_STORIES SET ");
			sql.append("  NEWEST_CHAPTER_ID = ? ");
			sql.append(" ,CHAPTER_COUNT = ? ");
			sql.append(" ,NEWEST_UPDATE_DATETIME = NOW() ");	
			sql.append(" WHERE ID = ? ");
			KcsPreparedStatement kps = db.getPreparedStatement(sql.toString());
			int index = 1;
			kps.setString(index++,param.get("newestChapterId"));
			kps.setString(index++,param.get("chapterCount"));
			kps.setString(index++,param.get("storyId"));
			kps.execute();
			log.info("end");
		}
}
