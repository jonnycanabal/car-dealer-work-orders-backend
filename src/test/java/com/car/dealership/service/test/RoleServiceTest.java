package com.car.dealership.service.test;

import com.car.dealership.entity.Role;
import com.car.dealership.repository.RoleRepository;
import com.car.dealership.service.RoleServiceImplement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImplement roleServiceImplement;

    private final List<Role> roleList = new ArrayList<>();

    @BeforeEach
    public void roleTest(){

        Role role1 = new Role();

        role1.setId(1L);
        role1.setRoleName("ROLE_USER");
        role1.setUsers(null);

        Role role2 = new Role();

        role2.setId(2L);
        role2.setRoleName("ROLE_ADMIN");
        role2.setUsers(null);

        roleList.add(role1);
        roleList.add(role2);
    }

    @Test
    public void testFindAll() {

        Mockito.when(roleRepository.findAll()).thenReturn(roleList);

        Iterable<Role> result = roleRepository.findAll();

        for (int i = 0; i < roleList.size(); i++) {
            System.out.println(roleList.get(i).getId() + ", " + roleList.get(i).getRoleName() + ", " +
                    roleList.get(i).getUsers());
        }
        assertEquals(roleList, result);
    }

    @Test
    public void testFindById() {

        Role currentRole = new Role();
        Long roleToFindId = 1L;

        for (int i = 0; i < roleList.size(); i++) {
            Role roleToFor = roleList.get(i);
            if (roleToFor.getId().equals(roleToFindId)) {
                currentRole = roleToFor;
            }
        }

        Mockito.when(roleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(currentRole));

        Optional<Role> response = roleRepository.findById(1L);

        System.out.println(response.get().getId() + " " + response.get().getRoleName());
        System.out.println(currentRole.getId() + " " + currentRole.getRoleName());

        Assertions.assertTrue(response.isPresent());
        assertEquals(currentRole.getId(), response.get().getId());
    }

    @Test
    public void testSave() throws Exception {

        Role role = roleList.get(0);

        Mockito.when(roleRepository.save(Mockito.any(Role.class))).thenReturn(role);

        Role result = roleServiceImplement.createRole(role);

        System.out.println(result.getId() + ", " + result.getRoleName() + ", " + result.getUsers());

        Assertions.assertNotNull(result);
    }

    @Test
    public void testDeleteUser() throws Exception {

        Role role = roleList.get(0);
        Long roleId = role.getId();

        Mockito.when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        Exception exception = assertThrows(Exception.class, () -> roleServiceImplement.deleteById(roleId));

        assertEquals("Role successfully deleted!", exception.getMessage());

        Mockito.verify(roleRepository, Mockito.times(1)).deleteById(roleId);

        System.out.println("Role deleted is: " + role.getRoleName());
    }
}
