package com.api.manager.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

@Data
@Entity
@Table(name = "hired_employee")
public class HiredEmployeeDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Long idRole;
    @NonNull
    private Long idTeam;
}
