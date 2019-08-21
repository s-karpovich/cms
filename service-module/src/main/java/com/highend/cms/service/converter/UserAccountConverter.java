package com.highend.cms.service.converter;

import com.highend.cms.repository.model.UserAccount;
import com.highend.cms.service.model.UserAccountDTO;

public interface UserAccountConverter {

    UserAccountDTO toDTO(UserAccount userAccount);
    UserAccount fromDTO(UserAccountDTO userAccountDTO);
}
