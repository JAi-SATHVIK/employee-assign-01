package com.assignment.employee.service;

import com.assignment.employee.dto.CreateEmployeeRequest;
import com.assignment.employee.dto.EmployeeResponse;
import com.assignment.employee.dto.UpdateEmployeeRequest;
import com.assignment.employee.dto.UpdatePhoneRequest;
import com.assignment.employee.entity.Employee;
import com.assignment.employee.repository.EmployeeRepository;
import com.assignment.employee.repository.EmployeeSpecifications;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;
    private CreateEmployeeRequest createRequest;

    @BeforeEach
    void setUp() {
        employee = new Employee("Aarav Sharma", "aarav.sharma@example.com", "9876543210");
        employee.setId(1L);
        employee.setLastName("Sharma");
        employee.setAddress("DLF Phase 3, Gurugram");

        createRequest = new CreateEmployeeRequest("Aarav Sharma", "aarav.sharma@example.com", "9876543210");
    }

    @Test
    void testCreateEmployee() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeResponse response = employeeService.createEmployee(createRequest);

        assertNotNull(response);
        assertEquals("Aarav Sharma", response.getName());
        assertEquals("aarav.sharma@example.com", response.getEmail());
        assertEquals("9876543210", response.getPhone());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    void testGetEmployeeByEmailUsingSpecifications() {
        Specification<Employee> spec = EmployeeSpecifications.hasEmail("aarav.sharma@example.com");
        when(employeeRepository.findOne(any(Specification.class))).thenReturn(Optional.of(employee));

        EmployeeResponse response = employeeService.getEmployeeByEmailUsingSpecifications("aarav.sharma@example.com");

        assertNotNull(response);
        assertEquals("aarav.sharma@example.com", response.getEmail());
        verify(employeeRepository, times(1)).findOne(any(Specification.class));
    }

    @Test
    void testGetEmployeeByEmailUsingSpecificationsNotFound() {
        Specification<Employee> spec = EmployeeSpecifications.hasEmail("notfound@example.com");
        when(employeeRepository.findOne(spec)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            employeeService.getEmployeeByEmailUsingSpecifications("notfound@example.com");
        });
    }

    @Test
    void testGetEmployeeByEmailUsingHQL() {
        when(employeeRepository.findByEmailUsingHQL("aarav.sharma@example.com")).thenReturn(Optional.of(employee));

        EmployeeResponse response = employeeService.getEmployeeByEmailUsingHQL("aarav.sharma@example.com");

        assertNotNull(response);
        assertEquals("aarav.sharma@example.com", response.getEmail());
        verify(employeeRepository, times(1)).findByEmailUsingHQL("aarav.sharma@example.com");
    }

    @Test
    void testGetEmployeeByEmailUsingNativeSQL() {
        when(employeeRepository.findByEmailUsingNativeSQL("aarav.sharma@example.com")).thenReturn(Optional.of(employee));

        EmployeeResponse response = employeeService.getEmployeeByEmailUsingNativeSQL("aarav.sharma@example.com");

        assertNotNull(response);
        assertEquals("aarav.sharma@example.com", response.getEmail());
        verify(employeeRepository, times(1)).findByEmailUsingNativeSQL("aarav.sharma@example.com");
    }

    @Test
    @SuppressWarnings("unchecked")
    void testGetEmployeeByNameUsingSpecifications() {
        Specification<Employee> spec = EmployeeSpecifications.hasName("Aarav Sharma");
        when(employeeRepository.findOne(any(Specification.class))).thenReturn(Optional.of(employee));

        EmployeeResponse response = employeeService.getEmployeeByNameUsingSpecifications("Aarav Sharma");

        assertNotNull(response);
        assertEquals("Aarav Sharma", response.getName());
        verify(employeeRepository, times(1)).findOne(any(Specification.class));
    }

    @Test
    void testGetEmployeeByNameUsingHQL() {
        when(employeeRepository.findByNameUsingHQL("Aarav Sharma")).thenReturn(Optional.of(employee));

        EmployeeResponse response = employeeService.getEmployeeByNameUsingHQL("Aarav Sharma");

        assertNotNull(response);
        assertEquals("Aarav Sharma", response.getName());
        verify(employeeRepository, times(1)).findByNameUsingHQL("Aarav Sharma");
    }

    @Test
    void testGetEmployeeByNameUsingNativeSQL() {
        when(employeeRepository.findByNameUsingNativeSQL("Aarav Sharma")).thenReturn(Optional.of(employee));

        EmployeeResponse response = employeeService.getEmployeeByNameUsingNativeSQL("Aarav Sharma");

        assertNotNull(response);
        assertEquals("Aarav Sharma", response.getName());
        verify(employeeRepository, times(1)).findByNameUsingNativeSQL("Aarav Sharma");
    }

    @Test
    void testUpdateEmployee() {
        UpdateEmployeeRequest updateRequest = new UpdateEmployeeRequest();
        updateRequest.setLastName("Smith");
        updateRequest.setPhone("9876543210");
        updateRequest.setAddress("456 Oak Ave");

        when(employeeRepository.findByEmailUsingHQL("aarav.sharma@example.com")).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeResponse response = employeeService.updateEmployee("aarav.sharma@example.com", updateRequest);

        assertNotNull(response);
        verify(employeeRepository, times(1)).findByEmailUsingHQL("aarav.sharma@example.com");
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployeePhone() {
        UpdatePhoneRequest phoneRequest = new UpdatePhoneRequest("9998887777");

        when(employeeRepository.findByEmailUsingHQL("aarav.sharma@example.com")).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeResponse response = employeeService.updateEmployeePhone("aarav.sharma@example.com", phoneRequest);

        assertNotNull(response);
        verify(employeeRepository, times(1)).findByEmailUsingHQL("aarav.sharma@example.com");
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testDeleteEmployeeByEmail() {
        when(employeeRepository.findByEmailUsingHQL("aarav.sharma@example.com")).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(any(Employee.class));

        employeeService.deleteEmployeeByEmail("aarav.sharma@example.com");

        verify(employeeRepository, times(1)).findByEmailUsingHQL("aarav.sharma@example.com");
        verify(employeeRepository, times(1)).delete(any(Employee.class));
    }

    @Test
    void testDeleteEmployeeByEmailNotFound() {
        when(employeeRepository.findByEmailUsingHQL("notfound@example.com")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            employeeService.deleteEmployeeByEmail("notfound@example.com");
        });
    }
}

