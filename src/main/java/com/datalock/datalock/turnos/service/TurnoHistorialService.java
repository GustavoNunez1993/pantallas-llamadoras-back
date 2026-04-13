package com.datalock.datalock.turnos.service;



import com.datalock.datalock.authentication.entities.UserJpaModel;
import com.datalock.datalock.turnos.entities.AccionTurnoHistorialEnum;
import com.datalock.datalock.turnos.entities.EstadoTurnoEnum;
import com.datalock.datalock.turnos.entities.TurnoHistorialJpaModel;
import com.datalock.datalock.turnos.entities.TurnosJpaModel;

import java.util.List;
import java.util.UUID;

public interface TurnoHistorialService {

    TurnoHistorialJpaModel registrarHistorial(
            TurnosJpaModel turno,
            EstadoTurnoEnum estadoAnterior,
            EstadoTurnoEnum estadoNuevo,
            AccionTurnoHistorialEnum accion,
            UserJpaModel usuario,
            String observacion
    );

    List<TurnoHistorialJpaModel> obtenerHistorialPorTurno(UUID turnoId);
}
