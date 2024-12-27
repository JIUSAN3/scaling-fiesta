package com.example.application.services;

import com.example.application.data.entity.Seat;
import com.example.application.data.repository.SeatRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SeatService {

    private final SeatRepository repository;

    public SeatService(SeatRepository repository) {
        this.repository = repository;
    }

    public Optional<Seat> get(Long id) {
        return repository.findById(id);
    }

    public Seat update(Seat entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Seat> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Seat> list(Pageable pageable, Specification<Seat> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
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
}
