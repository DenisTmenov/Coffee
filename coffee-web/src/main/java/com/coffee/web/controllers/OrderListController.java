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
import com.coffee.utils.HttpUtils;
import com.coffee.utils.LinkKeeper;

@WebServlet("/OrderList")
public class OrderListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String BUTTON_NAME = "btnMakeOrder";
	public static final String CHECK_NOT_EXISTS_CODE = "check.not.exists";
	public static final String CHECK_NOT_EXISTS_VALUE = "Any coffee is not enter!!!";
	public static final String COUNT_NOT_EXISTS_CODE = "count.not.exists";
	public static final String COUNT_NOT_EXISTS_VALUE = "Enter count!!!";
	public static final String USER_CHOICE = "userChoice";
	public static final String TRANSPORT_COST_KEY = "transport";
	public static final Double TRANSPORT_COST_VALUE = 5.0;
	public static final String SUM_COST_KEY = "sumCost";
	public static final String TOTAL_COST_KEY = "totalCost";

	private final String JSP_CHECK_KEY = "check";
	private final String JSP_CHECK_VALUE = "on";
	private final String JSP_COUNT_KEY = "count";

	private Double sumCost;
	private Double totalCost;

	private List<CoffeeListDto> coffeeDto;
	private List<CoffeeListDto> order;
	private Map<String, String> errorMap;
	private HttpSession session;

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

		session = request.getSession();
		errorMap = new HashMap<>();

		sumCost = 0.0;
		totalCost = 0.0;

		coffeeDto = new ArrayList<>();
		order = new ArrayList<>();

		if (buttonIsEnter(request)) {
			if (compareCheckAndCount(request)) {
				setAttributsInSession(request);
				response.sendRedirect(LinkKeeper.PAGE_ORDER_LIST);
			} else {
				response.sendRedirect(LinkKeeper.PAGE_COFFEE_LIST);
			}
		} else {
			session.removeAttribute(LinkKeeper.VALIDATION_ERRORS);
			response.sendRedirect(LinkKeeper.PAGE_COFFEE_LIST);
		}
	}

	private boolean buttonIsEnter(HttpServletRequest request) {
		return HttpUtils.isParameterExists(request, BUTTON_NAME);
	}

	private boolean compareCheckAndCount(HttpServletRequest request) {
		boolean rezult = false;
		Map<String, String> checkMap = new HashMap<>();
		Map<String, Integer> countMap = new HashMap<>();

		getCoffeeList(request);
		divideParameters(checkMap, countMap, request);

		Map<String, String> dataForDto = new HashMap<>();

		if (checkMap.size() > 0) {
			for (Map.Entry<String, String> entry : checkMap.entrySet()) {
				String num = entry.getKey().replace(JSP_CHECK_KEY, "");

				if (countMap.containsKey("count" + num)) {
					rezult = true;
					Integer count = countMap.get("count" + num);
					for (CoffeeListDto coffee : coffeeDto) {
						Integer id = coffee.getId();
						if (id == Integer.valueOf(num)) {

							dataForDto.put("count", count.toString());
							dataForDto.put("id", id.toString());
							dataForDto.put("price", coffee.getPrice().toString());
							dataForDto.put("typeName", coffee.getTypeName());

							CoffeeListDto dto = dataToDto(dataForDto);
							sumCost += dto.getTotalPrice();
							order.add(dto);
						}
					}

				} else {
					errorMap.put(COUNT_NOT_EXISTS_CODE, COUNT_NOT_EXISTS_VALUE);
					session.setAttribute(LinkKeeper.VALIDATION_ERRORS, errorMap);
				}
			}

		} else {
			errorMap.put(CHECK_NOT_EXISTS_CODE, CHECK_NOT_EXISTS_VALUE);
			session.setAttribute(LinkKeeper.VALIDATION_ERRORS, errorMap);
		}

		totalCost += sumCost + TRANSPORT_COST_VALUE;
		return rezult;
	}

	private CoffeeListDto dataToDto(Map<String, String> map) {
		CoffeeListDto dto = new CoffeeListDto();

		dto.setCount(Integer.valueOf(map.get("count")));
		dto.setId(Integer.valueOf(map.get("id")));
		dto.setPrice(Double.parseDouble(map.get("price")));
		dto.setTotalPrice(Double.parseDouble(map.get("price")) * Double.parseDouble(map.get("count")));
		dto.setTypeName(map.get("typeName"));

		return dto;
	}

	private void divideParameters(Map<String, String> checkMap, Map<String, Integer> countMap, HttpServletRequest request) {
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
		// !!!!!!!!!
		if (order.size() > 0) {
			session.setAttribute(USER_CHOICE, order);
			session.setAttribute(SUM_COST_KEY, sumCost);
			session.setAttribute(TRANSPORT_COST_KEY, TRANSPORT_COST_VALUE);
			session.setAttribute(TOTAL_COST_KEY, totalCost);
		}

	}

	private void cleanSessionOfErrors(HttpServletRequest request) {

	}

	@SuppressWarnings("unchecked")
	private void getCoffeeList(HttpServletRequest request) {
		HttpSession session = request.getSession();
		// coffeeDto = (List<CoffeeListDto>)
		// session.getAttribute(CoffeeListController.COFFEE_TYPE_LIST);
	}

}
