package com.datalock.datalock.turnos.service;


import com.datalock.datalock.turnos.entities.SeccionesJpaModel;
import com.datalock.datalock.turnos.entities.TurnosJpaModel;

import java.util.List;
import java.util.UUID;

public interface SeccionesService {

    SeccionesJpaModel crear(SeccionesJpaModel secciones);

    SeccionesJpaModel obtenerPorId(UUID id);

    List<SeccionesJpaModel> listarTodos();

    SeccionesJpaModel actualizar(UUID id, SeccionesJpaModel secciones);

    void eliminar(UUID id);
}