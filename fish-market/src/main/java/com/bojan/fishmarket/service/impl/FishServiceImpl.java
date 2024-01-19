package com.bojan.fishmarket.service.impl;

import com.bojan.fishmarket.dto.FishDTO;
import com.bojan.fishmarket.entity.Fish;
import com.bojan.fishmarket.entity.FishMarket;
import com.bojan.fishmarket.exception.ResourceNotFoundException;
import com.bojan.fishmarket.mapper.FishMapper;
import com.bojan.fishmarket.repository.FishMarketRepository;
import com.bojan.fishmarket.repository.FishRepository;
import com.bojan.fishmarket.service.FishService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FishServiceImpl implements FishService {

    public FishRepository fishRepository;

    public FishMarketRepository fishMarketRepository;

    @Override
    public List<Fish> getAllFishes() {
        return fishRepository.findAllByOrderBySortAsc();
    }

    @Override
    public Fish findOneFish(Long id) {
        return fishRepository.findOneById(id);
    }

    @Override
    public List<Fish> getFishesBySort(String sort) {
        return fishRepository.findAllBySortOrderByPrice(sort);
    }

    @Override
    public FishDTO addFish(FishDTO fishDTO) {
        Fish fish = FishMapper.mapToFish(fishDTO);

        FishMarket fishMarket = fishMarketRepository.findById(fishDTO.getFishMarketId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("FishMarket doesn't exist with id: " + fishDTO.getFishMarketId()));

        fish.setFishMarket(fishMarket);

        Fish savedFish = fishRepository.save(fish);
        return FishMapper.mapToFishDTO(savedFish);
    }

    @Override
    public FishDTO updateFish(Long fishId, FishDTO updatedFish) {

        Fish fish = fishRepository.findById(fishId).orElseThrow(
                () -> new ResourceNotFoundException("Fish doesn't exist with given id: " + fishId)
        );

        fish.setSort(updatedFish.getSort());
        fish.setPlaceOfCatch(updatedFish.getPlaceOfCatch());
        fish.setPrice(updatedFish.getPrice());
        fish.setAvailableQuantity(updatedFish.getAvailableQuantity());


        FishMarket fishMarket = fishMarketRepository.findById(updatedFish.getFishMarketId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Fish market doesn't exist with id: " + updatedFish.getFishMarketId()));

        fish.setFishMarket(fishMarket);

        Fish updatedFishObj = fishRepository.save(fish);

        return FishMapper.mapToFishDTO(updatedFishObj);
    }

    @Override
    public void removeFish(Long fishId) {
        fishRepository.deleteById(fishId);
    }

    @Override
    public List<Fish> getAllByParameters(int min, int max) {
        return fishRepository.findByAvailableQuantityBetweenOrderByPriceDesc(min, max);
    }


}
