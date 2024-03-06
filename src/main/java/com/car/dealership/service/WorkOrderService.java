package com.car.dealership.service;

import com.car.dealership.entity.WorkOrder;

import java.util.List;

public interface WorkOrderService {

    List<WorkOrder> findAll();

    WorkOrder findById(Long id);

    WorkOrder createWorkOrder(WorkOrder workOrder);

    WorkOrder updateWorkOrder(Long id, WorkOrder workOrder);

    void deleteById(Long id);
}
