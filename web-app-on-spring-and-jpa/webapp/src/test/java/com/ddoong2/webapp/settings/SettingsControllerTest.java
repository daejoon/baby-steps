package com.ddoong2.webapp.settings;

import com.ddoong2.webapp.WithAccount;
import com.ddoong2.webapp.account.AccountRepository;
import com.ddoong2.webapp.account.AccountService;
import com.ddoong2.webapp.domain.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SettingsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
    }

    @Test
    @WithAccount("testuser")
    @DisplayName("프로필 수정 폼")
    void 프로필_수정_폼() throws Exception {

        // Given - 사전 조건 설정
        final String bio = "짧은 소개를 수정하는 경우.";
        // When - 검증하려는 로직 실행
        // Then - 출력 확인
        this.mockMvc.perform(get(SettingsController.SETTINGS_PROFILE_URL)
                )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("profile"));
    }

    @Test
    @WithAccount("testuser")
    @DisplayName("프로필 수정하기 - 입력값 정상")
    void 프로필_수정하기___입력값_정상() throws Exception {

        // Given - 사전 조건 설정
        final String bio = "짧은 소개를 수정하는 경우.";
        // When - 검증하려는 로직 실행
        // Then - 출력 확인
        this.mockMvc.perform(post(SettingsController.SETTINGS_PROFILE_URL)
                        .param("bio", bio)
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(SettingsController.SETTINGS_PROFILE_URL))
                .andExpect(flash().attributeExists("message"));

        final Optional<Account> findAccount = accountRepository.findByNickname("testuser");
        assertThat(findAccount).isPresent();
        assertThat(findAccount.get().getBio()).isEqualTo(bio);
    }

    @Test
    @WithAccount("testuser")
    @DisplayName("프로필 수정 - 입력값 에러")
    void 프로필_수정_입력값_에러() throws Exception {

        // Given - 사전 조건 설정
        final String bio = "짧은 소개를 수정하는 경우.짧은 소개를 수정하는 경우.짧은 소개를 수정하는 경우.짧은 소개를 수정하는 경우.짧은 소개를 수정하는 경우.짧은 소개를 수정하는 경우.짧은 소개를 수정하는 경우.짧은 소개를 수정하는 경우.짧은 소개를 수정하는 경우.짧은 소개를 수정하는 경우.";
        // When - 검증하려는 로직 실행
        // Then - 출력 확인
        this.mockMvc.perform(post(SettingsController.SETTINGS_PROFILE_URL)
                        .param("bio", bio)
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name(SettingsController.SETTINGS_PROFILE_VEW_NAME))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("profile"))
                .andExpect(model().hasErrors());

        final Optional<Account> findAccount = accountRepository.findByNickname("testuser");
        assertThat(findAccount).isPresent();
        assertThat(findAccount.get().getBio()).isNull();
    }

}