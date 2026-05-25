package com.datalock.datalock.consultar_ruc.services.impl;

import com.datalock.datalock.consultar_ruc.services.ConsultarRucServices;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ConsultarRucServiceImpl implements ConsultarRucServices {

    private static final String BASE_URL = "https://api.consulta-ruc.com.py/api/v1";
    private static final String EMAIL    = "gnunez@code100.com.py";
    private static final String PASSWORD = "je20142021";

    private final HttpClient     httpClient   = HttpClient.newHttpClient();
    private final ObjectMapper   objectMapper = new ObjectMapper();

    // ── 1. Autenticación ────────────────────────────────────────────────────────

    private String obtenerToken() throws Exception {
        String body = objectMapper.writeValueAsString(
                new java.util.HashMap<>() {{
                    put("email",    EMAIL);
                    put("password", PASSWORD);
                }}
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/auth/signin"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Error al autenticar: " + response.statusCode());
        }

        JsonNode json = objectMapper.readTree(response.body());

        // Intentamos "token" primero, luego "accessToken"
        if (json.has("token"))       return json.get("token").asText();
        if (json.has("accessToken")) return json.get("accessToken").asText();

        throw new RuntimeException("No se encontró token en la respuesta de autenticación");
    }

    // ── 2. Consulta de RUC ──────────────────────────────────────────────────────

    @Override
    public JsonNode consultarRuc(String ruc) {
        try {
            String token = obtenerToken();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/consultas-ruc/" + ruc))
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode result = objectMapper.readTree(response.body());

            // Validación: sin data
            if (result == null || !result.has("data") || result.get("data").isNull()) {
                throw new RuntimeException("El RUC ingresado no existe en la base de datos.");
            }

            // Normalización: data puede ser array u objeto
            JsonNode data   = result.get("data");
            JsonNode cliente = data.isArray() ? data.get(0) : data;

            // En consultarRuc(), antes del return cliente, agregá esto temporalmente:
            System.out.println("=== RESPUESTA CRUDA API RUC ===");
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(cliente));
            System.out.println("================================");

            return cliente;

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error consultando RUC: " + e.getMessage(), e);
        }
    }
}