package com.coffee.factory;

import java.util.List;

import com.coffee.dao.mysql.CoffeeTypeDao;
import com.coffee.entity.CoffeeTypeEntity;

public class DtoFactory {
	private DtoFactory() {

	}

	public static DtoFactory getFactory() {
		return new DtoFactory();
	}

	public List<CoffeeTypeEntity> getAllCoffeeType() {
		DaoFactory daoFactory = DaoFactory.getMySqlFactory();

		CoffeeTypeDao coffeeTypeDao = daoFactory.getCoffeeType();

		List<CoffeeTypeEntity> loadAllCoffeeType = coffeeTypeDao.loadAllCoffeeType();
		return loadAllCoffeeType;
	}

}
