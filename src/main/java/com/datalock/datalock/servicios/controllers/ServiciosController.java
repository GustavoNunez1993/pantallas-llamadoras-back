package com.datalock.datalock.servicios.controllers;

import com.datalock.datalock.servicios.entities.ServiciosJpaModel;
import com.datalock.datalock.servicios.service.ServiciosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
public class ServiciosController {

    private final ServiciosService service;

    @GetMapping
    public List<ServiciosJpaModel> listar() {
        return service.listarTodos();
    }


    @GetMapping("/{id}")
    public ServiciosJpaModel obtener(@PathVariable UUID id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiciosJpaModel crear(@RequestBody ServiciosJpaModel servicios) {
        return service.crear(servicios);
    }

    @PutMapping("/{id}")
    public ServiciosJpaModel actualizar(@PathVariable UUID id, @RequestBody ServiciosJpaModel servicios) {
        return service.actualizar(id, servicios);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable UUID id) {
        service.eliminar(id);
    }
}