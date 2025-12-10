package com.backandwhite.core.infrastructure.bd.postgres.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

    @Column(name = "name", length = 30, unique = true)
    private String name;

    @Column(name = "description", length =  250)
    private String description;

    @Column(name = "isActive")
    private Boolean isActive;

}
