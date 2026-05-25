package com.datalock.datalock.servicios.entities;

import com.datalock.datalock.utils.infra.BaseDbModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "servicios")
@Getter
@Setter
public class ServiciosJpaModel extends BaseDbModel {

    @Column(name = "codigo", length = 10)
    private String codigo;

    @Column(name = "descripcion", length = 150, nullable = false)
    private String descripcion;


}
