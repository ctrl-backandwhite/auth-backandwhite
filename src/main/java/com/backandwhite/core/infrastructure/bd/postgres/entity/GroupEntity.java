package com.backandwhite.core.infrastructure.bd.postgres.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@With
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups")
public class GroupEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "", length = 50, unique = true)
    private String uniqueName;

    @Column(name = "description", length =  250)
    private String description;

    @Column(name = "isActive")
    private Boolean isActive;

}
