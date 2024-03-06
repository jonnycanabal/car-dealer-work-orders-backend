package com.car.dealership.repository;

import com.car.dealership.entity.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTypeRepository extends JpaRepository<OrderType, Long> {
}
