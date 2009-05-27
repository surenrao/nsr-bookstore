package com.nyayapati.bookstore.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.nyayapati.bookstore.web.util.HttpUtil;

public class CustomUrlRewriteFilter implements Filter{
	List<UrlWrite> rules;
	public final static String CONTEXT_PATH_FULL = "CONTEXT_PATH_FULL";
//	static final Logger logger = LoggerFactory.getLogger(CustomUrlRewriteFilter.class);
	public CustomUrlRewriteFilter() {
		Pattern productsPatern=Pattern.compile("^(.*)/products(.*)$");
		Pattern productPatern=Pattern.compile("^(.*)/product(.*)$");
		rules = new ArrayList<UrlWrite>();
		//rule 3
		rules.add(new UrlWrite("Homepage rule","^("+CONTEXT_PATH_FULL+")/$","$1/products"));
		
		List<Condition> conditionTrailSlash = new ArrayList<Condition>();
		conditionTrailSlash.add(new Condition(productsPatern, Next.AND));
		conditionTrailSlash.add(new Condition(Pattern.compile("^(.*)[^\\?](.*)/$"), Next.AND));
		//rule 4
		rules.add(new UrlWrite("Remove trailing slash","^(.*)/$","$1",conditionTrailSlash));

		List<Condition> conditionProducts = new ArrayList<Condition>();
		conditionProducts.add(new Condition(productsPatern, Next.AND));
		//rule 5
		rules.add(new UrlWrite("Replace /? with ?", "^(.*)/\\?(.*)$", "$1?$2", conditionProducts));
		//rule 6
		rules.add(new UrlWrite("Replace /? with &", "^(.*)products(.*)([\\?]+)(.*)$", "$1products$2&$4", conditionProducts));

		List<Condition> conditionProduct = new ArrayList<Condition>();
		conditionProduct.add(new Condition(productPatern, Next.AND));
		//rule 7
		rules.add(new UrlWrite("Convert article number","^(.*)(/[a-z]{2}|com)?/product/([a-zA-Z0-9]{6})(.*)$", "$1$2/product&articleNumber=$3", conditionProduct));
		//rule 8
		rules.add(new UrlWrite("Convert country code", "^(.*)/([a-z]{2}|com)/(.*)$", "$1/$3&country=$2"));
		//rule 9
		rules.add(new UrlWrite("Convert gender code","^(.*)/products(.*?)/(men|women|kids)(.*)$", "$1/products$2&gender=$3$4", conditionProducts));
		//rule 10
		rules.add(new UrlWrite("Convert division","^(.*)/products(.*?)/(performance|originals|style)[\\?]?(.*)$", "$1/products$2&division=$3$4", conditionProducts));
		//rule 11
		rules.add(new UrlWrite("product group","^(.*)/products(.*?)/(shoes|clothing|accessories|equipment)(.*)$","$1/products$2&group=$3/*$4", conditionProducts));

		List<Condition> conditionProductOrS = new ArrayList<Condition>();
		conditionProduct.add(new Condition(productPatern, Next.OR));
		conditionProduct.add(new Condition(productsPatern, Next.OR));
		//rule 12
		rules.add(new UrlWrite("Put ? after products page","^(.*)/product(s?)(.*)$", "$1/product$2?$3", conditionProductOrS));
		//rule 13
		rules.add(new UrlWrite("?& replace with ?","^(.*)/product(s?)\\?&(.*)$", "$1/product$2?$3", conditionProductOrS));
		//rule 14
		rules.add(new UrlWrite("Products.do page","^(.*)/products(.*)$", "/products.do$2", new ArrayList<Condition>(),true));
		//rule 15
		rules.add(new UrlWrite("Product.do page","^(.*)/product(.*)$", "/product.do$2", new ArrayList<Condition>(),true));
		//rule 16
		rules.add(new UrlWrite("javaScript.do page","^(.*)/javaScript(.*)$", "/javaScript.do$2", new ArrayList<Condition>(),true));
	}
	
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		httpRequest.setCharacterEncoding("UTF-8");
		String fullContextPath = HttpUtil.getFullContextPath(httpRequest);
		StringBuffer sbFullUrl = httpRequest.getRequestURL(); 
		sbFullUrl.append(httpRequest.getQueryString()==null? "":"?"+httpRequest.getQueryString());
		
		boolean isUrlChanged=false;
		
		// 0 - Don't rewrite known extensions
		Pattern conditionIgnore = Pattern.compile("^(.*)(\\.)(xml|htm|html|swf|gif|jpg|jpeg|png|js|css|dec|txt|do)(.*?)$");		
		Matcher matchIgnore = conditionIgnore.matcher(sbFullUrl);
		if (matchIgnore.matches())
		{
//			logger.trace("incommingUrl: {}",sbFullUrl);
//			logger.trace("0. ruleIgnore - {} matched",sbFullUrl);
			//last rule
			filterChain.doFilter(httpRequest, response);
			return;
		}
		else
		{
//			logger.debug("incommingUrl: {}",sbFullUrl);
		}
		Status status=null;
		Pattern productsPatern=Pattern.compile("^(.*)/products(.*)$");		
		Matcher matchProducts = productsPatern.matcher(sbFullUrl.toString());
		
		// 1 - DestUrl Redirects, For Ajax URL's redirect if the cookie destUrl is set
		if(matchProducts.matches())
		{
			String destUrl = (String)httpRequest.getSession().getAttribute("destUrl");
			if(destUrl != null && Pattern.compile("^(.*/.*)$").matcher(destUrl).matches())
			{
				status = fromToStatus("^.*$", sbFullUrl.toString());
				isUrlChanged = isUrlChanged==false? status.isUrlChanged:true;		
				if(status.isUrlChanged){
					sbFullUrl.replace(0, sbFullUrl.length(), status.match.replaceAll(destUrl));
//					logger.debug("1. DestUrl Redirects - {}",sbFullUrl);
				}
				
				httpRequest.setAttribute("redirected", "y");
			}
		}
		
		// 2 - Reset destUrl, Now that we have used destUrl, reset the value
		if(matchProducts.matches())
		{
			String destUrl = (String)httpRequest.getSession().getAttribute("destUrl");
			if(destUrl != null && Pattern.compile("^(.*/.*)$").matcher(destUrl).matches())
			{
				httpRequest.getSession().setAttribute("destUrl", null);
//				logger.debug("2. Reset destUrl");
			}
		}

		int i = 2;		
		for (Iterator<UrlWrite> iterator = rules.iterator(); iterator.hasNext();) {
			UrlWrite urlWrite = iterator.next();
			i++;
			boolean continueRule = true;
			if(urlWrite.conditions!=null)
			for (Iterator<Condition> iterator2 = urlWrite.conditions.iterator(); iterator2
					.hasNext();) {
				Condition condition = iterator2.next();				
				if(condition.next==Next.AND)
				{
					continueRule=true;
					if(!condition.patern.matcher(sbFullUrl.toString()).matches())
					{
						continueRule =false;
						break;
					}
				}
				else //OR
				{
					continueRule=false;
					if(condition.patern.matcher(sbFullUrl.toString()).matches())
					{
						continueRule =true;
						break;
					}					
				}
			} 
			if(continueRule){
				urlWrite.from = StringUtils.replaceEach(urlWrite.from, new String[]{CONTEXT_PATH_FULL}, new String[]{fullContextPath});
				status = fromToStatus(urlWrite.from, sbFullUrl.toString());
				isUrlChanged = isUrlChanged==false? status.isUrlChanged:true;		
				if(status.isUrlChanged){
					sbFullUrl.replace(0, sbFullUrl.length(), status.match.replaceAll(urlWrite.to));
//					logger.trace("{}. {} - {}", new Object[]{i, urlWrite.name,sbFullUrl});

					if(urlWrite.isLast)
					{
//						logger.debug("OutgoingUrl: {}",sbFullUrl.toString());
						if(!isUrlChanged)	
							filterChain.doFilter(httpRequest, response);
						else
							httpRequest.getRequestDispatcher(sbFullUrl.toString()).forward(httpRequest, response);
						
						return;
					}
				}
			}
		}
//		logger.debug("OutgoingUrl: {}",sbFullUrl.toString());
		if(!isUrlChanged)	
			filterChain.doFilter(httpRequest, response);
		else
			httpRequest.getRequestDispatcher(sbFullUrl.toString()).forward(httpRequest, response);
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	private Status fromToStatus(String regex, String fullUrl)
	{		
		Matcher match = Pattern.compile(regex).matcher(fullUrl);		
		return new Status(match, match.matches());		
	}
	
	static class Status
	{		
		public Status(Matcher match, boolean isUrlChanged) {
			super();
			this.match = match;
			this.isUrlChanged = isUrlChanged;
		}
		public Matcher match;
		public boolean isUrlChanged;
	}

	static class UrlWrite{
		public UrlWrite() {}
		
		public UrlWrite(String name, String from, String to) {
			super();
			this.name = name;
			this.to = to;
			this.from = from;
		}
		public UrlWrite(String name, String from, String to,  List<Condition> conditions) {
		super();
		this.name = name;
		this.to = to;
		this.from = from;
		this.conditions = conditions;
		}

		public UrlWrite(String name, String from, String to,  List<Condition> conditions, boolean isLast
				) {
			super();
			this.name = name;
			this.to = to;
			this.from = from;
			this.isLast = isLast;
			this.conditions = conditions;
		}

		String name;
		String to;
		String from;
		boolean isLast;
		List<Condition> conditions;
	}
	static class Condition
	{
		public Condition() {}
		public Condition(Pattern patern, Next next) {
			super();
			this.patern = patern;
			this.next = next;
		}	
		
		Pattern patern;
		Next next;
	}
	enum Next{
		AND,OR
	}
	

	
	
}
