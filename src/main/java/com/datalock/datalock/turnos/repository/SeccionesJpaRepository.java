package com.datalock.datalock.turnos.repository;


import com.datalock.datalock.turnos.entities.SeccionesJpaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SeccionesJpaRepository extends JpaRepository<SeccionesJpaModel, UUID> {
}
