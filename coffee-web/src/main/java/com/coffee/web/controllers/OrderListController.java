package com.coffee.web.controllers;

import java.io.IOException;
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
import com.coffee.domian.DeliveryDto;
import com.coffee.domian.UserChoiceCostDto;
import com.coffee.entity.ConfigurationEntity;
import com.coffee.factory.DtoFactory;
import com.coffee.utils.HttpUtils;
import com.coffee.utils.LinkKeeper;

@WebServlet("/OrderList")
public class OrderListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String ADDRESS_NOT_EXISTS_CODE = "address.not.exists";
	private final String ADDRESS_NOT_EXISTS_VALUE = "Please enter address.";
	private final String FULLNAME_NOT_EXISTS_CODE = "fullname.not.exists";
	private final String FULLNAME_NOT_EXISTS_VALUE = "Please enter your fullname.";

	private final String FULLNAME_PARAMETER = "fullname";
	private final String ADDRESS_PARAMETER = "contactAddress";
	private final String BUTTON_PARAMETER = "btnOrderOK";

	private Map<String, String> errorMap;
	private DeliveryDto delivery;

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

		errorMap = new HashMap<String, String>();

		HttpSession session = request.getSession();

		if (session.getAttribute(LinkKeeper.USER_CHOICE) != null) {

			CalculateTotalAmountOrder(session);

			if (buttonIsEnter(request)) {

				delivery = new DeliveryDto();

				if (fullNameIsEmpty(request)) {
					errorMap.put(FULLNAME_NOT_EXISTS_CODE, FULLNAME_NOT_EXISTS_VALUE);
				} else {
					String fullname = request.getParameter(FULLNAME_PARAMETER);
					delivery.setFullname(fullname);
				}
				if (addressIsEmpty(request)) {
					errorMap.put(ADDRESS_NOT_EXISTS_CODE, ADDRESS_NOT_EXISTS_VALUE);
				} else {
					String address = request.getParameter(ADDRESS_PARAMETER);
					delivery.setAddress(address);
				}

				session.setAttribute(LinkKeeper.USER_DELIVERY, delivery);

				if (errorMap.isEmpty()) {
					saveOrder(request, session);

					HttpUtils.forwardToView(LinkKeeper.PAGE_ORDER, request, response);
				} else {
					session.setAttribute(LinkKeeper.VALIDATION_ERRORS_ORDER_LIST_PAGE, errorMap);
					response.sendRedirect(LinkKeeper.PAGE_ORDER_LIST);
				}

			} else

			{
				session.removeAttribute(LinkKeeper.VALIDATION_ERRORS_ORDER_LIST_PAGE);
				response.sendRedirect(LinkKeeper.PAGE_ORDER_LIST);
			}
		} else {
			response.sendRedirect(LinkKeeper.PAGE_COFFEE_LIST);
		}

	}

	private void CalculateTotalAmountOrder(HttpSession session) {
		DtoFactory dtoFactory = DtoFactory.getFactory();
		List<ConfigurationEntity> configurationEntity = dtoFactory.getConfigurationDataFromDb();

		Map<String, Double> configFromDB = getConfigFromDB(configurationEntity);

		Double delivery = configFromDB.get(LinkKeeper.CONFIG_DELIVERY_NAME);
		Double countCup = configFromDB.get(LinkKeeper.CONFIG_COUNT_CUP_NAME);
		Double minimalCost = configFromDB.get(LinkKeeper.CONFIG_MINIMAL_COST_NAME);

		Double sumCost = 0.0;
		Double totalCost = 0.0;

		@SuppressWarnings("unchecked")
		List<CoffeeListDto> userChoice = (List<CoffeeListDto>) session.getAttribute(LinkKeeper.USER_CHOICE);

		if (userChoice != null) {
			for (CoffeeListDto coffeeDto : userChoice) {
				Integer countBonus = (int) (coffeeDto.getQuantity() / countCup);
				Double total = coffeeDto.getPrice() * coffeeDto.getQuantity() - coffeeDto.getPrice() * countBonus;
				sumCost += total;
				coffeeDto.setTotalPrice(total);
			}

			if (sumCost < minimalCost) {
				totalCost = sumCost + delivery;
			} else {
				totalCost = sumCost;
				delivery = 0.0;
			}

			UserChoiceCostDto userChoiceCostDto = new UserChoiceCostDto();

			userChoiceCostDto.setSumCost(sumCost);
			userChoiceCostDto.setTotalCost(totalCost);
			userChoiceCostDto.setShipping(delivery);

			session.setAttribute(LinkKeeper.USER_CHOICE_COST, userChoiceCostDto);
			session.setAttribute(LinkKeeper.USER_CHOICE, userChoice);
		}

	}

	private Map<String, Double> getConfigFromDB(List<ConfigurationEntity> configurationEntity) {
		Map<String, Double> result = new HashMap<String, Double>();
		for (ConfigurationEntity entity : configurationEntity) {
			if (entity.getName().equals(LinkKeeper.CONFIG_DELIVERY_NAME)) {
				result.put(LinkKeeper.CONFIG_DELIVERY_NAME, Double.valueOf(entity.getValue()));
			}
			if (entity.getName().equals(LinkKeeper.CONFIG_COUNT_CUP_NAME)) {
				result.put(LinkKeeper.CONFIG_COUNT_CUP_NAME, Double.valueOf(entity.getValue()));
			}
			if (entity.getName().equals(LinkKeeper.CONFIG_MINIMAL_COST_NAME)) {
				result.put(LinkKeeper.CONFIG_MINIMAL_COST_NAME, Double.valueOf(entity.getValue()));
			}
		}
		return result;
	}

	private boolean buttonIsEnter(HttpServletRequest request) {
		return HttpUtils.isParameterExists(request, BUTTON_PARAMETER);
	}

	private boolean fullNameIsEmpty(HttpServletRequest request) {
		return !HttpUtils.isParameterExists(request, FULLNAME_PARAMETER);
	}

	private boolean addressIsEmpty(HttpServletRequest request) {
		return !HttpUtils.isParameterExists(request, ADDRESS_PARAMETER);
	}

	private void removeAttrivutesFromSession(HttpSession session) {
		session.removeAttribute(LinkKeeper.COFFEE_TYPE_LIST);
		session.removeAttribute(LinkKeeper.VALIDATION_ERRORS_ORDER_LIST_PAGE);
		session.removeAttribute(LinkKeeper.USER_CHOICE);
		session.removeAttribute(LinkKeeper.USER_CHOICE_COST);
		session.removeAttribute(LinkKeeper.USER_DELIVERY);

	}

	private void saveOrder(HttpServletRequest request, HttpSession session) {
		saveOrderToDB(session);

		removeAttrivutesFromSession(session);

		request.setAttribute(LinkKeeper.ORDER_ATTRIBUTE_CODE, LinkKeeper.ORDER_ATTRIBUTE_VALUE);
	}

	private void saveOrderToDB(HttpSession session) {
		@SuppressWarnings("unchecked")
		List<CoffeeListDto> coffeeListDto = (List<CoffeeListDto>) session.getAttribute(LinkKeeper.USER_CHOICE);
		UserChoiceCostDto userChoiceCostDto = (UserChoiceCostDto) session.getAttribute(LinkKeeper.USER_CHOICE_COST);
		DeliveryDto deliveryDto = (DeliveryDto) session.getAttribute(LinkKeeper.USER_DELIVERY);

		DtoFactory dtoFactory = DtoFactory.getFactory();
		dtoFactory.saveCoffeeOrder(coffeeListDto, userChoiceCostDto, deliveryDto);
	}

}
