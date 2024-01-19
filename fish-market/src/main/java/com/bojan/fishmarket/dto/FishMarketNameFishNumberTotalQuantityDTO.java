package com.bojan.fishmarket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FishMarketNameFishNumberTotalQuantityDTO {
    public String fishMarket;
    public Long differentFishesNumber;
    public int totalAvailableQuantity;
}
