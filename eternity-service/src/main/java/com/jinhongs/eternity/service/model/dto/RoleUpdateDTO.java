package com.jinhongs.eternity.service.model.dto;

import lombok.Data;

@Data
public class RoleUpdateDTO {
    private Long id;
    private String roleName;
    private Byte isEnable;
}
