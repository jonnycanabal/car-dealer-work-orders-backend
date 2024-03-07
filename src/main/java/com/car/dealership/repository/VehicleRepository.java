package com.car.dealership.repository;

import com.car.dealership.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query(value = "SELECT * FROM vehicles WHERE plate = :plate", nativeQuery = true)
    List<Vehicle> findByPlate(@Param("plate") String plate);
}
