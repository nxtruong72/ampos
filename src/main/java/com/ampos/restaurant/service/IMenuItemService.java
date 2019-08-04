package com.ampos.restaurant.service;

import java.util.List;

import com.ampos.restaurant.exception.ApplicationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ampos.restaurant.model.MenuItem;

public interface IMenuItemService {
    /**
     * Get menu item with page request info
     * @param pageRequest
     * @return
     */
    public Page<MenuItem> getMenuPage(Pageable pageRequest) throws ApplicationException;

    /**
     * Create the new item
     *
     * @param menuItem
     * @return
     */
    public void createItem(MenuItem menuItem) throws ApplicationException;

    /**
     * Update the item if it is exist, or else return false
     *
     * @param menuItem
     */
    public void updateItem(MenuItem menuItem) throws ApplicationException;

    /**
     * Find and remove the item
     *
     * @param menuItem
     * @return
     */
    public void removeItem(MenuItem menuItem) throws ApplicationException;

    /**
     * Search item based on the keyword that has appear in the title, description or additional details
     *
     * @param keyword
     * @return
     */
    public List<MenuItem> searchItem(String keyword) throws ApplicationException;

    /**
     * Get price of this bill base on the menu item name and its quantity
     * @param itemName
     * @param quantity
     * @return
     */
    public int getTotalPrice(String itemName, int quantity) throws ApplicationException;
}
