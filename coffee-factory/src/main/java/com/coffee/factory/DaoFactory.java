package com.coffee.factory;

import com.coffee.dao.mysql.CoffeeTypeDao;
import com.coffee.exception.UnsupportedStoradgeTypeException;
import com.coffee.factory.type.StoradgeTypes;

public abstract class DaoFactory {

	public abstract CoffeeTypeDao getCoffeeType();

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
