package com.car.dealership.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role() {
        this.users = new ArrayList<>();
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
