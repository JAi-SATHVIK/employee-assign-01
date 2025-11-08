package com.assignment.employee.controller;

import com.assignment.employee.dto.*;
import com.assignment.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/email/{email}/specifications")
    public ResponseEntity<EmployeeResponse> getEmployeeByEmailUsingSpecifications(@PathVariable String email) {
        EmployeeResponse response = employeeService.getEmployeeByEmailUsingSpecifications(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{email}/hql")
    public ResponseEntity<EmployeeResponse> getEmployeeByEmailUsingHQL(@PathVariable String email) {
        EmployeeResponse response = employeeService.getEmployeeByEmailUsingHQL(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{email}/native")
    public ResponseEntity<EmployeeResponse> getEmployeeByEmailUsingNativeSQL(@PathVariable String email) {
        EmployeeResponse response = employeeService.getEmployeeByEmailUsingNativeSQL(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/{name}/specifications")
    public ResponseEntity<EmployeeResponse> getEmployeeByNameUsingSpecifications(@PathVariable String name) {
        EmployeeResponse response = employeeService.getEmployeeByNameUsingSpecifications(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/{name}/hql")
    public ResponseEntity<EmployeeResponse> getEmployeeByNameUsingHQL(@PathVariable String name) {
        EmployeeResponse response = employeeService.getEmployeeByNameUsingHQL(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/{name}/native")
    public ResponseEntity<EmployeeResponse> getEmployeeByNameUsingNativeSQL(@PathVariable String name) {
        EmployeeResponse response = employeeService.getEmployeeByNameUsingNativeSQL(name);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody CreateEmployeeRequest request) {
        EmployeeResponse response = employeeService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{email}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable String email, @RequestBody UpdateEmployeeRequest request) {
        EmployeeResponse response = employeeService.updateEmployee(email, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{email}/phone")
    public ResponseEntity<EmployeeResponse> updateEmployeePhone(@PathVariable String email, @Valid @RequestBody UpdatePhoneRequest request) {
        EmployeeResponse response = employeeService.updateEmployeePhone(email, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteEmployeeByEmail(@PathVariable String email) {
        employeeService.deleteEmployeeByEmail(email);
        return ResponseEntity.noContent().build();
    }
}

