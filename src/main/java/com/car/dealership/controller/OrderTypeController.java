package com.car.dealership.controller;

import com.car.dealership.entity.OrderType;
import com.car.dealership.service.OrderTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderType")
public class OrderTypeController {

    @Autowired
    private OrderTypeService orderTypeService;

    @GetMapping
    public List<OrderType> viewAll(){
        return orderTypeService.findAll();
    }

    @GetMapping("/{id}")
    public OrderType viewById(@PathVariable Long id){
        return orderTypeService.findById(id);
    }

    @PostMapping("/create")
    public OrderType create (@RequestBody OrderType orderType){
        return orderTypeService.createOrderType(orderType);
    }

    @PutMapping("/update/{id}")
    public OrderType update(@PathVariable Long id, @RequestBody  OrderType orderType){
        return orderTypeService.updateOrderType(id, orderType);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) throws Exception {
        orderTypeService.deleteById(id);
    }
}
