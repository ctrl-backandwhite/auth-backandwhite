package com.backandwhite.core.api.dtos.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupDtoOut {

    @Schema(name = "id", example = "1")
    private Long id;
    @Schema(name = "name", example = "GUEST")
    private String name;
    @Schema(name = "uniqueName", example = "GROUP_GUEST")
    private String uniqueName;
}
