package com.datalock.datalock.turnos.service.impl;


import com.datalock.datalock.authentication.entities.UserJpaModel;
import com.datalock.datalock.turnos.entities.AccionTurnoHistorialEnum;
import com.datalock.datalock.turnos.entities.EstadoTurnoEnum;
import com.datalock.datalock.turnos.entities.TurnoHistorialJpaModel;
import com.datalock.datalock.turnos.entities.TurnosJpaModel;
import com.datalock.datalock.turnos.repository.TurnoHistorialJpaRepository;
import com.datalock.datalock.turnos.service.TurnoHistorialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TurnoHistorialServiceImpl implements TurnoHistorialService {

    private final TurnoHistorialJpaRepository turnoHistorialJpaRepository;

    @Override
    public TurnoHistorialJpaModel registrarHistorial(
            TurnosJpaModel turno,
            EstadoTurnoEnum estadoAnterior,
            EstadoTurnoEnum estadoNuevo,
            AccionTurnoHistorialEnum accion,
            UserJpaModel usuario,
            String observacion
    ) {
        TurnoHistorialJpaModel historial = new TurnoHistorialJpaModel();
        historial.setTurno(turno);
        historial.setEstadoAnterior(estadoAnterior);
        historial.setEstadoNuevo(estadoNuevo);
        historial.setAccion(accion);
        historial.setUsuario(usuario);
        historial.setObservacion(observacion);
        historial.setModuloActual(turno.getModuloActual());
        historial.setPantallaDestino(turno.getPantallaDestino());

        return turnoHistorialJpaRepository.save(historial);
    }

    @Override
    public List<TurnoHistorialJpaModel> obtenerHistorialPorTurno(UUID turnoId) {
        return turnoHistorialJpaRepository.findByTurnoIdOrderByFechaHoraEventoAsc(turnoId);
    }
}
