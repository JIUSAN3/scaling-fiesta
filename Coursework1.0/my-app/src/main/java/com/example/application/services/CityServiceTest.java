package com.example.application.services;

import com.example.application.data.entity.City;
import com.example.application.data.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CityServiceTest {

    @Mock
    private CityRepository repository;

    @InjectMocks
    private CityService cityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGet() {
        Long cityId = 10L;
        City city = new City();
        city.setId(cityId);
        when(repository.findById(cityId)).thenReturn(Optional.of(city));

        Optional<City> result = cityService.get(cityId);

        assertTrue(result.isPresent(), "Expected city to be present");
        assertEquals(cityId, result.get().getId(), "Expected city ID to match");
        verify(repository, times(1)).findById(cityId);
    }

    @Test
    void testUpdate() {
        City city = new City();
        when(repository.save(city)).thenReturn(city);

        City result = cityService.update(city);

        assertNotNull(result, "Expected updated city to not be null");
        verify(repository, times(1)).save(city);
    }

    @Test
    void testDelete() {
        City city = new City();

        cityService.delete(city);

        verify(repository, times(1)).delete(city);
    }

    @Test
    void testList() {
        List<City> cities = new ArrayList<>();
        cities.add(new City());
        cities.add(new City());
        Page<City> page = new PageImpl<>(cities);
        when(repository.findAll(any(Pageable.class))).thenReturn(page);

        Page<City> result = cityService.list(Pageable.unpaged());

        assertNotNull(result, "Expected city page to not be null");
        assertEquals(2, result.getTotalElements(), "Expected 2 cities in the page");
        verify(repository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testCount() {
        when(repository.count()).thenReturn(10L);

        int result = cityService.count();

        assertEquals(10, result, "Expected city count to be 10");
        verify(repository, times(1)).count();
    }
}