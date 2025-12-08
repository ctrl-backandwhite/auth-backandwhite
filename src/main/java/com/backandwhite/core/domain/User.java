package com.backandwhite.core.domain;

import lombok.*;

import java.util.Objects;
import java.util.regex.Pattern;

@With
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^\\S+@\\S+\\.\\S+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\+?[0-9]{10,15}");

    private Long id;
    private String  firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;

    public void validateEmail() {
        if (Objects.isNull(email) || !email.matches(EMAIL_PATTERN.pattern())) {
            throw new IllegalArgumentException("Invalid email address");
        }
    }

    public void validatePhoneNumber() {
        if (!phoneNumber.matches(PHONE_PATTERN.pattern())) {
            throw new IllegalArgumentException("Invalid phone number");
        }
    }
}
