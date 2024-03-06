package com.car.dealership.service;

import com.car.dealership.entity.WorkOrder;
import com.car.dealership.entity.WorkOrderItem;
import com.car.dealership.repository.WorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class WorkOrderServiceImplement implements WorkOrderService {

    @Autowired
    private WorkOrderRepository workOrderRepository;

    @Override
    public List<WorkOrder> findAll() {
        return workOrderRepository.findAll();
    }

    @Override
    public WorkOrder findById(Long id) {

        WorkOrder currentWorkOrder = workOrderRepository.findById(id).orElseThrow(() -> new
                NoSuchElementException("Work Order not found!"));

        return currentWorkOrder;
    }

    @Override
    @Transactional
    public WorkOrder createWorkOrder(WorkOrder workOrder) {
        return workOrderRepository.save(workOrder);
    }

    @Override
    @Transactional
    public WorkOrder updateWorkOrder(Long id, WorkOrder workOrder) {

        WorkOrder currentWorkOrder = workOrderRepository.findById(id).orElseThrow(() -> new
                NoSuchElementException("Work Order not found!"));

        currentWorkOrder.setVehicle(workOrder.getVehicle() != null ? workOrder.getVehicle() : currentWorkOrder.getVehicle());

        if (workOrder.getOrderTypes() != null) {
            currentWorkOrder.getOrderTypes().addAll(workOrder.getOrderTypes());
        }

        List<WorkOrderItem> updatedItem = new ArrayList<>();

        if (workOrder.getWorkOrderItems() != null) {

            for (WorkOrderItem item : workOrder.getWorkOrderItems()) {
                item.setWorkOrder(currentWorkOrder);

                updatedItem.add(item);
            }
        } else {
            currentWorkOrder.setWorkOrderItems(null);
        }

        currentWorkOrder.getWorkOrderItems().clear();
        currentWorkOrder.getWorkOrderItems().addAll(updatedItem);

        return workOrderRepository.save(currentWorkOrder);
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws Exception {

        WorkOrder currentWorkOrder = workOrderRepository.findById(id).orElseThrow(() -> new
                NoSuchElementException("Work Order not found!"));

        workOrderRepository.deleteById(id);

        throw new Exception("Work Order successfully deleted!");
    }
}
