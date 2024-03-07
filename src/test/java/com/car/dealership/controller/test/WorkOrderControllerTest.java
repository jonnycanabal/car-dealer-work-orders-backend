package com.car.dealership.controller.test;

import com.car.dealership.entity.Client;
import com.car.dealership.entity.OrderType;
import com.car.dealership.entity.Vehicle;
import com.car.dealership.entity.WorkOrder;
import com.car.dealership.service.WorkOrderService;
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
public class WorkOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkOrderService workOrderService;

    @InjectMocks
    private ObjectMapper objectMapper;

    private List<WorkOrder> workOrderList;
    private List<OrderType> orderTypesList1;
    private List<OrderType> orderTypesList2;

    @BeforeEach
    public void vehicleTest() {

        this.workOrderList = new ArrayList<>();
        this.orderTypesList1 = new ArrayList<>();
        this.orderTypesList2 = new ArrayList<>();

        Client client1 = new Client();
        client1.setId(1L);
        client1.setFirstsName("Jonny");
        client1.setMiddleName("Mauricio");
        client1.setLastName("Canabal");
        client1.setSecondLastName("Ospina");
        client1.setIdentificationCard(1115854752);
        client1.setEmail("jonnycanabal@gmail.com");
        client1.setPhoneNumber("+57 3183698547");
        client1.setPlaceOfResidence("Buga Valle");
        client1.setAddress("Calle 12 # 10 - 30");
        client1.setWorkOrders(null);

        Client client2 = new Client();
        client2.setId(2L);
        client2.setFirstsName("Karen");
        client2.setMiddleName("");
        client2.setLastName("Rubiano");
        client2.setSecondLastName("Torres");
        client2.setIdentificationCard(1115854752);
        client2.setEmail("karenrubiano@gmail.com");
        client2.setPhoneNumber("+57 3185475985");
        client2.setPlaceOfResidence("Bogota D.C.");
        client2.setAddress("carrera 86 # 36sur - 15");
        client2.setWorkOrders(null);

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

        OrderType orderType1 = new OrderType();
        orderType1.setId(1L);
        orderType1.setOrderName("Mantenimiento");

        orderTypesList1.add(orderType1);

        OrderType orderType2 = new OrderType();
        orderType2.setId(2L);
        orderType2.setOrderName("Pintura");

        orderTypesList2.add(orderType2);

        WorkOrder workOrder1 = new WorkOrder();
        workOrder1.setId(1L);
        workOrder1.setClient(client1);
        workOrder1.setVehicle(vehicle1);
        workOrder1.setOrderTypes(orderTypesList1);

        WorkOrder workOrder2 = new WorkOrder();
        workOrder2.setId(2L);
        workOrder2.setClient(client2);
        workOrder2.setVehicle(vehicle2);
        workOrder2.setOrderTypes(orderTypesList2);

        this.workOrderList.add(workOrder1);
        this.workOrderList.add(workOrder2);
    }

    @Test
    @WithMockUser(username = "Admin", password = "12345", roles = {"ADMIN", "USER"})
    public void findAllTest() throws Exception {

        given(workOrderService.findAll()).willReturn(workOrderList);

        ResultActions response = mockMvc.perform(get("/workOrder")
                .contentType(MediaType.APPLICATION_JSON));

        for (int i = 0; i < workOrderList.size(); i++) {
            WorkOrder currentWorkOrder = workOrderList.get(i);
            response.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[" + i + "].id").value(currentWorkOrder.getId()));
        }
    }

    @Test
    @WithMockUser(username = "Admin", password = "12345", roles = {"ADMIN", "USER"})
    public void findByIdTest() throws Exception {

        Long WorkOrderToFindId = 1L;
        WorkOrder WorkToFind = new WorkOrder();
        for (int i = 0; i < workOrderList.size(); i++) {
            WorkOrder workOrderToFor = workOrderList.get(i);
            if (workOrderToFor.getId().equals(WorkOrderToFindId)) {
                WorkToFind = workOrderToFor;
                break;
            }
        }

        given(workOrderService.findById(anyLong())).willReturn(WorkToFind);

        ResultActions response = mockMvc.perform(get("/workOrder/{id}", WorkOrderToFindId)
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(objectMapper.writeValueAsString(WorkOrderToFindId)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(WorkToFind.getId()));

    }

    @Test
    @WithMockUser(username = "Admin", password = "12345", roles = {"ADMIN", "USER"})
    public void saveWorkOrderTest() throws Exception {

        Client client = new Client();
        client.setId(3L);
        client.setFirstsName("Diego");
        client.setMiddleName("");
        client.setLastName("Briñez");
        client.setSecondLastName("");
        client.setIdentificationCard(1115852246);
        client.setEmail("diego.briñez@gmail.com");
        client.setPhoneNumber("+57 3183695874");
        client.setPlaceOfResidence("Tulua Valle");
        client.setAddress("Calle 11 # 5 - 23");
        client.setWorkOrders(null);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(3L);
        vehicle.setBrand("Renault");
        vehicle.setModel("Duster");
        vehicle.setYear(2022);
        vehicle.setPlate("XRJ444");
        vehicle.setColor("Negro");
        vehicle.setMileage(18000);
        vehicle.setWorkOrders(null);

        List<OrderType> orderTypeList3 = new ArrayList<>();
        OrderType orderType1 = new OrderType();
        orderType1.setId(1L);
        orderType1.setOrderName("Mantenimiento");
        orderTypeList3.add(orderType1);

        WorkOrder newWorkOrder = new WorkOrder();
        newWorkOrder.setId(3L);
        newWorkOrder.setClient(client);
        newWorkOrder.setVehicle(vehicle);
        newWorkOrder.setOrderTypes(orderTypeList3);

        given(workOrderService.createWorkOrder(any(WorkOrder.class))).willReturn(newWorkOrder);

        System.out.println("Body Content: " + objectMapper.writeValueAsString(newWorkOrder));

        ResultActions response = mockMvc.perform(post("/workOrder/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newWorkOrder)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(newWorkOrder.getId()));
    }

    @Test
    @WithMockUser(username = "Admin", password = "12345", roles = {"ADMIN", "USER"})
    public void updateWorkOrderTest() throws Exception {

        Long workOrderIdFind = 1L;

        Client client = new Client();
        client.setId(3L);
        client.setFirstsName("Diego");
        client.setMiddleName("");
        client.setLastName("Briñez");
        client.setSecondLastName("");
        client.setIdentificationCard(1115852246);
        client.setEmail("diego.briñez@gmail.com");
        client.setPhoneNumber("+57 3183695874");
        client.setPlaceOfResidence("Tulua Valle");
        client.setAddress("Calle 11 # 5 - 23");
        client.setWorkOrders(null);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(3L);
        vehicle.setBrand("Renault");
        vehicle.setModel("Duster");
        vehicle.setYear(2022);
        vehicle.setPlate("XRJ444");
        vehicle.setColor("Negro");
        vehicle.setMileage(18000);
        vehicle.setWorkOrders(null);

        List<OrderType> orderTypeList3 = new ArrayList<>();
        OrderType orderType1 = new OrderType();
        orderType1.setId(1L);
        orderType1.setOrderName("Mantenimiento");
        orderTypeList3.add(orderType1);

        WorkOrder currentOrderWork = new WorkOrder();
        currentOrderWork.setId(3L);
        currentOrderWork.setClient(client);
        currentOrderWork.setVehicle(vehicle);
        currentOrderWork.setOrderTypes(orderTypeList3);

        /////////////////////////////////////
        Client client2 = new Client();
        client2.setId(2L);
        client2.setFirstsName("Karen");
        client2.setMiddleName("");
        client2.setLastName("Rubiano");
        client2.setSecondLastName("Torres");
        client2.setIdentificationCard(1115854752);
        client2.setEmail("karenrubiano@gmail.com");
        client2.setPhoneNumber("+57 3185475985");
        client2.setPlaceOfResidence("Bogota D.C.");
        client2.setAddress("carrera 86 # 36sur - 15");
        client2.setWorkOrders(null);

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1L);
        vehicle1.setBrand("Nissan");
        vehicle1.setModel("Frontier");
        vehicle1.setYear(2020);
        vehicle1.setPlate("XTE458");
        vehicle1.setColor("Naranja");
        vehicle1.setMileage(25350);
        vehicle1.setWorkOrders(null);

        List<OrderType> orderTypeList4 = new ArrayList<>();
        OrderType orderType2 = new OrderType();
        orderType2.setId(2L);
        orderType2.setOrderName("Pintura");
        orderTypeList4.add(orderType1);

        WorkOrder updatedWorkOrder = new WorkOrder();
        updatedWorkOrder.setId(3L);
        updatedWorkOrder.setClient(client2);
        updatedWorkOrder.setVehicle(vehicle1);
        updatedWorkOrder.setOrderTypes(orderTypeList4);

        System.out.println("WorkOrder Before Update - Client: " + currentOrderWork.getClient().getFirstsName());
        System.out.println("WorkOrder Before Update - Vehicle: " + currentOrderWork.getVehicle().getBrand());
        System.out.println("WorkOrder Before Update - OrderTypes: " + currentOrderWork.getOrderTypes().get(0).getOrderName());
        System.out.println("-----------------------------------------------------------");

        given(workOrderService.findById(workOrderIdFind)).willReturn(currentOrderWork);

        given(workOrderService.updateWorkOrder(anyLong(), any(WorkOrder.class)))
                .willAnswer(invocation -> invocation.getArgument(1));

        ResultActions response = mockMvc.perform(put("/workOrder/update/{id}", workOrderIdFind)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedWorkOrder)));

        System.out.println("WorkOrder After Update - Client: " + updatedWorkOrder.getClient().getFirstsName());
        System.out.println("WorkOrder After Update - Vehicle: " + updatedWorkOrder.getVehicle().getBrand());
        System.out.println("WorkOrder After Update - OrderTypes: " + updatedWorkOrder.getOrderTypes().get(0).getOrderName());
        System.out.println("-----------------------------------------------------------");

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedWorkOrder.getId()));
    }

    @Test
    @WithMockUser(username = "Admin",password = "12345", roles = {"ADMIN"})
    public void deleteWorkOrder() throws Exception {

        WorkOrder currentWorkOrder = workOrderList.get(0);
        Long workOrderToDeleteId = currentWorkOrder.getId();

        given(workOrderService.findById(workOrderToDeleteId)).willReturn(currentWorkOrder);

        doNothing().when(workOrderService).deleteById(workOrderToDeleteId);

        MvcResult result = mockMvc.perform(delete("/workOrder/delete/{id}", workOrderToDeleteId))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("Work Order " + currentWorkOrder.getId() + " successfully deleted!");

        verify(workOrderService, times(1)).deleteById(workOrderToDeleteId);
    }
}
