package com.coffee.services;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.coffee.entity.CoffeeTypeEntity;
import com.coffee.entity.ConfigurationEntity;
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
			throw new EntityException("Exception in createCoffeeTypeEntity().", e);
		}

		CoffeeTypeEntity entity = new CoffeeTypeEntity();

		entity.setId(id);
		entity.setTypeName(typeName);
		entity.setPrice(price);
		entity.setDisabled(disabled);

		return entity;
	}

	public static ConfigurationEntity createConfigurationEntity(ResultSet set) {
		Integer id = null;
		String name = null;
		Integer value = null;
		String transcript = null;

		try {
			id = set.getInt("id");
			name = set.getString("name");
			value = set.getInt("value");
			transcript = set.getString("transcript");

		} catch (SQLException e) {
			throw new EntityException("Exception in createConfigurationEntity().", e);
		}

		ConfigurationEntity entity = new ConfigurationEntity();

		entity.setId(id);
		entity.setName(name);
		entity.setValue(value);
		entity.setTranscript(transcript);

		return entity;
	}

}
