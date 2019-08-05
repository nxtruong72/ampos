package com.ampos.restaurant.model.dto;

import com.ampos.restaurant.util.DateUtil;

import java.text.ParseException;
import java.util.Date;

public class BillOrderDto {
    private Integer id;
    private String itemName;
    private String orderedTime;
    private Integer quantity;

    public BillOrderDto() {
		super();
	}

	public BillOrderDto(Integer id, String itemName, String orderedTime, Integer quantity) {
		super();
		this.id = id;
		this.itemName = itemName;
		this.orderedTime = orderedTime;
		this.quantity = quantity;
	}

	public Date getOrderedTimeCoverted(String timezone) throws ParseException {
        return DateUtil.convertStringToDate(this.orderedTime, timezone);
    }

    public void setOrderedTime(Date date, String timezone) {
        this.orderedTime = DateUtil.convertDateToString(date, timezone);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(String orderedTime) {
        this.orderedTime = orderedTime;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}