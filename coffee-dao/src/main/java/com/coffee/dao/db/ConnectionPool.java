package com.coffee.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.PropertyConfigurator;

import com.coffee.utils.PropertiesClass;

public class ConnectionPool {
	private BasicDataSource basicDS;

	private ConnectionPool() {
		try {
			PropertyConfigurator.configure(getClass().getResource("/settings/log4j.properties"));

			Properties jdbcSettings = PropertiesClass.getSettings("db");

			String driverName = jdbcSettings.getProperty("db.driver");
			String url = jdbcSettings.getProperty("db.url");
			String user = jdbcSettings.getProperty("db.user");
			String password = jdbcSettings.getProperty("db.password");
			String ssl = jdbcSettings.getProperty("db.ssl");
			basicDS = new BasicDataSource();
			basicDS.setDriverClassName(driverName);
			basicDS.setUrl(url);
			basicDS.setUsername(user);
			basicDS.setPassword(password);

			if (ssl != null) {
				basicDS.setConnectionProperties("useSSL=" + ssl);
			}

		} catch (Exception e) {
			throw new RuntimeException("Some errors occurred during create connection!", e);
		}
	}

	private static class DataSourceHolder {
		private static final ConnectionPool HOLDER_INSTANCE = new ConnectionPool();
	}

	public static ConnectionPool getInstance() {
		return DataSourceHolder.HOLDER_INSTANCE;
	}

	public Connection getConnection() throws SQLException {
		return basicDS.getConnection();
	}

	public BasicDataSource getDataSource() {
		return basicDS;
	}

	public static void closeDbResources(Connection connection, PreparedStatement statement, ResultSet resultSet) {
		closeResultSet(resultSet);
		closeStatement(statement);
		closeConnection(connection);
	}

	private static void closeConnection(Connection connection) {
		if (connection != null) {

			try {
				connection.close();
			} catch (SQLException e) {
			}

		}
	}

	private static void closeStatement(PreparedStatement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
			}
		}
	}

	private static void closeResultSet(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
			}
		}
	}

}
