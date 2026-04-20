package com.datalock.datalock.turnos.service.impl;


import com.datalock.datalock.authentication.entities.UserJpaModel;
import com.datalock.datalock.turnos.entities.AccionTurnoHistorialEnum;
import com.datalock.datalock.turnos.entities.EstadoTurnoEnum;
import com.datalock.datalock.turnos.entities.TurnoHistorialJpaModel;
import com.datalock.datalock.turnos.entities.TurnosJpaModel;
import com.datalock.datalock.turnos.repository.TurnosJpaRepository;
import com.datalock.datalock.turnos.service.TurnoHistorialService;
import com.datalock.datalock.turnos.service.TurnosService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TurnosServiceImpl implements TurnosService {

    private final TurnosJpaRepository turnosJpaRepository;
    private final TurnoHistorialService turnoHistorialService;

    @Override
    public TurnosJpaModel crear(TurnosJpaModel turno, UserJpaModel usuario) {
        if (turnosJpaRepository.existsByNumeroTurno(turno.getNumeroTurno())) {
            throw new IllegalArgumentException("Ya existe un turno con el número " + turno.getNumeroTurno());
        }

        TurnosJpaModel turnoGuardado = turnosJpaRepository.save(turno);

        turnoHistorialService.registrarHistorial(
                turnoGuardado,
                null,
                turnoGuardado.getEstadoTurno(),
                AccionTurnoHistorialEnum.CREACION,
                usuario,
                "Turno creado"
        );

        return turnoGuardado;
    }

    @Override
    @Transactional(readOnly = true)
    public TurnosJpaModel obtenerPorId(UUID id) {
        return turnosJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Turno no encontrado con id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public TurnosJpaModel obtenerPorNumeroTurno(String numeroTurno) {
        return turnosJpaRepository.findByNumeroTurno(numeroTurno)
                .orElseThrow(() -> new EntityNotFoundException("Turno no encontrado con número: " + numeroTurno));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TurnosJpaModel> listarTodos() {
        return turnosJpaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TurnosJpaModel> listarPorFecha(LocalDate fecha) {
        return turnosJpaRepository.findByFechaTurnoOrderByNumeroSecuenciaAsc(fecha);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TurnosJpaModel> listarPorSeccionYFecha(UUID seccionId, LocalDate fecha) {
        return turnosJpaRepository.findBySeccionIdAndFechaTurnoOrderByNumeroSecuenciaAsc(seccionId, fecha);
    }

    @Override
    public TurnosJpaModel llamarTurno(UUID turnoId, UserJpaModel usuario, String moduloActual, String pantallaDestino) {
        TurnosJpaModel turno = obtenerPorId(turnoId);
        EstadoTurnoEnum estadoAnterior = turno.getEstadoTurno();

        turno.setEstadoTurno(EstadoTurnoEnum.LLAMADO);
        turno.setFechaHoraLlamado(LocalDateTime.now());
        turno.setCantidadLlamados(turno.getCantidadLlamados() == null ? 1 : turno.getCantidadLlamados() + 1);
        turno.setModuloActual(moduloActual);
        turno.setPantallaDestino(pantallaDestino);

        TurnosJpaModel turnoGuardado = turnosJpaRepository.save(turno);

        turnoHistorialService.registrarHistorial(
                turnoGuardado,
                estadoAnterior,
                EstadoTurnoEnum.LLAMADO,
                AccionTurnoHistorialEnum.LLAMADO,
                usuario,
                "Turno llamado"
        );

        return turnoGuardado;
    }

    @Override
    public TurnosJpaModel iniciarAtencion(UUID turnoId, UserJpaModel usuario) {
        TurnosJpaModel turno = obtenerPorId(turnoId);
        EstadoTurnoEnum estadoAnterior = turno.getEstadoTurno();

        turno.setEstadoTurno(EstadoTurnoEnum.EN_ATENCION);
        turno.setFechaHoraInicioAtencion(LocalDateTime.now());
        turno.setUsuarioAtencion(usuario);

        TurnosJpaModel turnoGuardado = turnosJpaRepository.save(turno);

        turnoHistorialService.registrarHistorial(
                turnoGuardado,
                estadoAnterior,
                EstadoTurnoEnum.EN_ATENCION,
                AccionTurnoHistorialEnum.INICIO_ATENCION,
                usuario,
                "Inicio de atención"
        );

        return turnoGuardado;
    }

    @Override
    public TurnosJpaModel finalizarAtencion(UUID turnoId, UserJpaModel usuario, String observacion) {
        TurnosJpaModel turno = obtenerPorId(turnoId);
        EstadoTurnoEnum estadoAnterior = turno.getEstadoTurno();

        turno.setEstadoTurno(EstadoTurnoEnum.FINALIZADO);
        turno.setFechaHoraFinAtencion(LocalDateTime.now());
        turno.setUsuarioAtencion(usuario);

        TurnosJpaModel turnoGuardado = turnosJpaRepository.save(turno);

        turnoHistorialService.registrarHistorial(
                turnoGuardado,
                estadoAnterior,
                EstadoTurnoEnum.FINALIZADO,
                AccionTurnoHistorialEnum.FIN_ATENCION,
                usuario,
                observacion == null || observacion.isBlank() ? "Atención finalizada" : observacion
        );

        return turnoGuardado;
    }

    @Override
    public TurnosJpaModel cancelarTurno(UUID turnoId, UserJpaModel usuario, String observacion) {
        TurnosJpaModel turno = obtenerPorId(turnoId);
        EstadoTurnoEnum estadoAnterior = turno.getEstadoTurno();

        turno.setEstadoTurno(EstadoTurnoEnum.CANCELADO);

        TurnosJpaModel turnoGuardado = turnosJpaRepository.save(turno);

        turnoHistorialService.registrarHistorial(
                turnoGuardado,
                estadoAnterior,
                EstadoTurnoEnum.CANCELADO,
                AccionTurnoHistorialEnum.CANCELACION,
                usuario,
                observacion == null || observacion.isBlank() ? "Turno cancelado" : observacion
        );

        return turnoGuardado;
    }

    @Override
    public TurnosJpaModel marcarAusente(UUID turnoId, UserJpaModel usuario, String observacion) {
        TurnosJpaModel turno = obtenerPorId(turnoId);
        EstadoTurnoEnum estadoAnterior = turno.getEstadoTurno();

        turno.setEstadoTurno(EstadoTurnoEnum.AUSENTE);

        TurnosJpaModel turnoGuardado = turnosJpaRepository.save(turno);

        turnoHistorialService.registrarHistorial(
                turnoGuardado,
                estadoAnterior,
                EstadoTurnoEnum.AUSENTE,
                AccionTurnoHistorialEnum.AUSENCIA,
                usuario,
                observacion == null || observacion.isBlank() ? "Turno marcado como ausente" : observacion
        );

        return turnoGuardado;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TurnoHistorialJpaModel> obtenerHistorial(UUID turnoId) {
        return turnoHistorialService.obtenerHistorialPorTurno(turnoId);
    }
}