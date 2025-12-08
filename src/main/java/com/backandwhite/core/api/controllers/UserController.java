package com.backandwhite.core.api.controllers;

import com.backandwhite.core.api.BaseApi;
import com.backandwhite.core.api.dtos.in.UserDtoIn;
import com.backandwhite.core.api.dtos.out.UserDtoOut;
import com.backandwhite.core.application.usecase.UserUseCase;
import com.backandwhite.core.domain.User;
import com.backandwhite.core.infrastructure.bd.postgres.repository.UserJpaRepositoryAdapter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.backandwhite.core.api.mappers.UserDtoMappers.USER_DTO_MAPPERS;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/users")
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController implements BaseApi<UserDtoIn, UserDtoOut, Long> {

    private final UserUseCase userUseCase;
    private final UserJpaRepositoryAdapter userJpaRepositoryAdapter;

    @Override
    public ResponseEntity<UserDtoOut> create(UserDtoIn input) {
        User user = USER_DTO_MAPPERS.toDomain(input);
        UserDtoOut userDtoOut = USER_DTO_MAPPERS.toDtoOut(userUseCase.create(user));
        return new ResponseEntity<>(userDtoOut, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<UserDtoOut>> findAll(Integer page, Integer size, String sort) {
        List<User> users = userUseCase.findAll(page, size, sort);
        long totalCount = userJpaRepositoryAdapter.findAll().size();
        return ResponseEntity.ok()
                .header("X-total-Count", String.valueOf(totalCount))
                .body(USER_DTO_MAPPERS.toDtoOutList(users));
    }

    @Override
    public ResponseEntity<UserDtoOut> getById(Long id) {
        User user = userUseCase.getById(id);
        return new ResponseEntity<>(USER_DTO_MAPPERS.toDtoOut(user), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDtoOut> update(Long id, UserDtoIn input) {
        User user = userUseCase.update(id, USER_DTO_MAPPERS.toDomain(input));
        return new ResponseEntity<>(USER_DTO_MAPPERS.toDtoOut(user), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        userUseCase.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
