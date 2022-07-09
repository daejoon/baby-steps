package com.ddoong2.study.springboot.service.impl;

import com.ddoong2.study.springboot.exception.ResourceNotFoundException;
import com.ddoong2.study.springboot.model.Employee;
import com.ddoong2.study.springboot.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    private Employee employee;


    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .id(1L)
                .firstName("daejoon")
                .lastName("ko")
                .email("kkode1911@gmail.com")
                .build();
    }

    @Test
    @DisplayName("Employee 객체를 저장한다")
    void Employee_객체를_저장한다() {

        // Given - 사전 조건 설정
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        // When - 검증하려는 로직 실행
        final Employee savedEmployee = employeeService.saveEmployee(employee);

        // Then - 출력 확인
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    @DisplayName("존재하는 Employee라면 ResourceNotFoundException이 발생한다")
    void 존재하는_Employee라면_ResourceNotFoundException이_발생한다() {

        // Given - 사전 조건 설정
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        // When - 검증하려는 로직 실행
        final ThrowingCallable action = () -> employeeService.saveEmployee(employee);

        // Then - 출력 확인
        assertThatCode(action).isInstanceOf(ResourceNotFoundException.class);
        then(employeeRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("Employee 전체를 조회한다")
    void Employee_전체를_조회한다() {

        // Given - 사전 조건 설정
        Employee employee2 = Employee.builder()
                .id(2L)
                .firstName("wyatt")
                .lastName("ko")
                .email("wyatt@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee, employee2));

        // When - 검증하려는 로직 실행
        final List<Employee> employees = employeeService.getAllEmployees();

        // Then - 출력 확인
        assertThat(employees).hasSize(2);
    }

    @Test
    @DisplayName("Employee 전체를 조회하는데 아무도 없다")
    void Employee_전체를_조회하는데_아무도_없다() {

        // Given - 사전 조건 설정
        given(employeeRepository.findAll())
                .willReturn(Collections.emptyList());

        // When - 검증하려는 로직 실행
        final List<Employee> employees = employeeService.getAllEmployees();

        // Then - 출력 확인
        assertThat(employees).isEmpty();
        assertThat(employees).hasSize(0);
    }

    @Test
    @DisplayName("Employee ID로 조회한다")
    void Employee_ID로_조회한다() {

        // Given - 사전 조건 설정
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        // When - 검증하려는 로직 실행
        final Employee findEmployee = employeeService.getEmployeeById(this.employee.getId()).get();

        // Then - 출력 확인
        assertThat(findEmployee).isNotNull();
        assertThat(findEmployee.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Employee 객체를 업데이트 한다")
    void Employee_객체를_업데이트_한다() {

        // Given - 사전 조건 설정
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.updateEmail("wyatt@gmail.com");
        employee.updateFirstName("wyatt");

        // When - 검증하려는 로직 실행
        final Employee updatedEmployee = employeeService.updateEmployee(this.employee);

        // Then - 출력 확인
        assertThat(updatedEmployee.getEmail()).isEqualTo("wyatt@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("wyatt");
    }

    @Test
    @DisplayName("Employee 객체를 삭제한다")
    void Employee_객체를_삭제한다() {

        // Given - 사전 조건 설정
        final long employeeId = 1L;
        willDoNothing().given(employeeRepository).deleteById(employeeId);

        // When - 검증하려는 로직 실행
        employeeService.deleteEmployee(employeeId);

        // Then - 출력 확인
        then(employeeRepository).should(times(1)).deleteById(employeeId);
    }
}