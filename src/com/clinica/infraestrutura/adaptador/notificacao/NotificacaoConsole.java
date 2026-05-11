package com.clinica.infraestrutura.adaptador.notificacao;

import com.clinica.dominio.modelo.Animal;
import com.clinica.dominio.modelo.Consulta;
import com.clinica.dominio.porta.saida.PortaNotificacaoTutor;

public class NotificacaoConsole implements PortaNotificacaoTutor {

    @Override
    public void notificarAgendamento(String tutor, Animal animal, Consulta consulta) {

        System.out.println("NOTIFICAÇÃO");
        System.out.println("Tutor: " + tutor);
        System.out.println("Animal: " + animal.getNome());
        System.out.println("Consulta agendada em: " + consulta.getData());
    }

    @Override
    public void notificarCancelamento(String tutor, Animal animal, String motivo) {

        System.out.println("CANCELAMENTO");
        System.out.println("Tutor: " + tutor);
        System.out.println("Animal: " + animal.getNome());
        System.out.println("Motivo: " + motivo);
    }
}
