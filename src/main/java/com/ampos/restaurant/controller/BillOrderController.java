package com.ampos.restaurant.controller;

import com.ampos.restaurant.model.BillId;
import com.ampos.restaurant.model.BillOrder;
import com.ampos.restaurant.model.dto.BillOrderDto;
import com.ampos.restaurant.model.dto.BillReportDto;
import com.ampos.restaurant.service.IBillService;
import com.ampos.restaurant.service.IMenuItemService;

import org.modelmapper.Converter;
import org.modelmapper.Converters;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

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
	public List<BillReportDto> getAll() {
		List<BillOrder> billOrders = billService.getAll();
		return billOrders.stream().map(bill -> convertToReportDto(bill)).collect(Collectors.toList());
	}

	/**
	 * Get the bill order by bill id
	 * 
	 * @param billIde
	 * @return
	 */
	@GetMapping(path = "/{id}")
	public List<BillReportDto> getBillOrder(@PathVariable("id") int billId) {
		List<BillOrder> billOrders = billService.searchBill(billId);
		return billOrders.stream().map(bill -> convertToReportDto(bill)).collect(Collectors.toList());
	}

	/**
	 * Create a new bill order
	 * 
	 * @param billOrderDto
	 * @return
	 * @throws ParseException
	 */
	@PostMapping(path = "/")
	public ResponseEntity<String> createBill(@RequestBody BillOrderDto billOrderDto) throws ParseException {
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

	/**
	 * Update info of a bill order
	 * 
	 * @param billOrderDto
	 * @return
	 */
	@PutMapping(path = "/")
	public ResponseEntity<String> updateBill(@RequestBody BillOrderDto billOrderDto) {
		try {
			BillOrder billOrder = convertToEntity(billOrderDto);
			if (billService.updateBill(billOrder)) {
				return ResponseEntity.status(HttpStatus.OK).body("Successfully update bill");
			} else {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to update bill");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to update bill");
		}
	}

	/**
	 * Calculate the total price and then convert to BillReportDto
	 * 
	 * @param billOrder
	 * @return
	 */
	private BillReportDto convertToReportDto(BillOrder billOrder) {
		String itemName = billOrder.getId().getItemName();
		int quantity = billOrder.getQuantity();
		int totalPrice = menuItemService.getTotalPrice(itemName, quantity);

		return new BillReportDto(itemName, quantity, totalPrice);
	}

	/**
	 * Convert BillOderDto to BillOrder
	 * 
	 * @param billOrderDto
	 * @return
	 * @throws ParseException
	 */
	private BillOrder convertToEntity(BillOrderDto billOrderDto) throws ParseException {
		// modelMapper.addMappings(mapperBillIdFromBillOrderDtoToBillOrder());
		// BillOrder billOrder = modelMapper.map(billOrderDto, BillOrder.class);
		BillOrder billOrder = convertBillOrderFromDto(billOrderDto);
		billOrder.getId().setOrderedTime(billOrderDto.getOrderedTimeCoverted(TIME_ZONE));

		return billOrder;
	}

	/*
	 * Convert BillOderDto to BillOrder by manual
	 */
	private BillOrder convertBillOrderFromDto(BillOrderDto billOrderDto) {
		return new BillOrder(new BillId(billOrderDto.getId(), billOrderDto.getItemName()), billOrderDto.getQuantity());
	}

	/*
	 * convert BillId
	 */
	private PropertyMap<BillOrderDto, BillOrder> mapperBillIdFromBillOrderDtoToBillOrder() {
		PropertyMap<BillOrderDto, BillOrder> mapper = new PropertyMap<BillOrderDto, BillOrder>() {

			@Override
			protected void configure() {
				map().setId(new BillId(source.getId(), source.getItemName()));
			}
		};
		return mapper;

	}
}
