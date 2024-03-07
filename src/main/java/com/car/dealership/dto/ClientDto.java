package com.car.dealership.dto;

import com.car.dealership.entity.Role;
import com.car.dealership.entity.WorkOrder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ClientDto {

//NO SE ALCANZA A IMPLEMENTAR EL DTO

    @JsonIgnoreProperties("id")
    private Long id;
    private String firstsName;
    private String middleName;
    private String lastName;
    private String secondLastName;
    private String email;
    private String phoneNumber;


    private List<WorkOrder> workOrders = new ArrayList<>();

    public ClientDto(String firstsName, String middleName, String lastName, String secondLastName, String email,
                     String phoneNumber) {

        this.firstsName = firstsName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public ClientDto() {
    }

}
