package com.ampos.restaurant.repository;

import com.ampos.restaurant.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer>, JpaSpecificationExecutor<MenuItem> {
    public MenuItem findByName(String name);
}
