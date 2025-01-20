package com.imd.web2.entities.DTO;

import com.imd.web2.usuarios.Enums.RoleEnum;

public record RegisterDTO(
        String login,
        String password,
        RoleEnum role
) {

}