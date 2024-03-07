package com.car.dealership.repository;

import com.car.dealership.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(String roleName);

    @Query(value = "SELECT * FROM roles WHERE role_name = :roleName", nativeQuery = true)
    List<Role> findByUrlRoleName(@Param("roleName") String roleName);
}
