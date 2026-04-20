package com.datalock.datalock.turnos.controllers;

import com.datalock.datalock.turnos.dto.response.TurnoHistorialResponse;
import com.datalock.datalock.turnos.mappers.TurnosMapper;
import com.datalock.datalock.turnos.service.TurnosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/turnos/{turnoId}/historial")
@RequiredArgsConstructor
@Tag(
        name = "Historial de turnos",
        description = "Endpoints para consultar la trazabilidad y los cambios de estado de un turno."
)
@SecurityRequirement(name = "bearerAuth")
public class TurnoHistorialController {

    private final TurnosService turnosService;

    @GetMapping
    @Operation(
            summary = "Obtener historial de un turno",
            description = "Retorna la trazabilidad completa del turno, incluyendo cambios de estado, acciones realizadas, usuario responsable y fecha del evento."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado")
    })
    public ResponseEntity<List<TurnoHistorialResponse>> obtenerHistorial(
            @Parameter(description = "ID del turno", required = true)
            @PathVariable UUID turnoId
    ) {
        List<TurnoHistorialResponse> response = turnosService.obtenerHistorial(turnoId)
                .stream()
                .map(TurnosMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }
}