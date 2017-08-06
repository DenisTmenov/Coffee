package com.coffee.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffee.exceptions.UtilsException;

public final class HttpUtils {

	private HttpUtils() {
		throw new AssertionError("Class contains static methods only. You should not instantiate it!");
	}

	public static void setEncoding(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new UtilsException("Exception in setEncoding().", e);
		}
		response.setCharacterEncoding("UTF-8");
	}

	public static void includeView(String viewName, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewName);
		dispatcher.include(request, response);
	}

	public static void forwardToView(String viewName, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewName);
		dispatcher.forward(request, response);
	}

	/*
	 * public Map<String, String> createMapParameters(HttpServletRequest request)
	 * throws IOException {
	 * 
	 * Enumeration<String> parameterNames = request.getParameterNames(); Map<String,
	 * String> parametersRequest = new HashMap<String, String>(); while
	 * (parameterNames.hasMoreElements()) { String paramName =
	 * parameterNames.nextElement(); String paramValue =
	 * request.getParameter(paramName); parametersRequest.put(paramName,
	 * paramValue); } return parametersRequest; }
	 * 
	 * public void printMapToScrean(Map<String, String> map, HttpServletResponse
	 * response) throws IOException {
	 * 
	 * PrintWriter out = response.getWriter();
	 * response.setContentType("text/plain");
	 * 
	 * for (String name : map.keySet()) {
	 * 
	 * String key = name.toString(); String value = map.get(name).toString();
	 * StringBuffer sb = new StringBuffer();
	 * sb.append(key).append("\t").append(value).append("\n");
	 * out.write(sb.toString()); }
	 * 
	 * out.close();
	 * 
	 * }
	 * 
	 * @SuppressWarnings("unchecked") public static <T> T getAttribute(HttpSession
	 * session, String attrName) { if (session == null) { throw new
	 * IllegalArgumentException("HttpSession can not be NULL!"); }
	 * 
	 * if (StringUtils.isEmpty(attrName)) { throw new
	 * IllegalArgumentException("Attribute name can not be blank!"); }
	 * 
	 * return (T) session.getAttribute(attrName); }
	 * 
	 * public static Map<String, String> getMapAttribute(HttpSession session, String
	 * attrName) { Map<String, String> attribute = getAttribute(session, attrName);
	 * if (attribute == null) { return new HashMap<>(); }
	 * 
	 * return attribute; }
	 * 
	 * public static Map<String, String> getMapAttribute(HttpServletRequest request,
	 * String attrName) { Map<String, String> attribute = getAttribute(request,
	 * attrName); if (attribute == null) { return new HashMap<>(); }
	 * 
	 * return attribute; }
	 * 
	 * @SuppressWarnings("unchecked") public static <T> T
	 * getAttribute(HttpServletRequest request, String attrName) { if (request ==
	 * null) { throw new
	 * IllegalArgumentException("HttpServletRequest can not be NULL!"); }
	 * 
	 * if (StringUtils.isEmpty(attrName)) { throw new
	 * IllegalArgumentException("Attribute name can not be blank!"); }
	 * 
	 * return (T) request.getAttribute(attrName); }
	 * 
	 * public static boolean isParameterExists(HttpServletRequest request, String
	 * paramName) { if (StringUtils.isEmpty(paramName)) { throw new
	 * IllegalArgumentException("Parameter name can not be blank!"); }
	 * 
	 * String valueStr = request.getParameter(paramName); return
	 * StringUtils.isNotEmpty(valueStr); }
	 * 
	 * public static boolean isParameterExists(HttpSession session, String
	 * paramName) { if (StringUtils.isEmpty(paramName)) { throw new
	 * IllegalArgumentException("Parameter name can not be blank!"); }
	 * 
	 * String valueStr = (String) session.getAttribute(paramName); return
	 * StringUtils.isNotEmpty(valueStr); }
	 * 
	 * public static Integer getIntParam(HttpServletRequest request, String
	 * paramName) { if (StringUtils.isEmpty(paramName)) { throw new
	 * IllegalArgumentException("Parameter name can not be blank!"); }
	 * 
	 * String valueStr = request.getParameter(paramName); try { return new
	 * Integer(valueStr); } catch (Exception e) { return null; } }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * public static String returnRequestParameterValue(String nameParameter,
	 * HttpServletRequest request) { if (HttpUtils.isParameterExists(request,
	 * nameParameter)) { String value = (String)
	 * request.getParameter(nameParameter); return value; } return ""; }
	 */

}
