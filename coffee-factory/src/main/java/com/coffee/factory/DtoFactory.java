package com.coffee.factory;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import com.coffee.dao.mysql.CoffeeOrderDao;
import com.coffee.dao.mysql.CoffeeOrderItemDao;
import com.coffee.dao.mysql.CoffeeTypeDao;
import com.coffee.domian.CoffeeListDto;
import com.coffee.domian.DeliveryDto;
import com.coffee.domian.UserChoiceCostDto;
import com.coffee.entity.CoffeeOrderEntity;
import com.coffee.entity.CoffeeOrderItemEntity;
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

	public void saveCoffeeOrder(List<CoffeeListDto> coffeeListDto, UserChoiceCostDto userChoiceCostDto, DeliveryDto deliveryDto) {
		DaoFactory daoFactory = DaoFactory.getMySqlFactory();

		Mapper mapper = new DozerBeanMapper();
		CoffeeOrderEntity coffeeOrderEntity = mapper.map(deliveryDto, CoffeeOrderEntity.class);
		coffeeOrderEntity.setCost(userChoiceCostDto.getTotalCost());

		CoffeeOrderDao coffeeOrder = daoFactory.getCoffeeOrder();

		String dateOrder = coffeeOrder.saveAndReturnDate(coffeeOrderEntity);
		Integer idOrder = coffeeOrder.getIdOrderByDate(dateOrder);

		CoffeeOrderItemDao coffeeOrderItemDao = daoFactory.getCoffeeOrderItem();
		List<CoffeeOrderItemEntity> listEntity = new ArrayList<>();

		for (CoffeeListDto dto : coffeeListDto) {
			if (dto.getQuantity() != null) {
				CoffeeOrderItemEntity entity = new CoffeeOrderItemEntity();
				entity.setOrderId(idOrder);
				entity.setTypeId(dto.getId());
				entity.setQuantity(dto.getQuantity());
				listEntity.add(entity);
			}

		}

		for (CoffeeOrderItemEntity entity : listEntity) {
			coffeeOrderItemDao.save(entity);
		}

	}

}
