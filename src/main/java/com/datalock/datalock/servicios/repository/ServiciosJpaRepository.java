package com.datalock.datalock.servicios.repository;


import com.datalock.datalock.servicios.entities.ServiciosJpaModel;
import com.datalock.datalock.turnos.entities.SeccionesJpaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ServiciosJpaRepository extends JpaRepository<ServiciosJpaModel, UUID> {


}
