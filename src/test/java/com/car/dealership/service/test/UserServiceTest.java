package com.car.dealership.service.test;

import com.car.dealership.entity.Role;
import com.car.dealership.entity.User;
import com.car.dealership.repository.RoleRepository;
import com.car.dealership.repository.UserRepository;
import com.car.dealership.service.UserServiceImplement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository rolRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImplement userServiceImplement;

    private final List<User> userList = new ArrayList<>();
    private final List<Role> roleList = new ArrayList<>();

    @BeforeEach
    public void testUser() {

        Role userRole = new Role();
        userRole.setRoleName("ROLE_USER");

        Role adminRole = new Role();
        adminRole.setRoleName("ROLE_ADMIN");

        roleList.add(userRole);
        roleList.add(adminRole);

        User user1 = new User();

        user1.setId((long) 1);
        user1.setFirstsName("Jonny");
        user1.setLastName("Canabal");
        user1.setPhoneNumber("+57 3183654785");
        user1.setEmail("jmco@gmail.com");
        user1.setUsername("jonny");
        user1.setPassword("12345");
        user1.setAdmin(false);


        User user2 = new User();

        user2.setId((long) 2);
        user2.setFirstsName("Karen");
        user2.setLastName("Rubiano");
        user2.setPhoneNumber("+57 3141475820");
        user2.setEmail("krn@gmail.com");
        user2.setUsername("karen");
        user2.setPassword("12345");
        user2.setAdmin(true);

        userList.add(user1);
        userList.add(user2);
    }

    @Test
    public void testFindAll() {

        Mockito.when(userRepository.findAll()).thenReturn(userList);

        Iterable<User> result = userServiceImplement.findAll();

        for (int i = 0; i < userList.size(); i++) {
            System.out.println(userList.get(i).getId() + ", " + userList.get(i).getFirstsName() + ", " +
                    userList.get(i).getLastName() + ", " + userList.get(i).getIdentificationCard() + ", " +
                    userList.get(i).getEmail() + ", " + userList.get(i).getPhoneNumber() + ", " +
                    userList.get(i).getUsername());
        }
        assertEquals(userList, result);
    }

    @Test
    public void testFindById() {

        User currentUser = new User();
        Long userToFindId = 1L;

        for (int i = 0; i < userList.size(); i++) {
            User userToFor = userList.get(i);
            if (userToFor.getId().equals(userToFindId)) {
                currentUser = userToFor;
            }
        }

        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(currentUser));

        Optional<User> response = userRepository.findById(1L);

        System.out.println(response.get().getId() + " " + response.get().getFirstsName());
        System.out.println(currentUser.getId() + " " + currentUser.getFirstsName());

        Assertions.assertTrue(response.isPresent());
        assertEquals(currentUser.getId(), response.get().getId());
    }

    @Test
    public void testSave() throws Exception {

        User user = userList.get(0);
        Role userRole = roleList.get(0);

        Mockito.when(rolRepository.findByRoleName("ROLE_USER")).thenReturn(Optional.of(userRole));

        Mockito.when(passwordEncoder.encode("12345")).thenReturn("encodedPassword");

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User result = userServiceImplement.createUser(user);

        System.out.println(result.getFirstsName() + ", " + result.getLastName() + ", username: " + result.getUsername());
        System.out.println(result.getRoles().get(0).getRoleName());

        Assertions.assertNotNull(result);
        assertEquals("encodedPassword", result.getPassword());
        Assertions.assertFalse(result.isAdmin());
    }

    @Test
    public void saveAdmin() throws Exception {

        User user = userList.get(1);
        Role userRole = roleList.get(0);
        Role adminRole = roleList.get(1);

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        Mockito.when(passwordEncoder.encode("12345")).thenReturn("encodedPassword");

        Optional<Role> expectedRole = user.isAdmin() ? Optional.of(adminRole) : Optional.of(userRole);
        Mockito.when(rolRepository.findByRoleName(Mockito.anyString())).thenReturn(expectedRole);

        User result = userServiceImplement.createUser(user);

        System.out.println(user);
        System.out.println(result.getFirstsName() +", "+ result.getLastName() +", username: "+ result.getUsername());
        System.out.println("The assigned role is: " + expectedRole.get().getRoleName());

        Assertions.assertNotNull(result);
        assertEquals("encodedPassword", result.getPassword());
    }

    @Test
    public void testDeleteUser() throws Exception {

        User user = userList.get(0);
        Long userId = user.getId();

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Exception exception = assertThrows(Exception.class, () -> userServiceImplement.deleteById(userId));

        assertEquals("User successfully deleted!", exception.getMessage());

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(userId);

        System.out.println("User deleted is: " + user.getUsername() + " " + user.getLastName());
    }

}
