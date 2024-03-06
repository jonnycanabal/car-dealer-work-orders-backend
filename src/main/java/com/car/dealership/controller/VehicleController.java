package com.car.dealership.controller;

import com.car.dealership.entity.Vehicle;
import com.car.dealership.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public List<Vehicle> viewAll() {
        return vehicleService.findAll();
    }

    @GetMapping("/{id}")
    public Vehicle viewById(@PathVariable Long id) {
        return vehicleService.findById(id);
    }

    @PostMapping("/create")
    public Vehicle create(@RequestBody Vehicle vehicle) {
        return vehicleService.createVehicle(vehicle);
    }

    @PutMapping("/update/{id}")
    public Vehicle update(@PathVariable Long id, @RequestBody Vehicle vehicle) {
        return vehicleService.updateVehicle(id, vehicle);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) throws Exception {
        vehicleService.deleteById(id);
    }
}
