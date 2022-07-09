package com.ddoong2.study.springboot.integration;

import com.ddoong2.study.springboot.model.Employee;
import com.ddoong2.study.springboot.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
    }

    @Test
    @DisplayName("Employee 객체를 저장합니다")
    void Employee_객체를_저장합니다() throws Exception {

        // Given - 사전 조건 설정
        final Employee employee = Employee.builder()
                .firstName("daejoon")
                .lastName("ko")
                .email("kkode1911@gmail.com")
                .build();

        // When - 검증하려는 로직 실행
        final ResultActions response = this.mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(employee)));

        // Then - 출력 확인
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    @Test
    @DisplayName("Employee 전체를 조회한다")
    void Employee_전체를_조회한다() throws Exception {

        // Given - 사전 조건 설정
        final List<Employee> listOfEmployees = List.of(
                Employee.builder().firstName("daejoon").lastName("ko").email("kkode1911@gmail.com").build(),
                Employee.builder().firstName("wyatt").lastName("ko").email("wyatt@gmail.com").build()
        );
        employeeRepository.saveAll(listOfEmployees);

        // When - 검증하려는 로직 실행
        final ResultActions response = this.mockMvc.perform(get("/api/employees"));

        // Then - 출력 확인
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(listOfEmployees.size())));
    }

    @Test
    @DisplayName("Employee ID로 조회한다")
    void Employee_ID로_조회한다() throws Exception {

        // Given - 사전 조건 설정
        final Employee employee = Employee.builder()
                .firstName("daejoon")
                .lastName("ko")
                .email("kkode1911@gmail.com")
                .build();
        employeeRepository.save(employee);

        // When - 검증하려는 로직 실행
        final ResultActions response = this.mockMvc.perform(get("/api/employees/{id}", employee.getId()));

        // Then - 출력 확인
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    @Test
    @DisplayName("Employee ID로 조회하는데 결과가 없으면 404를 반환한다")
    void Employee_ID로_조회하는데_결과가_없으면_404를_반환한다() throws Exception {

        // Given - 사전 조건 설정
        final Employee employee = Employee.builder()
                .firstName("daejoon")
                .lastName("ko")
                .email("kkode1911@gmail.com")
                .build();
        employeeRepository.save(employee);

        // When - 검증하려는 로직 실행
        final ResultActions response = this.mockMvc.perform(get("/api/employees/{id}", employee.getId()+1));

        // Then - 출력 확인
        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Employee를 업데이트 한다")
    void Employee를_업데이트_한다() throws Exception {

        // Given - 사전 조건 설정
        final Employee savedEmployee = Employee.builder()
                .firstName("daejoon")
                .lastName("ko")
                .email("kkode1911@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);

        final Employee updatedEmployee = Employee.builder()
                .firstName("wyatt")
                .lastName("ko")
                .email("wyatt@gmail.com")
                .build();

        // When - 검증하려는 로직 실행
        final ResultActions response = this.mockMvc.perform(
                put("/api/employees/{id}", savedEmployee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(updatedEmployee))
        );

        // Then - 출력 확인
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));
    }

    @Test
    @DisplayName("Employee를 업데이트 하는데 기존 데이터를 찾지 못하면 404를 반환한다")
    void Employee를_업데이트_하는데_기존_데이터를_찾지_못하면_404를_반환한다() throws Exception {

        // Given - 사전 조건 설정
        final Employee savedEmployee = Employee.builder()
                .firstName("daejoon")
                .lastName("ko")
                .email("kkode1911@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);

        final Employee updatedEmployee = Employee.builder()
                .firstName("wyatt")
                .lastName("ko")
                .email("wyatt@gmail.com")
                .build();

        // When - 검증하려는 로직 실행
        final ResultActions response = this.mockMvc.perform(
                put("/api/employees/{id}", savedEmployee.getId()+1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(updatedEmployee))
        );

        // Then - 출력 확인
        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Employee를 삭제한다")
    void Employee를_삭제한다() throws Exception {

        // Given - 사전 조건 설정
        final Employee savedEmployee = Employee.builder()
                .firstName("daejoon")
                .lastName("ko")
                .email("kkode1911@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);

        // When - 검증하려는 로직 실행
        final ResultActions response = this.mockMvc.perform(delete("/api/employees/{id}", savedEmployee.getId()));

        // Then - 출력 확인
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Employee deleted successfully!"));
    }
}
