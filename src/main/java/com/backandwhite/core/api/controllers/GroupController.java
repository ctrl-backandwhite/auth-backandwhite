package com.backandwhite.core.api.controllers;

import com.backandwhite.core.api.GroupApi;
import com.backandwhite.core.api.dtos.in.GroupDtoIn;
import com.backandwhite.core.api.dtos.out.GroupDtoOut;
import com.backandwhite.core.api.mappers.GroupDtoInMapper;
import com.backandwhite.core.application.usecase.GroupUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/groups")
@Tag(name = "Group Management", description = "APIs for managing group.")
public class GroupController implements GroupApi {

    private final GroupDtoInMapper mapper;
    private final GroupUseCase groupUseCase;

    @Override
    public ResponseEntity<GroupDtoOut> create(GroupDtoIn input) {
        return new ResponseEntity<>(mapper.toDto(groupUseCase.create(mapper.toDomain(input))), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<GroupDtoOut>> findAll(Integer page, Integer size, String sort) {
        return new ResponseEntity<>(mapper.toDtoList(groupUseCase.findAll(page, size, sort)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GroupDtoOut> getById(Long id) {
        return new ResponseEntity<>(mapper.toDto(groupUseCase.getById(id)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GroupDtoOut> update(Long id, GroupDtoIn input) {
        return new ResponseEntity<>(mapper.toDto(groupUseCase.update(id, mapper.toDomain(input))), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        groupUseCase.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
