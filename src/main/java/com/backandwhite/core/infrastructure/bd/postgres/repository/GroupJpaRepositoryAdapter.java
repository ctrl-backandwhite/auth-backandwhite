package com.backandwhite.core.infrastructure.bd.postgres.repository;

import com.backandwhite.core.infrastructure.bd.postgres.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupJpaRepositoryAdapter extends JpaRepository<GroupEntity, Long> {
}