package com.bojan.fishmarket.controllers;

import com.bojan.fishmarket.dto.FishMarketDTO;
import com.bojan.fishmarket.dto.FishMarketNameFishNumberTotalQuantityDTO;
import com.bojan.fishmarket.entity.FishMarket;
import com.bojan.fishmarket.exception.ResourceNotFoundException;
import com.bojan.fishmarket.mapper.FishMarketMapper;
import com.bojan.fishmarket.service.FishMarketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("api")
public class FishMarketsController {
    public FishMarketService fishMarketService;

    @GetMapping("/fishmarkets")
    public ResponseEntity<List<FishMarketDTO>> getAllFishMarkets(){
        List<FishMarket> fishMarkets = fishMarketService.getAllFishMarkets();
        return ResponseEntity.ok(fishMarkets.stream().map((fishMarket) -> FishMarketMapper.mapToFishMarketDto(fishMarket))
                .collect(Collectors.toList()));
    }

    @GetMapping("/fishmarkets/{id}")
    public ResponseEntity<FishMarketDTO> getFishMarketById(@PathVariable("id") Long fishMarketId) {
        FishMarket fishMarket = fishMarketService.findOneFishMarket(fishMarketId);

        if (fishMarket == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(FishMarketMapper.mapToFishMarketDto(fishMarket));
    }

    @GetMapping("/info")
    public ResponseEntity<?> getInfoFishMarkets(@RequestParam double bound) {
        if (bound < 0) {
            return ResponseEntity.badRequest().body("Bound can't be negative number.");
        }

        return ResponseEntity.ok(fishMarketService.getFishMarketInfo(bound));
    }

    @GetMapping("/status")
    public ResponseEntity<List<FishMarketNameFishNumberTotalQuantityDTO>> getFishmarketStatus() {
        return ResponseEntity.ok(fishMarketService.getFishmarketStatus());
    }

    @GetMapping("/fishmarkets/find")
    public ResponseEntity<?> getFishMarketsByName(@RequestParam String name) {

        if (name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("No fish markets with the given name were found.");
        }

        return ResponseEntity.ok(fishMarketService.getFishMarketsByName(name));
    }

}
