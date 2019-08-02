package com.ampos.restaurant.controller;

import com.ampos.restaurant.model.MenuItem;
import com.ampos.restaurant.service.impl.IMenuItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping(path = "/item")
public class MenuController {
    @Autowired
    private IMenuItemServiceImpl menuItemService;

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getMenuPage(@RequestParam int page, @RequestParam int pageSize) {
        ModelAndView modelAndView = new ModelAndView();
        PageRequest pageRequest = PageRequest.of(page-1, pageSize);
        Page<MenuItem> menuPage = menuItemService.getMenuPage(pageRequest);
        int totalPages = menuPage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("menuItemList", menuPage.getContent());

        return modelAndView;
    }

    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody MenuItem menuItem) {
        if (menuItemService.createItem(menuItem)) {
            return ResponseEntity.status(HttpStatus.OK).body("Successfully create item");
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to create item");
        }
    }

    @PutMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody MenuItem menuItem) {
        if (menuItemService.updateItem(menuItem)) {
            return ResponseEntity.status(HttpStatus.OK).body("Successfully upate item");
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to update item");
        }
    }

    @DeleteMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> remove(@RequestBody MenuItem menuItem) {
        if (menuItemService.removeItem(menuItem)) {
            return ResponseEntity.status(HttpStatus.OK).body("Successfully delete item");
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to delete item");
        }
    }

    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MenuItem>> searchItem(@RequestParam String keyword) {
        List<MenuItem> item = menuItemService.searchItem(keyword);
        if (item == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(item);
        }
    }
}
