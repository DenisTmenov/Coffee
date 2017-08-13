package com.coffee.dao;

import com.coffee.entity.CoffeeOrderItemEntity;
import com.coffee.exception.EntityException;

public interface CoffeeOrderItemDaoInterface {

	void save(CoffeeOrderItemEntity bean) throws EntityException;

}
