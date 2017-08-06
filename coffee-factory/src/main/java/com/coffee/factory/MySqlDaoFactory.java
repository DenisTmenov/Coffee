package com.coffee.factory;

import com.coffee.dao.mysql.CoffeeTypeDao;
import com.coffee.exception.UnsupportedDaoTypeException;

public class MySqlDaoFactory extends DaoFactory {

	MySqlDaoFactory() {
		super();
	}

	@Override
	public CoffeeTypeDao getCoffeeType() {
		try {
			Class<?> daoClass = Class.forName("com.coffee.dao.mysql.CoffeeTypeDao");

			Object daoObject = daoClass.newInstance();

			return ((CoffeeTypeDao) daoObject);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new UnsupportedDaoTypeException();
		}
	}

}
