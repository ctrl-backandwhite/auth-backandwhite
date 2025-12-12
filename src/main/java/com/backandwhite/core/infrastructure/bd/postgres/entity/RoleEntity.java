package com.backandwhite.core.infrastructure.bd.postgres.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@With
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class RoleEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 30, unique = true)
    private String name;

    @Column(name = "", length = 50, unique = true)
    private String uniqueName;

    @Column(name = "description", length =  250)
    private String description;

    @Column(name = "isActive")
    private Boolean isActive;

}
