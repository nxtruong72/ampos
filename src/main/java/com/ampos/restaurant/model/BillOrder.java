package com.ampos.restaurant.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bill")
public class BillOrder {
    @EmbeddedId
    private BillId id;
    private Integer quantity;

    public BillId getId() {
        return id;
    }

    public void setId(BillId id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}