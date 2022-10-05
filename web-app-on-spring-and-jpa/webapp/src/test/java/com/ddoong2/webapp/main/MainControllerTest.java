package com.ddoong2.webapp.main;

import com.ddoong2.webapp.account.AccountRepository;
import com.ddoong2.webapp.account.AccountService;
import com.ddoong2.webapp.account.SignUpForm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {

        SignUpForm signUpForm = new SignUpForm();
        signUpForm = new SignUpForm();
        signUpForm.setNickname("testuser");
        signUpForm.setEmail("testuser@email.com");
        signUpForm.setPassword("12345678");
        accountService.processNewAccount(signUpForm);
    }

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("이메일로 로그인 확인")
    void 이메일로_로그인_확인() throws Exception {

        // Given - 사전 조건 설정
        // When - 검증하려는 로직 실행
        // Then - 출력 확인
        this.mockMvc.perform(post("/login")
                        .param("username", "testuser@email.com")
                        .param("password", "12345678")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated().withUsername("testuser"))
        ;
    }

    @Test
    @DisplayName("닉네임으로 로그인 성공")
    void 닉네임으로_로그인_성공() throws Exception {

        // Given - 사전 조건 설정
        // When - 검증하려는 로직 실행
        // Then - 출력 확인
        this.mockMvc.perform(post("/login")
                        .param("username", "testuser")
                        .param("password", "12345678")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated().withUsername("testuser"))
        ;
    }

    @Test
    @DisplayName("로그인 실패")
    void 로그인_실패() throws Exception {

        // Given - 사전 조건 설정
        // When - 검증하려는 로직 실행
        // Then - 출력 확인
        this.mockMvc.perform(post("/login")
                        .param("username", "1111")
                        .param("password", "12345678")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated())
        ;
    }

    @Test
    @DisplayName("로그아웃")
    void 로그아웃() throws Exception {

        // Given - 사전 조건 설정
        // When - 검증하려는 로직 실행
        // Then - 출력 확인
        this.mockMvc.perform(post("/logout")
                        .param("username", "1111")
                        .param("password", "12345678")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(unauthenticated())
        ;
    }
}