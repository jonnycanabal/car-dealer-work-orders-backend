package com.car.dealership.service.test;

import com.car.dealership.entity.Client;
import com.car.dealership.repository.ClientRepository;
import com.car.dealership.service.ClientServiceImplement;
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
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImplement clientServiceImplement;

    private final List<Client> clientList = new ArrayList<>();

    @BeforeEach
    public void clientTest(){

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

        clientList.add(client1);
        clientList.add(client2);
    }

    @Test
    public void testFindAll() {

        Mockito.when(clientRepository.findAll()).thenReturn(clientList);

        Iterable<Client> result = clientRepository.findAll();

        for (int i = 0; i < clientList.size(); i++) {
            System.out.println(clientList.get(i).getId() + ", " + clientList.get(i).getFirstsName() + ", " +
                    clientList.get(i).getLastName() + ", " + clientList.get(i).getIdentificationCard() + ", " +
                    clientList.get(i).getEmail() + ", " + clientList.get(i).getPhoneNumber());
        }
        assertEquals(clientList, result);
    }

    @Test
    public void testFindById() {

        Client currentClient = new Client();
        Long clientToFindId = 1L;

        for (int i = 0; i < clientList.size(); i++) {
            Client clientToFor = clientList.get(i);
            if (clientToFor.getId().equals(clientToFindId)) {
                currentClient = clientToFor;
            }
        }

        Mockito.when(clientRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(currentClient));

        Optional<Client> response = clientRepository.findById(1L);

        System.out.println(response.get().getId() + " " + response.get().getFirstsName());
        System.out.println(currentClient.getId() + " " + currentClient.getFirstsName());

        Assertions.assertTrue(response.isPresent());
        assertEquals(currentClient.getId(), response.get().getId());
    }

    @Test
    public void testSave() throws Exception {

        Client client = clientList.get(0);

        Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);

        Client result = clientServiceImplement.createClient(client);

        System.out.println(result.getFirstsName() + ", " + result.getLastName());

        Assertions.assertNotNull(result);
    }

    @Test
    public void testDeleteUser() throws Exception {

        Client client = clientList.get(0);
        Long clientId = client.getId();

        Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        Exception exception = assertThrows(Exception.class, () -> clientServiceImplement.deleteById(clientId));

        assertEquals("Client successfully deleted!", exception.getMessage());

        Mockito.verify(clientRepository, Mockito.times(1)).deleteById(clientId);

        System.out.println("Client deleted is: " + client.getFirstsName() + " " + client.getLastName());
    }
}
