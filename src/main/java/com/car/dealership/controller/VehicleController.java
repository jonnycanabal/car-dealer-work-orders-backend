package com.car.dealership.controller;

import com.car.dealership.entity.Vehicle;
import com.car.dealership.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Vehicle> viewAll() {
        return vehicleService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Vehicle viewById(@PathVariable Long id) {
        return vehicleService.findById(id);
    }

    @GetMapping("/search/url/byPlate/{plate}")
    @ResponseStatus(HttpStatus.OK)
    public List<Vehicle> findByPlate(@PathVariable String plate) throws Exception {
        return vehicleService.findByPlate(plate);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicle create(@RequestBody Vehicle vehicle) {
        return vehicleService.createVehicle(vehicle);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Vehicle update(@PathVariable Long id, @RequestBody Vehicle vehicle) {
        return vehicleService.updateVehicle(id, vehicle);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) throws Exception {
        vehicleService.deleteById(id);
    }
}
