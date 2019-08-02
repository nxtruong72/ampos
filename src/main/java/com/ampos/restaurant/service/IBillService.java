package com.ampos.restaurant.service;

import com.ampos.restaurant.model.BillOrder;

import java.util.ArrayList;
import java.util.List;

public interface IBillService {
    /**
     * Get all bill orders
     * @return
     */
    public List<BillOrder> getAll();

    /**
     * Create new bill order
     * @param billOrder
     * @return
     */
    public boolean createBill(BillOrder billOrder);

    /**
     * Update a bill order
     * @param billOrder
     * @return
     */
    public boolean updateBill(BillOrder billOrder);

    /**
     * Search a bill order with bill id
     * @param billId
     * @return
     */
    public List<BillOrder> searchBill(int billId);
}