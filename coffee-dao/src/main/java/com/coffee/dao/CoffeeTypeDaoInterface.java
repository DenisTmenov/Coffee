package com.coffee.dao;

import java.util.List;

import com.coffee.entity.CoffeeTypeEntity;
import com.coffee.exception.EntityException;

public interface CoffeeTypeDaoInterface {

	void save(CoffeeTypeEntity bean) throws EntityException;

	void update(CoffeeTypeEntity bean) throws EntityException;

	void remove(Integer idUser) throws EntityException;

	public List<CoffeeTypeEntity> loadAllCoffeeType() throws EntityException;

}
