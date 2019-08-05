package com.ampos.restaurant.controller;

import com.ampos.restaurant.exception.ApplicationException;
import com.ampos.restaurant.model.BillId;
import com.ampos.restaurant.model.BillOrder;
import com.ampos.restaurant.model.dto.BillOrderDto;
import com.ampos.restaurant.model.dto.BillReportDto;
import com.ampos.restaurant.service.IBillService;
import com.ampos.restaurant.service.IMenuItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(path = "/bill")
public class BillOrderController {
    // will use real user's time zone for the real product
    private static final String TIME_ZONE = "GMT+7:00";

    @Autowired
    private IBillService billService;

    @Autowired
    private IMenuItemService menuItemService;

    private ModelMapper modelMapper = new ModelMapper();

    /**
     * Get all bill orders
     *
     * @return
     */
    @GetMapping(path = "/all")
    public BillReportDto getAll() throws ApplicationException {
        List<BillOrder> billOrders = billService.getAll();
        return convertToReportDto(billOrders);
    }

    /**
     * Get the bill order by bill id
     *
     * @param billId
     * @return
     */
    @GetMapping
    public BillReportDto getBillOrder(@RequestParam int billId) throws ApplicationException {
        List<BillOrder> billOrders = billService.searchBill(billId);
        return convertToReportDto(billOrders);
    }

    /**
     * Create a new bill order
     *
     * @param billOrderDto
     * @return
     */
    @PostMapping
    public ResponseEntity<String> createBill(@RequestBody BillOrderDto billOrderDto) throws ApplicationException, ParseException {
        BillOrder billOrder = convertToEntity(billOrderDto);
        billService.createBill(billOrder);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully create bill");
    }

    /**
     * Update info of a bill order
     *
     * @param billOrderDto
     * @return
     */
    @PutMapping
    public ResponseEntity<String> updateBill(@RequestBody BillOrderDto billOrderDto) throws ApplicationException {
        try {
            BillOrder billOrder = convertToEntity(billOrderDto);
            billService.updateBill(billOrder);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully update bill");
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to convert bill to entity");
        }
    }

    /**
     * Calculate the total price and then convert to BillReportDto
     *
     * @param billOrders
     * @return
     */
    private BillReportDto convertToReportDto(List<BillOrder> billOrders) throws ApplicationException {
        BillReportDto billReportDto = new BillReportDto();

        billOrders.forEach(e -> {
            try {
                String itemName = e.getId().getItemName();
                int quantity = e.getQuantity();
                int totalPrice = menuItemService.getTotalPrice(itemName, quantity);

                billReportDto.addItem(itemName, quantity, totalPrice);
            } catch (ApplicationException ex) {
                // do nothing
            }
        });

        return billReportDto;
    }

    /**
     * Convert BillOderDto to BillOrder
     *
     * @param billOrderDto
     * @return
     * @throws ParseException
     */
    private BillOrder convertToEntity(BillOrderDto billOrderDto) throws ParseException {
        BillOrder billOrder = new BillOrder();
        BillId billId = new BillId(billOrderDto.getId(), billOrderDto.getItemName(), billOrderDto.getOrderedTimeCoverted(TIME_ZONE));

        billOrder.setId(billId);
        billOrder.setQuantity(billOrderDto.getQuantity());

        return billOrder;
    }
}
