package com.car.dealership.initializer;

import com.car.dealership.entity.Role;
import com.car.dealership.entity.User;
import com.car.dealership.service.RoleService;
import com.car.dealership.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleService roleService;

    private final UserService userService;

    public DataInitializer(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!roleService.findByRoleName("ROLE_ADMIN").isPresent()) {
            Role adminRole = new Role("ROLE_ADMIN");
            roleService.createRole(adminRole);
        }

        if (!roleService.findByRoleName("ROLE_USER").isPresent()) {
            Role adminRole = new Role("ROLE_USER");
            roleService.createRole(adminRole);
        }

        Optional<User> existingAdminUser = userService.findByUsername("Admin");

        if (!existingAdminUser.isPresent()) {
            User userAdmin = new User();
            userAdmin.setFirstsName("User");
            userAdmin.setLastName("Management");
            userAdmin.setUsername("Admin");
            userAdmin.setPassword("12345");
            userAdmin.setEnabled(true);
            userAdmin.setAdmin(true);

            userAdmin.setRoles(Collections.singletonList(roleService.findByRoleName("ROLE_ADMIN").get()));
            userService.createUser(userAdmin);
        }
    }
}
