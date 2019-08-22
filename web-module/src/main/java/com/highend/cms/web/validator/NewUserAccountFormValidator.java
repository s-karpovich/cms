package com.highend.cms.web.validator;

import com.highend.cms.service.UserAccountService;
import com.highend.cms.service.model.UserAccountDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.highend.cms.web.constant.ValidationMessages.LATIN_PATTERN;
import static com.highend.cms.web.constant.ValidationMessages.LATIN_PATTERN_INVALID;
import static com.highend.cms.web.constant.ValidationMessages.LOGIN_MAX_LENGTH;
import static com.highend.cms.web.constant.ValidationMessages.LOGIN_MIN_LENGTH;
import static com.highend.cms.web.constant.ValidationMessages.NAME_MAX_LENGTH;
import static com.highend.cms.web.constant.ValidationMessages.NAME_MIN_LENGTH;
import static com.highend.cms.web.constant.ValidationMessages.PASSWORD_PATTERN;
import static com.highend.cms.web.constant.ValidationMessages.PASSWORD_PATTERN_INVALID;
import static com.highend.cms.web.constant.ValidationMessages.USERNAME_DUPLICATE;
import static com.highend.cms.web.constant.ValidationMessages.USER_LOGIN_LENGTH_INVALID;
import static com.highend.cms.web.constant.ValidationMessages.USER_NAME_LENGTH_INVALID;
import static com.highend.cms.web.constant.ValidationMessages.USER_ROLE_BLANK;

@Component
public class NewUserAccountFormValidator implements Validator {
    private final UserAccountService userAccountService;

    public NewUserAccountFormValidator(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserAccountDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserAccountDTO user = (UserAccountDTO) o;



        if (user.getUsername().length() < LOGIN_MIN_LENGTH || user.getUsername().length() > NAME_MAX_LENGTH) {
            errors.rejectValue("username", "", USER_NAME_LENGTH_INVALID);
        }  else if (userAccountService.getByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "", USERNAME_DUPLICATE);
        }

        if (user.getPassword().length() < LOGIN_MIN_LENGTH || user.getPassword().length() > LOGIN_MAX_LENGTH) {
            errors.rejectValue("password", "", USER_LOGIN_LENGTH_INVALID);
        }

        if (user.getFirstName().length() < NAME_MIN_LENGTH || user.getFirstName().length() > NAME_MAX_LENGTH) {
            errors.rejectValue("firstName", "", USER_NAME_LENGTH_INVALID);
        }

        if (user.getLastName().length() < NAME_MIN_LENGTH || user.getLastName().length() > NAME_MAX_LENGTH) {
            errors.rejectValue("lastName", "", USER_NAME_LENGTH_INVALID);
        }

        if (!user.getUsername().matches(LATIN_PATTERN)) {
            errors.rejectValue("username", "", LATIN_PATTERN_INVALID);
        }

        if (!user.getPassword().matches(PASSWORD_PATTERN)) {
            errors.rejectValue("password", "", PASSWORD_PATTERN_INVALID);
        }

        if (!user.getFirstName().matches(LATIN_PATTERN)) {
            errors.rejectValue("firstName", "", LATIN_PATTERN_INVALID);
        }

        if (!user.getLastName().matches(LATIN_PATTERN)) {
            errors.rejectValue("lastName", "", LATIN_PATTERN_INVALID);
        }

        if (user.getRoleDTO() == null || user.getRoleDTO().getId() == null || user.getRoleDTO().getName() != null) {
            errors.rejectValue("roleDTO", "", USER_ROLE_BLANK);
        }
    }
}
