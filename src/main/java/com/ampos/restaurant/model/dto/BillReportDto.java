package com.ampos.restaurant.model.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BillReportDto {
    Set<BillItem> items = new HashSet<>();
    int grandTotalPrice = 0;

    public BillReportDto() {
    }

    public BillReportDto(String itemName, int quantity, int price) {
        addItem(itemName, quantity, price);
    }

    public void addItem(String itemName, int quantity, int price) {
        BillItem billItem = new BillItem(itemName, quantity, price);
        if (!items.contains(billItem)) {
            items.add(billItem);
            grandTotalPrice += price;
        } else {
            items.forEach(e -> {
                if (e.equals(billItem)) {
                    billItem.update(e.getQuantity(), e.getTotalPrice());
                    grandTotalPrice -= e.getTotalPrice();
                    return;
                }
            });
            items.remove(billItem);
            items.add(billItem);
            grandTotalPrice += billItem.getTotalPrice();
        }
    }

    @Override
    public String toString() {
        return items.toString() + " - " + grandTotalPrice;
    }

    public Set<BillItem> getItems() {
        return items;
    }

    public void setItems(Set<BillItem> items) {
        this.items = items;
    }

    public int getGrandTotalPrice() {
        return grandTotalPrice;
    }

    public void setGrandTotalPrice(int grandTotalPrice) {
        this.grandTotalPrice = grandTotalPrice;
    }
}

class BillItem {
    private String itemName;
    private int quantity;
    private int totalPrice;

    public BillItem(String itemName, int quantity, int totalPrice) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public void update(int quantity, int totalPrice) {
        this.quantity += quantity;
        this.totalPrice += totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillItem billItem = (BillItem) o;
        return itemName.equals(billItem.itemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName);
    }

    @Override
    public String toString() {
        return "[" + itemName + ", " + quantity + ", " + totalPrice + "]";
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}