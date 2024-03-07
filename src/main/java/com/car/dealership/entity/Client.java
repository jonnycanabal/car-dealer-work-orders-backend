package com.car.dealership.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstsName;
    private String middleName;
    private String lastName;
    private String secondLastName;
    private Integer identificationCard;
    private String email;
    private String phoneNumber;
    private String placeOfResidence;
    private String address;

    @OneToMany(mappedBy = "client")
    @JsonIgnoreProperties({"client"})
    private List<WorkOrder> workOrders = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Bogota")
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }

}
