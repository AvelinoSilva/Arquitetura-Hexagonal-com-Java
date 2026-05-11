package com.clinica.infraestrutura.adaptador.notificacao;

import com.clinica.dominio.modelo.Animal;
import com.clinica.dominio.modelo.Consulta;
import com.clinica.dominio.porta.saida.PortaNotificacaoTutor;

import java.io.FileWriter;
import java.io.IOException;

public class NotificacaoCsv implements PortaNotificacaoTutor {

    private static final String ARQUIVO = "notificacoes.csv";

    @Override
    public void notificarAgendamento(String tutor, Animal animal, Consulta consulta) {

        try (FileWriter writer = new FileWriter(ARQUIVO, true)) {

            writer.write("AGENDAMENTO," +
                    tutor + "," +
                    animal.getNome() + "," +
                    consulta.getData() + "\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void notificarCancelamento(String tutor, Animal animal, String motivo) {

        try (FileWriter writer = new FileWriter(ARQUIVO, true)) {

            writer.write("CANCELAMENTO," +
                    tutor + "," +
                    animal.getNome() + "," +
                    motivo + "\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
