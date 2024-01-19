package com.bojan.fishmarket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FishMarketDTO {
    public Long id;
    public String name;
    public int yearOfOpening;
}
