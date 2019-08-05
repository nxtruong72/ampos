package com.ampos.restaurant.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ampos.restaurant.exception.ApplicationException;
import com.ampos.restaurant.model.BillOrder;
import com.ampos.restaurant.repository.BillOrderRepository;
import com.ampos.restaurant.service.IBillService;

@Service
public class BillServiceImpl implements IBillService {
    @Autowired
    private BillOrderRepository billOrderRepository;

    @Override
    public List<BillOrder> getAll() {
        return billOrderRepository.findAll();
    }

    @Override
    public void createBill(BillOrder billOrder) throws ApplicationException {
        if (billOrder == null || billOrderRepository.existsById(billOrder.getId()))
            throw new ApplicationException("Cannot create bill");
        billOrderRepository.save(billOrder);
    }

    @Override
    public void updateBill(BillOrder billOrder) throws ApplicationException {
        if (billOrder == null || !billOrderRepository.existsById(billOrder.getId()))
            throw new ApplicationException("Cannot update bill");
        billOrderRepository.save(billOrder);
    }

    @Override
    public List<BillOrder> searchBill(int billId) {
    	return billOrderRepository.findById_Id(billId);
       // return billOrderRepository.findAll(new BillOrderSpecification(billId));
    }
}
