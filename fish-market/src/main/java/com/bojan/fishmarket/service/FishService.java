package com.bojan.fishmarket.service;

import com.bojan.fishmarket.dto.FishDTO;
import com.bojan.fishmarket.entity.Fish;
import com.bojan.fishmarket.entity.FishMarket;

import java.util.List;

public interface FishService {
    List<Fish> getAllFishes();
    Fish findOneFish(Long id);
    List<Fish> getFishesBySort(String sort);
    FishDTO addFish(FishDTO fishDTO);
    FishDTO updateFish(Long fishId, FishDTO updatedFish);
    void removeFish(Long fishId);
    List<Fish> getAllByParameters(int min, int max);
}
