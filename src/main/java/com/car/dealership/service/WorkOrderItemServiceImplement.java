package com.car.dealership.service;

import com.car.dealership.entity.WorkOrderItem;
import com.car.dealership.repository.WorkOrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class WorkOrderItemServiceImplement implements WorkOrderItemService{

    @Autowired
    private WorkOrderItemRepository workOrderItemRepository;

    @Override
    public List<WorkOrderItem> findAll() {
        return workOrderItemRepository.findAll();
    }

    @Override
    public WorkOrderItem findById(Long id) {

        WorkOrderItem currentWorkOrderItem = workOrderItemRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Work Order Item not found!"));

        return currentWorkOrderItem;
    }

    @Override
    @Transactional
    public WorkOrderItem create(WorkOrderItem workOrderItem) {
        return workOrderItemRepository.save(workOrderItem);
    }

    @Override
    @Transactional
    public WorkOrderItem update(Long id, WorkOrderItem workOrderItem) {

        WorkOrderItem currentWorkOrderItem = workOrderItemRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Work Order Item not found!"));

        currentWorkOrderItem.setWorkOrder(workOrderItem.getWorkOrder() != null ? workOrderItem.getWorkOrder() : currentWorkOrderItem.getWorkOrder());
        currentWorkOrderItem.setOrderType(workOrderItem.getOrderType() != null ? workOrderItem.getOrderType() : currentWorkOrderItem.getOrderType());
        currentWorkOrderItem.setDescription(workOrderItem.getDescription() != null ? workOrderItem.getDescription() : currentWorkOrderItem.getDescription());

        return workOrderItemRepository.save(currentWorkOrderItem);
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws Exception {

        WorkOrderItem currentWorkOrderItem = workOrderItemRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Work Order Item not found!"));

        workOrderItemRepository.deleteById(id);

        throw new Exception("Work Order Item successfully deleted!");
    }
}
