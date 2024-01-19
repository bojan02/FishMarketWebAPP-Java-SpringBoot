package com.bojan.fishmarket.mapper;

import com.bojan.fishmarket.dto.FishDTO;
import com.bojan.fishmarket.entity.Fish;

public class FishMapper {
    public static FishDTO mapToFishDTO(Fish fish){
        return new FishDTO(
                fish.getId(),
                fish.getSort(),
                fish.getPlaceOfCatch(),
                fish.getPrice(),
                fish.getAvailableQuantity(),
                fish.getFishMarket().getName(),
                fish.getFishMarket().getYearOfOpening(),
                fish.getFishMarket().getId()
        );
    }

    public static Fish mapToFish(FishDTO fishDTO){
        Fish fish = new Fish();

        fish.setId(fishDTO.getId());
        fish.setSort(fishDTO.getSort());
        fish.setPlaceOfCatch(fishDTO.getPlaceOfCatch());
        fish.setPrice(fishDTO.getPrice());
        fish.setAvailableQuantity(fishDTO.getAvailableQuantity());
        return fish;
    }
}
