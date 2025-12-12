package com.backandwhite.core.api.dtos.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDtoOut {

    @Schema(name = "id", example = "1")
    private Long id;

    @Schema(name = "name", example = "GUEST")
    private String name;

    @Schema(name = "uniqueName", example = "GROUP_GUEST")
    private String uniqueName;

    @Schema(name = "description", example = "Grupo de usuarios de la app")
    private String description;

    @Schema(name = "isActive", example = "true")
    private Boolean isActive;
}
