package com.datalock.datalock.turnos.service;

import com.datalock.datalock.turnos.entities.SeccionesJpaModel;

import java.util.List;
import java.util.UUID;

public interface SeccionesService {

    SeccionesJpaModel crear(SeccionesJpaModel secciones);

    SeccionesJpaModel obtenerPorId(UUID id);

    List<SeccionesJpaModel> listarTodos();

    List<SeccionesJpaModel> listarActivos(); // ← nuevo

    SeccionesJpaModel actualizar(UUID id, SeccionesJpaModel secciones);

    void eliminar(UUID id);
}