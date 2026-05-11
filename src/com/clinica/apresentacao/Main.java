package com.clinica.apresentacao;

import com.clinica.dominio.modelo.*;
import com.clinica.dominio.servico.ServicoAgendaConsulta;
import com.clinica.infraestrutura.adaptador.persistencia.*;
import com.clinica.infraestrutura.adaptador.notificacao.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {

    public static void main(String[] args) {

        var animalRepo = new AnimalRepositorioMemoria();
        var vetRepo = new VeterinarioRepositorioMemoria();
        var consultaRepo = new ConsultaRepositorioMemoria();

        var notificacao = new NotificacaoConsole();

        var servico = new ServicoAgendaConsulta(
                animalRepo,
                vetRepo,
                consultaRepo,
                notificacao
        );

        Animal a1 = new Animal(null, "Rex", "Cachorro", "Labrador",
                LocalDate.of(2020,1,1),"Carlos");

        animalRepo.salvar(a1);

        Veterinario v1 = new Veterinario(null,"Dr João","123","Clínico");

        vetRepo.salvar(v1);

        var consulta = servico.agendarConsulta(
                a1.getId(),
                v1.getId(),
                LocalDate.now(),
                LocalTime.of(10,0),
                TipoConsulta.ROTINA
        );

        servico.realizarConsulta(consulta.getId(),"Animal saudável");

        System.out.println("Fluxo executado com sucesso");
    }
}
