package com.coffee.utils;

public final class StringUtils {

	public static final String EMPTY_STR = "";

	private StringUtils() {
		throw new AssertionError("Class contains static methods only. You should not instantiate it!");
	}

	public static boolean isEmpty(String str) {
		if (str != null) {
			return EMPTY_STR.equals(str);
		}

		return true;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isBlank(String str) {
		if (isNotEmpty(str)) {
			return EMPTY_STR.equals(str.trim());
		}

		return true;
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	public static String getStringBegin(String str) {
		return getStringBegin(str, 5);
	}

	public static String getStringBegin(String str, int countChars) {
		if (isBlank(str)) {
			return EMPTY_STR;
		}

		String strBegin = EMPTY_STR;
		if (str.length() < countChars) {
			strBegin = str;
			strBegin = strBegin + "...";

			return strBegin;
		}

		strBegin = str.substring(0, countChars - 1);
		strBegin = strBegin + "...";

		return strBegin;
	}
}
