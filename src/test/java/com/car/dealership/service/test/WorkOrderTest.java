package com.car.dealership.service.test;

import com.car.dealership.entity.Client;
import com.car.dealership.entity.OrderType;
import com.car.dealership.entity.Vehicle;
import com.car.dealership.entity.WorkOrder;
import com.car.dealership.repository.WorkOrderRepository;
import com.car.dealership.service.WorkOrderServiceImplement;
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
public class WorkOrderTest {

    @Mock
    private WorkOrderRepository workOrderRepository;

    @InjectMocks
    private WorkOrderServiceImplement workOrderServiceImplement;

    private final List<WorkOrder> workOrderList = new ArrayList<>();
    private final List<OrderType> orderTypesList1 = new ArrayList<>();
    private final List<OrderType> orderTypesList2 = new ArrayList<>();

    @BeforeEach
    public void workOrderTest(){

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

        workOrderList.add(workOrder1);
        workOrderList.add(workOrder2);
    }

    @Test
    public void testFindAll() {

        Mockito.when(workOrderRepository.findAll()).thenReturn(workOrderList);

        List<WorkOrder> result = workOrderRepository.findAll();

        for (int i = 0; i < workOrderList.size(); i++) {
            System.out.println(workOrderList.get(i).getId() + ", " + workOrderList.get(i).getClient().getFirstsName() + ", " +
                    workOrderList.get(i).getVehicle().getPlate() + ", " + workOrderList.get(i).getOrderTypes().get(0).getOrderName());
        }
        assertEquals(workOrderList, result);
    }

    @Test
    public void testFindById() {

        WorkOrder currentWorkOrder = new WorkOrder();
        Long workOrderToFindId = 1L;

        for (int i = 0; i < workOrderList.size(); i++) {
            WorkOrder workOrderToFor = workOrderList.get(i);
            if (workOrderToFor.getId().equals(workOrderToFindId)) {
                currentWorkOrder = workOrderToFor;
            }
        }

        Mockito.when(workOrderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(currentWorkOrder));

        Optional<WorkOrder> response = workOrderRepository.findById(1L);

        System.out.println(response.get().getId() + " " + response.get().getClient().getFirstsName() + " "
                + response.get().getVehicle().getPlate() + " " + response.get().getOrderTypes().get(0).getOrderName());
        System.out.println(currentWorkOrder.getId() + " " + currentWorkOrder.getClient().getFirstsName() + " "
                + currentWorkOrder.getVehicle().getPlate() + " " + currentWorkOrder.getOrderTypes().get(0).getOrderName());

        Assertions.assertTrue(response.isPresent());
        assertEquals(currentWorkOrder.getId(), response.get().getId());
    }

    @Test
    public void testSave() throws Exception {

        WorkOrder workOrder = workOrderList.get(0);

        Mockito.when(workOrderRepository.save(Mockito.any(WorkOrder.class))).thenReturn(workOrder);

        WorkOrder result = workOrderServiceImplement.createWorkOrder(workOrder);

        System.out.println(result.getId() + ", " + result.getClient().getFirstsName() + ", " + result.getVehicle().getPlate()
                + ", " + result.getOrderTypes().get(0).getOrderName());

        Assertions.assertNotNull(result);
    }

    @Test
    public void testDeleteUser() throws Exception {

        WorkOrder workOrder = workOrderList.get(0);
        Long workOrderId = workOrder.getId();

        Mockito.when(workOrderRepository.findById(workOrderId)).thenReturn(Optional.of(workOrder));

        Exception exception = assertThrows(Exception.class, () -> workOrderServiceImplement.deleteById(workOrderId));

        assertEquals("Work Order successfully deleted!", exception.getMessage());

        Mockito.verify(workOrderRepository, Mockito.times(1)).deleteById(workOrderId);

        System.out.println("Work Order deleted is: " + workOrder.getId() + ", " + workOrder.getClient().getFirstsName()
        + ", " + workOrder.getVehicle().getPlate() + ", " + workOrder.getOrderTypes().get(0).getOrderName());
    }
}
