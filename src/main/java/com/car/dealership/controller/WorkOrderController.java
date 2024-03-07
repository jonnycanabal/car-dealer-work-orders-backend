package com.car.dealership.controller;

import com.car.dealership.entity.WorkOrder;
import com.car.dealership.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workOrder")
public class WorkOrderController {

    @Autowired
    private WorkOrderService workOrderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<WorkOrder> view() {
        return workOrderService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WorkOrder viewById(@PathVariable Long id) {
        return workOrderService.findById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public WorkOrder create(@RequestBody WorkOrder workOrder) {
        return workOrderService.createWorkOrder(workOrder);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WorkOrder update(@PathVariable Long id, @RequestBody WorkOrder workOrder) {
        return workOrderService.updateWorkOrder(id, workOrder);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) throws Exception {
        workOrderService.deleteById(id);
    }
}
