package com.ddoong2.webapp.settings;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class PasswordFormValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return PasswordForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {

        final PasswordForm passwordForm = (PasswordForm) target;
        if (!passwordForm.getNewPassword().equals(passwordForm.getNewPasswordConfirm())) {
            errors.rejectValue("newPassword",
                    "wrong.value",
                    "입력한 새 패스워드가 일치하지 않습니다.");
        }
    }
}
