package com.coffee.web.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffee.utils.HttpUtils;
import com.coffee.utils.LinkKeeper;

@WebServlet("/Order")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpUtils.setEncoding(request, response);

		if (HttpUtils.isAttributeExists(request, LinkKeeper.ORDER_ATTRIBUTE_CODE)
				&& request.getAttribute(LinkKeeper.ORDER_ATTRIBUTE_CODE).equals(LinkKeeper.ORDER_ATTRIBUTE_VALUE)) {
			HttpUtils.includeView(LinkKeeper.JSP_HEADER, request, response);
			HttpUtils.includeView(LinkKeeper.JSP_MENU, request, response);
			HttpUtils.includeView(LinkKeeper.JSP_ORDER, request, response);
			HttpUtils.includeView(LinkKeeper.JSP_FOOTER, request, response);
		} else {
			response.sendRedirect(LinkKeeper.PAGE_COFFEE_LIST);
		}
	}

}
