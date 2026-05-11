package com.clinica.dominio.modelo;

import com.clinica.dominio.excecao.VeterinarioIndisponivelException;

public class Veterinario {

    public enum Situacao {
        DISPONIVEL,
        OCUPADO
    }

    private Long id;
    private String nome;
    private String crmv;
    private String especialidade;
    private Situacao situacao;

    public Veterinario(Long id, String nome, String crmv, String especialidade) {
        this.id = id;
        this.nome = nome;
        this.crmv = crmv;
        this.especialidade = especialidade;
        this.situacao = Situacao.DISPONIVEL;
    }

    public boolean estaDisponivel() {
        return situacao == Situacao.DISPONIVEL;
    }

    public void ocupar() {
        if (!estaDisponivel())
            throw new VeterinarioIndisponivelException("Veterinário está ocupado");

        situacao = Situacao.OCUPADO;
    }

    public void liberar() {
        situacao = Situacao.DISPONIVEL;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEspecialidade() { return especialidade; }
    public Situacao getSituacao() { return situacao; }
}
