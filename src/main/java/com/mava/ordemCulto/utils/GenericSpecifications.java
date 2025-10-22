package com.mava.ordemCulto.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenericSpecifications {

    public static <T> Specification<T> buildFilter(Map<String, Object> filters, Map<String, String> fieldTypes) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, Object> filter : filters.entrySet()) {
                String field = filter.getKey();
                Object value = filter.getValue();

                if (value == null || (value instanceof String && ((String) value).isBlank())) {
                    continue;
                }

                String fieldTypeRaw = fieldTypes.getOrDefault(field, "string");
                String[] typeAndField = fieldTypeRaw.split(":");
                String fieldType = typeAndField[0];
                String realField = typeAndField.length > 1 ? typeAndField[1] : field;

                // SUPORTE PARA RELACIONAMENTOS ANINHADOS (objeto.id)
                Path<?> path = root;
                String[] fieldParts = realField.split("\\.");
                for (String part : fieldParts) {
                    path = path.get(part);
                }

                switch (fieldType.toLowerCase()) {
                    case "string":
                        predicates.add(cb.like(cb.lower(path.as(String.class)), "%" + value.toString().toLowerCase() + "%"));
                        break;
                    case "long":
                    case "integer":
                        predicates.add(cb.equal(path, value));
                        break;
                    case "enum":
                    case "exact":
                        predicates.add(cb.equal(path, value));
                        break;
                    case "datestart":
                        predicates.add(cb.greaterThanOrEqualTo(path.as(LocalDate.class), (LocalDate) value));
                        break;
                    case "dateend":
                        predicates.add(cb.lessThanOrEqualTo(path.as(LocalDate.class), (LocalDate) value));
                        break;
                    case "long_range":
                        predicates.add(cb.between(path.as(Long.class), (Long) ((Object[]) value)[0], (Long) ((Object[]) value)[1]));
                        break;
                    default:
                        throw new IllegalArgumentException("Tipo de campo não suportado: " + fieldType);
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

