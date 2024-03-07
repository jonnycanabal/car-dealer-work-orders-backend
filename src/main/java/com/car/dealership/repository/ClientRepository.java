package com.car.dealership.repository;

import com.car.dealership.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(value = "SELECT * FROM clients WHERE identification_card = :identificationCard", nativeQuery = true)
    List<Client> findByIdentificationCard(@Param("identificationCard") Integer identificationCard);
}
