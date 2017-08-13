package com.coffee.factory;

import com.coffee.dao.mysql.CoffeeOrderDao;
import com.coffee.dao.mysql.CoffeeOrderItemDao;
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

	@Override
	public CoffeeOrderDao getCoffeeOrder() {
		try {
			Class<?> daoClass = Class.forName("com.coffee.dao.mysql.CoffeeOrderDao");

			Object daoObject = daoClass.newInstance();

			return ((CoffeeOrderDao) daoObject);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new UnsupportedDaoTypeException();
		}
	}

	@Override
	public CoffeeOrderItemDao getCoffeeOrderItem() {
		try {
			Class<?> daoClass = Class.forName("com.coffee.dao.mysql.CoffeeOrderItemDao");

			Object daoObject = daoClass.newInstance();

			return ((CoffeeOrderItemDao) daoObject);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new UnsupportedDaoTypeException();
		}
	}

}
