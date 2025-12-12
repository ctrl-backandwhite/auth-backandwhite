package com.backandwhite.core.api.dtos.out;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDtoOut {

    @Schema(name = "id", example = "id")
    private Long id;

    @Schema(name = "name", example = "ADMIN")
    @NotBlank(message = "El nombre del rol no puede estar vacío.")
    @Size(max = 50, message = "El nombre del rol no debe exceder los 50 caracteres.")
    private String name;

    @Schema(name = "uniqueName", example = "ROLE_ADMIN")
    private String uniqueName;

    @Schema(name = "description", example = "Rol del administrado de la aplicacion.")
    @Size(max = 255, message = "La descripción no debe exceder los 255 caracteres.")
    private String description;

    @Schema(name = "isActive", example = "true")
    private Boolean isActive;
}
