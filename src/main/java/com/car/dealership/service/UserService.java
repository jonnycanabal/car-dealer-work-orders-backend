package com.car.dealership.service;

import com.car.dealership.entity.User;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User createUser(User user) throws Exception;

    User updateUser(Long id, User user) throws ValidationException;

    void deleteById(Long id) throws Exception;

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
