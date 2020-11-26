package jp.co.kcs_grp.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import jp.co.kcs_grp.dao.T_AccessTokenKeyDao;
import jp.co.kcs_grp.utils.AppParams;
import spark.Request;
import spark.Response;
import spark.Spark;

public class AuthenApp {
	
	private T_AccessTokenKeyDao accessTokenKeyDao = new T_AccessTokenKeyDao();
	
	public void authenData(Request request, Response response) throws Exception {
//		String authKey = StringUtils.trimToEmpty(request.headers("AUTH-KEY"));
//		
//		if ( StringUtils.isEmpty(authKey) ) {
//			setAuthen();
//		} else {
//			String status = accessTokenKeyDao.statusAuthentication(authKey);
//			
//			if ( StringUtils.equals("ok", status) ) {
//				
//				Spark.get("/js/min/authen.js", (req, res) -> {
//					StringBuffer cont = new StringBuffer();
//					cont.append("var AUTH-DATA = \"" + authKey + "\";");
//					return cont.toString();
//				});
//				
//			} else if ( StringUtils.equals("nothing", status) ) {
//				((HttpServletResponse) response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
//				 return;
//			} else if ( StringUtils.equals("expires", status) ) {
//				setAuthen();
//			}
//		}
		
	}
	
	private void setAuthen() throws Exception {
		
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		DateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = Calendar.getInstance().getTime();
		
		String key = df.format(date);
		
		String keyGenerate = accessTokenKeyDao.newAuthentication(AppParams.getValue("parameterpath", "KEY_DEFAULT"), df.format(date));
		
		Spark.get("/js/min/authen.js", (req, res) -> {
			StringBuffer cont = new StringBuffer();
			cont.append("var AUTH-DATA = \"" + keyGenerate + "\";");
			return cont.toString();
		});
	}
	
}
