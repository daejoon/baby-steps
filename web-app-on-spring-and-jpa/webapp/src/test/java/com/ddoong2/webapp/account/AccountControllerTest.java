package com.ddoong2.webapp.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @MockBean
    private JavaMailSender javaMailSender;


    @Test
    @DisplayName("회원 가입 화면 보이는지 테스트")
    void 회원_가입_화면_보이는지_테스트() throws Exception {

        // Given - 사전 조건 설정
        final String url = "/sign-up";

        // When - 검증하려는 로직 실행
        final ResultActions actual = this.mockMvc.perform(get(url))
                .andDo(print());

        // Then - 출력 확인
        actual.andExpect(status().isOk())
                .andExpect(view().name("account/sign-up"))
                .andExpect(model().attributeExists("signUpForm"));
    }

    @Test
    @DisplayName("회원 가입 처리 - 입력값 오류")
    void 회원_가입_처리_wrong_input() throws Exception {

        // Given - 사전 조건 설정
        // When - 검증하려는 로직 실행
        // Then - 출력 확인
        this.mockMvc.perform(post("/sign-up")
                        .param("nickname", "testuser")
                        .param("email", "email...")
                        .param("password", "12345")
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up"));
    }

    @Test
    @DisplayName("회원가입 처리 - 입력값 정상")
    void 회원가입_처리_입력값_정상() throws Exception {

        // Given - 사전 조건 설정

        // When - 검증하려는 로직 실행
        final ResultActions actual = this.mockMvc.perform(post("/sign-up")
                .param("nickname", "testuser")
                .param("email", "testuser@gmail.com")
                .param("password", "12345678")
                .with(csrf())
        );

        // Then - 출력 확인
        actual.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        assertThat(accountRepository.existsByEmail("testuser@gmail.com")).isTrue();
        then(javaMailSender).should().send(isA(SimpleMailMessage.class));
    }
}