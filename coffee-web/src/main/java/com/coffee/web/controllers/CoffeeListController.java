package com.coffee.web.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffee.domian.CoffeeListDto;
import com.coffee.factory.DtoFactory;
import com.coffee.utils.HttpUtils;
import com.coffee.utils.LinkKeeper;

@WebServlet("/CoffeeList")
public class CoffeeListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String COFFEE_TYPE_LIST = "CoffeeTypeList";
	public static final Character DISABLE_COFFEE_ARGUMENT = 'Y';

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpUtils.setEncoding(request, response);

		HttpUtils.includeView(LinkKeeper.JSP_HEADER, request, response);
		HttpUtils.includeView(LinkKeeper.JSP_MENU, request, response);

		transferDataFromDatabaseToThisPage(request);

		HttpUtils.includeView(LinkKeeper.JSP_COFFEE_LIST, request, response);
		HttpUtils.includeView(LinkKeeper.JSP_FOOTER, request, response);

		cleanSessionOfErrors(request);
	}

	private void transferDataFromDatabaseToThisPage(HttpServletRequest request) {
		List<CoffeeListDto> coffeeType = loadDataFromDB();
		setAttributeInSession(coffeeType, request);
	}

	private List<CoffeeListDto> loadDataFromDB() {
		DtoFactory mySqlFactory = DtoFactory.getFactory();
		List<CoffeeListDto> coffeeType = mySqlFactory.getAllCoffeeType();
		return coffeeType;
	}

	private void setAttributeInSession(List<CoffeeListDto> coffeeList, HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<CoffeeListDto> result = new ArrayList<>();

		for (CoffeeListDto coffeeTypeEntity : coffeeList) {
			Character disabled = coffeeTypeEntity.getDisabled();
			if (disabled.equals(DISABLE_COFFEE_ARGUMENT)) {
				continue;
			}
			result.add(coffeeTypeEntity);
		}

		session.setAttribute(COFFEE_TYPE_LIST, result);

	}

	private void cleanSessionOfErrors(HttpServletRequest request) {
		request.getSession().removeAttribute(OrderListController.ERROR_CHECK_KEY);
		request.getSession().removeAttribute(OrderListController.ERROR_COUNT_KEY);
	}

}
