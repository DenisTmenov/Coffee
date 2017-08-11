package com.coffee.factory;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import com.coffee.dao.mysql.CoffeeTypeDao;
import com.coffee.domian.CoffeeListDto;
import com.coffee.entity.CoffeeTypeEntity;

public class DtoFactory {
	private DtoFactory() {

	}

	public static DtoFactory getFactory() {
		return new DtoFactory();
	}

	public List<CoffeeListDto> getAllCoffeeType() {
		List<CoffeeListDto> result = new ArrayList<>();

		DaoFactory daoFactory = DaoFactory.getMySqlFactory();

		CoffeeTypeDao coffeeTypeDao = daoFactory.getCoffeeType();

		List<CoffeeTypeEntity> loadAllCoffeeType = coffeeTypeDao.loadAllCoffeeType();

		for (CoffeeTypeEntity entity : loadAllCoffeeType) {
			Mapper mapper = new DozerBeanMapper();
			CoffeeListDto dto = mapper.map(entity, CoffeeListDto.class);

			result.add(dto);
		}

		return result;
	}

	public void saveCoffeeOrder() {
		DaoFactory daoFactory = DaoFactory.getMySqlFactory();
		/// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	}

}
