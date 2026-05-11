package com.clinica.dominio.servico;

import com.clinica.dominio.modelo.*;
import com.clinica.dominio.excecao.*;
import com.clinica.dominio.porta.entrada.PortaAgendaConsulta;
import com.clinica.dominio.porta.saida.*;

import java.time.*;
import java.util.*;

public class ServicoAgendaConsulta implements PortaAgendaConsulta {

    private final PortaAnimalRepositorio animalRepo;
    private final PortaVeterinarioRepositorio vetRepo;
    private final PortaConsultaRepositorio consultaRepo;
    private final PortaNotificacaoTutor notificacao;

    public ServicoAgendaConsulta(
            PortaAnimalRepositorio animalRepo,
            PortaVeterinarioRepositorio vetRepo,
            PortaConsultaRepositorio consultaRepo,
            PortaNotificacaoTutor notificacao) {

        this.animalRepo = animalRepo;
        this.vetRepo = vetRepo;
        this.consultaRepo = consultaRepo;
        this.notificacao = notificacao;
    }

    @Override
    public Consulta agendarConsulta(Long animalId, Long veterinarioId,
                                    LocalDate data, LocalTime hora,
                                    TipoConsulta tipo) {

        Animal animal = animalRepo.buscarPorId(animalId)
                .orElseThrow(() -> new AnimalNaoEncontradoException("Animal não encontrado"));

        Veterinario vet = vetRepo.buscarPorId(veterinarioId)
                .orElseThrow(() -> new VeterinarioIndisponivelException("Veterinário não encontrado"));

        if (!vet.estaDisponivel())
            throw new VeterinarioIndisponivelException("Veterinário indisponível");

        vet.ocupar();

        Consulta consulta = new Consulta(null, animal, vet, data, hora, tipo);

        consultaRepo.salvar(consulta);

        notificacao.notificarAgendamento(animal.getTutor(), animal, consulta);

        return consulta;
    }

    @Override
    public Consulta realizarConsulta(Long consultaId, String observacoes) {

        Consulta consulta = consultaRepo.buscarPorId(consultaId)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        consulta.realizar(observacoes);

        consulta.getVeterinario().liberar();

        consultaRepo.salvar(consulta);

        return consulta;
    }

    @Override
    public void cancelarConsulta(Long consultaId) {

        Consulta consulta = consultaRepo.buscarPorId(consultaId)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        consulta.cancelar();

        consulta.getVeterinario().liberar();

        notificacao.notificarCancelamento(
                consulta.getAnimal().getTutor(),
                consulta.getAnimal(),
                "Consulta cancelada");

        consultaRepo.salvar(consulta);
    }

    @Override
    public List<Consulta> obterHistoricoAnimal(Long animalId) {
        return consultaRepo.buscarPorAnimal(animalId);
    }

    @Override
    public List<Consulta> obterAgendaVeterinario(Long vetId) {
        return consultaRepo.buscarPorVeterinario(vetId);
    }
}
