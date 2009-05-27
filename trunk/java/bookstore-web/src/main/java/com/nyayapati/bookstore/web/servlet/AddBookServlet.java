package com.nyayapati.bookstore.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//http://userpages.umbc.edu/~tarr/jst/lectures/14-MVC-6pp.pdf
//http://74.125.47.132/search?q=cache:PN83NwHcAzkJ:userpages.umbc.edu/~tarr/jst/lectures/14-MVC-6pp.pdf+servlet+with+jsp+custom+mvc&cd=1&hl=en&ct=clnk&gl=us&client=firefox-a
//Use this text to explain
//¥ Understanding the benefits of MVC
//¥ Using RequestDispatcher to implement MVC
//¥ Forwarding requests from servlets to JSP pages
//¥ Handling relative URLs
//¥ Choosing among different display options
//¥ Comparing data-sharing strategies
//¥ Forwarding requests from JSP pages
//¥ Including pages instead of forwarding to them
//Good material for writing
//http://www.web4j.com/Criticisms_Drawbacks_Pitfalls_Spring_Rails_PHP.jsp
//http://www.web4j.com/Java_Web_Application_Framework_Overview.jsp
//No framework
public class AddBookServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String view = "/WEB-INF/test.jsp";

		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}

}
