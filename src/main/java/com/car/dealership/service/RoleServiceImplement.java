package com.car.dealership.service;

import com.car.dealership.entity.Role;
import com.car.dealership.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RoleServiceImplement implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {

        List<Role> roles = roleRepository.findAll();

        roles.sort(Comparator.comparing(Role::getId));

        return roles;
    }

    @Override
    public Role findById(Long id) {

        Role currentRole = roleRepository.findById(id).orElseThrow(() -> new
                NoSuchElementException("Role not found!"));

        return currentRole;
    }

    @Override
    public List<Role> findByUrlRoleName(String roleName) throws Exception {

        List<Role> roles = roleRepository.findByUrlRoleName(roleName);

        if (!roles.isEmpty()){
            return roleRepository.findByUrlRoleName(roleName);
        }

        throw new NoSuchElementException("Role not found!");
    }

    @Override
    @Transactional
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public Role updateRole(Long id, Role role) {

        Role currentRole = roleRepository.findById(id).orElseThrow(() -> new
                NoSuchElementException("Role not found!"));

        currentRole.setRoleName(role.getRoleName() != null ? role.getRoleName() : currentRole.getRoleName());
        currentRole.setUsers(role.getUsers() != null ? role.getUsers() : currentRole.getUsers());

        return roleRepository.save(currentRole);
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws Exception {

        Role currentRole = roleRepository.findById(id).orElseThrow(() -> new
                NoSuchElementException("Role not found!"));

        roleRepository.deleteById(id);

        throw new Exception("Role successfully deleted!");
    }

    @Override
    public Optional<Role> findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

}
