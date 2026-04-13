package com.datalock.datalock.turnos.repository;


import com.datalock.datalock.turnos.entities.TurnoHistorialJpaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TurnoHistorialJpaRepository extends JpaRepository<TurnoHistorialJpaModel, UUID> {

    List<TurnoHistorialJpaModel> findByTurnoIdOrderByFechaHoraEventoAsc(UUID turnoId);

    List<TurnoHistorialJpaModel> findByTurnoIdOrderByFechaHoraEventoDesc(UUID turnoId);
}
