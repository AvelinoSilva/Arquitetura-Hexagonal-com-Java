package com.clinica.infraestrutura.adaptador.persistencia;

import com.clinica.dominio.modelo.Veterinario;
import com.clinica.dominio.porta.saida.PortaVeterinarioRepositorio;

import java.util.*;

public class VeterinarioRepositorioMemoria implements PortaVeterinarioRepositorio {

    private Map<Long, Veterinario> banco = new HashMap<>();
    private Long sequencia = 1L;

    @Override
    public void salvar(Veterinario vet) {

        if (vet.getId() == null) {
            vet = new Veterinario(sequencia++, vet.getNome(), "", vet.getEspecialidade());
        }

        banco.put(vet.getId(), vet);
    }

    @Override
    public Optional<Veterinario> buscarPorId(Long id) {
        return Optional.ofNullable(banco.get(id));
    }

    @Override
    public List<Veterinario> buscarDisponiveis() {

        List<Veterinario> lista = new ArrayList<>();

        for (Veterinario v : banco.values()) {
            if (v.estaDisponivel()) {
                lista.add(v);
            }
        }

        return lista;
    }

    @Override
    public List<Veterinario> buscarPorEspecialidade(String especialidade) {

        List<Veterinario> lista = new ArrayList<>();

        for (Veterinario v : banco.values()) {
            if (v.getEspecialidade().equalsIgnoreCase(especialidade)) {
                lista.add(v);
            }
        }

        return lista;
    }
}
