package com.coffee.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.coffee.dao.ConfigurationDaoInterface;
import com.coffee.dao.db.ConnectionPool;
import com.coffee.entity.ConfigurationEntity;
import com.coffee.exception.EntityException;
import com.coffee.services.CreatorEntity;

public class ConfigurationDao implements ConfigurationDaoInterface {

	@Override
	public List<ConfigurationEntity> getConfigurationDataFromDb() throws EntityException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet set = null;

		List<ConfigurationEntity> result = new ArrayList<ConfigurationEntity>();

		try {
			connection = ConnectionPool.getInstance().getConnection();
			statement = connection.prepareStatement("SELECT * FROM configuration");
			set = statement.executeQuery();

			while (set.next()) {
				ConfigurationEntity entity = CreatorEntity.createConfigurationEntity(set);
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
