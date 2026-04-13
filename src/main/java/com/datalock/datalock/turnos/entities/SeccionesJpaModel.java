package com.datalock.datalock.turnos.entities;

import com.datalock.datalock.utils.infra.BaseDbModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "secciones")
@Getter
@Setter
public class SeccionesJpaModel extends BaseDbModel {

    @Column(name = "codigo", length = 10)
    private String codigo;

    @Column(name = "descripcion", length = 150, nullable = false)
    private String descripcion;

}
