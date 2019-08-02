package com.ampos.restaurant.controller;

import com.ampos.restaurant.model.BillOrder;
import com.ampos.restaurant.model.dto.BillOrderDto;
import com.ampos.restaurant.service.IBillService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/bill")
public class BillOrderController {
    private static final String TIME_ZONE = "GMT+7:00";

    @Autowired
    private IBillService billService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(path = "/")
    public List<BillOrderDto> getAll() {
        List<BillOrder> billOrders = billService.getAll();
        return billOrders.stream().map(bill -> convertToDto(bill)).collect(Collectors.toList());
    }

    @GetMapping(path = "/")
    public List<BillOrderDto> getBillOrder(@RequestParam int billId) {
        List<BillOrder> billOrders = billService.searchBill(billId);
        return billOrders.stream().map(bill -> convertToDto(bill)).collect(Collectors.toList());
    }

    @PostMapping(path = "/")
    public ResponseEntity<String> createBill(@RequestBody BillOrderDto billOrderDto) {
        try {
            BillOrder billOrder = convertToEntity(billOrderDto);
            if (billService.createBill(billOrder)) {
                return ResponseEntity.status(HttpStatus.OK).body("Successfully create bill");
            } else {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to create bill");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to create bill");
        }
    }

    @PutMapping(path = "/")
    public ResponseEntity<String> updateBill(@RequestBody BillOrderDto billOrderDto) {
        try {
            BillOrder billOrder = convertToEntity(billOrderDto);
            if (billService.createBill(billOrder)) {
                return ResponseEntity.status(HttpStatus.OK).body("Successfully update bill");
            } else {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to update bill");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to update bill");
        }
    }

    private BillOrderDto convertToDto(BillOrder billOrder) {
        BillOrderDto billOrderDto = modelMapper.map(billOrder, BillOrderDto.class);

        billOrderDto.setOrderedTime(billOrder.getId().getOrderedTime(), TIME_ZONE);

        return billOrderDto;
    }

    private BillOrder convertToEntity(BillOrderDto billOrderDto) throws ParseException {
        BillOrder billOrder = modelMapper.map(billOrderDto, BillOrder.class);

        billOrder.getId().setOrderedTime(billOrderDto.getOrderedTimeCoverted(TIME_ZONE));

        return billOrder;
    }
}
