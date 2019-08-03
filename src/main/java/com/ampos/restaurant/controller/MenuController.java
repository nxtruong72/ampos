package com.ampos.restaurant.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ampos.restaurant.model.MenuItem;
import com.ampos.restaurant.model.dto.MenuItemDto;
import com.ampos.restaurant.service.impl.MenuItemServiceImpl;

@RestController
@RequestMapping(path = "/item")
public class MenuController {
    @Autowired
    private MenuItemServiceImpl menuItemService;

    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping(path = "")
    public Page<MenuItem> getMenuPage(Pageable pageable) {
//        ModelAndView modelAndView = new ModelAndView();
//        PageRequest pageRequest = PageRequest.of(page-1, pageSize);
//        Page<MenuItem> menuPage = menuItemService.getMenuPage(pageRequest);
//        int totalPages = menuPage.getTotalPages();
//
//        if (totalPages > 0) {
//            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
//            modelAndView.addObject("pageNumbers", pageNumbers);
//        }
//        modelAndView.addObject("menuItemList", menuPage.getContent());
//
//        return modelAndView;
    	return menuItemService.getMenuPage(pageable);
    }

    @PostMapping(path = "/")
    public ResponseEntity<String> create(@RequestBody MenuItemDto menuItemDto) {
        if (menuItemService.createItem(modelMapper.map(menuItemDto, MenuItem.class))) {
            return ResponseEntity.status(HttpStatus.OK).body("Successfully create item");
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to create item");
        }
    }

    @PutMapping(path = "/")
    public ResponseEntity<String> update(@RequestBody MenuItemDto menuItemDto) {
        if (menuItemService.updateItem(modelMapper.map(menuItemDto, MenuItem.class))) {
            return ResponseEntity.status(HttpStatus.OK).body("Successfully upate item");
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to update item");
        }
    }

    @DeleteMapping(path = "/")
    public ResponseEntity<String> remove(@RequestBody MenuItemDto menuItemDto) {
        if (menuItemService.removeItem(modelMapper.map(menuItemDto, MenuItem.class))) {
            return ResponseEntity.status(HttpStatus.OK).body("Successfully delete item");
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to delete item");
        }
    }

    @GetMapping(path = "/search")
    public List<MenuItemDto> searchItem(@RequestParam String keyword) {
        List<MenuItem> items = menuItemService.searchItem(keyword);
        return items.stream()
                .map(item -> modelMapper.map(item, MenuItemDto.class))
                .collect(Collectors.toList());
    }
}
