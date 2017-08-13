package com.coffee.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

	public static boolean isParameterExists(HttpServletRequest request, String paramName) {
		if (StringUtils.isEmpty(paramName)) {
			throwIllegalArgumentException(paramName);
		}
		String valueStr = request.getParameter(paramName);
		return StringUtils.isNotEmpty(valueStr);
	}

	public static boolean isAttributeExists(HttpServletRequest request, String paramName) {
		if (StringUtils.isEmpty(paramName)) {
			throwIllegalArgumentException(paramName);
		}
		String valueStr = (String) request.getAttribute(paramName);
		return StringUtils.isNotEmpty(valueStr);
	}

	public static boolean isParameterExists(HttpSession session, String paramName) {
		if (StringUtils.isEmpty(paramName)) {
			throwIllegalArgumentException(paramName);
		}

		Object result = session.getAttribute(paramName);
		return result != null;
	}

	private static void throwIllegalArgumentException(String paramName) {
		throw new IllegalArgumentException("Parameter " + paramName + " is not exists!");
	}

	public static Map<String, String> getMapParametersFromRequest(HttpServletRequest request) {
		Map<String, String> rezult = new HashMap<>();
		Enumeration<String> requestAttributeNames = request.getParameterNames();
		while (requestAttributeNames.hasMoreElements()) {
			String attributeName = requestAttributeNames.nextElement();
			String attributeValue = request.getParameter(attributeName);
			rezult.put(attributeName, attributeValue);
		}
		return rezult;
	}

	public static void dropErrorFromSession(String codeForSearch, Map<?, ?> errorMap, HttpSession session, String nameInSession) {
		if (errorMap != null) {
			if (errorMap.containsKey(codeForSearch)) {
				errorMap.remove(codeForSearch);
				session.setAttribute(nameInSession, errorMap);
			}
		}
	}

}
