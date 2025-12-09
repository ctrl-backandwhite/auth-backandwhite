package com.backandwhite.core.domain;

import lombok.*;

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
}
