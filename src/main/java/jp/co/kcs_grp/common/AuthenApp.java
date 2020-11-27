package jp.co.kcs_grp.common;

import static spark.Spark.halt;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.StringUtils;

import jp.co.kcs_grp.dao.T_AccessTokenKeyDao;
import jp.co.kcs_grp.utils.AppParams;
import spark.Request;
import spark.Response;
import spark.Spark;

public class AuthenApp extends main.CallExec {
	
	private T_AccessTokenKeyDao accessTokenKeyDao = new T_AccessTokenKeyDao();
	
	public void authenData(Request request, Response response) throws Exception {
		String authKey = StringUtils.trimToEmpty(request.headers("AUTH_DATA"));
		
		if ( StringUtils.isEmpty(authKey) ) {
			String keyGenerate = accessTokenKeyDao.getAuthenticationKey();
			
			if ( StringUtils.isEmpty(keyGenerate) ) {
				setAuthen();
			} else {
				Spark.get("/js/min/authen.js", (req, res) -> {
					StringBuffer cont = new StringBuffer();
					cont.append("var AUTH_DATA = \"" + keyGenerate + "\";");
					return cont.toString();
				});
			}
			
		} else {
			String status = accessTokenKeyDao.statusAuthentication(authKey);
			
			if ( StringUtils.equals("ok", status) ) {
				
				Spark.get("/js/min/authen.js", (req, res) -> {
					StringBuffer cont = new StringBuffer();
					cont.append("var AUTH_DATA = \"" + authKey + "\";");
					return cont.toString();
				});
				
			} else if ( StringUtils.equals("nothing", status) ) {
				((HttpServletResponse) response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
				 return;
			} else if ( StringUtils.equals("expires", status) ) {
				setAuthen();
			}
		}
		
		
		
	}
	
	private void setAuthen() throws Exception {
		
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = Calendar.getInstance().getTime();
		
		String keyGen = AppParams.getValue("parameterpath", "KEY_DEFAULT") + df.format(date);
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(keyGen.getBytes());
		byte[] digest = md.digest();
		String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		String keyGenerate = accessTokenKeyDao.newAuthentication(myHash, df1.format(date));
		
		Spark.get("/js/min/authen.js", (req, res) -> {
			StringBuffer cont = new StringBuffer();
			cont.append("var AUTH_DATA = \"" + keyGenerate + "\";");
			return cont.toString();
		});
	}
	
}
