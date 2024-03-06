package com.car.dealership.repository;

import com.car.dealership.entity.WorkOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrderItemRepository extends JpaRepository<WorkOrderItem, Long> {
}
