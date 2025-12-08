package com.backandwhite.core.api.dtos.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoIn {

        @NotBlank
        @Schema(description = "First name of the user", example = "John")
        String firstName;

        @NotBlank
        @Schema(description = "Last name of the user", example = "Doe")
        String lastName;

        @Email
        @NotBlank
        @Schema(description = "Email address of the user")
        String email;

        @Schema(description = "Phone number of the user", example = "+1234567890")
        String phoneNumber;

        @Schema(description = "Password for the user account", example = "P@ssw0rd!")
        String password;
}
