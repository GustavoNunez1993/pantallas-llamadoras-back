package com.datalock.datalock.turnos.dto.request;

import com.datalock.datalock.turnos.entities.PrioridadTurnoEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModificarTurnoRequest {
    private PrioridadTurnoEnum prioridadTurno;
    private String nombreCliente;
    private String documentoCliente;
    private String observacion;
}
