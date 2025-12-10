package com.backandwhite.core.api.dtos.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDtoIn {

    @Schema(name = "name", example = "GUEST")
    @NotBlank(message = "El nombre del grupo no puede estar vacío.")
    @Size(max = 100, message = "El nombre del grupo no debe exceder los 100 caracteres.")
    private String name;

    @Schema(name = "description", example = "Grupo de usuarios de la app")
    @Size(max = 500, message = "La descripción no debe exceder los 500 caracteres.")
    private String description;

    @Schema(name = "isActive", example = "true")
    private Boolean isActive;
}
