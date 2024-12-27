package com.example.application.services;

import com.example.application.data.entity.Screen;
import com.example.application.data.repository.ScreenRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ScreenService {

    private final ScreenRepository repository;

    public ScreenService(ScreenRepository repository) {
        this.repository = repository;
    }

    public Optional<Screen> get(Long id) {
        return repository.findById(id);
    }

    public Screen update(Screen entity) {
        return repository.save(entity);
    }

    public void delete(Screen screen) {
        repository.delete(screen);
    }

    public Page<Screen> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Screen> list(Pageable pageable, Specification<Screen> filter) {
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

}
