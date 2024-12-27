package com.example.application.services;

import com.example.application.data.entity.Cinema;
import com.example.application.data.repository.CinemaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CinemaService {

    private final CinemaRepository repository;

    public CinemaService(CinemaRepository repository) {
        this.repository = repository;
    }

    public Optional<Cinema> get(Long id) {
        return repository.findById(id);
    }

    public Cinema update(Cinema entity) {
        return repository.save(entity);
    }

    public void delete(Cinema cinema) {
        repository.delete(cinema);
    }

    public Page<Cinema> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Cinema> list(Pageable pageable, Specification<Cinema> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    public List<String> getAllCityNames() {
        return repository.findAllCityNames();
    }
}
