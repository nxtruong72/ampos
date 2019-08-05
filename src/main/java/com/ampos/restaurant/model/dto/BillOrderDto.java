package com.ampos.restaurant.model.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class BillOrderDto {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
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
        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        return dateFormat.parse(this.orderedTime);
    }

    public void setOrderedTime(Date date, String timezone) {
        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        this.orderedTime = dateFormat.format(date);
    }

    public static SimpleDateFormat getDateFormat() {
        return dateFormat;
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