package com.car.dealership.controller.test;

import com.car.dealership.entity.Role;
import com.car.dealership.service.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @InjectMocks
    private ObjectMapper objectMapper;

    private List<Role> roleList;

    @BeforeEach
    public void roleTest(){

        this.roleList = new ArrayList<>();

        Role role1 = new Role();
        role1.setId(1L);
        role1.setRoleName("ROLE_USER");
        role1.setUsers(null);

        Role role2 = new Role();

        role2.setId(2L);
        role2.setRoleName("ROLE_ADMIN");
        role2.setUsers(null);

        this.roleList.add(role1);
        this.roleList.add(role2);
    }

    @Test
    @WithMockUser(username = "Admin", password = "12345", roles = {"ADMIN", "USER"})
    public void findAllTest() throws Exception {

        given(roleService.findAll()).willReturn(roleList);

        ResultActions response = mockMvc.perform(get("/role")
                .contentType(MediaType.APPLICATION_JSON));

        for (int i = 0; i < roleList.size(); i++) {
            Role currentRole = roleList.get(i);
            response.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[" + i + "].id").value(currentRole.getId()))
                    .andExpect(jsonPath("$[" + i + "].roleName").value(currentRole.getRoleName()))
                    .andExpect(jsonPath("$[" + i + "].users").value(currentRole.getUsers()));
        }
    }

    @Test
    @WithMockUser(username = "Admin", password = "12345", roles = {"ADMIN", "USER"})
    public void findByIdTest() throws Exception {

        Long roleToFindId = 1L;
        Role roleToFind = new Role();
        for (int i = 0; i < roleList.size(); i++) {
            Role roleToFor = roleList.get(i);
            if (roleToFor.getId().equals(roleToFindId)) {
                roleToFind = roleToFor;
                break;
            }
        }

        given(roleService.findById(anyLong())).willReturn(roleToFind);

        ResultActions response = mockMvc.perform(get("/role/{id}", roleToFindId)
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(objectMapper.writeValueAsString(roleToFindId)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(roleToFind.getId()));

    }

    @Test
    @WithMockUser(username = "Admin", password = "12345", roles = {"ADMIN", "USER"})
    public void saveRoleTest() throws Exception {

        Role role = new Role();
        role.setId(3L);
        role.setRoleName("ROLE_PRUEBA");
        role.setUsers(null);

        given(roleService.createRole(any(Role.class))).willReturn(role);

        System.out.println("Body Content: " + objectMapper.writeValueAsString(role));

        ResultActions response = mockMvc.perform(post("/role/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(role)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(role.getId()))
                .andExpect(jsonPath("$.roleName").value(role.getRoleName()))
                .andExpect(jsonPath("$.users").value(role.getUsers()));
    }

    @Test
    @WithMockUser(username = "Admin", password = "12345", roles = {"ADMIN", "USER"})
    public void updateRoleTest() throws Exception {

        Long roleIdFind = 1L;

        Role currentRole = new Role();
        currentRole.setId(1L);
        currentRole.setRoleName("ROLE_USER");
        currentRole.setUsers(null);

        Role updatedRole = new Role();
        updatedRole.setId(3L);
        updatedRole.setRoleName("ROLE_PRUEBA");
        updatedRole.setUsers(null);

        System.out.println("Role Before Update - RoleName: " + currentRole.getRoleName());
        System.out.println("Role Before Update - Users: " + currentRole.getUsers());
        System.out.println("-----------------------------------------------------------");

        given(roleService.findById(roleIdFind)).willReturn(currentRole);

        given(roleService.updateRole(anyLong(), any(Role.class)))
                .willAnswer(invocation -> invocation.getArgument(1));

        ResultActions response = mockMvc.perform(put("/role/update/{id}", roleIdFind)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedRole)));

        System.out.println("Role After Update - RoleName: " + updatedRole.getRoleName());
        System.out.println("Role After Update - Users: " + updatedRole.getUsers());
        System.out.println("-----------------------------------------------------------");

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedRole.getId()))
                .andExpect(jsonPath("$.roleName").value(updatedRole.getRoleName()))
                .andExpect(jsonPath("$.users").value(updatedRole.getUsers()));
    }

    @Test
    @WithMockUser(username = "Admin",password = "12345", roles = {"ADMIN"})
    public void deleteRole() throws Exception {

        Role currentRole = roleList.get(0);
        Long roleToDeleteId = currentRole.getId();

        given(roleService.findById(roleToDeleteId)).willReturn(currentRole);

        doNothing().when(roleService).deleteById(roleToDeleteId);

        MvcResult result = mockMvc.perform(delete("/role/delete/{id}", roleToDeleteId))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("Role " + currentRole.getRoleName() + " successfully deleted!");

        verify(roleService, times(1)).deleteById(roleToDeleteId);
    }

}
