package com.car.dealership.service;

import com.car.dealership.entity.Client;

import java.util.List;

public interface ClientService {

    List<Client> findAll();

    Client findById(Long id);

    Client createClient(Client client);

    Client updateClient(Long id, Client client);

    void deleteById(Long id) throws Exception;
}
