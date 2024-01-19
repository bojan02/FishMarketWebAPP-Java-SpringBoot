package com.bojan.fishmarket.service;

import com.bojan.fishmarket.dto.FishMarketAgeAvgFishPriceDTO;
import com.bojan.fishmarket.dto.FishMarketDTO;
import com.bojan.fishmarket.dto.FishMarketNameFishNumberTotalQuantityDTO;
import com.bojan.fishmarket.entity.FishMarket;

import java.util.List;

public interface FishMarketService {
    List<FishMarket> getAllFishMarkets();
    FishMarket findOneFishMarket(Long id);
    List<FishMarketAgeAvgFishPriceDTO> getFishMarketInfo(double bound);
    List<FishMarketNameFishNumberTotalQuantityDTO> getFishmarketStatus();
    List<FishMarket> getFishMarketsByName(String name);
}
