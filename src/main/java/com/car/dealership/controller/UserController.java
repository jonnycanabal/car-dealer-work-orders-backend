package com.car.dealership.controller;

import com.car.dealership.entity.User;
import com.car.dealership.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> viewAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User viewById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) throws Exception {

        user.setEnabled(true);

        user.setAdmin(false);

        return userService.createUser(user);
    }

    @PostMapping("/create/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public User createAdmin(@RequestBody User user) throws Exception {

        user.setEnabled(true);

        return userService.createUser(user);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User update(@PathVariable Long id, @RequestBody User user) throws ValidationException {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Long id) throws Exception {
        userService.deleteById(id);
    }
}
