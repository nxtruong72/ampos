package com.ampos.restaurant.repository;

import com.ampos.restaurant.model.BillId;
import com.ampos.restaurant.model.BillOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BillOrderRepository extends JpaRepository<BillOrder, BillId>, JpaSpecificationExecutor<BillOrder> {

}
