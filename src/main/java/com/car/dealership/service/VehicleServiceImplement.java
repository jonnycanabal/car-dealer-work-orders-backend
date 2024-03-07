package com.car.dealership.service;

import com.car.dealership.entity.Vehicle;
import com.car.dealership.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class VehicleServiceImplement implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle findById(Long id) {

        Vehicle currentVehicle = vehicleRepository.findById(id).orElseThrow(() -> new
                NoSuchElementException("Vehicle not found!"));

        return currentVehicle;
    }

    public List<Vehicle> findByPlate(String plate) throws Exception {

        List<Vehicle> vehicles = vehicleRepository.findByPlate(plate);

        if (!vehicles.isEmpty()) {
            return vehicleRepository.findByPlate(plate);
        }

        throw new Exception("Vehicle not found!");

    }

    @Override
    @Transactional
    public Vehicle createVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional
    public Vehicle updateVehicle(Long id, Vehicle vehicle) {

        Vehicle currentVehicle = vehicleRepository.findById(id).orElseThrow(() -> new
                NoSuchElementException("Vehicle not found!"));

        currentVehicle.setBrand(vehicle.getBrand() != null ? vehicle.getBrand() : currentVehicle.getBrand());
        currentVehicle.setModel(vehicle.getModel() != null ? vehicle.getModel() : currentVehicle.getModel());
        currentVehicle.setYear(vehicle.getYear() != null ? vehicle.getYear() : currentVehicle.getYear());
        currentVehicle.setPlate(vehicle.getPlate() != null ? vehicle.getPlate() : currentVehicle.getPlate());
        currentVehicle.setColor(vehicle.getColor() != null ? vehicle.getColor() : currentVehicle.getColor());
        currentVehicle.setMileage(vehicle.getMileage() != null ? vehicle.getMileage() : currentVehicle.getMileage());

        return vehicleRepository.save(currentVehicle);
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws Exception {

        Vehicle currentVehicle = vehicleRepository.findById(id).orElseThrow(() -> new
                NoSuchElementException("Vehicle not found!"));

        vehicleRepository.deleteById(id);

        throw new Exception("Vehicle successfully deleted!");
    }
}
