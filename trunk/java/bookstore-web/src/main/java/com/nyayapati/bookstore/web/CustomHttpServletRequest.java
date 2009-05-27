package com.nyayapati.bookstore.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CustomHttpServletRequest extends HttpServletRequestWrapper {

	public CustomHttpServletRequest(HttpServletRequest request) {
		super(request);		
	}

}
