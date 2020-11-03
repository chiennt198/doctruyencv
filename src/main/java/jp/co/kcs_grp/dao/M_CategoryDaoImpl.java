package jp.co.kcs_grp.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import jp.co.kcs_grp.base.DBAccess;
import jp.co.kcs_grp.base.KcsPreparedStatement;
import jp.co.kcs_grp.dao.bean.M_Category;

public class M_CategoryDaoImpl implements M_CategoryDao {

	/**
	 * ログ.
	 */
	private Logger log = Logger.getLogger(M_CategoryDaoImpl.class);

	
	@Override
	public List<M_Category> getList() throws Exception {
		DBAccess db = null;
		ResultSet rs = null;
		StringBuilder sbSql = null;
		// 開始ログ出力
		log.warn("start");
		List<M_Category> rtnVal = new ArrayList<>();
		try {
			// データベース接続
			db = new DBAccess();
			if (!db.dbConnection()) {
				db.DBClose();
				throw new Exception("データベース接続が失敗です。");
			}

			// SQL作成
			sbSql = new StringBuilder();
			sbSql.append("SELECT ");
			sbSql.append("  CATEGORY_ID as categoryId ");
			sbSql.append(" ,CATEGORY_NAME as categoryName ");
			sbSql.append(" FROM M_CATEGORY ");

			// SQL実行
			KcsPreparedStatement kps = db.getPreparedStatement(sbSql.toString());
			rs = kps.executeQuery();

			if (rs != null) {
				M_Category data = null;
				while (rs.next()) {
					data = new M_Category();
					data.setCategoryId(rs.getString("categoryId"));
					data.setCategoryName(rs.getString("categoryName"));
					rtnVal.add(data);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// データベース切断
			if (db != null) {
				db.DBClose();
			}

			// 終了ログ出力
			log.warn("end");
		}
		return rtnVal;
	}
}
