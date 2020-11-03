package jp.co.kcs_grp.dao;

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
}
