package com.imd.web2.usuarios.Enums;

public enum RoleEnum {
    ADMIN("admin"),
    USUARIO("users");

    String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

}
