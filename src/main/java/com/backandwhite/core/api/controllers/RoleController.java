package com.backandwhite.core.api.controllers;

import com.backandwhite.core.api.RoleApi;
import com.backandwhite.core.api.dtos.in.RoleDtoIn;
import com.backandwhite.core.api.dtos.out.RoleDtoOut;
import com.backandwhite.core.api.mappers.RoleDtoInMapper;
import com.backandwhite.core.application.usecase.RoleUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/roles")
@Tag(name = "Role Management.", description = "APIs for managing roles.")
public class RoleController implements RoleApi {

    private final RoleDtoInMapper mapper;
    private final RoleUseCase roleUseCase;

    @Override
    public ResponseEntity<RoleDtoOut> create(RoleDtoIn input) {
        return new ResponseEntity<>(mapper.toDto(roleUseCase.create(mapper.toDomain(input))), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<RoleDtoOut>> findAll(Integer page, Integer size, String sort) {
        return new ResponseEntity<>(mapper.toDtoList(roleUseCase.findAll(page, size, sort)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RoleDtoOut> getById(Long id) {
        return new ResponseEntity<>(mapper.toDto(roleUseCase.getById(id)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RoleDtoOut> update(Long id, RoleDtoIn input) {
        return new ResponseEntity<>(mapper.toDto(roleUseCase.update(id, mapper.toDomain(input))), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        roleUseCase.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
