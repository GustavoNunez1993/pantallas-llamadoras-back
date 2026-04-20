package com.datalock.datalock.authentication.entities;


import com.datalock.datalock.turnos.entities.SeccionesJpaModel;
import com.datalock.datalock.utils.infra.BaseDbModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario_seccion")
@Getter
@Setter
public class UsuarioSeccionJpaModel extends BaseDbModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UserJpaModel usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seccion_id", nullable = false)
    private SeccionesJpaModel seccion;

    @Column(name = "fecha_desde", nullable = false)
    private LocalDateTime fechaDesde;

    @Column(name = "fecha_hasta")
    private LocalDateTime fechaHasta;

    @PrePersist
    public void prePersist() {
        if (this.fechaDesde == null) {
            this.fechaDesde = LocalDateTime.now();
    }
}

}