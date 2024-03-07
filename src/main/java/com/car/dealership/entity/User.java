package com.car.dealership.entity;

//import com.car.dealership.validation.ExistsByUsername;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
//@JsonPropertyOrder({"firstsName"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstsName;
    private String lastName;
    private Integer identificationCard;
    private String email;
    private String phoneNumber;

//    @ExistsByUsername
    @Column(unique = true)
    @NotBlank
    @Size(min = 4, max = 12)
    private String username;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String password;

    @JsonIgnoreProperties({"users", "handler", "hibernateLazy/initializer"})
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})})
    private List<Role> roles;

    public User() {
        roles = new ArrayList<>();
    }

    @Column(name = "enabled")
    private Boolean enabled;

    @Transient
    private boolean admin;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Bogota")
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }
}
