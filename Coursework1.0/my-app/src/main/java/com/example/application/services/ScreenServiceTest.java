package com.example.application.services;

import com.example.application.data.entity.Screen;
import com.example.application.data.repository.ScreenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

class ScreenServiceTest {

    @Mock
    private ScreenRepository repository;

    @InjectMocks
    private ScreenService screenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test get() method")
    void testGet() {
        Long screenId = 1L;
        Screen screen = new Screen();
        screen.setId(screenId);
        when(repository.findById(screenId)).thenReturn(Optional.of(screen));

        Optional<Screen> result = screenService.get(screenId);

        assertTrue(result.isPresent(), "Expected screen to be present");
        assertEquals(screenId, result.get().getId(), "Expected screen ID to match");
        verify(repository, times(1)).findById(screenId);

        System.out.println("Test get() method passed.");
    }

    @Test
    @DisplayName("Test update() method")
    void testUpdate() {
        Screen screen = new Screen();
        when(repository.save(screen)).thenReturn(screen);

        Screen result = screenService.update(screen);

        assertNotNull(result, "Expected updated screen to not be null");
        verify(repository, times(1)).save(screen);

        System.out.println("Test update() method passed.");
    }

    @Test
    @DisplayName("Test delete() method")
    void testDelete() {
        Screen screen = new Screen();

        screenService.delete(screen);

        verify(repository, times(1)).delete(screen);

        System.out.println("Test delete() method passed.");
    }

    @Test
    @DisplayName("Test list() method")
    void testList() {
        List<Screen> screens = new ArrayList<>();
        screens.add(new Screen());
        screens.add(new Screen());
        Page<Screen> page = new PageImpl<>(screens);
        when(repository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Screen> result = screenService.list(Pageable.unpaged());

        assertNotNull(result, "Expected screen page to not be null");
        assertEquals(2, result.getTotalElements(), "Expected 2 screens in the page");
        verify(repository, times(1)).findAll(any(Pageable.class));

        System.out.println("Test list() method passed.");
    }

    @Test
    @DisplayName("Test count() method")
    void testCount() {
        when(repository.count()).thenReturn(10L);

        int result = screenService.count();

        assertEquals(10, result, "Expected screen count to be 10");
        verify(repository, times(1)).count();

        System.out.println("Test count() method passed.");
    }

    @Test
    @DisplayName("Test getAllCityNames() method")
    void testGetAllCityNames() {
        List<String> cityNames = new ArrayList<>();
        cityNames.add("City 1");
        cityNames.add("City 2");
        when(repository.findAllCityNames()).thenReturn(cityNames);

        List<String> result = screenService.getAllCityNames();

        assertNotNull(result, "Expected city names list to not be null");
        assertEquals(2, result.size(), "Expected 2 city names in the list");
        verify(repository, times(1)).findAllCityNames();

        System.out.println("Test getAllCityNames() method passed.");
    }

    @Test
    @DisplayName("Test getCinemaNamesByCityName() method")
    void testGetCinemaNamesByCityName() {
        String cityName = "City 1";
        List<String> cinemaNames = new ArrayList<>();
        cinemaNames.add("Cinema 1");
        cinemaNames.add("Cinema 2");
        when(repository.findCinemaNamesByCityName(cityName)).thenReturn(cinemaNames);

        List<String> result = screenService.getCinemaNamesByCityName(cityName);

        assertNotNull(result, "Expected cinema names list to not be null");
        assertEquals(2, result.size(), "Expected 2 cinema names in the list");
        verify(repository, times(1)).findCinemaNamesByCityName(cityName);

        System.out.println("Test getCinemaNamesByCityName() method passed.");
    }

    @Test
    @DisplayName("Test get() method with non-existent screen ID")
    void testGetWithNonExistentScreenId() {
        Long nonExistentScreenId = 999L;
        when(repository.findById(nonExistentScreenId)).thenReturn(Optional.empty());

        Optional<Screen> result = screenService.get(nonExistentScreenId);

        assertFalse(result.isPresent(), "Expected screen to not be present");
        verify(repository, times(1)).findById(nonExistentScreenId);

        System.out.println("Test get() method with non-existent screen ID passed.");
    }

    @Test
    @DisplayName("Test update() method with null screen")
    void testUpdateWithNullScreen() {
        assertThrows(NullPointerException.class, () -> screenService.update(null),
                "Expected NullPointerException when updating null screen");

        verify(repository, never()).save(any(Screen.class));

        System.out.println("Test update() method with null screen passed.");
    }

    @Test
    @DisplayName("Test delete() method with null screen")
    void testDeleteWithNullScreen() {
        assertThrows(NullPointerException.class, () -> screenService.delete(null),
                "Expected NullPointerException when deleting null screen");

        verify(repository, never()).delete(any(Screen.class));

        System.out.println("Test delete() method with null screen passed.");
    }

    @Test
    @DisplayName("Test getAllCityNames() method with empty result")
    void testGetAllCityNamesWithEmptyResult() {
        when(repository.findAllCityNames()).thenReturn(new ArrayList<>());

        List<String> result = screenService.getAllCityNames();

        assertNotNull(result, "Expected city names list to not be null");
        assertTrue(result.isEmpty(), "Expected city names list to be empty");
        verify(repository, times(1)).findAllCityNames();

        System.out.println("Test getAllCityNames() method with empty result passed.");
    }

    @Test
    @DisplayName("Test getCinemaNamesByCityName() method with non-existent city name")
    void testGetCinemaNamesByCityNameWithNonExistentCityName() {
        String nonExistentCityName = "Non-existent City";
        when(repository.findCinemaNamesByCityName(nonExistentCityName)).thenReturn(new ArrayList<>());

        List<String> result = screenService.getCinemaNamesByCityName(nonExistentCityName);

        assertNotNull(result, "Expected cinema names list to not be null");
        assertTrue(result.isEmpty(), "Expected cinema names list to be empty");
        verify(repository, times(1)).findCinemaNamesByCityName(nonExistentCityName);

        System.out.println("Test getCinemaNamesByCityName() method with non-existent city name passed.");
    }
}