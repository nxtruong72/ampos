package com.ampos.restaurant.model;

import java.io.Serializable;
import java.util.Date;

public class BillId implements Serializable {
    private Integer id;
    private String itemName;
    private Date orderedTime;

    public BillId() {
    }

    public BillId(Integer id, String itemName, Date orderedTime) {
        this.id = id;
        this.itemName = itemName;
        this.orderedTime = orderedTime;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result +
                ((itemName == null) ? 0 : itemName.hashCode()) +
                ((orderedTime == null) ? 0 : orderedTime.hashCode());
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        BillId other = (BillId) obj;
        // check for itemName
        if (itemName == null && other.itemName != null) {
            return false;
        } else if (!itemName.equals(other.itemName)) {
            return false;
        }
        // Check for orderedTime
        if (orderedTime == null && other.orderedTime != null) {
            return false;
        } else if (!orderedTime.equals(other.orderedTime)) {
            return false;
        }
        // check for id
        if (id != other.id)
            return false;
        return true;
    }

    public Integer getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public Date getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(Date orderedTime) {
        this.orderedTime = orderedTime;
    }
}