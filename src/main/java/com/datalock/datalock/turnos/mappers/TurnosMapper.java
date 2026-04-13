package com.datalock.datalock.turnos.mappers;


import com.datalock.datalock.turnos.dto.response.TurnoHistorialResponse;
import com.datalock.datalock.turnos.dto.response.TurnoResponse;
import com.datalock.datalock.turnos.entities.TurnoHistorialJpaModel;
import com.datalock.datalock.turnos.entities.TurnosJpaModel;

public final class TurnosMapper {

    private TurnosMapper() {
    }

    public static TurnoResponse toResponse(TurnosJpaModel entity) {
        return TurnoResponse.builder()
                .id(entity.getId())
                .numeroTurno(entity.getNumeroTurno())
                .prefijo(entity.getPrefijo())
                .numeroSecuencia(entity.getNumeroSecuencia())
                .fechaTurno(entity.getFechaTurno())
                .fechaHoraEmision(entity.getFechaHoraEmision())
                .estadoTurno(entity.getEstadoTurno())
                .prioridadTurno(entity.getPrioridadTurno())
                .nombreCliente(entity.getNombreCliente())
                .documentoCliente(entity.getDocumentoCliente())
                .observacion(entity.getObservacion())
                .cantidadLlamados(entity.getCantidadLlamados())
                .fechaHoraLlamado(entity.getFechaHoraLlamado())
                .fechaHoraInicioAtencion(entity.getFechaHoraInicioAtencion())
                .fechaHoraFinAtencion(entity.getFechaHoraFinAtencion())
                .moduloActual(entity.getModuloActual())
                .pantallaDestino(entity.getPantallaDestino())
                .seccionId(entity.getSeccion() != null ? entity.getSeccion().getId() : null)
                .seccionDescripcion(entity.getSeccion() != null ? entity.getSeccion().getDescripcion() : null)
                .usuarioAtencionId(entity.getUsuarioAtencion() != null ? entity.getUsuarioAtencion().getId() : null)
                .usuarioAtencionNombre(
                        entity.getUsuarioAtencion() != null
                                ? entity.getUsuarioAtencion().getNombre() + " " + entity.getUsuarioAtencion().getApellido()
                                : null
                )
                .build();
    }

    public static TurnoHistorialResponse toResponse(TurnoHistorialJpaModel entity) {
        return TurnoHistorialResponse.builder()
                .id(entity.getId())
                .turnoId(entity.getTurno() != null ? entity.getTurno().getId() : null)
                .estadoAnterior(entity.getEstadoAnterior())
                .estadoNuevo(entity.getEstadoNuevo())
                .accion(entity.getAccion())
                .usuarioId(entity.getUsuario() != null ? entity.getUsuario().getId() : null)
                .usuarioNombre(
                        entity.getUsuario() != null
                                ? entity.getUsuario().getNombre() + " " + entity.getUsuario().getApellido()
                                : null
                )
                .fechaHoraEvento(entity.getFechaHoraEvento())
                .observacion(entity.getObservacion())
                .moduloActual(entity.getModuloActual())
                .pantallaDestino(entity.getPantallaDestino())
                .build();
    }
}
