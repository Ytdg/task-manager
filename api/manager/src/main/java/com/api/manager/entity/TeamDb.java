package com.api.manager.entity;

import com.api.manager.common.StatusObj;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

@Data
@Entity
@Table(name = "team")
public class TeamDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @NotBlank
    private String name;
    @NonNull
    private  Long idTask;
}
