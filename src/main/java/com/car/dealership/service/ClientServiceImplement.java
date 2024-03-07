package com.car.dealership.service;

import com.car.dealership.dto.ClientDto;
import com.car.dealership.entity.Client;
import com.car.dealership.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClientServiceImplement implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {

        Client currentClient = clientRepository.findById(id).orElseThrow(() -> new
                NoSuchElementException("Client not found!"));

        return currentClient;
    }

    @Override
    public List<Client> findByIdentificationCard(Integer identificationCard) throws Exception {

        List<Client> clients = clientRepository.findByIdentificationCard(identificationCard);

        if (!clients.isEmpty()){
            return clientRepository.findByIdentificationCard(identificationCard);
        }
        throw new NoSuchElementException("Client not found!");
    }

    @Override
    @Transactional
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public Client updateClient(Long id, Client client) {

        Client currentClient = clientRepository.findById(id).orElseThrow(() -> new
                NoSuchElementException("Client not found!"));

        currentClient.setFirstsName(client.getFirstsName() != null ? client.getFirstsName() : currentClient.getFirstsName());
        currentClient.setMiddleName(client.getMiddleName() != null ? client.getMiddleName() : currentClient.getMiddleName());
        currentClient.setLastName(client.getLastName() != null ? client.getLastName() : currentClient.getSecondLastName());
        currentClient.setSecondLastName(client.getSecondLastName() != null ? client.getSecondLastName() : currentClient.getLastName());
        currentClient.setIdentificationCard(client.getIdentificationCard() != null ? client.getIdentificationCard() : currentClient.getIdentificationCard());
        currentClient.setEmail(client.getEmail() != null ? client.getEmail() : currentClient.getEmail());
        currentClient.setPhoneNumber(client.getPhoneNumber() != null ? client.getPhoneNumber() : currentClient.getPhoneNumber());
        currentClient.setPlaceOfResidence(client.getPlaceOfResidence() != null ? client.getPlaceOfResidence() : currentClient.getPlaceOfResidence());
        currentClient.setAddress(client.getAddress() != null ? client.getAddress() : currentClient.getAddress());
        currentClient.setWorkOrders(client.getWorkOrders() != null ? client.getWorkOrders() : currentClient.getWorkOrders());

        return clientRepository.save(currentClient);
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws Exception {

        Client currentClient = clientRepository.findById(id).orElseThrow(() -> new
                NoSuchElementException("Client not found!"));

        clientRepository.deleteById(id);

        throw new Exception("Client successfully deleted!");
    }

}
