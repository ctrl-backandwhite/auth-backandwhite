package com.backandwhite.core.api.dtos.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoOut {

        @Schema(description = "Unique identifier of the user", example = "123")
        private Long id;

        @Schema(description = "First name of the user", example = "John")
        private String firstName;

        @Schema(description = "Last name of the user", example = "Doe")
        private String lastName;

        @Schema(description = "Email address of the user", example = "john.doe@example.com")
        private String email;

        @Schema(description = "Phone number of the user", example = "+1234567890")
        private String phoneNumber;

        private List<RoleDtoOut> roles;
}
