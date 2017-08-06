package com.coffee.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.coffee.exceptions.UtilsException;

public class PropertiesClass {
	public static Properties getSettings(String nameSettings) {
		String settingsPath = "settings/" + nameSettings + ".properties";

		Properties properties = new Properties();
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(settingsPath);

		try {
			properties.load(stream);
		} catch (IOException e) {
			throw new UtilsException("Exception in reading properties.", e);
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				throw new UtilsException("Exception in closing properties stream.", e);
			}
		}

		return properties;
	}

}
