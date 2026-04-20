package com.datalock.datalock.turnos.dto.response;

import com.datalock.datalock.turnos.entities.AccionTurnoHistorialEnum;
import com.datalock.datalock.turnos.entities.EstadoTurnoEnum;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class TurnoHistorialResponse {

    private UUID id;
    private UUID turnoId;
    private EstadoTurnoEnum estadoAnterior;
    private EstadoTurnoEnum estadoNuevo;
    private AccionTurnoHistorialEnum accion;
    private UUID usuarioId;
    private String usuarioNombre;
    private LocalDateTime fechaHoraEvento;
    private String observacion;
    private String moduloActual;
    private String pantallaDestino;
}
