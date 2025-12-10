package com.backandwhite.core.api.dtos.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDtoOut {

    @Schema(name = "", example = "")
    private Long id;

    @Schema(name = "", example = "")
    private String name;

    @Schema(name = "", example = "")
    private String description;

    @Schema(name = "", example = "")
    private Boolean isActive;
}
