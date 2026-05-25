package com.datalock.datalock.turnos.websocket;

import com.datalock.datalock.turnos.dto.response.TurnoResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class TurnosWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    public void broadcast(String type, TurnoResponse turno) {
        String payload;

        try {
            payload = objectMapper.writeValueAsString(new TurnoSocketMessage(type, turno));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("No se pudo serializar el evento de turno", e);
        }

        TextMessage message = new TextMessage(payload);

        sessions.removeIf((session) -> {
            if (!session.isOpen()) {
                return true;
            }

            try {
                session.sendMessage(message);
                return false;
            } catch (IOException e) {
                return true;
            }
        });
    }
}
