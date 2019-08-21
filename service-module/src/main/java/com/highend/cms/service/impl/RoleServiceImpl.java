package com.highend.cms.service.impl;

import com.highend.cms.repository.RoleRepository;
import com.highend.cms.repository.model.Role;
import com.highend.cms.service.RoleService;
import com.highend.cms.service.converter.RoleConverter;
import com.highend.cms.service.model.RoleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;

    public RoleServiceImpl(RoleRepository roleRepository, RoleConverter roleConverter) {
        this.roleRepository = roleRepository;
        this.roleConverter = roleConverter;
    }

    @Override
    @Transactional
    public List<RoleDTO> getRoles() {
        List<Role> roleNames = roleRepository.getAll();
        return roleNames.stream()
                .map(roleConverter::toDTO)
                .collect(Collectors.toList());
    }
}

