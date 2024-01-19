package com.bojan.fishmarket.controllers;

import com.bojan.fishmarket.dto.FishDTO;
import com.bojan.fishmarket.dto.SearchDTO;
import com.bojan.fishmarket.entity.Fish;
import com.bojan.fishmarket.entity.FishMarket;
import com.bojan.fishmarket.mapper.FishMapper;
import com.bojan.fishmarket.service.FishService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FishesController.class)
class FishesControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @InjectMocks
    private FishesController fishesController;

    @MockBean
    private FishService fishService;

    @Test
    void testGetFishById_ReturnsFishDTOAnd200Status() throws Exception {
        // Arrange
        Long fishId = 1L;
        Fish mockFish = new Fish(fishId, "Som", "Dunav", 500.0, 100, new FishMarket(1L, "Dunav doo", 2012));
        FishDTO expectedFishDTO = FishMapper.mapToFishDTO(mockFish);

        when(fishService.findOneFish(fishId)).thenReturn(mockFish);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/fishes/{id}", fishId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(fishId.intValue())))
                .andExpect(jsonPath("$.sort", is(expectedFishDTO.getSort())))
                .andExpect(jsonPath("$.placeOfCatch", is(expectedFishDTO.getPlaceOfCatch())))
                .andExpect(jsonPath("$.price", is(expectedFishDTO.getPrice())))
                .andExpect(jsonPath("$.availableQuantity", is(expectedFishDTO.getAvailableQuantity())))
                .andExpect(jsonPath("$.fishMarketName", is(expectedFishDTO.getFishMarketName())))
                .andExpect(jsonPath("$.fishMarketYearOfOpening", is(expectedFishDTO.getFishMarketYearOfOpening())))
                .andExpect(jsonPath("$.fishMarketId", is(expectedFishDTO.getFishMarketId().intValue())))
                .andReturn();
    }

    @Test
    public void testPutFish_WithBindingErrors_ReturnsBadRequest() throws Exception {
        // Arrange
        Long fishId = 1L;
        FishDTO updatedFish = new FishDTO();
        updatedFish.setSort("S");
        updatedFish.setPlaceOfCatch("A");
        updatedFish.setPrice(50.0);
        updatedFish.setAvailableQuantity(0);
        updatedFish.setFishMarketId(1L);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(updatedFish);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/fishes/" + fishId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    public void testDeleteFish_NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/fishes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void search_ReturnsOk() throws Exception{
        // Arrange
        List<Fish> fishes = List.of(
                new Fish(1L, "Smudj", "Ribnjak Bager", 1100, 20, new FishMarket(1L, "Tisa str", 2012)),
                new Fish(2L, "Saran", "Dunav", 860, 30, new FishMarket(2L, "Dunav doo", 2015))
        );

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setMin(1);
        searchDTO.setMax(31);

        FishService fishService = mock(FishService.class);
        when(fishService.getAllByParameters(searchDTO.getMin(), searchDTO.getMax())).thenReturn(fishes);

        FishesController fishController = new FishesController(fishService);

        // Act
        ResponseEntity<List<FishDTO>> response = fishController.search(searchDTO);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        List<FishDTO> fishDTOs = response.getBody();
        assertEquals(2, fishDTOs.size());

        FishDTO fishDTO1 = fishDTOs.get(0);
        assertEquals(1L, fishDTO1.getId());
        assertEquals("Smudj", fishDTO1.getSort());
        assertEquals(1100, fishDTO1.getPrice());
        assertEquals("Ribnjak Bager", fishDTO1.getPlaceOfCatch());
        assertEquals(20, fishDTO1.getAvailableQuantity());
        assertEquals(1L, fishDTO1.getFishMarketId());
        assertEquals("Tisa str", fishDTO1.getFishMarketName());
        assertEquals(2012, fishDTO1.getFishMarketYearOfOpening());

        FishDTO fishDTO2 = fishDTOs.get(1);
        assertEquals(2L, fishDTO2.getId());
        assertEquals("Saran", fishDTO2.getSort());
        assertEquals(860, fishDTO2.getPrice());
        assertEquals("Dunav", fishDTO2.getPlaceOfCatch());
        assertEquals(30, fishDTO2.getAvailableQuantity());
        assertEquals(2L, fishDTO2.getFishMarketId());
        assertEquals("Dunav doo", fishDTO2.getFishMarketName());
        assertEquals(2015, fishDTO2.getFishMarketYearOfOpening());
    }










}