package com.backandwhite.core.api.dtos.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDtoIn {

    @NotEmpty
    @Schema(name = "name", example = "GUEST")
    private String name;

    @NotEmpty
    @Schema(name = "uniqueName", example = "GROUP_GUEST")
    private String uniqueName;

    @Schema(name = "description", example = "Grupo de usuarios de la app")
    private String description;

    @Schema(name = "isActive", example = "true")
    private Boolean isActive;
}
