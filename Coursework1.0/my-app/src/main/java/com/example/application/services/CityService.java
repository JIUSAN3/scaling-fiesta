package com.example.application.services;

import com.example.application.data.entity.City;
import com.example.application.data.repository.CityRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    private final CityRepository repository;

    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    public Optional<City> get(Long id) {
        return repository.findById(id);
    }

    public City update(City entity) {
        return repository.save(entity);
    }

    public void delete(City city) {repository.delete(city);}

    public Page<City> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<City> list(Pageable pageable, Specification<City> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
