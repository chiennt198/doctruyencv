/**
 * ファイル名: ApplicationPage.java
 * バージョン: 1.0.0
 * 作成日付: Jan 11, 2017
 * 最終更新日付: Jan 11, 2017
 * 作成者: KCS
 * 更新履歴: Jan 11, 2017 : 新規作成
 */

package jp.co.kcs_grp.page;

import java.util.Map;

import org.apache.log4j.Logger;

import jp.co.kcs_grp.base.json.JsonTransformer;
import jp.co.kcs_grp.utils.AppParams;
import spark.Spark;
import spark.servlet.SparkApplication;

public class ApplicationPage implements SparkApplication {

	/**
	 * ログ
	 */
	private final Logger logger = Logger.getLogger(ApplicationPage.class);

	/**
	 * '/*'Path管理
	 */
	private WebTruyenPage  webPage = new WebTruyenPage();

	@Override
	public void init() {
		logger.info("start");

		/*****************************************************************
		 * FILTER
		 ******************************************************************/
		enableCORS cors = new enableCORS();
		cors.apply_all();
		try {
			Map<String, String> map =  AppParams.getParameterProp();
			Spark.externalStaticFileLocation(map.get("EXTERNAL_STATIC_FILE_LOCATION"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Spark.before((request, response) -> {
			String url = request.servletPath();
			response.header("Expires", "-1");
			if ("/logout".equals(url)) {
				response.redirect(request.raw().getContextPath() + "/");
			}
		});
		
		webRoute();
		adminRoute();
		
		Spark.get("/js/min/environ.js", (req, res) -> {
			StringBuffer cont = new StringBuffer();
			cont.append("var API_HTTP_COMMON = \"" + AppParams.getValue("parameterpath", "API_HTTP_COMMON") + "\";");
			cont.append("var ITEMS_PER_PAGE = \"" + AppParams.getValue("parameterpath", "ITEMS_PER_PAGE") + "\";");
			cont.append("var contextPath = \"" + AppParams.getValue("parameterpath", "contextPath") + "\";");
			cont.append("var STATUS_NORMAL = \"" + AppParams.getValue("parameterpath", "STATUS_NORMAL") + "\";");
			cont.append("var STATUS_NO_DATA = \"" + AppParams.getValue("parameterpath", "STATUS_NO_DATA") + "\";");
			cont.append("var STATUS_INPUT_URI_ERROR = \"" + AppParams.getValue("parameterpath", "STATUS_INPUT_URI_ERROR") + "\";");
			cont.append("var STATUS_DB_ERROR = \"" + AppParams.getValue("parameterpath", "STATUS_DB_ERROR") + "\";");
			return cont.toString();
		});
		logger.info("end");
	}
	
	private void adminRoute() {
		Spark.post("/api/admin-login", webPage.adminLogin(), new JsonTransformer());
		Spark.post("/api/admin-regist-story", webPage.adminRegistStory(), new JsonTransformer());
		Spark.post("/api/admin-regist-chapter", webPage.adminRegistChapter(), new JsonTransformer());
		Spark.post("/api/admin-get-list-story", webPage.adminGetListStory(), new JsonTransformer());
		Spark.get("/api/admin-get-story-detail/:id", webPage.getStoryDetail(), new JsonTransformer());
		Spark.post("/api/admin-get-list-chapters", webPage.adminGetListChapters(), new JsonTransformer());
		Spark.post("/api/get-chapter-detail", webPage.adminGetListChapters(), new JsonTransformer());
	}
	
	private void webRoute() {
		Spark.get("/api/get-category-list", webPage.getCategoryList(), new JsonTransformer());
		Spark.get("/api/get-m-wide-list/:idx", webPage.getMWideList(), new JsonTransformer());
		Spark.post("/api/get-chapter-detail", webPage.getChapterDetail(), new JsonTransformer());
		Spark.get("/api/get-story-items", webPage.getStoryItems(), new JsonTransformer());
		Spark.get("/api/get-story-info/:storyKey", webPage.getStoryInfo(), new JsonTransformer());
		Spark.get("/api/get-chapter-list/:storyId", webPage.getChapterList(), new JsonTransformer());
		Spark.get("/api/get-chapter-details/:storyKey/:chapterKey", webPage.getChapterInfo(), new JsonTransformer());
		Spark.get("/api/get-story-list", webPage.getStoryList(), new JsonTransformer());
		Spark.post("/api/update-watch-count-stories/:storyKey", webPage.updateWatchCountStories(), new JsonTransformer());
	}

	
}
