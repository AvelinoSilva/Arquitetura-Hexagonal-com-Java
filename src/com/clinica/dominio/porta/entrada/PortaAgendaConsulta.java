package com.clinica.dominio.porta.entrada;

import com.clinica.dominio.modelo.*;
import java.time.*;
import java.util.*;

public interface PortaAgendaConsulta {

    Consulta agendarConsulta(Long animalId, Long veterinarioId,
                             LocalDate data, LocalTime hora,
                             TipoConsulta tipo);

    Consulta realizarConsulta(Long consultaId, String observacoes);

    void cancelarConsulta(Long consultaId);

    List<Consulta> obterHistoricoAnimal(Long animalId);

    List<Consulta> obterAgendaVeterinario(Long vetId);
}
