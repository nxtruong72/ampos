package com.ampos.restaurant.service.impl;

import com.ampos.restaurant.exception.ApplicationException;
import com.ampos.restaurant.model.BillOrder;
import com.ampos.restaurant.repository.BillOrderRepository;
import com.ampos.restaurant.service.IBillService;
import com.ampos.restaurant.specification.BillOrderSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return billOrderRepository.findAll(new BillOrderSpecification(billId));
    }
}
