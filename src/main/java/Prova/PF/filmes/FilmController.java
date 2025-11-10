package br.edu.insper.exercicio.controller;

import br.edu.insper.exercicio.model.Film;
import br.edu.insper.exercicio.service.FilmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/films")
public class FilmController {
    private final FilmService service;

    public FilmController(FilmService service) {
        this.service = service;
    }

    @GetMapping
    public List<Film> list() {
        return service.all();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> payload) {
        String nome = payload.get("nome") != null ? payload.get("nome").toString().trim() : null;
        String diretor = payload.get("diretor") != null ? payload.get("diretor").toString().trim() : null;
        String descricao = payload.get("descricao") != null ? payload.get("descricao").toString().trim() : null;
        Integer nota = null;
        try {
            if (payload.get("nota") != null) {
                Object n = payload.get("nota");
                if (n instanceof Number) {
                    nota = ((Number) n).intValue();
                } else {
                    nota = Integer.parseInt(n.toString());
                }
            }
        } catch (Exception ignored) {}

        if (nome == null || nome.isEmpty()) {
            return ResponseEntity.badRequest().body("Campo 'nome' é obrigatório.");
        }
        if (diretor == null || diretor.isEmpty()) {
            return ResponseEntity.badRequest().body("Campo 'diretor' é obrigatório.");
        }
        if (nota == null) {
            return ResponseEntity.badRequest().body("Campo 'nota' é obrigatório e deve ser um número inteiro (0-5).");
        }
        if (nota < 0 || nota > 5) {
            return ResponseEntity.badRequest().body("Campo 'nota' deve estar entre 0 e 5.");
        }

        Film film = new Film(nome, descricao, nota, diretor);
        Film saved = service.save(film);
        return ResponseEntity.created(URI.create("/api/films/" + saved.getId())).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Film> found = service.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
