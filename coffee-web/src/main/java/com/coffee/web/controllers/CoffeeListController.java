package com.coffee.web.controllers;

import java.io.IOException;
import java.util.ArrayList;
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
import com.coffee.factory.DtoFactory;
import com.coffee.utils.HttpUtils;
import com.coffee.utils.LinkKeeper;
import com.coffee.utils.StringUtils;

@WebServlet("/CoffeeList")
public class CoffeeListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String COFFEE_TYPE_LIST = "CoffeeTypeList";
	private final Character DISABLE_COFFEE_ARGUMENT = 'Y';

	private final String BUTTON_NAME = "btnMakeOrder";

	private final String CHECK_NOT_EXISTS_CODE = "check.not.exists";
	private final String CHECK_NOT_EXISTS_VALUE = "Any coffee is not enter!!!";
	private final String COUNT_NOT_EXISTS_CODE = "count.not.exists";
	private final String COUNT_NOT_EXISTS_VALUE = "Enter count!!!";
	private final String COUNT_IS_NOT_NUMERIC_CODE = "count.not.numeric";
	private final String COUNT_IS_NOT_NUMERIC_VALUE = "Entery count can be numeric!!!";
	private final String CHECK_COUNT_ERROR_KEY = "check.and.count.not.equals";
	private final String CHECK_COUNT_ERROR_VALUE = "Tick the amount opposite.";

	private final String JSP_CHECK_KEY = "check";
	private final String JSP_COUNT_KEY = "count";
	private final String CHECKBOX_CHECKED = "checked";

	private Map<String, String> allParametersFromRequest;
	private Map<String, String> errorMap;
	private HttpSession session;

	private List<CoffeeListDto> coffeeType;
	private List<CoffeeListDto> userChoice;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpUtils.setEncoding(request, response);

		transferDataFromDatabaseToThisPage(request);

		HttpUtils.includeView(LinkKeeper.JSP_HEADER, request, response);
		HttpUtils.includeView(LinkKeeper.JSP_MENU, request, response);
		HttpUtils.includeView(LinkKeeper.JSP_COFFEE_LIST, request, response);
		HttpUtils.includeView(LinkKeeper.JSP_FOOTER, request, response);
	}

	private void transferDataFromDatabaseToThisPage(HttpServletRequest request) {
		if (coffeeType == null) {
			coffeeType = loadDataFromDB();
			setAttributeInSession(coffeeType, request);
		}
		if (session.getAttribute(LinkKeeper.USER_CHOICE) != null) {
			session.removeAttribute(LinkKeeper.USER_CHOICE);
		}
	}

	private List<CoffeeListDto> loadDataFromDB() {
		DtoFactory mySqlFactory = DtoFactory.getFactory();
		List<CoffeeListDto> coffeeType = mySqlFactory.getAllCoffeeType();
		return coffeeType;
	}

	private void setAttributeInSession(List<CoffeeListDto> coffeeList, HttpServletRequest request) {
		List<CoffeeListDto> result = new ArrayList<>();

		for (CoffeeListDto coffeeDto : coffeeList) {
			Character disabled = coffeeDto.getDisabled();
			if (disabled.equals(DISABLE_COFFEE_ARGUMENT)) {
				continue;
			}
			result.add(coffeeDto);
		}
		coffeeType = result;
		session = request.getSession();
		session.setAttribute(COFFEE_TYPE_LIST, result);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dropAllErrors();
		initializeFields(request);

		if (HttpUtils.isParameterExists(session, COFFEE_TYPE_LIST)) {
			addCheckCountToDto(request);

		}
		if (allFieldIsOK(request)) {
			dropAllErrors();
			session.setAttribute(LinkKeeper.USER_CHOICE, userChoice);
			HttpUtils.forwardToView(LinkKeeper.PAGE_ORDER_LIST, request, response);
		} else {
			response.sendRedirect(LinkKeeper.PAGE_COFFEE_LIST);
		}
	}

	private void initializeFields(HttpServletRequest request) {
		allParametersFromRequest = new HashMap<>();
		allParametersFromRequest = checkFieldMap(allParametersFromRequest, request);
		errorMap = new HashMap<>();
		session = request.getSession();

	}

	private Map<String, String> checkFieldMap(Map<String, String> map, HttpServletRequest request) {
		if (map.isEmpty()) {
			map = HttpUtils.getMapParametersFromRequest(request);
		}

		return map;
	}

	private void addCheckCountToDto(HttpServletRequest request) {
		for (CoffeeListDto coffeeListDto : coffeeType) {
			coffeeListDto.setChecked(null);
			for (Map.Entry<String, String> entry : allParametersFromRequest.entrySet()) {
				addCheck(entry, coffeeListDto);
				addCount(entry, coffeeListDto);
			}
		}
		setAttributeInSession(coffeeType, request);
	}

	private void addCheck(Map.Entry<String, String> entry, CoffeeListDto coffeeListDto) {
		if (entry.getKey().startsWith(JSP_CHECK_KEY)) {
			Integer checkId = Integer.parseInt(entry.getKey().replaceAll(JSP_CHECK_KEY, ""));
			if (checkId == coffeeListDto.getId()) {
				if (entry.getValue() != null) {
					coffeeListDto.setChecked(CHECKBOX_CHECKED);
				}
			}
		}
	}

	private void addCount(Map.Entry<String, String> entry, CoffeeListDto coffeeListDto) {
		if (entry.getKey().startsWith(JSP_COUNT_KEY)) {
			Integer checkId = Integer.parseInt(entry.getKey().replaceAll(JSP_COUNT_KEY, ""));
			if (checkId == coffeeListDto.getId()) {
				if (entry.getValue() != null && !entry.getValue().isEmpty()) {
					coffeeListDto.setCount(Integer.parseInt(entry.getValue()));
				} else {
					coffeeListDto.setCount(null);
				}
			}
		}
	}

	private boolean allFieldIsOK(HttpServletRequest request) {
		if (buttonIsEnter(request)) {
			if (checkBoxsChecked(request)) {
				if (countFildsIsEnter(request)) {
					if (numCheckEquelsNumCount(request)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean buttonIsEnter(HttpServletRequest request) {
		return HttpUtils.isParameterExists(request, BUTTON_NAME);
	}

	private boolean checkBoxsChecked(HttpServletRequest request) {

		for (Map.Entry<String, String> entry : allParametersFromRequest.entrySet()) {
			if (entry.getKey().startsWith(JSP_CHECK_KEY)) {
				dropErrorFromSession(CHECK_NOT_EXISTS_CODE);
				return true;
			}
		}

		setErrorInSession(CHECK_NOT_EXISTS_CODE, CHECK_NOT_EXISTS_VALUE);

		return false;
	}

	private boolean countFildsIsEnter(HttpServletRequest request) {
		allParametersFromRequest = checkFieldMap(allParametersFromRequest, request);

		for (Map.Entry<String, String> entry : allParametersFromRequest.entrySet()) {
			if (entry.getKey().startsWith(JSP_COUNT_KEY)) {
				if (StringUtils.isNotEmpty(entry.getValue())) {
					if (StringUtils.isNumeric(entry.getValue())) {
						dropErrorFromSession(COUNT_NOT_EXISTS_CODE);
						dropErrorFromSession(COUNT_IS_NOT_NUMERIC_CODE);
						return true;
					} else {
						setErrorInSession(COUNT_IS_NOT_NUMERIC_CODE, COUNT_IS_NOT_NUMERIC_VALUE);
					}
				} else {
					setErrorInSession(COUNT_NOT_EXISTS_CODE, COUNT_NOT_EXISTS_VALUE);
				}
			}
		}
		return false;
	}

	private boolean numCheckEquelsNumCount(HttpServletRequest request) {
		userChoice = new ArrayList<>();
		for (CoffeeListDto entry : coffeeType) {
			if (entry.getChecked() != null && entry.getCount() != null) {
				if (entry.getCount() > 0) {
					entry.setTotalPrice(entry.getPrice() * entry.getCount());
					userChoice.add(entry);
				}
			}
		}

		if (userChoice.isEmpty()) {
			setErrorInSession(CHECK_COUNT_ERROR_KEY, CHECK_COUNT_ERROR_VALUE);
			return false;
		}

		return true;
	}

	private void setErrorInSession(String code, String value) {
		errorMap.put(code, value);
		session.setAttribute(LinkKeeper.VALIDATION_ERRORS, errorMap);
	}

	private void dropAllErrors() {
		dropErrorFromSession(CHECK_NOT_EXISTS_CODE);
		dropErrorFromSession(COUNT_NOT_EXISTS_CODE);
		dropErrorFromSession(COUNT_IS_NOT_NUMERIC_CODE);
		dropErrorFromSession(CHECK_COUNT_ERROR_KEY);
	}

	private void dropErrorFromSession(String code) {
		if (errorMap != null) {
			if (errorMap.containsKey(code)) {
				errorMap.remove(code);
				session.setAttribute(LinkKeeper.VALIDATION_ERRORS, errorMap);
			}
		}
	}
}
