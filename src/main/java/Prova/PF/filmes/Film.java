package br.edu.insper.exercicio.model;

import jakarta.persistence.*;

@Entity
@Table(name = "films")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(length = 2000)
    private String descricao;

    private Integer nota;

    private String diretor;

    public Film() {}

    public Film(String nome, String descricao, Integer nota, String diretor) {
        this.nome = nome;
        this.descricao = descricao;
        this.nota = nota;
        this.diretor = diretor;
    }

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
