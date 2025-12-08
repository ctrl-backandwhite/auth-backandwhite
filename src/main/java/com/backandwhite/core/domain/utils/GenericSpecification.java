package com.backandwhite.core.domain.utils;

import com.backandwhite.core.api.dtos.in.SearchCriteria;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class GenericSpecification<T> implements Specification<T> {

    private final List<SearchCriteria> criteriaList;

    public GenericSpecification(List<SearchCriteria> criteriaList) {
        this.criteriaList = criteriaList;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteriaList == null || criteriaList.isEmpty()) {
            return builder.conjunction();
        }

        List<Predicate> predicates = List.of(criteriaList.stream()
                .map(criteria -> {
                    try {
                        return getPredicate(criteria, root, builder);
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Criterio de búsqueda inválido: " + criteria.getKey(), e);
                    }
                })
                .toArray(Predicate[]::new));

        return builder.and(predicates);
    }

    private Predicate getPredicate(SearchCriteria criteria, Root<T> root, CriteriaBuilder builder) {
        Path<?> path = root.get(criteria.getKey());
        String operation = criteria.getOperation().toLowerCase();
        Object value = criteria.getValue();

        return switch (operation) {
            case "eq" -> builder.equal(path, value); // Igual a
            case "ne" -> builder.notEqual(path, value); // No igual a
            case "gt" -> builder.greaterThan(path.as(String.class), value.toString()); // Mayor que (adaptar tipo si no es String)
            case "lt" -> builder.lessThan(path.as(String.class), value.toString()); // Menor que (adaptar tipo si no es String)
            case "like" -> builder.like(builder.lower(path.as(String.class)), "%" + value.toString().toLowerCase() + "%"); // Contiene (case-insensitive)
            default -> throw new UnsupportedOperationException("Operación de búsqueda no soportada: " + operation);
        };
    }
}