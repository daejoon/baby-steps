package com.ddoong2.webapp.account;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class SignUpForm {

    private String nickname;
    private String email;
    private String password;
}
