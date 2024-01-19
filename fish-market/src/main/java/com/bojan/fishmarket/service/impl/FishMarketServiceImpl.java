package com.bojan.fishmarket.service.impl;

import com.bojan.fishmarket.dto.FishMarketAgeAvgFishPriceDTO;
import com.bojan.fishmarket.dto.FishMarketDTO;
import com.bojan.fishmarket.dto.FishMarketNameFishNumberTotalQuantityDTO;
import com.bojan.fishmarket.entity.Fish;
import com.bojan.fishmarket.entity.FishMarket;
import com.bojan.fishmarket.exception.ResourceNotFoundException;
import com.bojan.fishmarket.mapper.FishMarketMapper;
import com.bojan.fishmarket.repository.FishMarketRepository;
import com.bojan.fishmarket.repository.FishRepository;
import com.bojan.fishmarket.service.FishMarketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FishMarketServiceImpl implements FishMarketService {

    public FishMarketRepository fishMarketRepository;
    public FishRepository fishRepository;

    @Override
    public List<FishMarket> getAllFishMarkets() {
        return fishMarketRepository.findAllByOrderByNameAsc();

    }

    @Override
    public FishMarket findOneFishMarket(Long id) {
        return fishMarketRepository.findOneById(id);
    }

    public List<FishMarketAgeAvgFishPriceDTO> getFishMarketInfo(double bound) {

        List<Fish> fishesData = fishRepository.findAll();
        List<FishMarket> fishMarketsData = fishMarketRepository.findAll();

        return fishesData.stream()
                .collect(Collectors.groupingBy(r -> r.getFishMarket().getId()))
                .entrySet().stream()
                .map(entry -> {
                    FishMarket fishMarket = fishMarketsData.stream()
                            .filter(r -> r.getId().equals(entry.getKey()))
                            .findFirst().orElse(null);

                    if (fishMarket != null) {
                        FishMarketAgeAvgFishPriceDTO dto = new FishMarketAgeAvgFishPriceDTO();
                        dto.setFishMarket(fishMarket.getName());
                        dto.setFishMarketAge(Year.now().getValue() - fishMarket.getYearOfOpening());
                        dto.setAvgFishPrice(entry.getValue().stream().mapToDouble(fish -> fish.getPrice()).average().orElse(0.0));

                        return dto;
                    } else {
                        return null;
                    }
                })
                .filter(dto -> dto != null && dto.getAvgFishPrice() < bound)
                .sorted((a, b) -> b.getFishMarket().compareTo(a.getFishMarket()))
                .collect(Collectors.toList());
    }

    @Override
    public List<FishMarketNameFishNumberTotalQuantityDTO> getFishmarketStatus() {
        List<Fish> fishesData = fishRepository.findAll();
        List<FishMarket> fishMarketsData = fishMarketRepository.findAll();

        return fishesData.stream()
                .collect(Collectors.groupingBy(r -> r.getFishMarket().getId()))
                .entrySet().stream()
                .map(entry -> {
                    FishMarket fishMarket = fishMarketsData.stream()
                            .filter(r -> r.getId().equals(entry.getKey()))
                            .findFirst().orElse(null);

                    if (fishMarket != null) {
                        FishMarketNameFishNumberTotalQuantityDTO dto = new FishMarketNameFishNumberTotalQuantityDTO();
                        dto.setFishMarket(fishMarket.getName());
                        dto.setDifferentFishesNumber(entry.getValue().stream().map(riba -> riba.getSort()).distinct().count());
                        dto.setTotalAvailableQuantity(entry.getValue().stream().mapToInt(riba -> riba.getAvailableQuantity()).sum());
                        return dto;
                    } else {
                        return null;
                    }
                })
                .sorted((a, b) -> Long.compare(b.getDifferentFishesNumber(), a.getDifferentFishesNumber()))
                .collect(Collectors.toList());
    }

    @Override
    public List<FishMarket> getFishMarketsByName(String name) {
        return fishMarketRepository.findByNameContainingOrderByYearOfOpeningAscNameDesc(name);
    }
}








