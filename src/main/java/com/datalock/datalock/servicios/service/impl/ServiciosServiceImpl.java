package com.datalock.datalock.servicios.service.impl;

import com.datalock.datalock.servicios.entities.ServiciosJpaModel;
import com.datalock.datalock.servicios.repository.ServiciosJpaRepository;
import com.datalock.datalock.servicios.service.ServiciosService;
import com.datalock.datalock.turnos.entities.SeccionesJpaModel;
import com.datalock.datalock.turnos.repository.SeccionesJpaRepository;
import com.datalock.datalock.turnos.service.SeccionesService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiciosServiceImpl implements ServiciosService {

    private final ServiciosJpaRepository repository;

    @Override
    public ServiciosJpaModel crear(ServiciosJpaModel secciones) {
        return repository.save(secciones);
    }

    @Override
    public ServiciosJpaModel obtenerPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sección no encontrada: " + id));
    }

    @Override
    public List<ServiciosJpaModel> listarTodos() {
        return repository.findAll();
    }


    @Override
    public ServiciosJpaModel actualizar(UUID id, ServiciosJpaModel datos) {
        ServiciosJpaModel existente = obtenerPorId(id);
        existente.setCodigo(datos.getCodigo());
        existente.setDescripcion(datos.getDescripcion());
        return repository.save(existente);
    }

    @Override
    public void eliminar(UUID id) {
        ServiciosJpaModel existente = obtenerPorId(id);
        existente.softDelete();
        repository.save(existente);
    }
}