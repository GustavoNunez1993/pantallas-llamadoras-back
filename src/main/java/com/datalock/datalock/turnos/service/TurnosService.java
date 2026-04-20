package com.datalock.datalock.turnos.service;


import com.datalock.datalock.authentication.entities.UserJpaModel;
import com.datalock.datalock.turnos.entities.TurnoHistorialJpaModel;
import com.datalock.datalock.turnos.entities.TurnosJpaModel;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TurnosService {

    TurnosJpaModel crear(TurnosJpaModel turno, UserJpaModel usuario);

    TurnosJpaModel obtenerPorId(UUID id);

    TurnosJpaModel obtenerPorNumeroTurno(String numeroTurno);

    List<TurnosJpaModel> listarTodos();

    List<TurnosJpaModel> listarPorFecha(LocalDate fecha);

    List<TurnosJpaModel> listarPorSeccionYFecha(UUID seccionId, LocalDate fecha);

    TurnosJpaModel llamarTurno(UUID turnoId, UserJpaModel usuario, String moduloActual, String pantallaDestino);

    TurnosJpaModel iniciarAtencion(UUID turnoId, UserJpaModel usuario);

    TurnosJpaModel finalizarAtencion(UUID turnoId, UserJpaModel usuario, String observacion);

    TurnosJpaModel cancelarTurno(UUID turnoId, UserJpaModel usuario, String observacion);

    TurnosJpaModel marcarAusente(UUID turnoId, UserJpaModel usuario, String observacion);

    List<TurnoHistorialJpaModel> obtenerHistorial(UUID turnoId);
}
