package com.backandwhite.core.api.dtos.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDtoOut {

    @Schema(name = "id", example = "1")
    private Long id;
    @Schema(name = "name", example = "GUEST")
    private String name;
    @Schema(name = "uniqueName", example = "ROLE_ADMIN")
    private String uniqueName;
}
