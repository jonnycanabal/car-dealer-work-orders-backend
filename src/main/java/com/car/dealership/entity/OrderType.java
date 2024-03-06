package com.car.dealership.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orderTypes")
public class OrderType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderName;

    @ManyToMany(mappedBy = "orderTypes")
    @JsonIgnoreProperties("orderTypes")
    private List<WorkOrder> workOrders = new ArrayList<>();

//    @OneToMany(mappedBy = "orderType", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<WorkOrderItem> workOrderItems = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Bogota")
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }
}
