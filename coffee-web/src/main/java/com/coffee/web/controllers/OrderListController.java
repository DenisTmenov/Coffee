package com.coffee.web.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffee.domian.CoffeeListDto;
import com.coffee.entity.CoffeeTypeEntity;
import com.coffee.utils.HttpUtils;
import com.coffee.utils.LinkKeeper;

@WebServlet("/OrderList")
public class OrderListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String BUTTON_NAME = "btnMakeOrder";
	public static final String ERROR_CHECK_KEY = "ERROR_IN_CHECK";
	public static final String ERROR_CHECK_VALUE = "Any coffee is not enter!!!";
	public static final String ERROR_COUNT_KEY = "ERROR_IN_COUNT";
	public static final String ERROR_COUNT_VALUE = "Enter count!!!";
	public static final String USER_MADE_CHOICE_OK = "userChoice";

	private final String JSP_CHECK_KEY = "check";
	private final String JSP_CHECK_VALUE = "on";
	private final String JSP_COUNT_KEY = "count";

	private boolean errorInCheck = false;
	private boolean errorInCount = false;

	private List<CoffeeTypeEntity> coffeeTypeEntity = new ArrayList<>();
	private List<CoffeeListDto> order = new ArrayList<>();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpUtils.setEncoding(request, response);
		HttpUtils.includeView(LinkKeeper.JSP_HEADER, request, response);
		HttpUtils.includeView(LinkKeeper.JSP_MENU, request, response);
		HttpUtils.includeView(LinkKeeper.JSP_ORDER_LIST, request, response);
		HttpUtils.includeView(LinkKeeper.JSP_FOOTER, request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpUtils.setEncoding(request, response);

		if (buttonIsEnter(request)) {
			if (compareCheckAndCount(request)) {
				setAttributsInSession(request);
				response.sendRedirect(LinkKeeper.PAGE_ORDER_LIST);
			} else {
				setAttributsInSession(request);
				response.sendRedirect(LinkKeeper.PAGE_COFFEE_LIST);
			}
		}
	}

	private boolean buttonIsEnter(HttpServletRequest request) {
		return !request.getParameter(BUTTON_NAME).isEmpty();
	}

	private boolean compareCheckAndCount(HttpServletRequest request) {
		boolean rezult = false;
		Map<String, String> checkMap = new HashMap<>();
		Map<String, Integer> countMap = new HashMap<>();

		getCoffeeList(request);
		divideParameters(checkMap, countMap, request);

		if (checkMap.size() > 0) {
			for (Map.Entry<String, String> entry : checkMap.entrySet()) {
				String num = entry.getKey().replace(JSP_CHECK_KEY, "");
				if (countMap.containsKey("count" + num)) {
					rezult = true;
					Integer count = countMap.get("count" + num);
					for (CoffeeTypeEntity coffee : coffeeTypeEntity) {
						Integer id = coffee.getId();
						if (id == Integer.valueOf(num)) {

							CoffeeListDto dto = new CoffeeListDto();
							dto.setCount(count);
							dto.setId(id);
							dto.setPrice(coffee.getPrice());
							dto.setTotalPrice(count * coffee.getPrice());
							dto.setTypeName(coffee.getTypeName());

							order.add(dto);
						}
					}
				}
			}
			errorInCount = true;
		} else {
			errorInCheck = true;
		}
		return rezult;
	}

	private void divideParameters(Map<String, String> checkMap, Map<String, Integer> countMap,
			HttpServletRequest request) {
		Map<String, String> parameters = getMapParametersFromRequest(request);

		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			if (itIsButton(entry)) {
				continue;
			}
			if (itIsCheckButton(entry)) {
				if (checkButtonIsActive(entry)) {
					checkMap.put(entry.getKey(), entry.getValue());
					continue;
				}
			}
			if (itIsCount(entry)) {
				if (itIsNumber(entry)) {
					if (Integer.valueOf(entry.getValue()) > 0) {
						countMap.put(entry.getKey(), Integer.valueOf(entry.getValue()));
					}
					continue;
				}
			}
		}
	}

	private Map<String, String> getMapParametersFromRequest(HttpServletRequest request) {
		Map<String, String> rezult = new HashMap<>();
		Enumeration<String> requestAttributeNames = request.getParameterNames();
		while (requestAttributeNames.hasMoreElements()) {
			String attributeName = requestAttributeNames.nextElement();
			String attributeValue = request.getParameter(attributeName);
			rezult.put(attributeName, attributeValue);
		}
		return rezult;
	}

	private boolean itIsButton(Map.Entry<String, String> entry) {
		return entry.getKey().equals(BUTTON_NAME);
	}

	private boolean itIsCheckButton(Map.Entry<String, String> entry) {
		return entry.getKey().startsWith(JSP_CHECK_KEY);
	}

	private boolean checkButtonIsActive(Map.Entry<String, String> entry) {
		return entry.getValue().equals(JSP_CHECK_VALUE);
	}

	private boolean itIsCount(Map.Entry<String, String> entry) {
		return entry.getKey().startsWith(JSP_COUNT_KEY);
	}

	private boolean itIsNumber(Map.Entry<String, String> entry) {
		String regex = "\\d+";
		return entry.getValue().matches(regex);
	}

	private void setAttributsInSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		cleanSessionOfErrors(request);
		if (errorInCheck) {
			session.setAttribute(ERROR_CHECK_KEY, ERROR_CHECK_VALUE);
		} else if (errorInCount) {
			session.setAttribute(ERROR_COUNT_KEY, ERROR_COUNT_VALUE);
		}

		if (order.size() > 0) {
			session.setAttribute(USER_MADE_CHOICE_OK, order);
		}

		cleanBooleans();
	}

	private void cleanSessionOfErrors(HttpServletRequest request) {
		request.getSession().removeAttribute(OrderListController.ERROR_CHECK_KEY);
		request.getSession().removeAttribute(OrderListController.ERROR_COUNT_KEY);
	}

	private void cleanBooleans() {
		errorInCheck = false;
		errorInCount = false;
	}

	// !!!!!!!!!!!!!!!!!!!!!!!!!

	@SuppressWarnings("unchecked")
	private void getCoffeeList(HttpServletRequest request) {
		HttpSession session = request.getSession();
		coffeeTypeEntity = (List<CoffeeTypeEntity>) session.getAttribute(CoffeeListController.COFFEE_TYPE_LIST);
		for (CoffeeTypeEntity coffeeTypeEntity : coffeeTypeEntity) {
			System.out.println(coffeeTypeEntity.getTypeName() + " " + coffeeTypeEntity.getDisabled() + " "
					+ coffeeTypeEntity.getId() + " " + coffeeTypeEntity.getPrice());
		}

	}

}
