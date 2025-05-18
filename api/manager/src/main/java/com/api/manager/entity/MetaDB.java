package com.api.manager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "meta_project")
public class MetaDB {
    @Id
    private final Long idProject;

    private LocalDate localDate;

    public MetaDB(Long idProject) {
        this.idProject = idProject;
    }
}
