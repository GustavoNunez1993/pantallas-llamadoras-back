package com.datalock.datalock.turnos.controllers;

import com.datalock.datalock.authentication.entities.UserJpaModel;
import com.datalock.datalock.turnos.dto.request.*;
import com.datalock.datalock.turnos.dto.response.TurnoResponse;
import com.datalock.datalock.turnos.entities.EstadoTurnoEnum;
import com.datalock.datalock.turnos.entities.PrioridadTurnoEnum;
import com.datalock.datalock.turnos.entities.SeccionesJpaModel;
import com.datalock.datalock.turnos.entities.TurnosJpaModel;
import com.datalock.datalock.turnos.mappers.TurnosMapper;
import com.datalock.datalock.turnos.repository.SeccionesJpaRepository;
import com.datalock.datalock.turnos.service.TurnosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/turnos")
@RequiredArgsConstructor
@Tag(
        name = "Turnos",
        description = "Endpoints para la gestión de turnos: creación, consulta, llamado, inicio de atención, finalización, cancelación y ausencia."
)
@SecurityRequirement(name = "bearerAuth")
public class TurnosController {

    private final TurnosService turnosService;
    private final SeccionesJpaRepository seccionesJpaRepository;

    @PostMapping
    @Operation(
            summary = "Crear turno",
            description = "Crea un nuevo turno asociado a una sección. El turno se registra inicialmente en estado EN_ESPERA."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Turno creado correctamente",
                    content = @Content(schema = @Schema(implementation = TurnoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Sección no encontrada")
    })
    public ResponseEntity<TurnoResponse> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos necesarios para crear el turno",
                    required = true
            )
            @Valid @RequestBody CrearTurnoRequest request,
            @AuthenticationPrincipal UserJpaModel usuario
    ) {
        SeccionesJpaModel seccion = seccionesJpaRepository.findById(request.getSeccionId())
                .orElseThrow(() -> new EntityNotFoundException("Sección no encontrada"));

        TurnosJpaModel turno = new TurnosJpaModel();
        turno.setNumeroTurno(request.getNumeroTurno());
        turno.setPrefijo(request.getPrefijo());
        turno.setNumeroSecuencia(request.getNumeroSecuencia());
        turno.setSeccion(seccion);
        turno.setNombreCliente(request.getNombreCliente());
        turno.setDocumentoCliente(request.getDocumentoCliente());
        turno.setObservacion(request.getObservacion());
        turno.setPrioridadTurno(
                request.getPrioridadTurno() != null ? request.getPrioridadTurno() : PrioridadTurnoEnum.NORMAL
        );
        turno.setEstadoTurno(EstadoTurnoEnum.EN_ESPERA);

        TurnosJpaModel guardado = turnosService.crear(turno, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(TurnosMapper.toResponse(guardado));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener turno por ID",
            description = "Retorna el detalle completo de un turno a partir de su identificador único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turno encontrado",
                    content = @Content(schema = @Schema(implementation = TurnoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado")
    })
    public ResponseEntity<TurnoResponse> obtenerPorId(
            @Parameter(description = "ID único del turno", required = true)
            @PathVariable UUID id
    ) {
        TurnosJpaModel turno = turnosService.obtenerPorId(id);
        return ResponseEntity.ok(TurnosMapper.toResponse(turno));
    }

    @GetMapping("/numero/{numeroTurno}")
    @Operation(
            summary = "Obtener turno por número",
            description = "Busca y retorna un turno utilizando su número visible, por ejemplo A-001."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turno encontrado",
                    content = @Content(schema = @Schema(implementation = TurnoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado")
    })
    public ResponseEntity<TurnoResponse> obtenerPorNumero(
            @Parameter(description = "Número visible del turno, por ejemplo A-001", required = true)
            @PathVariable String numeroTurno
    ) {
        TurnosJpaModel turno = turnosService.obtenerPorNumeroTurno(numeroTurno);
        return ResponseEntity.ok(TurnosMapper.toResponse(turno));
    }

    @GetMapping
    @Operation(
            summary = "Listar todos los turnos",
            description = "Retorna la lista completa de turnos registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de turnos obtenido correctamente")
    })
    public ResponseEntity<List<TurnoResponse>> listarTodos() {
        List<TurnoResponse> response = turnosService.listarTodos()
                .stream()
                .map(TurnosMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/fecha/{fecha}")
    @Operation(
            summary = "Listar turnos por fecha",
            description = "Retorna los turnos correspondientes a una fecha específica."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de turnos obtenido correctamente"),
            @ApiResponse(responseCode = "400", description = "Formato de fecha inválido")
    })
    public ResponseEntity<List<TurnoResponse>> listarPorFecha(
            @Parameter(description = "Fecha del turno en formato yyyy-MM-dd", required = true, example = "2026-04-13")
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ) {
        List<TurnoResponse> response = turnosService.listarPorFecha(fecha)
                .stream()
                .map(TurnosMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/seccion/{seccionId}/fecha/{fecha}")
    @Operation(
            summary = "Listar turnos por sección y fecha",
            description = "Retorna los turnos de una sección específica en una fecha determinada."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de turnos obtenido correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<List<TurnoResponse>> listarPorSeccionYFecha(
            @Parameter(description = "ID de la sección", required = true)
            @PathVariable UUID seccionId,
            @Parameter(description = "Fecha del turno en formato yyyy-MM-dd", required = true, example = "2026-04-13")
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ) {
        List<TurnoResponse> response = turnosService.listarPorSeccionYFecha(seccionId, fecha)
                .stream()
                .map(TurnosMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/llamar")
    @Operation(
            summary = "Llamar turno",
            description = "Cambia el estado del turno a LLAMADO, registra fecha y hora del llamado, incrementa la cantidad de llamados y asigna módulo y pantalla destino."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turno llamado correctamente",
                    content = @Content(schema = @Schema(implementation = TurnoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado"),
            @ApiResponse(responseCode = "409", description = "El turno no puede ser llamado por su estado actual")
    })
    public ResponseEntity<TurnoResponse> llamarTurno(
            @Parameter(description = "ID del turno a llamar", required = true)
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del módulo actual y pantalla donde será mostrado el turno",
                    required = true
            )
            @Valid @RequestBody LlamarTurnoRequest request,
            @AuthenticationPrincipal UserJpaModel usuario
    ) {
        TurnosJpaModel turno = turnosService.llamarTurno(
                id,
                usuario,
                request.getModuloActual(),
                request.getPantallaDestino()
        );

        return ResponseEntity.ok(TurnosMapper.toResponse(turno));
    }

    @PutMapping("/{id}/iniciar-atencion")
    @Operation(
            summary = "Iniciar atención",
            description = "Cambia el estado del turno a EN_ATENCION y registra el usuario que comienza la atención."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atención iniciada correctamente",
                    content = @Content(schema = @Schema(implementation = TurnoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado"),
            @ApiResponse(responseCode = "409", description = "El turno no puede iniciar atención por su estado actual")
    })
    public ResponseEntity<TurnoResponse> iniciarAtencion(
            @Parameter(description = "ID del turno", required = true)
            @PathVariable UUID id,
            @AuthenticationPrincipal UserJpaModel usuario
    ) {
        TurnosJpaModel turno = turnosService.iniciarAtencion(id, usuario);
        return ResponseEntity.ok(TurnosMapper.toResponse(turno));
    }

    @PutMapping("/{id}/finalizar")
    @Operation(
            summary = "Finalizar atención",
            description = "Cambia el estado del turno a FINALIZADO y registra la fecha y hora de fin de atención."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atención finalizada correctamente",
                    content = @Content(schema = @Schema(implementation = TurnoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado"),
            @ApiResponse(responseCode = "409", description = "El turno no puede finalizarse por su estado actual")
    })
    public ResponseEntity<TurnoResponse> finalizarAtencion(
            @Parameter(description = "ID del turno", required = true)
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Observación opcional del cierre de atención",
                    required = false
            )
            @RequestBody(required = false) FinalizarTurnoRequest request,
            @AuthenticationPrincipal UserJpaModel usuario
    ) {
        String observacion = request != null ? request.getObservacion() : null;
        TurnosJpaModel turno = turnosService.finalizarAtencion(id, usuario, observacion);
        return ResponseEntity.ok(TurnosMapper.toResponse(turno));
    }

    @PutMapping("/{id}/cancelar")
    @Operation(
            summary = "Cancelar turno",
            description = "Cambia el estado del turno a CANCELADO. Se utiliza cuando el turno debe anularse manualmente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turno cancelado correctamente",
                    content = @Content(schema = @Schema(implementation = TurnoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado"),
            @ApiResponse(responseCode = "409", description = "El turno no puede cancelarse por su estado actual")
    })
    public ResponseEntity<TurnoResponse> cancelarTurno(
            @Parameter(description = "ID del turno", required = true)
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Observación opcional de la cancelación",
                    required = false
            )
            @RequestBody(required = false) CancelarTurnoRequest request,
            @AuthenticationPrincipal UserJpaModel usuario
    ) {
        String observacion = request != null ? request.getObservacion() : null;
        TurnosJpaModel turno = turnosService.cancelarTurno(id, usuario, observacion);
        return ResponseEntity.ok(TurnosMapper.toResponse(turno));
    }

    @PutMapping("/{id}/ausente")
    @Operation(
            summary = "Marcar turno como ausente",
            description = "Cambia el estado del turno a AUSENTE cuando el cliente no responde al llamado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turno marcado como ausente correctamente",
                    content = @Content(schema = @Schema(implementation = TurnoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado"),
            @ApiResponse(responseCode = "409", description = "El turno no puede marcarse como ausente por su estado actual")
    })
    public ResponseEntity<TurnoResponse> marcarAusente(
            @Parameter(description = "ID del turno", required = true)
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Observación opcional sobre la ausencia",
                    required = false
            )
            @RequestBody(required = false) MarcarAusenteRequest request,
            @AuthenticationPrincipal UserJpaModel usuario
    ) {
        String observacion = request != null ? request.getObservacion() : null;
        TurnosJpaModel turno = turnosService.marcarAusente(id, usuario, observacion);
        return ResponseEntity.ok(TurnosMapper.toResponse(turno));
    }
}