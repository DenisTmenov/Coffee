package com.coffee.services;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.coffee.entity.CoffeeTypeEntity;
import com.coffee.exception.EntityException;

public class CreatorEntity {

	public static CoffeeTypeEntity createCoffeeTypeEntity(ResultSet set) {
		Integer id = null;
		String typeName = null;
		Double price = null;
		Character disabled = null;
		try {
			id = set.getInt("id");
			typeName = set.getString("type_name");
			price = set.getDouble("price");
			disabled = set.getString("disabled").charAt(0);
		} catch (SQLException e) {
			throw new EntityException("Exception in createDboBatchesEntity().", e);
		}

		CoffeeTypeEntity entity = new CoffeeTypeEntity();

		entity.setId(id);
		entity.setTypeName(typeName);
		entity.setPrice(price);
		entity.setDisabled(disabled);

		return entity;
	}

}
