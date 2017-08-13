package com.coffee.factory;

import com.coffee.dao.mysql.CoffeeOrderDao;
import com.coffee.dao.mysql.CoffeeOrderItemDao;
import com.coffee.dao.mysql.CoffeeTypeDao;
import com.coffee.exception.UnsupportedStoradgeTypeException;
import com.coffee.factory.type.StoradgeTypes;

public abstract class DaoFactory {

	public abstract CoffeeTypeDao getCoffeeType();

	public abstract CoffeeOrderDao getCoffeeOrder();

	public abstract CoffeeOrderItemDao getCoffeeOrderItem();

	public static DaoFactory getMySqlFactory() {
		return getFactory(StoradgeTypes.MySql);
	}

	public static DaoFactory getFactory(StoradgeTypes type) {
		switch (type) {
		case MySql: {
			return new MySqlDaoFactory();
		}

		}

		throw new UnsupportedStoradgeTypeException();
	}

}
