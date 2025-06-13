package com.api.manager.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "meta_project")
public class MetaEntity {
    @Id
    private Long idProject;
    @NonNull
    private LocalDateTime localDate;

    public MetaEntity(Long idProject, @NonNull LocalDateTime localDateTime) {
        this.idProject = idProject;
        this.localDate = localDateTime;
    }

    public MetaEntity() {
    }
}
