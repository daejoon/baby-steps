package com.ddoong2.webapp.account;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @ModelAttribute 에 맵핑되려면 @Setter가 존재해야 한다. 그럴바에는 @Data를 사용하는게 낫다고 생각한다.
 */
@Data
public class SignUpForm {

    @NotBlank
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
    private String nickname;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, max = 50)
    private String password;
}
