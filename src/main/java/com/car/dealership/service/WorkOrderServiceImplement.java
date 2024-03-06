package com.car.dealership.service;

import com.car.dealership.entity.WorkOrder;
import com.car.dealership.repository.WorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return workOrderRepository.findById(id).get();
    }

    @Override
    @Transactional
    public WorkOrder createWorkOrder(WorkOrder workOrder) {
        return workOrderRepository.save(workOrder);
    }

    @Override
    @Transactional
    public WorkOrder updateWorkOrder(Long id, WorkOrder workOrder) {
        return workOrderRepository.save(workOrder);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        workOrderRepository.deleteById(id);
    }
}
