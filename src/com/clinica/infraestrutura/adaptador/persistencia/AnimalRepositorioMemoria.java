package com.clinica.infraestrutura.adaptador.persistencia;

import com.clinica.dominio.modelo.Animal;
import com.clinica.dominio.porta.saida.PortaAnimalRepositorio;

import java.util.*;

public class AnimalRepositorioMemoria implements PortaAnimalRepositorio {

    private Map<Long, Animal> banco = new HashMap<>();
    private Long sequencia = 1L;

    @Override
    public void salvar(Animal animal) {

        if (animal.getId() == null) {
            animal.setId(sequencia++);
        }

        banco.put(animal.getId(), animal);
    }

    @Override
    public Optional<Animal> buscarPorId(Long id) {
        return Optional.ofNullable(banco.get(id));
    }

    @Override
    public List<Animal> listarPorTutor(String tutor) {

        List<Animal> lista = new ArrayList<>();

        for (Animal a : banco.values()) {
            if (a.getTutor().equalsIgnoreCase(tutor)) {
                lista.add(a);
            }
        }

        return lista;
    }

    @Override
    public List<Animal> listarTodos() {
        return new ArrayList<>(banco.values());
    }

    @Override
    public void remover(Long id) {
        banco.remove(id);
    }
}
