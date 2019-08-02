package com.ampos.restaurant.service.impl;

import com.ampos.restaurant.model.MenuItem;
import com.ampos.restaurant.repository.MenuItemRepository;
import com.ampos.restaurant.service.IMenuItemService;
import com.ampos.restaurant.specification.MenuItemSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemServiceImpl implements IMenuItemService {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Override
    public Page<MenuItem> getMenuPage(PageRequest pageRequest) {
        return menuItemRepository.findAll(pageRequest);
    }

    @Override
    public boolean createItem(MenuItem menuItem) {
        menuItemRepository.save(menuItem);
        return true;
    }

    @Override
    public boolean updateItem(MenuItem menuItem) {
        if (menuItem == null || menuItemRepository.getOne(menuItem.getId()) == null) {
            return false;
        }
        menuItemRepository.save(menuItem);
        return true;
    }

    @Override
    public boolean removeItem(MenuItem menuItem) {
        if (menuItem == null || menuItemRepository.getOne(menuItem.getId()) == null) {
            return false;
        }
        menuItemRepository.delete(menuItem);
        return true;
    }

    @Override
    public List<MenuItem> searchItem(String keyword) {
        return menuItemRepository.findAll(new MenuItemSpecification(keyword));
    }

    @Override
    public int getTotalPrice(String itemName, int quantity) {
        MenuItem menuItem = menuItemRepository.findByName(itemName);
        return menuItem.getPrice()*quantity;
    }
}