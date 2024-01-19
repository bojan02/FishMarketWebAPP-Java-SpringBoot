package com.bojan.fishmarket.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name="fishes")
public class Fish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fishMarketId")
    public FishMarket fishMarket;
}
