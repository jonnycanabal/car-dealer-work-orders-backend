package com.car.dealership.controller.test;

import com.car.dealership.entity.Client;
import com.car.dealership.service.ClientService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @InjectMocks
    private ObjectMapper objectMapper;

    private List<Client> clientList;

    @BeforeEach
    public void clientTest(){

        this.clientList = new ArrayList<>();

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

        this.clientList.add(client1);
        this.clientList.add(client2);
    }

    @Test
    @WithMockUser(username = "Admin", password = "12345", roles = {"ADMIN", "USER"})
    public void findAllTest() throws Exception {

        given(clientService.findAll()).willReturn(clientList);

        ResultActions response = mockMvc.perform(get("/client")
                .contentType(MediaType.APPLICATION_JSON));

        for (int i = 0; i < clientList.size(); i++) {
            Client currentClient = clientList.get(i);
            response.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[" + i + "].id").value(currentClient.getId()))
                    .andExpect(jsonPath("$[" + i + "].firstsName").value(currentClient.getFirstsName()))
                    .andExpect(jsonPath("$[" + i + "].lastName").value(currentClient.getLastName()))
                    .andExpect(jsonPath("$[" + i + "].phoneNumber").value(currentClient.getPhoneNumber()))
                    .andExpect(jsonPath("$[" + i + "].email").value(currentClient.getEmail()));
        }
    }

    @Test
    @WithMockUser(username = "Admin", password = "12345", roles = {"ADMIN", "USER"})
    public void findByIdTest() throws Exception {

        Long clientToFindId = 1L;
        Client clientToFind = new Client();
        for (int i = 0; i < clientList.size(); i++) {
            Client clientToFor = clientList.get(i);
            if (clientToFor.getId().equals(clientToFindId)) {
                clientToFind = clientToFor;
                break;
            }
        }

        given(clientService.findById(anyLong())).willReturn(clientToFind);

        ResultActions response = mockMvc.perform(get("/client/{id}", clientToFindId)
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(objectMapper.writeValueAsString(clientToFindId)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clientToFind.getId()));

    }

    @Test
    @WithMockUser(username = "Admin", password = "12345", roles = {"ADMIN", "USER"})
    public void saveClientTest() throws Exception {

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

        given(clientService.createClient(any(Client.class))).willReturn(client);

        System.out.println("Body Content: " + objectMapper.writeValueAsString(client));

        ResultActions response = mockMvc.perform(post("/client/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(client.getId()))
                .andExpect(jsonPath("$.firstsName").value(client.getFirstsName()))
                .andExpect(jsonPath("$.lastName").value(client.getLastName()))
                .andExpect(jsonPath("$.phoneNumber").value(client.getPhoneNumber()))
                .andExpect(jsonPath("$.email").value(client.getEmail()))
                .andExpect(jsonPath("$.identificationCard").value(client.getIdentificationCard()))
                .andExpect(jsonPath("$.address").value(client.getAddress()));
    }

    @Test
    @WithMockUser(username = "Admin", password = "12345", roles = {"ADMIN", "USER"})
    public void updateUserTest() throws Exception {

        Long clientIdFind = 1L;

        Client currentUser = new Client();
        currentUser.setId(clientIdFind);
        currentUser.setFirstsName("Jonny");
        currentUser.setMiddleName("Mauricio");
        currentUser.setLastName("Canabal");
        currentUser.setSecondLastName("Ospina");
        currentUser.setIdentificationCard(1115854752);
        currentUser.setEmail("jonnycanabal@gmail.com");
        currentUser.setPhoneNumber("+57 3183698547");
        currentUser.setPlaceOfResidence("Buga Valle");
        currentUser.setAddress("Calle 12 # 10 - 30");
        currentUser.setWorkOrders(null);

        Client updatedUser = new Client();
        updatedUser.setId(3L);
        updatedUser.setFirstsName("Diego");
        updatedUser.setMiddleName("");
        updatedUser.setLastName("Briñez");
        updatedUser.setSecondLastName("");
        updatedUser.setIdentificationCard(1115852246);
        updatedUser.setEmail("diego.briñez@gmail.com");
        updatedUser.setPhoneNumber("+57 3183695874");
        updatedUser.setPlaceOfResidence("Tulua Valle");
        updatedUser.setAddress("Calle 11 # 5 - 23");
        updatedUser.setWorkOrders(null);

        System.out.println("Client Before Update - FirstsName: " + currentUser.getFirstsName());
        System.out.println("Client Before Update - LastName: " + currentUser.getLastName());
        System.out.println("Client Before Update - Email: " + currentUser.getEmail());
        System.out.println("-----------------------------------------------------------");

        given(clientService.findById(clientIdFind)).willReturn(currentUser);

        given(clientService.updateClient(anyLong(), any(Client.class)))
                .willAnswer(invocation -> invocation.getArgument(1));

        ResultActions response = mockMvc.perform(put("/client/update/{id}", clientIdFind)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)));

        System.out.println("Client After Update - FirstsName: " + updatedUser.getFirstsName());
        System.out.println("Client After Update - LastName: " + updatedUser.getLastName());
        System.out.println("Client After Update - Email: " + updatedUser.getEmail());
        System.out.println("-----------------------------------------------------------");

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedUser.getId()))
                .andExpect(jsonPath("$.firstsName").value(updatedUser.getFirstsName()))
                .andExpect(jsonPath("$.lastName").value(updatedUser.getLastName()))
                .andExpect(jsonPath("$.phoneNumber").value(updatedUser.getPhoneNumber()))
                .andExpect(jsonPath("$.email").value(updatedUser.getEmail()))
                .andExpect(jsonPath("$.identificationCard").value(updatedUser.getIdentificationCard()))
                .andExpect(jsonPath("$.address").value(updatedUser.getAddress()));
    }

    @Test
    @WithMockUser(username = "Admin",password = "12345", roles = {"ADMIN"})
    public void deleteClient() throws Exception {
        Client currentClient = clientList.get(0);
        Long clientToDeleteId = currentClient.getId();

        given(clientService.findById(clientToDeleteId)).willReturn(currentClient);

        doNothing().when(clientService).deleteById(clientToDeleteId);

        MvcResult result = mockMvc.perform(delete("/client/delete/{id}", clientToDeleteId))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("Client " + currentClient.getFirstsName() + " successfully deleted!");

        verify(clientService, times(1)).deleteById(clientToDeleteId);
    }
}
