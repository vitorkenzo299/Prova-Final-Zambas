package br.edu.insper.exercicio.service;

import br.edu.insper.exercicio.model.Film;
import br.edu.insper.exercicio.repository.FilmRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {
    private final FilmRepository repo;

    public FilmService(FilmRepository repo) {
        this.repo = repo;
    }

    public List<Film> all() {
        return repo.findAll();
    }

    public Film save(Film film) {
        return repo.save(film);
    }

    public Optional<Film> findById(Long id) {
        return repo.findById(id);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
