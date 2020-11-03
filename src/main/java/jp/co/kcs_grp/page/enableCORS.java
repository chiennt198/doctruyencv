/**
 * ファイル名: enableCORS.java
 * バージョン: 1.0.0
 * 作成日付: Jan 11, 2017
 * 最終更新日付: Jan 11, 2017
 * 作成者: KCS
 * 更新履歴: Jan 11, 2017 : 新規作成
 */


package jp.co.kcs_grp.page;

import static spark.Spark.*;

import org.apache.log4j.Logger;

/**
 * 	CORS (Cross Origin Resource Sharing)の許可をする
 * @author dsaito
 *
 */
public class enableCORS {

	private static final Logger log = Logger.getLogger(enableCORS.class);

	/**
	 * これはとりあえずどこのオリジンからでもどんなメソッドでも通す<br>
	 * <br>
	 * 各APIが個別に認証を持っていることを前提とする場合はこれでよいはず
	 */
	public void apply_all(){
		options("/*", (request, response) -> {

			log.info("preflight start");

			//Methods
			String acrm = request.headers("Access-Control-Request-Methods");
			if(acrm != null){
				response.header("Access-Control-Allow-Methods",acrm);
			}

			//Headers
			String acrh = request.headers("Access-Control-Request-Headers");
			if(acrh != null){
				response.header("Access-Control-Allow-Headers", acrh);
			}

			log.info("preflight end");
			return "OK";
		});

		before((request, response) -> {
			//Origin
			response.header("Access-Control-Allow-Origin","*");
		});
	};
};
