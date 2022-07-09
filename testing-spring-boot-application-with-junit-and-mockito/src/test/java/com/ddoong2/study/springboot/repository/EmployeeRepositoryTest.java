package com.ddoong2.study.springboot.repository;

import com.ddoong2.study.springboot.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .firstName("daejoon")
                .lastName("ko")
                .email("kkode1911@gmail.com")
                .build();
    }

    @Test
    @DisplayName("Employee 객체를 저장하고 확인한다")
    void Employee_객체를_저장하고_확인한다() {

        // Given

        // When
        final Employee savedEmployee = employeeRepository.save(employee);

        // Then
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Employee 전체를 조회 한다")
    void Employee_전체를_조회_한다() {

        // Given - 사전 조건 설정
        final Employee employee2 = Employee.builder()
                .firstName("wyatt")
                .lastName("ko")
                .email("wyatt@gmail.com")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee2);

        // When - 검증하려는 로직 실행
        final List<Employee> employees = employeeRepository.findAll();

        // Then - 출력 확인
        assertThat(employees).isNotNull();
        assertThat(employees).hasSize(2);
    }

    @Test
    @DisplayName("Employee ID로 Employee 객체를 조회 한다")
    void Employee_ID로_Employee_객체를_조회_한다() {

        // Given - 사전 조건 설정
        employeeRepository.save(employee);

        // When - 검증하려는 로직 실행
        final Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        // Then - 출력 확인
        assertThat(employeeOptional).isPresent();
    }

    @Test
    @DisplayName("Employee Email을 가지고 객체를 조회 한다")
    void Employee_Email을_가지고_객체를_조회_한다() {

        // Given - 사전 조건 설정
        employeeRepository.save(employee);

        // When - 검증하려는 로직 실행
        final Optional<Employee> employeeOptional = employeeRepository.findByEmail(employee.getEmail());

        // Then - 출력 확인
        assertThat(employeeOptional).isPresent();
    }

    @Test
    @DisplayName("Employee 객체를 업데이트 한다")
    void Employee_객체를_업데이트_한다() {

        // Given - 사전 조건 설정
        employeeRepository.save(employee);

        // When - 검증하려는 로직 실행
        final Employee findEmployee = employeeRepository.findById(employee.getId()).get();
        findEmployee.updateEmail("wyatt@gmail.com");
        findEmployee.updateFirstName("wyatt");
        final Employee updateEmployee = employeeRepository.save(findEmployee);

        // Then - 출력 확인
        assertThat(updateEmployee).isNotNull();
        assertThat(updateEmployee.getEmail()).isEqualTo("wyatt@gmail.com");
        assertThat(updateEmployee.getFirstName()).isEqualTo("wyatt");
    }

    @Test
    @DisplayName("Employee를 삭제한다")
    void Employee를_삭제한다() {

        // Given - 사전 조건 설정
        employeeRepository.save(employee);

        // When - 검증하려는 로직 실행
        employeeRepository.delete(employee);
        final Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        // Then - 출력 확인
        assertThat(employeeOptional).isNotPresent();
    }

    @Test
    @DisplayName("JPQL Index Params으로 조회한다")
    void JPQL_Index_Params으로_조회한다() {

        // Given - 사전 조건 설정
        employeeRepository.save(employee);

        // When - 검증하려는 로직 실행
        final Employee findEmployee = employeeRepository.findByJPQL("daejoon", "ko");

        // Then - 출력 확인
        assertThat(findEmployee).isNotNull();
    }

    @Test
    @DisplayName("JPQL Named Params으로 조회한다")
    void JPQL_Named_Params으로_조회한다() {

        // Given - 사전 조건 설정
        employeeRepository.save(employee);

        // When - 검증하려는 로직 실행
        final Employee findEmployee = employeeRepository.findByJPQLNamedParams("daejoon", "ko");

        // Then - 출력 확인
        assertThat(findEmployee).isNotNull();
    }

    @Test
    @DisplayName("Native Index Params으로 조회한다")
    void Native_Index_Params으로_조회한다() {

        // Given - 사전 조건 설정
        employeeRepository.save(employee);

        // When - 검증하려는 로직 실행
        final Employee findEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());

        // Then - 출력 확인
        assertThat(findEmployee).isNotNull();
    }

    @Test
    @DisplayName("Native Named Params으로 조회 한다")
    void Native_Named_Params으로_조회_한다() {

        // Given - 사전 조건 설정
        employeeRepository.save(employee);

        // When - 검증하려는 로직 실행
        final Employee findEmployee = employeeRepository.findByNativeSQLNamedParams(employee.getFirstName(), employee.getLastName());

        // Then - 출력 확인
        assertThat(findEmployee).isNotNull();
    }
}