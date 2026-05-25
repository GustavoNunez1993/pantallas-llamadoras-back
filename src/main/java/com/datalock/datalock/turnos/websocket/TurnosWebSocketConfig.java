package com.datalock.datalock.turnos.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class TurnosWebSocketConfig implements WebSocketConfigurer {

    private final TurnosWebSocketHandler turnosWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(turnosWebSocketHandler, "/ws/turnos")
                .setAllowedOriginPatterns("*");
    }
}
