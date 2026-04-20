package com.datalock.datalock.turnos.controllers;

import com.datalock.datalock.turnos.entities.SeccionesJpaModel;
import com.datalock.datalock.turnos.service.SeccionesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/secciones")
@RequiredArgsConstructor
public class SeccionesController {

    private final SeccionesService service;

    @GetMapping
    public List<SeccionesJpaModel> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public SeccionesJpaModel obtener(@PathVariable UUID id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SeccionesJpaModel crear(@RequestBody SeccionesJpaModel secciones) {
        return service.crear(secciones);
    }

    @PutMapping("/{id}")
    public SeccionesJpaModel actualizar(@PathVariable UUID id, @RequestBody SeccionesJpaModel secciones) {
        return service.actualizar(id, secciones);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable UUID id) {
        service.eliminar(id);
    }
}