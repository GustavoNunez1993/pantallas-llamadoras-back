package com.datalock.datalock.turnos.entities;

public enum EstadoTurnoEnum {

    GENERADO,       // Se creó el turno
    EN_ESPERA,      // Esperando ser llamado
    LLAMADO,        // Ya fue llamado en pantalla
    EN_ATENCION,    // Lo está atendiendo un operador
    FINALIZADO,     // Atención terminada
    CANCELADO,      // Cancelado manualmente
    AUSENTE,        // No respondió al llamado
    REASIGNADO      // Se movió a otro módulo/puesto
}
