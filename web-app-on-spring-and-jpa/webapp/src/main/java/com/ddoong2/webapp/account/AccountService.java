package com.ddoong2.webapp.account;

import com.ddoong2.webapp.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public Account processNewAccount(final SignUpForm signUpForm) {

        final Account saveAccount = saveNewAccount(signUpForm);
        saveAccount.generateEmailCheckToken();
        sendSignUpConfirmEmail(saveAccount);
        return saveAccount;
    }

    private Account saveNewAccount(final SignUpForm signUpForm) {

        final Account account = Account.builder()
                .email(signUpForm.getEmail())
                .nickname(signUpForm.getNickname())
                .password(passwordEncoder.encode(signUpForm.getPassword())) // TODO encoding 해야함
                .studyCreatedByWeb(true)
                .studyEnrollmentResultByWeb(true)
                .studyUpdatedByWeb(true)
                .build();

        return accountRepository.save(account);
    }

    public void sendSignUpConfirmEmail(final Account saveAccount) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(saveAccount.getEmail());
        mailMessage.setSubject("스터디올래, 회원 가입 인증");
        mailMessage.setText("/check-email-token?token=" + saveAccount.getEmailCheckToken() + "&email=" + saveAccount.getEmail());
        javaMailSender.send(mailMessage);
    }

    public void login(final Account account) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserAccount(account),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    @Override
    public UserDetails loadUserByUsername(final String emailOrNickname) throws UsernameNotFoundException {

        final Account account = accountRepository.findByEmail(emailOrNickname)
                .or(() -> accountRepository.findByNickname(emailOrNickname))
                .orElseThrow(() -> new UsernameNotFoundException("이메일이나 닉네임으로 사용자를 찾을수 없습니다."));

        return new UserAccount(account);
    }
}
