package com.example.application.services;

import com.example.application.data.entity.Cinema;
import com.example.application.data.repository.CinemaRepository;
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
import static org.mockito.Mockito.*;

class CinemaServiceTest {

    @Mock
    private CinemaRepository repository;

    @InjectMocks
    private CinemaService cinemaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGet() {
        Long cityId = 10L;
        Cinema cinema = new Cinema();
        cinema.setId(cityId);
        when(repository.findById(cityId)).thenReturn(Optional.of(cinema));

        Optional<Cinema> result = cinemaService.get(cityId);

        assertTrue(result.isPresent(), "Expected cinema to be present");
        assertEquals(cityId, result.get().getId(), "Expected cinema ID to match");
        verify(repository, times(1)).findById(cityId);
    }

    @Test
    void testUpdate() {
        Cinema city = new Cinema();
        when(repository.save(city)).thenReturn(city);

        Cinema result = cinemaService.update(city);

        assertNotNull(result, "Expected updated city to not be null");
        verify(repository, times(1)).save(city);
    }

    @Test
    void testDelete() {
        Cinema cinema = new Cinema();

        cinemaService.delete(cinema);

        verify(repository, times(1)).delete(cinema);
    }

    @Test
    void testList() {
        List<Cinema> cinemas = new ArrayList<>();
        cinemas.add(new Cinema());
        cinemas.add(new Cinema());
        Page<Cinema> page = new PageImpl<>(cinemas);

        when(repository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Cinema> result = cinemaService.list(Pageable.unpaged());

        assertNotNull(result, "Expected city page to not be null");
        assertEquals(2, result.getTotalElements(), "Expected 2 cinemas in the page");
        verify(repository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testCount() {
        when(repository.count()).thenReturn(10L);

        int result = cinemaService.count();

        assertEquals(10, result, "Expected cinema count to be 10");
        verify(repository, times(1)).count();
    }
}