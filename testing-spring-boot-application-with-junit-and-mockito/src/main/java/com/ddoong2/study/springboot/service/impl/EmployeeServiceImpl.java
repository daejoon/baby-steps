package com.ddoong2.study.springboot.service.impl;

import com.ddoong2.study.springboot.exception.ResourceNotFoundException;
import com.ddoong2.study.springboot.model.Employee;
import com.ddoong2.study.springboot.repository.EmployeeRepository;
import com.ddoong2.study.springboot.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;


    @Override
    public Employee saveEmployee(final Employee employee) {

        final Optional<Employee> findEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (findEmployee.isPresent()) {
            throw new ResourceNotFoundException("Employee already exist with given email: " + employee.getEmail());
        }

        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(final Long id) {

        return employeeRepository.findById(id);
    }

    @Override
    public Employee updateEmployee(final Employee updatedEmployee) {

        return employeeRepository.save(updatedEmployee);
    }

    @Override
    public void deleteEmployee(final Long id) {

        employeeRepository.deleteById(id);
    }

}
