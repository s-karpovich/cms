package com.highend.cms.service.impl;

import com.highend.cms.repository.UserAccountRepository;
import com.highend.cms.repository.model.UserAccount;
import com.highend.cms.service.UserAccountService;
import com.highend.cms.service.converter.UserAccountConverter;
import com.highend.cms.service.enums.Status;
import com.highend.cms.service.model.UserAccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private static final Logger logger = LoggerFactory.getLogger(UserAccountServiceImpl.class);
    private final UserAccountConverter userAccountConverter;
    private final UserAccountRepository userAccountRepository;

    public UserAccountServiceImpl(UserAccountConverter userAccountConverter,
                                  UserAccountRepository userAccountRepository
    ) {
        this.userAccountConverter = userAccountConverter;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    @Transactional
    public void create(UserAccountDTO userAccountDTO) {
        userAccountDTO.setStatus(Status.ACTIVE);
        userAccountDTO.setChangedAt(LocalDateTime.now());
        UserAccount userAccount = userAccountConverter.fromDTO(userAccountDTO);
        userAccountRepository.persist(userAccount);
    }

    @Override
    @Transactional
    public void update(UserAccountDTO userAccountDTO) {
        UserAccount userAccount = userAccountConverter.fromDTO(userAccountDTO);
        userAccountRepository.merge(userAccount);
    }

    @Override
    @Transactional
    public List<UserAccountDTO> getUsers() {
        List<UserAccount> userList = userAccountRepository.getAll();
//        List<UserAccountDTO> userAccountDTOList = new ArrayList<>();
//        for (UserAccount userAccount : userList) {
//            userAccountDTOList.add(userAccountConverter.toDTO(userAccount));
//        }
       // return userAccountDTOList;
        return userList.stream()
                .map(userAccountConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserAccountDTO getByUsername(String username) {
        UserAccount userAccount = userAccountRepository.getByUsername(username);
        if (userAccount == null) {
            return null;
        }
        return userAccountConverter.toDTO(userAccount);
    }

    @Override
    @Transactional
    public UserAccountDTO getUserById(Long id) {
        return userAccountConverter.toDTO(userAccountRepository.getById(id));
    }
}



