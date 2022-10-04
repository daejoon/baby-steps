package com.ddoong2.webapp.account;

import com.ddoong2.webapp.domain.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
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
                .andExpect(view().name("account/sign-up"))
                .andExpect(unauthenticated());
    }

    @Test
    @DisplayName("회원가입 처리 - 입력값 정상")
    void 회원가입_처리_입력값_정상() throws Exception {

        // Given - 사전 조건 설정
        final String userId = "testuser";
        final String email = "testuser@gmail.com";
        final String password = "12345678";

        // When - 검증하려는 로직 실행
        final ResultActions actual = this.mockMvc.perform(post("/sign-up")
                .param("nickname", userId)
                .param("email", email)
                .param("password", password)
                .with(csrf())
        );

        // Then - 출력 확인
        actual.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated().withUsername("testuser"));

        Optional<Account> findAccount = accountRepository.findByEmail(email);

        assertThat(findAccount).isPresent();
        assertThat(findAccount.get().getPassword()).isNotEqualTo(password);
        assertThat(findAccount.get().getEmailCheckToken()).isNotNull();
        then(javaMailSender).should().send(isA(SimpleMailMessage.class));
    }

    @Test
    @DisplayName("인증 메일 확인 - 입력값 오류")
    void 인증_메일_확인_입력값_오류() throws Exception {

        // Given - 사전 조건 설정
        // When - 검증하려는 로직 실행
        // Then - 출력 확인
        this.mockMvc.perform(get("/check-email-token")
                        .param("token", "akdjsfkasfj")
                        .param("email", "email@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(view().name("account/checked-email"))
                .andExpect(unauthenticated());
    }

    @Test
    @DisplayName("인증 메일 확인 - 입력값 정상")
    void 인증_메일_확인_입력값_정상() throws Exception {
        final Account account = Account.builder()
                .email("test@email.com")
                .password("12345679")
                .nickname("testuser")
                .build();
        final Account savedAccount = accountRepository.save(account);
        savedAccount.generateEmailCheckToken();

        // Given - 사전 조건 설정
        // When - 검증하려는 로직 실행
        // Then - 출력 확인
        this.mockMvc.perform(get("/check-email-token")
                        .param("token", savedAccount.getEmailCheckToken())
                        .param("email", savedAccount.getEmail()))
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("error"))
                .andExpect(model().attributeExists("nickname"))
                .andExpect(model().attributeExists("numberOfUser"))
                .andExpect(view().name("account/checked-email"))
                .andExpect(authenticated().withUsername("testuser"));
    }
}