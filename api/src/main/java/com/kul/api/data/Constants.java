package com.kul.api.data;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.regex.Pattern;

public class Constants {
    public static final String HOST_URL = "http://localhost:8090/";
    public static final EmailValidator EMAIL_VALIDATOR = EmailValidator.getInstance();
    public static final Pattern SIMPLE_STRING_PATTERN = Pattern.compile("^([A-Z]|[a-z])+$");

    public static final String PASSWORD_PROMPT = "minimum 1 digit\n" +
            "minimum 1 lower case letter\n" +
            "minimum 1 upper case letter\n" +
            "minimum 1 special character\n" +
            "no whitespace allowed\n" +
            "length between 12 and 64";
}
