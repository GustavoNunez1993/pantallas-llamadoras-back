package com.datalock.datalock.servicios.service;

import com.datalock.datalock.servicios.entities.ServiciosJpaModel;

import java.util.List;
import java.util.UUID;

public interface ServiciosService {

    ServiciosJpaModel crear(ServiciosJpaModel servicios);

    ServiciosJpaModel obtenerPorId(UUID id);

    List<ServiciosJpaModel> listarTodos();


    ServiciosJpaModel actualizar(UUID id, ServiciosJpaModel secciones);

    void eliminar(UUID id);
}