package com.backandwhite.core.domain;

import lombok.*;

import java.util.List;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String  firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private List<Role> roles;
}
