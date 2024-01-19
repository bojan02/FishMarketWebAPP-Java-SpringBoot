package com.bojan.fishmarket.controllers;

import com.bojan.fishmarket.dto.FishDTO;
import com.bojan.fishmarket.dto.SearchDTO;
import com.bojan.fishmarket.entity.Fish;
import com.bojan.fishmarket.mapper.FishMapper;
import com.bojan.fishmarket.service.FishService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("api")
public class FishesController {

    public FishService fishService;

    @GetMapping("/fishes")
    public ResponseEntity<List<FishDTO>> getAllFishes(){
        List<Fish> fishes = fishService.getAllFishes();

        return ResponseEntity.ok(fishes.stream().map((fish) -> FishMapper.mapToFishDTO(fish)).
                collect(Collectors.toList()));
    }

    @GetMapping("/fishes/{id}")
    public ResponseEntity<FishDTO> getFishById(@PathVariable("id") Long fishId) {

        Fish fish = fishService.findOneFish(fishId);

        if (fish == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(FishMapper.mapToFishDTO(fish));
    }

    @GetMapping("/fishes/search")
    public ResponseEntity<?> getFishesBySort(@RequestParam String sort) {

        if (sort.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("The sort of fish mustn't be empty.");
        }

        List<Fish> fishes = fishService.getFishesBySort(sort);

        List<FishDTO> fishesDTO = fishes.stream()
                .map(FishMapper::mapToFishDTO)
                .collect(Collectors.toList());


        return ResponseEntity.ok(fishesDTO);
    }

    @PostMapping("/fishes")
    public ResponseEntity<?> postFish(@Valid @RequestBody FishDTO fishDTO, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        FishDTO savedFish = fishService.addFish(fishDTO);
        return new ResponseEntity<>(savedFish, HttpStatus.CREATED);
    }


    @PutMapping("/fishes/{id}")
    public ResponseEntity<?> putFish(@PathVariable("id") Long fishId, @Valid @RequestBody FishDTO updatedFish, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
           return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        FishDTO fishDTO = fishService.updateFish(fishId, updatedFish);
        return ResponseEntity.ok(fishDTO);
    }

    @DeleteMapping({"/fishes/{id}"})
    public ResponseEntity<String> deleteFish(@PathVariable("id") Long fishId){
        Fish fish = fishService.findOneFish(fishId);

        if (fish == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        fishService.removeFish(fishId);
        return ResponseEntity.ok("Riba deleted succeessfully!");
    }

    @PostMapping("/search")
    public ResponseEntity<List<FishDTO>> search(@RequestBody SearchDTO dto) {
        if (dto.getMin() < 1 || dto.getMax() < 1 || dto.getMax() > 1000 || dto.getMin() > dto.getMax()) {
            return ResponseEntity.badRequest().build();
        }

        List<Fish> result = fishService.getAllByParameters(dto.getMin(), dto.getMax());

        List<FishDTO> fishesDTO = result.stream()
                .map(FishMapper::mapToFishDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(fishesDTO);
    }
}
