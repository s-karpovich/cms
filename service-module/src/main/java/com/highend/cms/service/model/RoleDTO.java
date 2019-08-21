package com.highend.cms.service.model;

import javax.validation.constraints.NotNull;

public class RoleDTO {

    @NotNull
    private Long id;
    private String name;

    public RoleDTO() {
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
