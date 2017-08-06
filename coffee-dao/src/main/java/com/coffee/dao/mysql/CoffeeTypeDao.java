package com.coffee.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.coffee.dao.CoffeeTypeDaoInterface;
import com.coffee.dao.db.ConnectionPool;
import com.coffee.entity.CoffeeTypeEntity;
import com.coffee.exception.EntityException;
import com.coffee.services.CreatorEntity;

public class CoffeeTypeDao implements CoffeeTypeDaoInterface {

	@Override
	public void save(CoffeeTypeEntity bean) throws EntityException {
	}

	@Override
	public void update(CoffeeTypeEntity bean) throws EntityException {
	}

	@Override
	public void remove(Integer idUser) throws EntityException {
	}

	@Override
	public List<CoffeeTypeEntity> loadAllCoffeeType() throws EntityException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		List<CoffeeTypeEntity> result = new ArrayList<CoffeeTypeEntity>();

		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement("SELECT * FROM coffee_type");
			set = statement.executeQuery();

			while (set.next()) {
				CoffeeTypeEntity entity = CreatorEntity.createCoffeeTypeEntity(set);
				result.add(entity);
			}
		} catch (SQLException e) {
			throw new EntityException("Exception in loadAllCoffeeType().", e);
		} finally {
			ConnectionPool.closeDbResources(connection, statement, set);
		}

		return result;
	}

}
