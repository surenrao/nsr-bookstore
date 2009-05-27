package com.nyayapati.bookstore.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility to Print Request header 
 * @author Surya Nyayapati
 *
 */
public class HttpUtil 
{	
	private static int RETRY_ATTEMPTS = 3; 
	
	static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	private HttpClient httpClient;
	
	//key url to body as value
	private Map<String,String> cache;
	public HttpUtil() {
		cache = new HashMap<String, String>();
	}	

	public Map<String, String> getCache() {
		return cache;
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	/**
	 * for secure connection just pass https URL inside HttpMethod.
	 * 
	 * @param method
	 * @return
	 * @throws IOException
	 */
	public String sendRequest(HttpMethod method) throws IOException {
		return sendRequest(method,0);
	}

	public String sendRequest(HttpMethod method, int retriesCount) throws IOException {
		logger.debug("javax.net.ssl.trustStore = {}",System.getProperty("javax.net.ssl.trustStore"));
		logger.debug("URI: {}",method.getURI());
		
		BufferedReader rd;
		StringBuilder sb;
		if(httpClient==null) throw new RuntimeException("httpClient is not set");
		try {
			int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				String tmp = "Method failed: " + method.getStatusLine();
				logger.debug(tmp);				
				throw new IOException(tmp);
			}

			// Get the response
			rd = new BufferedReader(new InputStreamReader(method
					.getResponseBodyAsStream()));
			String line;
			sb = new StringBuilder();

			while ((line = rd.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}

			rd.close();			
			return sb.toString();
		} catch (SocketException se) {
			if (retriesCount <= RETRY_ATTEMPTS) {
				return sendRequest(method, retriesCount+1);
			} else {
				throw se;
			}
		} finally {
			method.releaseConnection();
			rd = null;//for GC
			sb = null;
		}
	}

	/**
	 * Same as {@link #sendRequest(HttpMethod)} but caches the response 
	 * and does not throw exception but will return empty string
	 * @param url
	 * @return response body of url
	 */
	public String getCachedResponse(String url) 
	{	
		try {
			if(cache==null)
			{
				cache = new HashMap<String, String>();
			}
			if(cache.containsKey(url))
			{
				logger.debug("Getting cached:{}",url);
				return cache.get(url);
			}
			else
			{
				GetMethod method = new GetMethod(url);	
				String response;
				
				response = sendRequest(method);
				
				cache.put(url, response);
				logger.debug("Setting cache and getting :{}",url);
				return response;
			}	
		} catch (IOException e) {
			return "";
		}
	}
	
	/**
	 * clear cache made by {@link #getCachedResponse(String)}
	 */
	public void clearCache()
	{
		logger.debug("Clearing cache");
		cache.clear();
	}
//	public void doIt()
//	{
//		if(logger.isDebugEnabled()){logger.debug("doIt Invoked");}
//	}
	/**
	 * Loops through Request header and formats the output to string
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getHeaderToString(HttpServletRequest request)
	{
		StringBuilder headerSb = new StringBuilder();
		headerSb.append("\nRequestURL - "+request.getRequestURL());
		headerSb.append("\nPathInfo - "+request.getPathInfo());		
		headerSb.append("\nQueryString - "+request.getQueryString());
// 		This can be grabbed from header as well		
//		Cookie[] cookie = request.getCookies();
//		for (int i = 0; i < cookie.length; i++) {
//			errSb.append("{Cookie} "+cookie[i].getName());
//			errSb.append(" - "+cookie[i].getValue());
//			errSb.append("\n");
//		}
		Enumeration<String> names = request.getHeaderNames();
	    while (names.hasMoreElements()) {
	      String name = names.nextElement();
	      Enumeration<String> values = request.getHeaders(name); // support multiple values
	      if (values != null) {
	        while (values.hasMoreElements()) {
	          String value = values.nextElement();
	          headerSb.append("\n{Header} ");	
	          headerSb.append(name + " - " + value);	          
	        }
	      }
	    }
	    try {
	    	return headerSb.toString();
		} finally {
			headerSb = null;
		}	    
	}
	
	/**
	 * Get querystring but if removeParam is passed, it will remove it from the query.
	 * @param parameter request.getParameterMap()
	 * @param removeParam
	 * @return
	 */
	public static String getModifiedQueryString(Map<String,Object[]> parameter, String[] removeParam)
	{
		if(parameter==null) return null;
		
		if(removeParam!=null)
		{
			for (int i = 0; i < removeParam.length; i++) {
				parameter.remove(removeParam[i]);
			}
		}
		StringBuilder sb = new StringBuilder();
		for (Iterator<Map.Entry<String,Object[]>> iterator = parameter.entrySet().iterator(); 
				iterator.hasNext();) {
			Map.Entry<String,Object[]> me = iterator.next();
			Object[] values = me.getValue();
			String key = me.getKey();
			if(values!=null)
			{				
				sb.append(key);
				sb.append("=");
				sb.append(values[0]);
				sb.append("&");				
			}else
			{
				sb.append(key);
				sb.append("=");
				sb.append("&");
			}				
		}
		if(sb.length()>1)
			sb.deleteCharAt(sb.length() - 1);
		try {
			return sb.toString();
		} finally {
			sb=null;
		}		
	}	
	
	/**
	 * convert Map to encoded url's post parameter string It ignores
	 * Map.getValue = empty or null
	 * 
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String map2QueryString(Map<String, String> params)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, String> entry : params.entrySet()) {
			logger.debug("entry.getKey: {} - entry.getValue:{}",entry.getKey() ,entry.getValue());
			
			if (entry.getValue() != null && !"".equals(entry.getValue().trim()))
				sb.append(String.format("%s=%s&", URLEncoder.encode(entry
						.getKey(), "UTF-8"), URLEncoder.encode(
						entry.getValue(), "UTF-8")));
		}

		if (sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);

		logger.debug(sb.toString());
		
		try{
			return sb.toString();
		}finally{
			sb=null;
		}
	}

	/**
	 * Pass in Encoded url's post String and it will decode and convert the
	 * params to Map
	 * 
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, String> queryString2Map(String url)
			throws UnsupportedEncodingException {
				
		Map<String, String> toReturn = new LinkedHashMap<String, String>();
		if(url==null) return toReturn;
		
		String[] init_response = url.split("&");
		for (String response_part : init_response) {
			String[] key_value = response_part.split("=");
			if (key_value.length == 2) {
				logger.debug("{} - {}",key_value[0],key_value[1]);
				
				toReturn.put(URLDecoder.decode(key_value[0], "UTF-8"),
						URLDecoder.decode(key_value[1], "UTF-8"));
			} else {
				logger.debug("key_value: blank - null, response_part: {}",response_part);
				
				toReturn.put(URLDecoder.decode(key_value[0], "UTF-8"), "");
			}
		}
		return toReturn;
	}
	/**
	 * The returned URL contains a protocol, server name, port number, and server path and context path 
	 * @param request
	 * @return
	 */
	public static String getFullContextPath(HttpServletRequest request)
	{
		String currentProtocol;		
		if(request.isSecure())
		{
			currentProtocol="https://";
		}else{
			currentProtocol="http://";
		}
		StringBuilder sbContextPath = new StringBuilder();
		sbContextPath.append(currentProtocol);
		sbContextPath.append(request.getServerName());
		if((!request.isSecure() && request.getServerPort() != 80)
		|| (request.isSecure() && request.getServerPort() != 443)) {
			sbContextPath.append(':');
			sbContextPath.append(request.getServerPort());
		}
		sbContextPath.append(request.getContextPath());		
//		if(sbContextPath.charAt(sbContextPath.length()-1) != '/')
//			sbContextPath.append("/");
		
		try {
			return	sbContextPath.toString();
		} finally {
			sbContextPath = null;
		}		
	}
	//key url to body as value
//	private Map<String,WebObject> cache;
//	class WebObject
//	{
//		String url;
//		String body;
//		String lastModified;
//	}
//	/**
//	 * This is based on last-modified
//	 * @param url
//	 */
//	public void getCachedResponse(String url)
//	{
//		HeadMethod head =null;
//		try {
//			head = new HeadMethod(url);
//			int statusCode = httpClient.executeMethod(head);
//			if (statusCode != HttpStatus.SC_OK) {
//				String tmp = "Method failed: " + head.getStatusLine();
//				if (logger.isDebugEnabled()){
//					logger.debug(tmp);
//				}
//				throw new IOException(tmp);
//			}			
//
//			// Retrieve just the last modified header value.
//			Header headerLastModified = head.getResponseHeader("last-modified");
//			Header headerDate = head.getResponseHeader("date");
//			SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
//			Calendar lastModified = Calendar.getInstance();
//			try {
//				if(headerLastModified!=null)
//				{
//					String strLastModified = headerLastModified.getValue();					
//					lastModified.setTime(sdf.parse(strLastModified));
//				}
//				else if(headerDate!=null)				
//				{					
//					String strDate = headerDate.getValue();
//					lastModified.setTime(sdf.parse(strDate));									
//				}				
//			} catch (ParseException e) {
//				logger.error("",e);
//			}
//			
//			
//		} 
//		catch( HttpException he ) 
//		{
//			logger.error("",he);
//		}
//		catch( IOException ie )
//		{
//			logger.error("",ie);
//		}
//		finally  
//		{
//			head.releaseConnection();
//		}        
//
//	}

}
