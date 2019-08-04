package com.ampos.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ampos.restaurant.model.BillId;
import com.ampos.restaurant.model.BillOrder;

@Repository
public interface BillOrderRepository extends JpaRepository<BillOrder, BillId>, JpaSpecificationExecutor<BillOrder> {
    public List<BillOrder> findById_Id(Integer id);
}
