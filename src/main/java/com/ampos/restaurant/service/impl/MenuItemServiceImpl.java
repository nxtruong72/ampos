package com.ampos.restaurant.service.impl;

import com.ampos.restaurant.exception.ApplicationException;
import com.ampos.restaurant.model.MenuItem;
import com.ampos.restaurant.repository.MenuItemRepository;
import com.ampos.restaurant.service.IMenuItemService;
import com.ampos.restaurant.specification.MenuItemSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemServiceImpl implements IMenuItemService {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Override
    public Page<MenuItem> getMenuPage(Pageable pageRequest) {
        return menuItemRepository.findAll(pageRequest);
    }

    @Override
    public void createItem(MenuItem menuItem) throws ApplicationException {
        if (menuItem == null || !menuItemRepository.findByName(menuItem.getName()).isEmpty())
            throw new ApplicationException("Cannot create menu item");
        menuItemRepository.save(menuItem);
    }

    @Override
    public void updateItem(MenuItem menuItem) throws ApplicationException {
        List<MenuItem> menuItems = menuItemRepository.findByName(menuItem.getName());
        MenuItem updateItem;

        if (menuItem == null || menuItems.isEmpty()) {
            throw new ApplicationException("Cannot update menu item");
        }
        updateItem = menuItemRepository.findById(menuItems.get(0).getId()).orElse(new MenuItem());
        updateItem.setName(menuItem.getName());
        updateItem.setImageName(menuItem.getImageName());
        updateItem.setDescription(menuItem.getDescription());
        updateItem.setAdditionalDetails(menuItem.getAdditionalDetails());
        updateItem.setPrice(menuItem.getPrice());
        menuItemRepository.save(updateItem);
    }

    @Override
    public void removeItem(MenuItem menuItem) throws ApplicationException {
        if (menuItem == null || menuItemRepository.findByName(menuItem.getName()).isEmpty()) {
            throw new ApplicationException("Cannot remove menu item");
        }
        menuItemRepository.deleteByName(menuItem.getName());
    }

    @Override
    public List<MenuItem> searchItem(String keyword) {
        return menuItemRepository.findAll(new MenuItemSpecification(keyword));
    }

    @Override
    public int getTotalPrice(String itemName, int quantity) throws ApplicationException {
        List<MenuItem> menuItems = menuItemRepository.findByName(itemName);
        if (menuItems.isEmpty())
            throw new ApplicationException("Cannot find menu item: " + itemName);
        return menuItems.get(0).getPrice() * quantity;
    }
}