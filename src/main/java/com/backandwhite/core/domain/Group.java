package com.backandwhite.core.domain;

import lombok.*;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    private Long id;
    private String name;
    private String description;
    private Boolean isActive;
}
