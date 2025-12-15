package com.backandwhite.core.api.dtos.in;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoIn {

        @NotBlank
        @Schema(description = "First name of the user", example = "John")
        private String firstName;

        @NotBlank
        @Schema(description = "Last name of the user", example = "Doe")
        private String lastName;

        @Email
        @NotBlank
        @Schema(description = "Email address of the user")
        private String email;

        @Schema(description = "Phone number of the user", example = "+1234567890")
        private String phoneNumber;

        @Schema(description = "Password for the user account", example = "P@ssw0rd!")
        String password;

        @ArraySchema(schema = @Schema(implementation = Long.class, example = "1"))
        private List<Long> roleIds;

        @ArraySchema(schema = @Schema(implementation = Long.class, example = "1"))
        private List<Long> groupIds;

}
