package com.car.dealership.controller;

import com.car.dealership.entity.WorkOrderItem;
import com.car.dealership.service.WorkOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workOrderItem")
public class WorkOrderItemController {

    @Autowired
    private WorkOrderItemService workOrderItemService;

    @GetMapping
    public List<WorkOrderItem> viewAll(){
        return workOrderItemService.findAll();
    }

    @GetMapping("/{id}")
    public WorkOrderItem viewById(@PathVariable Long id){
        return workOrderItemService.findById(id);
    }

    @PostMapping("/create")
    public WorkOrderItem create(@RequestBody WorkOrderItem workOrderItem){
        return workOrderItemService.create(workOrderItem);
    }

    @PutMapping("/update/{id}")
    public WorkOrderItem update(@PathVariable Long id, @RequestBody WorkOrderItem workOrderItem ){
        return workOrderItemService.update(id, workOrderItem);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) throws Exception {
        workOrderItemService.deleteById(id);
    }
}
