package com.car.dealership.controller;

import com.car.dealership.entity.WorkOrder;
import com.car.dealership.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workOrder")
public class WorkOrderController {

    @Autowired
    private WorkOrderService workOrderService;

    @GetMapping
    public List<WorkOrder> view() {
        return workOrderService.findAll();
    }

    @GetMapping("/{id}")
    public WorkOrder viewById(@PathVariable Long id) {
        return workOrderService.findById(id);
    }

    @PostMapping("/create")
    public WorkOrder create(@RequestBody WorkOrder workOrder) {
        return workOrderService.createWorkOrder(workOrder);
    }

    @PutMapping("/update/{id}")
    public WorkOrder update(@PathVariable Long id, @RequestBody WorkOrder workOrder) {
        return workOrderService.updateWorkOrder(id, workOrder);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) throws Exception {
        workOrderService.deleteById(id);
    }
}
