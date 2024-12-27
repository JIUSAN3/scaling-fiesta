package com.example.application.services;

import com.example.application.data.entity.Booking;
import com.example.application.data.repository.BookingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class BookingService {

    private final BookingRepository repository;
    private static final Logger LOGGER = Logger.getLogger(BookingService.class.getName());

    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Optional<Booking> get(Long id) {
        try {
            return repository.findById(id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting booking by id", e);
            return Optional.empty();
        }
    }

    @Transactional
    public Booking update(Booking entity) {
        try {
            return repository.save(entity);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating booking", e);
            throw e; // rethrow or handle it accordingly
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting booking", e);
            throw e; // rethrow or handle it accordingly
        }
    }

    @Transactional(readOnly = true)
    public Page<Booking> list(Pageable pageable) {
        try {
            return repository.findAll(pageable);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error listing bookings", e);
            return Page.empty(pageable);
        }
    }

    @Transactional(readOnly = true)
    public Page<Booking> list(Pageable pageable, Specification<Booking> filter) {
        try {
            return repository.findAll(filter, pageable);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error listing bookings with filter", e);
            return Page.empty(pageable);
        }
    }

    @Transactional(readOnly = true)
    public int count() {
        try {
            return (int) repository.count();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error counting bookings", e);
            return 0;
        }
    }

    public List<String> getAllCityNames() {
        return repository.findAllCityNames();
    }

    public List<String> getCinemaNamesByCityName(String cityName) {
        return repository.findCinemaNamesByCityName(cityName);
    }

    public List<String> getScreenNamesByCinemaName(String cinemaName) {
        return repository.findScreenNamesByCinemaName(cinemaName);
    }
    public List<String> getAllFilmNames() {
        return repository.findAllFilmNames();
    }

    public List<String> getAllShowingDates() {
        return repository.findAllShowingDates();
    }

    public List<String> getAllShowingTimes() {
        return repository.findAllShowingTimes();
    }
}
