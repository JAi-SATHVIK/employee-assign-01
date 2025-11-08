package com.assignment.employee.service;

import com.assignment.employee.dto.CreateEmployeeRequest;
import com.assignment.employee.dto.EmployeeResponse;
import com.assignment.employee.dto.UpdateEmployeeRequest;
import com.assignment.employee.dto.UpdatePhoneRequest;
import com.assignment.employee.entity.Employee;
import com.assignment.employee.repository.EmployeeRepository;
import com.assignment.employee.repository.EmployeeSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeResponse createEmployee(CreateEmployeeRequest request) {
        Employee employee = new Employee(request.getName(), request.getEmail(), request.getPhone());
        Employee saved = employeeRepository.save(employee);
        return mapToResponse(saved);
    }

    public EmployeeResponse getEmployeeByEmailUsingSpecifications(String email) {
        Specification<Employee> spec = EmployeeSpecifications.hasEmail(email);
        Employee employee = employeeRepository.findOne(spec)
                .orElseThrow(() -> new RuntimeException("Employee not found with email: " + email));
        return mapToResponse(employee);
    }

    public EmployeeResponse getEmployeeByEmailUsingHQL(String email) {
        Employee employee = employeeRepository.findByEmailUsingHQL(email)
                .orElseThrow(() -> new RuntimeException("Employee not found with email: " + email));
        return mapToResponse(employee);
    }

    public EmployeeResponse getEmployeeByEmailUsingNativeSQL(String email) {
        Employee employee = employeeRepository.findByEmailUsingNativeSQL(email)
                .orElseThrow(() -> new RuntimeException("Employee not found with email: " + email));
        return mapToResponse(employee);
    }

    public EmployeeResponse getEmployeeByNameUsingSpecifications(String name) {
        Specification<Employee> spec = EmployeeSpecifications.hasName(name);
        Employee employee = employeeRepository.findOne(spec)
                .orElseThrow(() -> new RuntimeException("Employee not found with name: " + name));
        return mapToResponse(employee);
    }

    public EmployeeResponse getEmployeeByNameUsingHQL(String name) {
        Employee employee = employeeRepository.findByNameUsingHQL(name)
                .orElseThrow(() -> new RuntimeException("Employee not found with name: " + name));
        return mapToResponse(employee);
    }

    public EmployeeResponse getEmployeeByNameUsingNativeSQL(String name) {
        Employee employee = employeeRepository.findByNameUsingNativeSQL(name)
                .orElseThrow(() -> new RuntimeException("Employee not found with name: " + name));
        return mapToResponse(employee);
    }

    public EmployeeResponse updateEmployee(String email, UpdateEmployeeRequest request) {
        Employee employee = employeeRepository.findByEmailUsingHQL(email)
                .orElseThrow(() -> new RuntimeException("Employee not found with email: " + email));

        if (request.getLastName() != null) {
            employee.setLastName(request.getLastName());
        }
        if (request.getPhone() != null) {
            employee.setPhone(request.getPhone());
        }
        if (request.getAddress() != null) {
            employee.setAddress(request.getAddress());
        }

        Employee updated = employeeRepository.save(employee);
        return mapToResponse(updated);
    }

    public EmployeeResponse updateEmployeePhone(String email, UpdatePhoneRequest request) {
        Employee employee = employeeRepository.findByEmailUsingHQL(email)
                .orElseThrow(() -> new RuntimeException("Employee not found with email: " + email));

        employee.setPhone(request.getPhone());

        Employee updated = employeeRepository.save(employee);
        return mapToResponse(updated);
    }

    public void deleteEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findByEmailUsingHQL(email)
                .orElseThrow(() -> new RuntimeException("Employee not found with email: " + email));
        employeeRepository.delete(employee);
    }

    private EmployeeResponse mapToResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setName(employee.getName());
        response.setLastName(employee.getLastName());
        response.setEmail(employee.getEmail());
        response.setPhone(employee.getPhone());
        response.setAddress(employee.getAddress());
        return response;
    }
}

