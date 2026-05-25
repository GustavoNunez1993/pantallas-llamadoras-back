package com.datalock.datalock.servicios.dto;


import com.datalock.datalock.turnos.entities.SeccionesJpaModel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SeccionesDto {

    private UUID   id;
    private String codigo;
    private String descripcion;

    public static SeccionesDto fromEntity(SeccionesJpaModel e) {
        SeccionesDto dto = new SeccionesDto();
        dto.id          = e.getId();
        dto.codigo      = e.getCodigo();
        dto.descripcion = e.getDescripcion();
        return dto;
    }
}