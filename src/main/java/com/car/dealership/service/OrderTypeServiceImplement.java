package com.car.dealership.service;

import com.car.dealership.entity.OrderType;
import com.car.dealership.repository.OrderTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderTypeServiceImplement implements OrderTypeService {

    @Autowired
    private OrderTypeRepository orderTypeRepository;

    @Override
    public List<OrderType> findAll() {
        return orderTypeRepository.findAll();
    }

    @Override
    public OrderType findById(Long id) {
        return orderTypeRepository.findById(id).get();
    }

    @Override
    @Transactional
    public OrderType createOrderType(OrderType orderType) {
        return orderTypeRepository.save(orderType);
    }

    @Override
    @Transactional
    public OrderType updateOrderType(Long id, OrderType orderType) {
        return orderTypeRepository.save(orderType);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        orderTypeRepository.deleteById(id);
    }
}
