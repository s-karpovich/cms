package com.highend.cms.service.impl;

import com.highend.cms.repository.UserAccountRepository;
import com.highend.cms.repository.model.UserAccount;
import com.highend.cms.service.UserAccountService;
import com.highend.cms.service.converter.UserAccountConverter;
import com.highend.cms.service.enums.Status;
import com.highend.cms.service.model.PageDTO;
import com.highend.cms.service.model.UserAccountDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import static com.highend.cms.service.constant.DateTimeConstant.DATE_PATTERN;


@Service
public class UserAccountServiceImpl implements UserAccountService {

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
        userAccountDTO.setChangedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
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
    public PageDTO<UserAccountDTO> getUserAccounts(int page) {
        PageDTO<UserAccountDTO> userAccounts = new PageDTO<>();
        List<UserAccountDTO> usersDTO = getPageOfUsers(page);
        userAccounts.setList(usersDTO);
        userAccounts.setCountOfPages(userAccountRepository.getCountOfPages());
        return userAccounts;
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

    private List<UserAccountDTO> getPageOfUsers(int page) {
        return userAccountRepository.getPage(page)
                .stream()
                .map(userAccountConverter::toDTO)
                .collect(Collectors.toList());
    }
}



