package com.highend.cms.service;

import com.highend.cms.service.model.PageDTO;
import com.highend.cms.service.model.UserAccountDTO;

public interface UserAccountService {

    void create(UserAccountDTO userAccountDTO);

    void update(UserAccountDTO userAccountDTO);

    UserAccountDTO getByUsername(String username);

    UserAccountDTO getUserById(Long id);

    PageDTO<UserAccountDTO> getUserAccounts(int page);
}
