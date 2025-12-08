package com.backandwhite.core.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface BaseApi<I, O, K> {

    @PostMapping
    default ResponseEntity<O> create(@Validated @RequestBody I input) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping
    default ResponseEntity<List<O>> findAll(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "9") Integer size,
            @RequestParam(name = "sort", defaultValue = "propertie:desc") String sort) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/{id}")
    default ResponseEntity<O> getById(@PathVariable K id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PutMapping("/{id}")
    default ResponseEntity<O> update(@PathVariable K id, @Validated @RequestBody I input) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @DeleteMapping("/{id}")
    default ResponseEntity<Void> delete(@PathVariable K id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
