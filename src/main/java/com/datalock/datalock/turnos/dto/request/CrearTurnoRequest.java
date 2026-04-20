package com.datalock.datalock.turnos.dto.request;


import com.datalock.datalock.turnos.entities.PrioridadTurnoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CrearTurnoRequest {

    @NotBlank
    private String numeroTurno;

    private String prefijo;

    @NotNull
    private Integer numeroSecuencia;

    @NotNull
    private UUID seccionId;

    private PrioridadTurnoEnum prioridadTurno;

    private String nombreCliente;

    private String documentoCliente;

    private String observacion;
}