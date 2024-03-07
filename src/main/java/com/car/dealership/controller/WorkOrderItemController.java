package com.car.dealership.controller;

import com.car.dealership.entity.WorkOrderItem;
import com.car.dealership.service.WorkOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workOrderItem")
public class WorkOrderItemController {

    @Autowired
    private WorkOrderItemService workOrderItemService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<WorkOrderItem> viewAll(){
        return workOrderItemService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WorkOrderItem viewById(@PathVariable Long id){
        return workOrderItemService.findById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public WorkOrderItem create(@RequestBody WorkOrderItem workOrderItem){
        return workOrderItemService.create(workOrderItem);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WorkOrderItem update(@PathVariable Long id, @RequestBody WorkOrderItem workOrderItem ){
        return workOrderItemService.update(id, workOrderItem);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) throws Exception {
        workOrderItemService.deleteById(id);
    }
}
