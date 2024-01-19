package com.bojan.fishmarket.repository;

import com.bojan.fishmarket.entity.FishMarket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FishMarketRepository extends JpaRepository<FishMarket, Long> {
    List<FishMarket> findAllByOrderByNameAsc();
    FishMarket findOneById (Long id);
    List<FishMarket> findByNameContainingOrderByYearOfOpeningAscNameDesc(String name);
}
