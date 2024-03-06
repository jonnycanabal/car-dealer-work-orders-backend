package com.car.dealership.service;

import com.car.dealership.entity.Vehicle;

import java.util.List;

public interface VehicleService {

    List<Vehicle> findAll();

    Vehicle findById(Long id);

    Vehicle createVehicle(Vehicle vehicle);

    Vehicle updateVehicle(Long id, Vehicle vehicle);

    void deleteById(Long id) throws Exception;
}
