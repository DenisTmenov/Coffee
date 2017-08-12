package com.coffee.web.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffee.domian.CoffeeListDto;
import com.coffee.utils.HttpUtils;
import com.coffee.utils.LinkKeeper;

@WebServlet("/OrderList")
public class OrderListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String SUM_COST = "sumCost";
	private final String TRANSPORT = "transport";
	private final String TOTAL_COST = "totalCost";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpUtils.setEncoding(request, response);
		HttpUtils.includeView(LinkKeeper.JSP_HEADER, request, response);
		HttpUtils.includeView(LinkKeeper.JSP_MENU, request, response);
		HttpUtils.includeView(LinkKeeper.JSP_ORDER_LIST, request, response);
		HttpUtils.includeView(LinkKeeper.JSP_FOOTER, request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpUtils.setEncoding(request, response);
		CalculateTotalAmountOrder(request);
		response.sendRedirect(LinkKeeper.PAGE_ORDER_LIST);
	}

	private void CalculateTotalAmountOrder(HttpServletRequest request) {
		Double sumCost = 0.0;
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<CoffeeListDto> userChoice = (List<CoffeeListDto>) session.getAttribute(LinkKeeper.USER_CHOICE);
		for (CoffeeListDto coffeeListDto : userChoice) {
			sumCost += coffeeListDto.getTotalPrice();
		}

		Double transport = 5.0;

		Double totalCost = sumCost + transport;

		setAttribyteToSession(session, sumCost, transport, totalCost);
	}

	private void setAttribyteToSession(HttpSession session, Double sumCost, Double transport, Double totalCost) {
		session.setAttribute(SUM_COST, sumCost);
		session.setAttribute(TRANSPORT, transport);
		session.setAttribute(TOTAL_COST, totalCost);
	}
}
