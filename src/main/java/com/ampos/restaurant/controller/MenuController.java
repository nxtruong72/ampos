package com.ampos.restaurant.controller;

import com.ampos.restaurant.exception.ApplicationException;
import com.ampos.restaurant.model.MenuItem;
import com.ampos.restaurant.model.dto.MenuItemDto;
import com.ampos.restaurant.service.impl.MenuItemServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/item")
public class MenuController {
    @Autowired
    private MenuItemServiceImpl menuItemService;

    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping
    public Page<MenuItem> getMenuPage(Pageable pageable) {
        return menuItemService.getMenuPage(pageable);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody MenuItemDto menuItemDto) throws ApplicationException {
        menuItemService.createItem(modelMapper.map(menuItemDto, MenuItem.class));
        return ResponseEntity.status(HttpStatus.OK).body("Successfully create item");
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody MenuItemDto menuItemDto) throws ApplicationException {
        menuItemService.updateItem(modelMapper.map(menuItemDto, MenuItem.class));
        return ResponseEntity.status(HttpStatus.OK).body("Successfully upate item");
    }

    @DeleteMapping
    public ResponseEntity<String> remove(@RequestBody MenuItemDto menuItemDto) throws ApplicationException {
        menuItemService.removeItem(modelMapper.map(menuItemDto, MenuItem.class));
        return ResponseEntity.status(HttpStatus.OK).body("Successfully delete item");
    }

    @GetMapping(path = "/search")
    public List<MenuItemDto> searchItem(@RequestParam String keyword) {
        List<MenuItem> items = menuItemService.searchItem(keyword);
        return items.stream()
                .map(item -> modelMapper.map(item, MenuItemDto.class))
                .collect(Collectors.toList());
    }
}