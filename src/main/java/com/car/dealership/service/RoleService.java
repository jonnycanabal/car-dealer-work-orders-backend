package com.car.dealership.service;

import com.car.dealership.entity.Role;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<Role> findAll();

    Role findById(Long id);

    Role createRole(Role role);

    Role updateRole(Long id, Role role);

    void deleteById(Long id) throws Exception;

    Optional<Role> findByRoleName(String roleName);

    List<Role> findByUrlRoleName(String roleName) throws Exception;
}
