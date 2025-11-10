package br.edu.insper.exercicio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "films")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @Column(length = 2000)
    private String descricao;

    @NotNull
    @Min(0)
    @Max(5)
    private Integer nota;

    @NotBlank
    private String diretor;

    // construtores
    public Film() {}

    public Film(String nome, String descricao, Integer nota, String diretor) {
        this.nome = nome;
        this.descricao = descricao;
        this.nota = nota;
        this.diretor = diretor;
    }

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { this.nota = nota; }
    public String getDiretor() { return diretor; }
    public void setDiretor(String diretor) { this.diretor = diretor; }
}
