package com.example.application.services;

import com.example.application.data.entity.Film;
import com.example.application.data.repository.FilmRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class FilmService {

    private final FilmRepository repository;

    public FilmService(FilmRepository repository) {
        this.repository = repository;
    }

    public Optional<Film> get(Long id) {
        return repository.findById(id);
    }

    public Film update(Film entity) {
        return repository.save(entity);
    }

    public void delete(Film id) {
        repository.deleteById(id);
    }

    public Page<Film> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Film> list(Pageable pageable, Specification<Film> filter) {
        return repository.findAll(filter, pageable);
    }

    public List<Film> findByFilmType(String filmType) {
        return repository.findByFilmType(filmType);
    }

    public int count() {
        return (int) repository.count();
    }

    public Optional<Film> getFilmById(Long id) {
        return repository.findById(id);
    }

//    public List<String> getCinemasByFilmId(Long filmId) {
//
//    }

    public List<Film> findAll() {
        return repository.findAll();
    }
}