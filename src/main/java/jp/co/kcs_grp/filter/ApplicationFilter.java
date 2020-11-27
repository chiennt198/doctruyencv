package jp.co.kcs_grp.filter;

import static spark.Spark.halt;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.kcs_grp.utils.AppParams;
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
		
		try {
			getAuthWebApi(request, response, chain);
		} catch (Exception e) {
			 ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
			 return;
		}
		
	}
	
	public void getAuthWebApi(ServletRequest request, ServletResponse response, FilterChain chain) throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest) request; // NOSONAR
		String url = httpRequest.getServletPath();
		
		
		Map<String, String> urlMap = AppParams.getUrlNotAuthProp();
        String[] pattern = urlMap.get("pattern").split(";");
        String[] uri = urlMap.get("uri").split(";");
        if (uri != null && uri.length == 1 && "".equals(uri[0])) uri = null;
        boolean checkAuth = true;
        
        if (pattern != null && pattern.length > 0) {
        	for (int i = 0; i < pattern.length; i++) {
        		if (url.endsWith(pattern[i])) {
        			checkAuth = false;
        			break;
        		}
        	}
        }
        
        if (url.equals("/")) {
            checkAuth = false;
        } else {
            if (uri != null && uri.length > 0) {
                for (int i = 0; i < uri.length; i++) {
                    if (url.startsWith(uri[i])) {
                        checkAuth = false;
                        break;
                    }
                }
            }
        }
        
        if (checkAuth) {
        	Enumeration<String> key = httpRequest.getHeaders("AUTH_DATA");
        	if (httpRequest.getHeaders("AUTH_DATA") == null ) {
        		halt(401);
        	} else {
        		super.doFilter(request, response, chain);
        	}
        } else {
        	super.doFilter(request, response, chain);
        }
	}
}
