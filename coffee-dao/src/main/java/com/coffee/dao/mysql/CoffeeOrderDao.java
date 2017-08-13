package com.coffee.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.coffee.dao.CoffeeOrderDaoInterface;
import com.coffee.dao.db.ConnectionPool;
import com.coffee.entity.CoffeeOrderEntity;
import com.coffee.exception.EntityException;

public class CoffeeOrderDao implements CoffeeOrderDaoInterface {

	@Override
	public void save(CoffeeOrderEntity bean) throws EntityException {
		Connection connection = null;
		PreparedStatement statement = null;
		Date orderDate = null;
		if (bean.getOrderDate() == null) {
			orderDate = new Date();
		}

		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(" INSERT INTO coffee_order (order_date, name, delivery_address, cost)  values (?, ?, ?, ?)");

			statement.setDate(1, new java.sql.Date(orderDate.getTime()));
			statement.setString(2, bean.getName());
			statement.setString(3, bean.getDeliveryAddress());
			statement.setDouble(4, bean.getCost());

			statement.execute();

		} catch (SQLException e) {
			throw new EntityException("Exception with save CoffeeOrder to DB!!!", e);
		} finally {
			ConnectionPool.closeDbResources(connection, statement);
		}

	}

	@Override
	public String saveAndReturnDate(CoffeeOrderEntity bean) throws EntityException {
		Connection connection = null;
		PreparedStatement statement = null;

		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date dateNow = new Date();

		String date = sdf.format(dateNow);

		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(" INSERT INTO coffee_order (order_date, name, delivery_address, cost)  values (?, ?, ?, ?)");

			statement.setString(1, date);
			statement.setString(2, bean.getName());
			statement.setString(3, bean.getDeliveryAddress());
			statement.setDouble(4, bean.getCost());

			statement.execute();

		} catch (SQLException e) {
			throw new EntityException("Exception with save CoffeeOrder to DB!!!", e);
		} finally {
			ConnectionPool.closeDbResources(connection, statement);
		}

		return date;

	}

	@Override
	public Integer getIdOrderByDate(String dateOrder) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		Integer idOrder = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement("SELECT id FROM coffee_order WHERE order_date = ?");
			statement.setString(1, dateOrder);
			set = statement.executeQuery();

			while (set.next()) {
				idOrder = set.getInt("id");
			}
		} catch (SQLException e) {
			throw new EntityException("Exception in loadAllCoffeeType().", e);
		} finally {
			ConnectionPool.closeDbResources(connection, statement, set);
		}

		return idOrder;
	}

}
