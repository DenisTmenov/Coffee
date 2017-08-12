package com.coffee.web.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffee.utils.HttpUtils;
import com.coffee.utils.LinkKeeper;

@WebServlet("/Order")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String ADDRESS_NOT_EXISTS_CODE = "address.not.exists";
	public static final String ADDRESS_NOT_EXISTS_VALUE = "Please enter address.";
	public static final String FULLNAME_NOT_EXISTS_CODE = "fullname.not.exists";
	public static final String FULLNAME_NOT_EXISTS_VALUE = "Please enter your fullname.";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpUtils.setEncoding(request, response);
		HttpUtils.includeView(LinkKeeper.JSP_HEADER, request, response);
		HttpUtils.includeView(LinkKeeper.JSP_MENU, request, response);
		HttpUtils.includeView(LinkKeeper.JSP_ORDER, request, response);
		HttpUtils.includeView(LinkKeeper.JSP_FOOTER, request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpUtils.setEncoding(request, response);

		HttpSession session = request.getSession();
		Map<String, String> errorMap = new HashMap<>();

		if (buttonIsEnter(request)) {
			if (fullNameIsEnter(request)) {
				if (addressIsEnter(request)) {
					System.out.println("tutututututu");
					response.sendRedirect(LinkKeeper.PAGE_ORDER);
				} else {
					errorMap.put(ADDRESS_NOT_EXISTS_CODE, ADDRESS_NOT_EXISTS_VALUE);
					session.setAttribute(LinkKeeper.VALIDATION_ERRORS, errorMap);
					HttpUtils.forwardToView(LinkKeeper.PAGE_ORDER_LIST, request, response);
				}
			} else {
				errorMap.put(FULLNAME_NOT_EXISTS_CODE, FULLNAME_NOT_EXISTS_VALUE);
				session.setAttribute(LinkKeeper.VALIDATION_ERRORS, errorMap);
				HttpUtils.forwardToView(LinkKeeper.PAGE_ORDER_LIST, request, response);
			}
		} else {
			session.removeAttribute(LinkKeeper.VALIDATION_ERRORS);
			response.sendRedirect(LinkKeeper.PAGE_ORDER_LIST);
		}

	}

	private boolean buttonIsEnter(HttpServletRequest request) {
		return HttpUtils.isParameterExists(request, "btnOrderOK");
	}

	private boolean fullNameIsEnter(HttpServletRequest request) {
		return HttpUtils.isParameterExists(request, "fullname");
	}

	private boolean addressIsEnter(HttpServletRequest request) {
		return HttpUtils.isParameterExists(request, "address");
	}
}
