package com.highend.cms.web.constant;

import org.springframework.stereotype.Component;

@Component
public class ValidationMessages {
    public static final String USERNAME_DUPLICATE = "Username already exists";

    public static final String USER_LOGIN_LENGTH_INVALID = "Must be between 3 and 16 characters";
    public static final String USER_NAME_LENGTH_INVALID = "Must be between 1 and 16 characters";
    public static final String USER_ROLE_BLANK = "UserAccount Role can not be blank";

    public static final String LATIN_PATTERN_INVALID = "Should contain only Latin characters";
    public static final String PASSWORD_PATTERN_INVALID = "Should contain at least 1 Latin character and at least 1 number";

    public static final String LATIN_PATTERN = "[a-zA-z]+";
    public static final String PASSWORD_PATTERN = "([A-Za-z]+[0-9]|[0-9]+[A-Za-z])[A-Za-z0-9]*";

    public static final int LOGIN_MIN_LENGTH = 3;
    public static final int LOGIN_MAX_LENGTH = 16;

    public static final int NAME_MIN_LENGTH = 1;
    public static final int NAME_MAX_LENGTH = 16;

    private ValidationMessages() {
    }
}