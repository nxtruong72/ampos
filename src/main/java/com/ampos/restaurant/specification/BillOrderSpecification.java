package com.ampos.restaurant.specification;

import com.ampos.restaurant.model.BillOrder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Scanner;

public class BillOrderSpecification implements Specification<BillOrder> {
    private int billId;

    public BillOrderSpecification(int billId) {
        this.billId = billId;
    }

    @Override
    public Predicate toPredicate(Root<BillOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.<String>get("id").get("id"), billId);
    }
}
