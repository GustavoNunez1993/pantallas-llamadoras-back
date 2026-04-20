package com.datalock.datalock.turnos.service.impl;

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
public class SeccionesServiceImpl implements SeccionesService {

    private final SeccionesJpaRepository repository;

    @Override
    public SeccionesJpaModel crear(SeccionesJpaModel secciones) {
        return repository.save(secciones);
    }

    @Override
    public SeccionesJpaModel obtenerPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sección no encontrada: " + id));
    }

    @Override
    public List<SeccionesJpaModel> listarTodos() {
        return repository.findAll();
    }

    @Override
    public SeccionesJpaModel actualizar(UUID id, SeccionesJpaModel datos) {
        SeccionesJpaModel existente = obtenerPorId(id);
        existente.setCodigo(datos.getCodigo());
        existente.setDescripcion(datos.getDescripcion());
        return repository.save(existente);
    }

    @Override
    public void eliminar(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Sección no encontrada: " + id);
        }
        repository.deleteById(id);
    }
}