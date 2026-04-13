package com.datalock.datalock.utils.infra;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
public abstract class BaseDbModel implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "active", nullable = false)
    private Boolean active = true;


    /**
     * Se ejecuta automáticamente antes de insertar en BD.
     */
    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        active = true;
    }

    /**
     * Se ejecuta automáticamente antes de actualizar en BD.
     */
    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Método para marcado lógico de eliminación.
     */
    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
        this.active = false;
    }
}
