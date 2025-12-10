package com.backandwhite.core.infrastructure.bd.postgres.repository;

import com.backandwhite.core.infrastructure.bd.postgres.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpaRepositoryAdapter extends JpaRepository<RoleEntity, Long> {
}