package com.coffee.dao;

import com.coffee.entity.CoffeeOrderEntity;
import com.coffee.exception.EntityException;

public interface CoffeeOrderDaoInterface {

	void save(CoffeeOrderEntity bean) throws EntityException;

	String saveAndReturnDate(CoffeeOrderEntity bean) throws EntityException;

	Integer getIdOrderByDate(String dateOrder) throws EntityException;

}
