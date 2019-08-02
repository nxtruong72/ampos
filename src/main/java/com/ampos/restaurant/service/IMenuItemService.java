package com.ampos.restaurant.service;

import com.ampos.restaurant.model.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IMenuItemService {
    /**
     * Get menu item with page request info
     * @param pageRequest
     * @return
     */
    public Page<MenuItem> getMenuPage(PageRequest pageRequest);

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
}
