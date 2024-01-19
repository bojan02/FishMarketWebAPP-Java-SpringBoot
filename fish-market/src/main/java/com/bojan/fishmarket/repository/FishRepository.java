package com.bojan.fishmarket.repository;

import com.bojan.fishmarket.entity.Fish;
import com.bojan.fishmarket.entity.FishMarket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FishRepository extends JpaRepository<Fish, Long> {
    List<Fish> findAllByOrderBySortAsc();
    Fish findOneById (Long id);
    List<Fish> findAllBySortOrderByPrice(String sort);
    List<Fish> findByAvailableQuantityBetweenOrderByPriceDesc(int min, int max);
}
