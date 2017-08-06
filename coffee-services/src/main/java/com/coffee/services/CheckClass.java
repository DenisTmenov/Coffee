package com.coffee.services;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class CheckClass {

	public static boolean check(HttpServletRequest request, String name, String value) {

		Map<String, String> map = new HashMap<>();

		map.put("Attribute", (String) request.getAttribute(name));
		map.put("Parameter", request.getParameter(name));
		map.put("Session", (String) request.getSession().getAttribute(name));

		for (Map.Entry<String, String> entry : map.entrySet()) {
			String mapValue = entry.getValue();
			if (mapValue != null) {
				if (mapValue.equals(value)) {
					System.out.println("key " + entry.getKey() + " value " + mapValue);
					return true;
				}
			}
		}
		System.out.println("nulllllllllllllllllll");
		return false;
	}

}
