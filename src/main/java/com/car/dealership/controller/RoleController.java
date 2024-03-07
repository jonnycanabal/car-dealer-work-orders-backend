package com.car.dealership.controller;

import com.car.dealership.entity.Role;
import com.car.dealership.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Role> viewAll() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Role viewById(@PathVariable Long id) {
        return roleService.findById(id);
    }

    @GetMapping("/search/url/byRoleName/{roleName}")
    @ResponseStatus(HttpStatus.OK)
    public List<Role> findByUrlRoleName(@PathVariable String roleName) throws Exception{
        return roleService.findByUrlRoleName(roleName);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Role create(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Role update(@PathVariable Long id, @RequestBody Role role) {
        return roleService.updateRole(id, role);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Long id) throws Exception {
        roleService.deleteById(id);
    }
}
