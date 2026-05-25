package com.datalock.datalock.turnos.websocket;

import com.datalock.datalock.turnos.dto.response.TurnoResponse;

public record TurnoSocketMessage(String type, TurnoResponse turno) {
}
