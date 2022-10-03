package com.ddoong2.webapp.account;

import com.ddoong2.webapp.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final JavaMailSender javaMailSender;

    public void processNewAccount(final SignUpForm signUpForm) {
        final Account saveAccount = saveNewAccount(signUpForm);
        saveAccount.generateEmailCheckToken();
        sendSignUpConfirmEmail(saveAccount);
    }

    private Account saveNewAccount(final SignUpForm signUpForm) {
        final Account account = Account.builder()
                .email(signUpForm.getEmail())
                .nickname(signUpForm.getNickname())
                .password(signUpForm.getPassword()) // TODO encoding 해야함
                .studyCreatedByWeb(true)
                .studyEnrollmentResultByWeb(true)
                .studyUpdatedByWeb(true)
                .build();

        return accountRepository.save(account);
    }

    private void sendSignUpConfirmEmail(final Account saveAccount) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(saveAccount.getEmail());
        mailMessage.setSubject("스터디올래, 회원 가입 인증");
        mailMessage.setText("/check-email-token?token=" + saveAccount.getEmailCheckToken() + "&email=" + saveAccount.getEmail());
        javaMailSender.send(mailMessage);
    }
}
