package com.nyayapati.bookstore.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nyayapati.bookstore.web.CustomHttpServletRequest;
import com.nyayapati.bookstore.web.CustomHttpServletResponse;

public class InterceptAllFilter implements Filter{

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if(!(httpRequest instanceof CustomHttpServletRequest))
		{
			httpRequest = new CustomHttpServletRequest(httpRequest);
		}
				
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		if(!(httpResponse instanceof CustomHttpServletResponse))
		{
			httpResponse = new CustomHttpServletResponse(httpResponse);
		}		
		
		filterChain.doFilter(httpRequest, httpResponse);
	}
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	

}
