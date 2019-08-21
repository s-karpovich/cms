package com.highend.cms.service;

import com.highend.cms.service.model.UserAccountDTO;

import java.util.List;

public interface UserAccountService {

    void create(UserAccountDTO userAccountDTO);

    void update(UserAccountDTO userAccountDTO);

    List<UserAccountDTO> getUsers();

    UserAccountDTO getByUsername(String username);

    UserAccountDTO getUserById(Long id);
}
