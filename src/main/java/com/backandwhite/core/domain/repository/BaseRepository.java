package com.backandwhite.core.domain.repository;

import java.util.List;

public interface BaseRepository<I, O, K> {

    O create(I input);

    List<O> findAll(Integer page, Integer size, String sort);

    O getById(K id);

    default O update(K id, I input) {return null;}

    default O update(I input) {return null;}

    void delete(K id);
}
