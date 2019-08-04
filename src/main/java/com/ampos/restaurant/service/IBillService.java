package com.ampos.restaurant.service;

import com.ampos.restaurant.exception.ApplicationException;
import com.ampos.restaurant.model.BillOrder;

import java.util.List;

public interface IBillService {
    /**
     * Get all bill orders
     *
     * @return
     */
    public List<BillOrder> getAll();

    /**
     * Create new bill order
     *
     * @param billOrder
     * @return
     */
    public void createBill(BillOrder billOrder) throws ApplicationException;

    /**
     * Update a bill order
     *
     * @param billOrder
     * @return
     */
    public void updateBill(BillOrder billOrder) throws ApplicationException;

    /**
     * Search a bill order with bill id
     *
     * @param billId
     * @return
     */
    public List<BillOrder> searchBill(int billId) throws ApplicationException;
}