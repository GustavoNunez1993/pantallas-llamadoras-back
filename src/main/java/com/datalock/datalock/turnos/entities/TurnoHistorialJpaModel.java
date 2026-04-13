package com.datalock.datalock.turnos.entities;


import com.datalock.datalock.authentication.entities.UserJpaModel;
import com.datalock.datalock.utils.infra.BaseDbModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "turno_historial")
@Getter
@Setter
public class TurnoHistorialJpaModel extends BaseDbModel {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "turno_id", nullable = false)
    private TurnosJpaModel turno;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_anterior", length = 30)
    private EstadoTurnoEnum estadoAnterior;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_nuevo", nullable = false, length = 30)
    private EstadoTurnoEnum estadoNuevo;

    @Enumerated(EnumType.STRING)
    @Column(name = "accion", nullable = false, length = 30)
    private AccionTurnoHistorialEnum accion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private UserJpaModel usuario;

    @Column(name = "fecha_hora_evento", nullable = false)
    private LocalDateTime fechaHoraEvento;

    @Column(name = "observacion", length = 500)
    private String observacion;

    @Column(name = "modulo_actual", length = 50)
    private String moduloActual;

    @Column(name = "pantalla_destino", length = 50)
    private String pantallaDestino;

    @PrePersist
    public void prePersist() {
        if (this.fechaHoraEvento == null) {
            this.fechaHoraEvento = LocalDateTime.now();
        }
    }
}