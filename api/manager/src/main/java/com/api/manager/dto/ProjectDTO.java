package com.api.manager.dto;

import com.api.manager.entity.MetaDB;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ProjectDTO {
    @Nullable
    private Long id;
    @NotBlank
    @Size(max = 350)
    private String name;
    @Nullable
    private UserDTO creator;
    @Nullable
    private MetaDB metaDB;

    public ProjectDTO() {
    }


}
