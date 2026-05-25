package com.datalock.datalock.consultar_ruc.controllers;

import com.datalock.datalock.consultar_ruc.services.ConsultarRucServices;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ruc")
@CrossOrigin(origins = "*")
public class ConsultarRucControllers {

    private final ConsultarRucServices consultarRucServices;

    public ConsultarRucControllers(ConsultarRucServices consultarRucServices) {
        this.consultarRucServices = consultarRucServices;
    }

    @GetMapping("/{ruc}")
    public ResponseEntity<?> consultarRuc(@PathVariable String ruc) {
        try {
            JsonNode cliente = consultarRucServices.consultarRuc(ruc);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    // ── DTO para errores ────────────────────────────────────────────────────────
    record ErrorResponse(String message) {}
}