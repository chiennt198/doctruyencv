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

		Spark.get("/environ.js", (req, res) -> {
			StringBuffer cont = new StringBuffer();
			cont.append("var API_HTTP_COMMON = \"" + AppParams.getValue("parameterpath", "API_HTTP_COMMON") + "\";\n");
			cont.append("\n");
			return cont.toString();
		});
		logger.info("end");
	}
	
	private void adminRoute() {
		Spark.get("/admin-regist-story", webPage.getCategoryList(), new JsonTransformer());
	}
	private void webRoute() {
		Spark.get("/get-category-list", webPage.adminRegistStory(), new JsonTransformer());
	}

	

}
