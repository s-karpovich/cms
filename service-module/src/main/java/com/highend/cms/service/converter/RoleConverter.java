package com.highend.cms.service.converter;

import com.highend.cms.repository.model.Role;
import com.highend.cms.service.model.RoleDTO;

public interface RoleConverter {

    RoleDTO toDTO(Role role);
    Role fromDTO(RoleDTO roleDTO);
}
