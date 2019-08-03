package com.ampos.restaurant.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ampos.restaurant.model.MenuItem;

public interface IMenuItemService {
    /**
     * Get menu item with page request info
     * @param pageRequest
     * @return
     */
    public Page<MenuItem> getMenuPage(Pageable pageRequest);

    /**
     * Create the new item
     *
     * @param menuItem
     * @return
     */
    public boolean createItem(MenuItem menuItem);

    /**
     * Update the item if it is exist, or else return false
     *
     * @param menuItem
     * @return
     */
    public boolean updateItem(MenuItem menuItem);

    /**
     * Find and remove the item
     *
     * @param menuItem
     * @return
     */
    public boolean removeItem(MenuItem menuItem);

    /**
     * Search item based on the keyword that has appear in the title, description or additional details
     *
     * @param keyword
     * @return
     */
    public List<MenuItem> searchItem(String keyword);

    /**
     * Get price of this bill base on the menu item name and its quantity
     * @param itemName
     * @param quantity
     * @return
     */
    public int getTotalPrice(String itemName, int quantity);
}
