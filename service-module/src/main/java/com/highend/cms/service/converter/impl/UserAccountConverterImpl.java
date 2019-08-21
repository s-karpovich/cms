package com.highend.cms.service.converter.impl;

import com.highend.cms.repository.model.UserAccount;
import com.highend.cms.service.converter.RoleConverter;
import com.highend.cms.service.converter.UserAccountConverter;
import com.highend.cms.service.enums.Status;
import com.highend.cms.service.model.UserAccountDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static com.highend.cms.service.constant.DateTimeConstant.DATE_PATTERN;

@Component
public class UserAccountConverterImpl implements UserAccountConverter {


    private final RoleConverter roleConverter;
    private final PasswordEncoder serviceEncoder;

    public UserAccountConverterImpl(RoleConverter roleConverter, PasswordEncoder serviceEncoder) {
        this.roleConverter = roleConverter;
        this.serviceEncoder = serviceEncoder;
    }

    @Override
    public UserAccountDTO toDTO(UserAccount userAccount) {
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setId(userAccount.getId());
        userAccountDTO.setChangedAt(userAccount.getChangedAt().
                format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
        userAccountDTO.setUsername(userAccount.getUsername());
        userAccountDTO.setPassword(userAccount.getPassword());
        userAccountDTO.setFirstName(userAccount.getFirstName());
        userAccountDTO.setLastName(userAccount.getLastName());
        userAccountDTO.setRoleDTO(roleConverter.toDTO(userAccount.getRole()));
        userAccountDTO.setStatus(Status.valueOf(userAccount.getStatus()));
        return userAccountDTO;
    }

    @Override
    public UserAccount fromDTO(UserAccountDTO userAccountDTO) {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(userAccountDTO.getId());
        userAccount.setChangedAt(LocalDateTime.parse(userAccountDTO.getChangedAt(),
                DateTimeFormatter.ofPattern(DATE_PATTERN)));
        userAccount.setUsername(userAccountDTO.getUsername());
        userAccount.setPassword(serviceEncoder.encode(userAccountDTO.getPassword()));
        userAccount.setFirstName(userAccountDTO.getFirstName());
        userAccount.setLastName(userAccountDTO.getLastName());
        userAccount.setRole(roleConverter.fromDTO(userAccountDTO.getRoleDTO()));
        userAccount.setStatus(userAccountDTO.getStatus().name());
        return userAccount;
    }
}
