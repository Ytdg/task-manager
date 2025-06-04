package com.api.manager.entity;

import com.api.manager.common.StatusObj;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

@Data
@Entity
@Table(name = "Task")
public class TaskDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private Long idSprint;
    @NonNull
    @Enumerated(EnumType.STRING)
    private StatusObj status;
    @NonNull
    @NotBlank
    private String detail;
}
