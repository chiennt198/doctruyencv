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
		
		super.doFilter(request, response, chain);
	}
}
