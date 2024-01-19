package com.bojan.fishmarket.mapper;

import com.bojan.fishmarket.dto.FishMarketDTO;
import com.bojan.fishmarket.entity.FishMarket;

public class FishMarketMapper {
    public static FishMarketDTO mapToFishMarketDto(FishMarket fishMarket){
        return new FishMarketDTO(
                fishMarket.getId(),
                fishMarket.getName(),
                fishMarket.getYearOfOpening()
        );
    }

    public static FishMarket maptoFishMarket(FishMarketDTO fishMarketDTO){
        return new FishMarket(
                fishMarketDTO.getId(),
                fishMarketDTO.getName(),
                fishMarketDTO.getYearOfOpening()
        );
    }
}
