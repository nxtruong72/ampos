package com.ampos.restaurant.service.impl;

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
    public boolean createBill(BillOrder billOrder) {
        if (billOrder == null || billOrderRepository.getOne(billOrder.getId()) != null)
            return false;
        billOrderRepository.save(billOrder);
        return true;
    }

    @Override
    public boolean updateBill(BillOrder billOrder) {
        if (billOrder == null || billOrderRepository.getOne(billOrder.getId()) == null)
            return false;
        billOrderRepository.save(billOrder);
        return true;
    }

    @Override
    public List<BillOrder> searchBill(int billId) {
        return billOrderRepository.findAll(new BillOrderSpecification(billId));
    }
}
