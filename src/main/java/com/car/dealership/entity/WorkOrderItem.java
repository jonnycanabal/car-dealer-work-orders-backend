package com.car.dealership.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class WorkOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "work_order_id")
    @JsonIgnoreProperties("workOrderItems")
    private WorkOrder workOrder;

    @ManyToOne
    @JoinColumn(name = "order_type_id")
    @JsonIgnoreProperties("workOrders")
    private OrderType orderType;

    private String description;
}
