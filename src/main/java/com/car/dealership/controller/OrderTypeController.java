package com.car.dealership.controller;

import com.car.dealership.entity.OrderType;
import com.car.dealership.service.OrderTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderType")
public class OrderTypeController {

    @Autowired
    private OrderTypeService orderTypeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderType> viewAll(){
        return orderTypeService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderType viewById(@PathVariable Long id){
        return orderTypeService.findById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderType create (@RequestBody OrderType orderType){
        return orderTypeService.createOrderType(orderType);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderType update(@PathVariable Long id, @RequestBody  OrderType orderType){
        return orderTypeService.updateOrderType(id, orderType);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) throws Exception {
        orderTypeService.deleteById(id);
    }
}
