package com.example.application.services;

import com.example.application.data.entity.Showing;
import com.example.application.data.repository.ScreenRepository;
import com.example.application.data.repository.ShowingRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ShowingService {

    private final ShowingRepository repository;


    public ShowingService(ShowingRepository repository) {
        this.repository = repository;

    }



    public Optional<Showing> get(Long id) {
        return repository.findById(id);
    }

    public Showing update(Showing entity) {
        return repository.save(entity);
    }

    public void delete(Showing entity) {
        repository.delete(entity);
    }

    public Page<Showing> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Showing> list(Pageable pageable, Specification<Showing> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    public List<String> getAllFilmNames() {
        return repository.findAllFilmNames();
    }



    }

