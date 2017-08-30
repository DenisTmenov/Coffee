package com.coffee.dao;

import java.util.List;

import com.coffee.entity.ConfigurationEntity;
import com.coffee.exception.EntityException;

public interface ConfigurationDaoInterface {

	public List<ConfigurationEntity> getConfigurationDataFromDb() throws EntityException;

}
