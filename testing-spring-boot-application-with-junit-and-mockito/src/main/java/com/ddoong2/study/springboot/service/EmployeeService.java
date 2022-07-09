package com.ddoong2.study.springboot.service;

import com.ddoong2.study.springboot.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Optional<Employee> getEmployeeById(Long id);

    Employee updateEmployee(Employee updatedEmployee);

    void deleteEmployee(Long id);

}
