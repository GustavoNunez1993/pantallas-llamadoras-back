package com.datalock.datalock.turnos.entities;

import com.datalock.datalock.authentication.entities.UserJpaModel;
import com.datalock.datalock.utils.infra.BaseDbModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "turnos")
@Getter
@Setter
public class TurnosJpaModel extends BaseDbModel {

        @Column(name = "numero_turno", nullable = false, length = 20, unique = true)
        private String numeroTurno;

        @Column(name = "prefijo", length = 10)
        private String prefijo;

        @Column(name = "numero_secuencia", nullable = false)
        private Integer numeroSecuencia;

        @Column(name = "fecha_turno", nullable = false)
        private LocalDate fechaTurno;

        @Column(name = "fecha_hora_emision", nullable = false)
        private LocalDateTime fechaHoraEmision;

        @Enumerated(EnumType.STRING)
        @Column(name = "estado_turno", nullable = false, length = 30)
        private EstadoTurnoEnum estadoTurno;

        @Enumerated(EnumType.STRING)
        @Column(name = "prioridad_turno", nullable = false, length = 20)
        private PrioridadTurnoEnum prioridadTurno;

        @Column(name = "nombre_cliente", length = 255)
        private String nombreCliente;

        @Column(name = "documento_cliente", length = 30)
        private String documentoCliente;

        @Column(name = "observacion", length = 500)
        private String observacion;

        @Column(name = "cantidad_llamados", nullable = false)
        private Integer cantidadLlamados;

        @Column(name = "fecha_hora_llamado")
        private LocalDateTime fechaHoraLlamado;

        @Column(name = "fecha_hora_inicio_atencion")
        private LocalDateTime fechaHoraInicioAtencion;

        @Column(name = "fecha_hora_fin_atencion")
        private LocalDateTime fechaHoraFinAtencion;

        @Column(name = "modulo_actual", length = 50)
        private String moduloActual;

        @Column(name = "pantalla_destino", length = 50)
        private String pantallaDestino;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "seccion_id", nullable = false)
        private SeccionesJpaModel seccion;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "usuario_atencion_id")
        private UserJpaModel usuarioAtencion;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "usuario_creacion_id")
        private UserJpaModel usuarioCreacion;

        @PrePersist
        public void prePersist() {
                this.fechaHoraEmision = this.fechaHoraEmision == null ? LocalDateTime.now() : this.fechaHoraEmision;
                this.fechaTurno = this.fechaTurno == null ? LocalDate.now() : this.fechaTurno;
                this.estadoTurno = this.estadoTurno == null ? EstadoTurnoEnum.EN_ESPERA : this.estadoTurno;
                this.prioridadTurno = this.prioridadTurno == null ? PrioridadTurnoEnum.NORMAL : this.prioridadTurno;
                this.cantidadLlamados = this.cantidadLlamados == null ? 0 : this.cantidadLlamados;
        }
}