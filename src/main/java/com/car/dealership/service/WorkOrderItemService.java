package com.car.dealership.service;

import com.car.dealership.entity.WorkOrderItem;

import java.util.List;

public interface WorkOrderItemService {

    List<WorkOrderItem> findAll();

    WorkOrderItem findById(Long id);

    WorkOrderItem create(WorkOrderItem workOrderItem);

    WorkOrderItem update(Long id, WorkOrderItem workOrderItem);

    void deleteById(Long id) throws Exception;
}
