package com.car.dealership.service;

import com.car.dealership.entity.OrderType;
import com.car.dealership.repository.OrderTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

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

        OrderType currentOrderType = orderTypeRepository.findById(id).orElseThrow(() -> new
                NoSuchElementException("Order Type not found!"));

        return currentOrderType;
    }

    @Override
    @Transactional
    public OrderType createOrderType(OrderType orderType) {
        return orderTypeRepository.save(orderType);
    }

    @Override
    @Transactional
    public OrderType updateOrderType(Long id, OrderType orderType) {

        OrderType currentOrderType = orderTypeRepository.findById(id).orElseThrow(() -> new
                NoSuchElementException("Order Type not found!"));

        currentOrderType.setOrderName(orderType.getOrderName() != null ? orderType.getOrderName() : currentOrderType.getOrderName());
        currentOrderType.setWorkOrders(orderType.getWorkOrders() != null ? orderType.getWorkOrders() : currentOrderType.getWorkOrders());

        return orderTypeRepository.save(currentOrderType);
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws Exception {

        OrderType currentOrderType = orderTypeRepository.findById(id).orElseThrow(() -> new
                NoSuchElementException("Order Type not found!"));

        orderTypeRepository.deleteById(id);

        throw new Exception("Order Type successfully deleted!");
    }
}
