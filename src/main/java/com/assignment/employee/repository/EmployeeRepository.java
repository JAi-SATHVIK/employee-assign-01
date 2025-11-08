package com.assignment.employee.repository;

import com.assignment.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    @Query("SELECT e FROM Employee e WHERE e.email = :email")
    Optional<Employee> findByEmailUsingHQL(@Param("email") String email);

    @Query("SELECT e FROM Employee e WHERE e.name = :name")
    Optional<Employee> findByNameUsingHQL(@Param("name") String name);

    @Query(value = "SELECT * FROM employees WHERE email = :email", nativeQuery = true)
    Optional<Employee> findByEmailUsingNativeSQL(@Param("email") String email);

    @Query(value = "SELECT * FROM employees WHERE name = :name", nativeQuery = true)
    Optional<Employee> findByNameUsingNativeSQL(@Param("name") String name);
}

