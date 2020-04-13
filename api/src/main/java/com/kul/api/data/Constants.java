package com.kul.api.data;

public class Constants {
    public static final String HOST_URL = "http://localhost:8090/";
    public static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    // ^                 # start-of-string
    // (?=.*[0-9])       # a digit must occur at least once
    // (?=.*[a-z])       # a lower case letter must occur at least once
    // (?=.*[A-Z])       # an upper case letter must occur at least once
    // (?=.*[@#$%^&+=])  # a special character must occur at least once
    // (?=\S+$)          # no whitespace allowed in the entire string
    // .{12,64}             # anything, at least eight places and not more than 64
    // $                 # end-of-string
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{12,64}$";

    public static final String PASSWORD_PROMPT = "a digit must occur at least once\n" +
            "a lower case letter must occur at least once\n" +
            "an upper case letter must occur at least once\n" +
            "a special character must occur at least once\n" +
            "no whitespace allowed in the entire string\n" +
            "minimum of 12 characters and maximum of 64";
}
