package com.clinica.infraestrutura.adaptador.persistencia;

import com.clinica.dominio.modelo.Consulta;
import com.clinica.dominio.porta.saida.PortaConsultaRepositorio;

import java.util.*;

public class ConsultaRepositorioMemoria implements PortaConsultaRepositorio {

    private Map<Long, Consulta> banco = new HashMap<>();
    private Long sequencia = 1L;

    @Override
    public void salvar(Consulta consulta) {

        if (consulta.getId() == null) {
            try {
                java.lang.reflect.Field field = Consulta.class.getDeclaredField("id");
                field.setAccessible(true);
                field.set(consulta, sequencia++);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        banco.put(consulta.getId(), consulta);
    }

    @Override
    public Optional<Consulta> buscarPorId(Long id) {
        return Optional.ofNullable(banco.get(id));
    }

    @Override
    public List<Consulta> buscarPorAnimal(Long animalId) {

        List<Consulta> lista = new ArrayList<>();

        for (Consulta c : banco.values()) {
            if (c.getAnimal().getId().equals(animalId)) {
                lista.add(c);
            }
        }

        return lista;
    }

    @Override
    public List<Consulta> buscarPorVeterinario(Long vetId) {

        List<Consulta> lista = new ArrayList<>();

        for (Consulta c : banco.values()) {
            if (c.getVeterinario().getId().equals(vetId)) {
                lista.add(c);
            }
        }

        return lista;
    }

    @Override
    public List<Consulta> listarAgendadas() {

        List<Consulta> lista = new ArrayList<>();

        for (Consulta c : banco.values()) {
            lista.add(c);
        }

        return lista;
    }
}
