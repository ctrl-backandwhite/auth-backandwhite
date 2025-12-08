package com.backandwhite.core.api.dtos.in;

import lombok.*;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {

    private String key;
    private String operation;
    private Object value;
}
