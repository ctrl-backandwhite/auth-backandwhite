package com.backandwhite.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@Data
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
