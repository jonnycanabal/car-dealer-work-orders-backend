package com.car.dealership.controller.test;

import com.car.dealership.entity.Vehicle;
import com.car.dealership.service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    @InjectMocks
    private ObjectMapper objectMapper;

    private List<Vehicle> vehicleList;

    @BeforeEach
    public void vehicleTest(){

        this.vehicleList = new ArrayList<>();

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

        this.vehicleList.add(vehicle1);
        this.vehicleList.add(vehicle2);
    }

    @Test
    @WithMockUser(username = "Admin", password = "12345", roles = {"ADMIN", "USER"})
    public void findAllTest() throws Exception {

        given(vehicleService.findAll()).willReturn(vehicleList);

        ResultActions response = mockMvc.perform(get("/vehicle")
                .contentType(MediaType.APPLICATION_JSON));

        for (int i = 0; i < vehicleList.size(); i++) {
            Vehicle currentVehicle = vehicleList.get(i);
            response.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[" + i + "].id").value(currentVehicle.getId()))
                    .andExpect(jsonPath("$[" + i + "].brand").value(currentVehicle.getBrand()))
                    .andExpect(jsonPath("$[" + i + "].model").value(currentVehicle.getModel()))
                    .andExpect(jsonPath("$[" + i + "].plate").value(currentVehicle.getPlate()));
        }
    }

    @Test
    @WithMockUser(username = "Admin", password = "12345", roles = {"ADMIN", "USER"})
    public void findByIdTest() throws Exception {

        Long vehicleToFindId = 1L;
        Vehicle vehicleToFind = new Vehicle();
        for (int i = 0; i < vehicleList.size(); i++) {
            Vehicle vehicleToFor = vehicleList.get(i);
            if (vehicleToFor.getId().equals(vehicleToFindId)) {
                vehicleToFind = vehicleToFor;
                break;
            }
        }

        given(vehicleService.findById(anyLong())).willReturn(vehicleToFind);

        ResultActions response = mockMvc.perform(get("/vehicle/{id}", vehicleToFindId)
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(objectMapper.writeValueAsString(vehicleToFindId)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(vehicleToFind.getId()));

    }

    @Test
    @WithMockUser(username = "Admin", password = "12345", roles = {"ADMIN", "USER"})
    public void saveVehicleTest() throws Exception {

        Vehicle vehicle = new Vehicle();
        vehicle.setId(3L);
        vehicle.setBrand("Renault");
        vehicle.setModel("Duster");
        vehicle.setYear(2022);
        vehicle.setPlate("XRJ444");
        vehicle.setColor("Negro");
        vehicle.setMileage(18000);
        vehicle.setWorkOrders(null);

        given(vehicleService.createVehicle(any(Vehicle.class))).willReturn(vehicle);

        System.out.println("Body Content: " + objectMapper.writeValueAsString(vehicle));

        ResultActions response = mockMvc.perform(post("/vehicle/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicle)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(vehicle.getId()))
                .andExpect(jsonPath("$.brand").value(vehicle.getBrand()))
                .andExpect(jsonPath("$.model").value(vehicle.getModel()))
                .andExpect(jsonPath("$.plate").value(vehicle.getPlate()));
    }

    @Test
    @WithMockUser(username = "Admin", password = "12345", roles = {"ADMIN", "USER"})
    public void updateVehicleTest() throws Exception {

        Long vehicleIdFind = 1L;

        Vehicle currentVehicle = new Vehicle();
        currentVehicle.setId(2L);
        currentVehicle.setBrand("Toyota");
        currentVehicle.setModel("Hilux");
        currentVehicle.setYear(2024);
        currentVehicle.setPlate("XRJ555");
        currentVehicle.setColor("Blanca");
        currentVehicle.setMileage(22358);
        currentVehicle.setWorkOrders(null);

        Vehicle updatedVehicle = new Vehicle();
        updatedVehicle.setId(3L);
        updatedVehicle.setBrand("Renault");
        updatedVehicle.setModel("Duster");
        updatedVehicle.setYear(2022);
        updatedVehicle.setPlate("XRJ444");
        updatedVehicle.setColor("Negro");
        updatedVehicle.setMileage(18000);
        updatedVehicle.setWorkOrders(null);

        System.out.println("Vehicle Before Update - Brand: " + currentVehicle.getBrand());
        System.out.println("Vehicle Before Update - Model: " + currentVehicle.getModel());
        System.out.println("Vehicle Before Update - Plate: " + currentVehicle.getPlate());
        System.out.println("-----------------------------------------------------------");

        given(vehicleService.findById(vehicleIdFind)).willReturn(currentVehicle);

        given(vehicleService.updateVehicle(anyLong(), any(Vehicle.class)))
                .willAnswer(invocation -> invocation.getArgument(1));

        ResultActions response = mockMvc.perform(put("/vehicle/update/{id}", vehicleIdFind)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedVehicle)));

        System.out.println("Vehicle After Update - Brand: " + updatedVehicle.getBrand());
        System.out.println("Vehicle After Update - Model: " + updatedVehicle.getModel());
        System.out.println("Vehicle After Update - Plate: " + updatedVehicle.getPlate());
        System.out.println("-----------------------------------------------------------");

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedVehicle.getId()))
                .andExpect(jsonPath("$.brand").value(updatedVehicle.getBrand()))
                .andExpect(jsonPath("$.model").value(updatedVehicle.getModel()))
                .andExpect(jsonPath("$.plate").value(updatedVehicle.getPlate()));
    }

    @Test
    @WithMockUser(username = "Admin",password = "12345", roles = {"ADMIN"})
    public void deleteVehicle() throws Exception {
        Vehicle currentVehicle = vehicleList.get(0);
        Long vehicleToDeleteId = currentVehicle.getId();

        given(vehicleService.findById(vehicleToDeleteId)).willReturn(currentVehicle);

        doNothing().when(vehicleService).deleteById(vehicleToDeleteId);

        MvcResult result = mockMvc.perform(delete("/vehicle/delete/{id}", vehicleToDeleteId))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("Vehicle " + currentVehicle.getPlate() + " successfully deleted!");

        verify(vehicleService, times(1)).deleteById(vehicleToDeleteId);
    }

}
