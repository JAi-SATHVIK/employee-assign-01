package com.assignment.employee.controller;

import com.assignment.employee.dto.*;
import com.assignment.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetEmployeeByEmailUsingSpecifications() throws Exception {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(1L);
        response.setName("Aarav Sharma");
        response.setEmail("aarav.sharma@example.com");

        when(employeeService.getEmployeeByEmailUsingSpecifications("aarav.sharma@example.com")).thenReturn(response);

        mockMvc.perform(get("/api/employees/email/aarav.sharma@example.com/specifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("aarav.sharma@example.com"));
    }

    @Test
    void testGetEmployeeByEmailUsingHQL() throws Exception {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(1L);
        response.setName("Aarav Sharma");
        response.setEmail("aarav.sharma@example.com");

        when(employeeService.getEmployeeByEmailUsingHQL("aarav.sharma@example.com")).thenReturn(response);

        mockMvc.perform(get("/api/employees/email/aarav.sharma@example.com/hql"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("aarav.sharma@example.com"));
    }

    @Test
    void testGetEmployeeByEmailUsingNativeSQL() throws Exception {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(1L);
        response.setName("Aarav Sharma");
        response.setEmail("aarav.sharma@example.com");

        when(employeeService.getEmployeeByEmailUsingNativeSQL("aarav.sharma@example.com")).thenReturn(response);

        mockMvc.perform(get("/api/employees/email/aarav.sharma@example.com/native"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("aarav.sharma@example.com"));
    }

    @Test
    void testGetEmployeeByNameUsingSpecifications() throws Exception {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(1L);
        response.setName("Aarav Sharma");
        response.setEmail("aarav.sharma@example.com");

        when(employeeService.getEmployeeByNameUsingSpecifications("Aarav Sharma")).thenReturn(response);

        mockMvc.perform(get("/api/employees/name/Aarav Sharma/specifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Aarav Sharma"));
    }

    @Test
    void testGetEmployeeByNameUsingHQL() throws Exception {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(1L);
        response.setName("Aarav Sharma");
        response.setEmail("aarav.sharma@example.com");

        when(employeeService.getEmployeeByNameUsingHQL("Aarav Sharma")).thenReturn(response);

        mockMvc.perform(get("/api/employees/name/Aarav Sharma/hql"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Aarav Sharma"));
    }

    @Test
    void testGetEmployeeByNameUsingNativeSQL() throws Exception {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(1L);
        response.setName("Aarav Sharma");
        response.setEmail("aarav.sharma@example.com");

        when(employeeService.getEmployeeByNameUsingNativeSQL("Aarav Sharma")).thenReturn(response);

        mockMvc.perform(get("/api/employees/name/Aarav Sharma/native"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Aarav Sharma"));
    }

    @Test
    void testCreateEmployee() throws Exception {
        CreateEmployeeRequest request = new CreateEmployeeRequest("Aarav Sharma", "aarav.sharma@example.com", "9876543210");
        EmployeeResponse response = new EmployeeResponse();
        response.setId(1L);
        response.setName("Aarav Sharma");
        response.setEmail("aarav.sharma@example.com");
        response.setPhone("9876543210");

        when(employeeService.createEmployee(any(CreateEmployeeRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("aarav.sharma@example.com"));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        UpdateEmployeeRequest request = new UpdateEmployeeRequest();
        request.setLastName("Smith");
        request.setPhone("9876543210");
        request.setAddress("456 Oak Ave");

        EmployeeResponse response = new EmployeeResponse();
        response.setId(1L);
        response.setName("Aarav Sharma");
        response.setLastName("Smith");
        response.setPhone("9876543210");
        response.setAddress("456 Oak Ave");

        when(employeeService.updateEmployee(eq("aarav.sharma@example.com"), any(UpdateEmployeeRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/employees/aarav.sharma@example.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Smith"));
    }

    @Test
    void testUpdateEmployeePhone() throws Exception {
        UpdatePhoneRequest request = new UpdatePhoneRequest("9998887777");
        EmployeeResponse response = new EmployeeResponse();
        response.setId(1L);
        response.setName("Aarav Sharma");
        response.setPhone("9998887777");

        when(employeeService.updateEmployeePhone(eq("aarav.sharma@example.com"), any(UpdatePhoneRequest.class))).thenReturn(response);

        mockMvc.perform(patch("/api/employees/aarav.sharma@example.com/phone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phone").value("9998887777"));
    }

    @Test
    void testDeleteEmployeeByEmail() throws Exception {
        mockMvc.perform(delete("/api/employees/aarav.sharma@example.com"))
                .andExpect(status().isNoContent());
    }
}

