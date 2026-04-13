package com.datalock.datalock.turnos.repository;



import com.datalock.datalock.turnos.entities.EstadoTurnoEnum;
import com.datalock.datalock.turnos.entities.TurnosJpaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TurnosJpaRepository extends JpaRepository<TurnosJpaModel, UUID> {

    Optional<TurnosJpaModel> findByNumeroTurno(String numeroTurno);

    List<TurnosJpaModel> findByFechaTurnoOrderByNumeroSecuenciaAsc(LocalDate fechaTurno);

    List<TurnosJpaModel> findBySeccionIdAndFechaTurnoOrderByNumeroSecuenciaAsc(UUID seccionId, LocalDate fechaTurno);

    List<TurnosJpaModel> findBySeccionIdAndEstadoTurnoOrderByFechaHoraEmisionAsc(UUID seccionId, EstadoTurnoEnum estadoTurno);

    List<TurnosJpaModel> findBySeccionIdAndEstadoTurnoInOrderByFechaHoraEmisionAsc(
            UUID seccionId,
            List<EstadoTurnoEnum> estados
    );

    boolean existsByNumeroTurno(String numeroTurno);
}