package com.car.dealership.service;

import com.car.dealership.entity.Role;
import com.car.dealership.entity.User;
import com.car.dealership.repository.RoleRepository;
import com.car.dealership.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImplement implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {

        User currentUser = userRepository.findById(id).orElseThrow(() -> new
                NoSuchElementException("User not found!"));

        return currentUser;
    }

    @Override
    @Transactional
    public User createUser(User user) throws Exception {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new Exception("Username already Exist!");
        }

        Optional<Role> optionalRoleUser = roleRepository.findByRoleName("ROLE_USER");

        List<Role> roles = new ArrayList<>();

        optionalRoleUser.ifPresent(role -> roles.add(role));

        if (user.isAdmin()) {

            Optional<Role> optionalRoleAdmin = roleRepository.findByRoleName("ROLE_ADMIN");

//			roles.clear();

            optionalRoleAdmin.ifPresent(roles::add);
        }

        user.setRoles(roles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(Long id, User user) throws ValidationException {

        if (user.getUsername() != null && (user.getUsername().length() < 4 ||
                user.getUsername().length() > 12)) {
            throw new ValidationException("Username must be between 4 and 12 characters");
        }

        User currentUser = userRepository.findById(id).orElseThrow(() -> new
                NoSuchElementException("User not found!"));

        currentUser.setFirstsName(user.getFirstsName() != null ? user.getFirstsName() : currentUser.getFirstsName());
        currentUser.setLastName(user.getLastName() != null ? user.getLastName() : currentUser.getLastName());
        currentUser.setIdentificationCard(user.getIdentificationCard() != null ? user.getIdentificationCard() : currentUser.getIdentificationCard());
        currentUser.setEmail(user.getEmail() != null ? user.getEmail() : currentUser.getEmail());
        currentUser.setPhoneNumber(user.getPhoneNumber() != null ? user.getPhoneNumber() : currentUser.getPhoneNumber());
        currentUser.setUsername(user.getUsername() != null ? user.getUsername() : currentUser.getUsername());

        currentUser.setPassword(passwordEncoder.encode(user.getPassword() != null ? user.getPassword() : currentUser.getPassword()));

        currentUser.setEnabled(user.getEnabled() != null ? user.getEnabled() : currentUser.getEnabled());
        currentUser.setAdmin(user.isAdmin() != currentUser.isAdmin() ? user.isAdmin() : currentUser.isAdmin());

        return userRepository.save(currentUser);
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws Exception {

        User currentUser = userRepository.findById(id).orElseThrow(() -> new
                NoSuchElementException("User not found!"));

        userRepository.deleteById(id);

        throw new Exception("User successfully deleted!");
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
