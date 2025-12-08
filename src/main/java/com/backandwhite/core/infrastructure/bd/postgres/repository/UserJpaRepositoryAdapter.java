package com.backandwhite.core.infrastructure.bd.postgres.repository;

import com.backandwhite.core.infrastructure.bd.postgres.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepositoryAdapter extends JpaRepository<UserEntity, Long> {
}
