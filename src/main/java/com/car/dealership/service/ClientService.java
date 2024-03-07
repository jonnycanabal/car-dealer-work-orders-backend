package com.car.dealership.service;

import com.car.dealership.entity.Client;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientService {

    List<Client> findAll();

    Client findById(Long id);

    Client createClient(Client client);

    Client updateClient(Long id, Client client);

    void deleteById(Long id) throws Exception;

    List<Client> findByIdentificationCard(Integer identificationCard) throws Exception;
}
