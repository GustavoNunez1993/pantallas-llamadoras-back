package com.datalock.datalock.turnos.dto.response;

import com.datalock.datalock.turnos.entities.EstadoTurnoEnum;
import com.datalock.datalock.turnos.entities.PrioridadTurnoEnum;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class TurnoResponse {

    private UUID id;
    private String numeroTurno;
    private String prefijo;
    private Integer numeroSecuencia;
    private LocalDate fechaTurno;
    private LocalDateTime fechaHoraEmision;
    private EstadoTurnoEnum estadoTurno;
    private PrioridadTurnoEnum prioridadTurno;
    private String nombreCliente;
    private String documentoCliente;
    private String observacion;
    private Integer cantidadLlamados;
    private LocalDateTime fechaHoraLlamado;
    private LocalDateTime fechaHoraInicioAtencion;
    private LocalDateTime fechaHoraFinAtencion;
    private String moduloActual;
    private String pantallaDestino;

    private UUID seccionId;
    private String seccionDescripcion;

    private UUID usuarioAtencionId;
    private String usuarioAtencionNombre;
}
