package com.nyayapati.bookstore.web;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
//http://www.coderanch.com/t/210679/JSF/java/Exception-Handling-using-error-page
public class CustomHttpServletResponse extends HttpServletResponseWrapper {
	HttpServletResponse response;
	public CustomHttpServletResponse(HttpServletResponse response) {
		super(response);
		this.response=response;
	}

}
