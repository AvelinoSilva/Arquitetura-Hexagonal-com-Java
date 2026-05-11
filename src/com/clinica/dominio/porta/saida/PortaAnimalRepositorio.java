package com.clinica.dominio.porta.saida;

import com.clinica.dominio.modelo.Animal;
import java.util.*;

public interface PortaAnimalRepositorio {

    void salvar(Animal animal);

    Optional<Animal> buscarPorId(Long id);

    List<Animal> listarPorTutor(String tutor);

    List<Animal> listarTodos();

    void remover(Long id);
}
