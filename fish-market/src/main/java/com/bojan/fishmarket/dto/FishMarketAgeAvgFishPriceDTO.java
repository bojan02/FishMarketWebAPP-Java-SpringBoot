package com.bojan.fishmarket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FishMarketAgeAvgFishPriceDTO {
    public String fishMarket;
    public int fishMarketAge;
    public double avgFishPrice;
}