package com.datalock.datalock.authentication.entities;

public enum Role {
    ADMIN,          // Administración total del sistema
    SUPERVISOR,     // Supervisión y control operativo
    OPERADOR,       // Atención directa y llamado de turnos
    RECEPCIONISTA,  // Registro y orientación inicial del cliente
    CLIENTE,        // Consulta de estado de turno o atención
    PANTALLA,       // Visualización pública de turnos
    BACKOFFICE      // Gestión administrativa interna y soporte
}