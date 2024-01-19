package com.bojan.fishmarket.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.NotBlank;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FishDTO {

    public Long id;
    @NotBlank
    @Size(min = 2, max = 30)
    public String sort;
    @NotBlank
    @Size(min = 3, max = 120)
    public String placeOfCatch;
    @NotNull
    @DecimalMin(value = "100")
    @DecimalMax(value = "10000")
    public double price;
    @NotNull
    @Min(value = 1)
    @Max(value = 1000)
    public int availableQuantity;
    public String fishMarketName;
    public int fishMarketYearOfOpening;
    public Long fishMarketId;










    /*public Long id;
    public String sort;
    public String placeOfCatch;
    public double price;
    public int availableQuantity;
    public String fishMarketName;
    public int fishMarketYearOfOpening;
    public Long fishMarketId;*/

}
