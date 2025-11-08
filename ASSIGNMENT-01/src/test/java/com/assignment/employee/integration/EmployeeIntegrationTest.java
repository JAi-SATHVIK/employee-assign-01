package com.assignment.employee.integration;

import com.assignment.employee.dto.CreateEmployeeRequest;
import com.assignment.employee.dto.EmployeeResponse;
import com.assignment.employee.dto.UpdateEmployeeRequest;
import com.assignment.employee.dto.UpdatePhoneRequest;
import com.assignment.employee.entity.Employee;
import com.assignment.employee.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class EmployeeIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee testEmployee;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
        testEmployee = new Employee("Aarav Sharma", "aarav.sharma@example.com", "9876543210");
        testEmployee.setLastName("Sharma");
        testEmployee.setAddress("DLF Phase 3, Gurugram");
        employeeRepository.save(testEmployee);
    }

    @Test
    void testCreateEmployee() throws Exception {
        CreateEmployeeRequest request = new CreateEmployeeRequest("Neha Verma", "neha.verma@example.com", "9123456780");

        MvcResult result = mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Neha Verma"))
                .andExpect(jsonPath("$.email").value("neha.verma@example.com"))
                .andExpect(jsonPath("$.phone").value("9123456780"))
                .andReturn();

        EmployeeResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                EmployeeResponse.class
        );

        assertNotNull(response.getId());
    }

    @Test
    void testGetEmployeeByEmailUsingSpecifications() throws Exception {
        mockMvc.perform(get("/api/employees/email/aarav.sharma@example.com/specifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("aarav.sharma@example.com"))
                .andExpect(jsonPath("$.name").value("Aarav Sharma"));
    }

    @Test
    void testGetEmployeeByEmailUsingHQL() throws Exception {
        mockMvc.perform(get("/api/employees/email/aarav.sharma@example.com/hql"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("aarav.sharma@example.com"))
                .andExpect(jsonPath("$.name").value("Aarav Sharma"));
    }

    @Test
    void testGetEmployeeByEmailUsingNativeSQL() throws Exception {
        mockMvc.perform(get("/api/employees/email/aarav.sharma@example.com/native"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("aarav.sharma@example.com"))
                .andExpect(jsonPath("$.name").value("Aarav Sharma"));
    }

    @Test
    void testGetEmployeeByNameUsingSpecifications() throws Exception {
        mockMvc.perform(get("/api/employees/name/Aarav Sharma/specifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Aarav Sharma"))
                .andExpect(jsonPath("$.email").value("aarav.sharma@example.com"));
    }

    @Test
    void testGetEmployeeByNameUsingHQL() throws Exception {
        mockMvc.perform(get("/api/employees/name/Aarav Sharma/hql"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Aarav Sharma"))
                .andExpect(jsonPath("$.email").value("aarav.sharma@example.com"));
    }

    @Test
    void testGetEmployeeByNameUsingNativeSQL() throws Exception {
        mockMvc.perform(get("/api/employees/name/Aarav Sharma/native"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Aarav Sharma"))
                .andExpect(jsonPath("$.email").value("aarav.sharma@example.com"));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        UpdateEmployeeRequest request = new UpdateEmployeeRequest();
        request.setLastName("Smith");
        request.setPhone("9998887777");
        request.setAddress("456 Oak Ave");

        mockMvc.perform(put("/api/employees/aarav.sharma@example.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andExpect(jsonPath("$.phone").value("9998887777"))
                .andExpect(jsonPath("$.address").value("456 Oak Ave"));
    }

    @Test
    void testUpdateEmployeePhone() throws Exception {
        UpdatePhoneRequest request = new UpdatePhoneRequest("5554443333");

        mockMvc.perform(patch("/api/employees/aarav.sharma@example.com/phone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phone").value("5554443333"));
    }

    @Test
    void testDeleteEmployeeByEmail() throws Exception {
        mockMvc.perform(delete("/api/employees/aarav.sharma@example.com"))
                .andExpect(status().isNoContent());

        assertFalse(employeeRepository.findByEmailUsingHQL("aarav.sharma@example.com").isPresent());
    }

    @Test
    void testEmployeeNotFound() throws Exception {
        mockMvc.perform(get("/api/employees/email/notfound@example.com/specifications"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void testCreateEmployeeValidation() throws Exception {
        CreateEmployeeRequest request = new CreateEmployeeRequest();
        request.setName("");
        request.setEmail("invalid-email");

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"));
    }
}

