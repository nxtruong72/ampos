package com.ampos.restaurant.specification;

import com.ampos.restaurant.model.MenuItem;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class MenuItemSpecification implements Specification<MenuItem> {
    private String keyword;

    public MenuItemSpecification(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public Predicate toPredicate(Root<MenuItem> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.or(criteriaBuilder.like(root.<String>get("name"), "%" + keyword + "%"),
                criteriaBuilder.like(root.<String>get("description"), "%" + keyword + "%"),
                criteriaBuilder.like(root.<String>get("additionalDetails"), "%" + keyword + "%"));
    }
}
