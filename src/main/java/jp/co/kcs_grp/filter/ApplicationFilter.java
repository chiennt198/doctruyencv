package jp.co.kcs_grp.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spark.servlet.SparkFilter;

public class ApplicationFilter extends SparkFilter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = (HttpServletRequest) request; // NOSONAR
		
		if (httpRequest.getRequestURI().endsWith(".conf")) {
			 ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
			 return;
		}
		
		if ( !httpRequest.getRequestURI().contains("/api/")) {
			String[] arrFile = httpRequest.getServletPath().split("\\.");
			
			if (  arrFile == null || arrFile != null && arrFile.length == 1) {
				String[] arrUrl = httpRequest.getRequestURI().split("/");
				
				if ( arrUrl == null || ( arrUrl != null && arrUrl.length != 4 ) ||  !"truyen".equals(arrUrl[2]) ) {
					 ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_OK);
					 return;
				}
				
				
				if ( httpRequest.getRequestURI().contains("/truyen/")) {
					
					if ( httpRequest.getSession().getAttribute("truyen") == null) {
						httpRequest.getSession().setAttribute("truyen", httpRequest.getServletPath());
					} else {
						String session = String.valueOf(httpRequest.getSession().getAttribute("truyen"));
						
						if ( httpRequest.getRequestURI().contains(session) ) {
							 ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_OK);
							 return;
						}
					}
							
					
				}
			}
		}
		
		super.doFilter(request, response, chain);
	}
}
