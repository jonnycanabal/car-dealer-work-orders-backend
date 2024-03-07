package com.car.dealership.service.test;

import com.car.dealership.entity.Vehicle;
import com.car.dealership.repository.VehicleRepository;
import com.car.dealership.service.VehicleServiceImplement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleServiceImplement vehicleServiceImplement;

    private final List<Vehicle> vehicleList = new ArrayList<>();

    @BeforeEach
    public void vehicleTest(){

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1L);
        vehicle1.setBrand("Nissan");
        vehicle1.setModel("Frontier");
        vehicle1.setYear(2020);
        vehicle1.setPlate("XTE458");
        vehicle1.setColor("Naranja");
        vehicle1.setMileage(25350);
        vehicle1.setWorkOrders(null);

        Vehicle vehicle2 = new Vehicle();

        vehicle2.setId(2L);
        vehicle2.setBrand("Toyota");
        vehicle2.setModel("Hilux");
        vehicle2.setYear(2024);
        vehicle2.setPlate("XRJ555");
        vehicle2.setColor("Blanca");
        vehicle2.setMileage(22358);
        vehicle2.setWorkOrders(null);

        vehicleList.add(vehicle1);
        vehicleList.add(vehicle2);
    }

    @Test
    public void testFindAll() {

        Mockito.when(vehicleRepository.findAll()).thenReturn(vehicleList);

        Iterable<Vehicle> result = vehicleRepository.findAll();

        for (int i = 0; i < vehicleList.size(); i++) {
            System.out.println(vehicleList.get(i).getId() + ", " + vehicleList.get(i).getBrand() + ", " +
                    vehicleList.get(i).getModel() + ", " + vehicleList.get(i).getPlate());
        }
        assertEquals(vehicleList, result);
    }

    @Test
    public void testFindById() {

        Vehicle currentVehicle = new Vehicle();
        Long vehicleToFindId = 1L;

        for (int i = 0; i < vehicleList.size(); i++) {
            Vehicle vihicleToFor = vehicleList.get(i);
            if (vihicleToFor.getId().equals(vehicleToFindId)) {
                currentVehicle = vihicleToFor;
            }
        }

        Mockito.when(vehicleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(currentVehicle));

        Optional<Vehicle> response = vehicleRepository.findById(1L);

        System.out.println(response.get().getId() + " " + response.get().getBrand() + " " + response.get().getPlate());
        System.out.println(currentVehicle.getId() + " " + currentVehicle.getBrand() + " " + currentVehicle.getPlate());

        Assertions.assertTrue(response.isPresent());
        assertEquals(currentVehicle.getId(), response.get().getId());
    }

    @Test
    public void testSave() throws Exception {

        Vehicle vehicle = vehicleList.get(0);

        Mockito.when(vehicleRepository.save(Mockito.any(Vehicle.class))).thenReturn(vehicle);

        Vehicle result = vehicleServiceImplement.createVehicle(vehicle);

        System.out.println(result.getId() + ", " + result.getBrand() + ", " + result.getPlate());

        Assertions.assertNotNull(result);
    }

    @Test
    public void testDeleteUser() throws Exception {

        Vehicle vehicle = vehicleList.get(0);
        Long vehicleId = vehicle.getId();

        Mockito.when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));

        Exception exception = assertThrows(Exception.class, () -> vehicleServiceImplement.deleteById(vehicleId));

        assertEquals("Vehicle successfully deleted!", exception.getMessage());

        Mockito.verify(vehicleRepository, Mockito.times(1)).deleteById(vehicleId);

        System.out.println("Vehicle deleted is: " + vehicle.getPlate());
    }
}
