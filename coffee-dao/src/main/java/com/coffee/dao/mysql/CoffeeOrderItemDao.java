package com.coffee.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.coffee.dao.CoffeeOrderItemDaoInterface;
import com.coffee.dao.db.ConnectionPool;
import com.coffee.entity.CoffeeOrderItemEntity;
import com.coffee.exception.EntityException;

public class CoffeeOrderItemDao implements CoffeeOrderItemDaoInterface {

	@Override
	public void save(CoffeeOrderItemEntity bean) throws EntityException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement(" INSERT INTO coffee_order_item (type_id, order_id, quantity)" + " values (?, ?, ?)");

			statement.setInt(1, bean.getTypeId());
			statement.setInt(2, bean.getOrderId());
			statement.setInt(3, bean.getQuantity());

			statement.execute();

		} catch (SQLException e) {
			throw new EntityException("Exception with save CoffeeOrderItem to DB!!!", e);
		} finally {
			ConnectionPool.closeDbResources(connection, statement);
		}
	}

}
