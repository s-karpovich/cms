package com.highend.cms.web.validator;

import com.highend.cms.service.UserAccountService;
import com.highend.cms.service.model.UserAccountDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.highend.cms.web.constant.ValidationMessages.*;

@Component
public class EditUserAccountFormValidator implements Validator {
    private final UserAccountService userAccountService;

    public EditUserAccountFormValidator(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserAccountDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserAccountDTO user = (UserAccountDTO) o;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserAccountDTO currentUserAccount = userAccountService.getByUsername(username);
        System.out.println(currentUserAccount.getUsername());
        System.out.println(user.getUsername());


        if (user.getUsername().length() < LOGIN_MIN_LENGTH || user.getUsername().length() > NAME_MAX_LENGTH) {
            errors.rejectValue("username", "", USER_NAME_LENGTH_INVALID);
        } else if (!user.getId().equals(userAccountService.getByUsername(user.getUsername()).getId())) {
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
