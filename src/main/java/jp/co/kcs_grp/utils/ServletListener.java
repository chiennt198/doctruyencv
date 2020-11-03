/**
 * ファイル名: ServletListener.java
 * バージョン: 1.0.0
 * 作成日付: Jan 11, 2017
 * 最終更新日付: Jan 11, 2017
 * 作成者: KCS
 * 更新履歴: Jan 11, 2017 : 新規作成
 */
package jp.co.kcs_grp.utils;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import spark.servlet.SparkFilter;

@WebListener
public class ServletListener extends SparkFilter implements ServletContextListener {

	public static String app_instance = "local";		// default
	public static ServletContext context = null;

	@Override
	public void contextInitialized(ServletContextEvent event){
		context = event.getServletContext();

		try {
		    Context ctx = new InitialContext();
		    ctx = (Context) ctx.lookup("java:comp/env");
		    String lookuppedValue = (String) ctx.lookup("AppInstance");
			if (lookuppedValue != null) app_instance = lookuppedValue;
		}
		catch (Exception e) {
		// デフォルトセットされるので特に何もしない
		}
		try {
		    Context ctx = new InitialContext();
		    ctx = (Context) ctx.lookup("java:comp/env");
			// For Log Properties
			String logBaseDir = (String) ctx.lookup("logBaseDir");
			String appName = (String) ctx.lookup("appName");
			if (logBaseDir != null) {
				System.setProperty("logBaseDir", logBaseDir);
			}
			if (appName != null) {
				System.setProperty("appName", appName);
			}
		}
		catch (Exception e) {
		// 特に何もしない
			System.out.println(e.getMessage());
		}
		System.out.println("[" + context.getContextPath() + "] ServletContext Initialized to [" + app_instance + "]");

		// App Initialize
		AppParams.useEnvParams(app_instance);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {

		System.out.println("[" + context.getContextPath() + "] ServletContext[" + app_instance + "] Start destroy.");
		// JDBC Deregister 処理
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while( drivers.hasMoreElements() ){
		    Driver driver = drivers.nextElement();
		    try {
				DriverManager.deregisterDriver(driver);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("[" + context.getContextPath() + "] ServletContext[" + app_instance + "] End destroy.");
	}
}

