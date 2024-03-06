package com.car.dealership.service;

import com.car.dealership.entity.OrderType;

import java.util.List;

public interface OrderTypeService {

    List<OrderType> findAll();

    OrderType findById(Long id);

    OrderType createOrderType(OrderType orderType);

    OrderType updateOrderType(Long id, OrderType orderType);

    void deleteById(Long id) throws Exception;
}
