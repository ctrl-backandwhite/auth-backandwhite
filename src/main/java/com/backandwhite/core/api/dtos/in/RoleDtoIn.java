package com.backandwhite.core.api.dtos.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDtoIn {

    @NotEmpty
    @Schema(name = "name", example = "ADMIN")
    private String name;

    @NotEmpty
    @Schema(name = "uniqueName", example = "ROLE_ADMIN")
    private String uniqueName;

    @Schema(name = "description", example = "Rol del administrado de la aplicacio.n")
    private String description;

    @Schema(name = "isActive", example = "true")
    private Boolean isActive;
}
