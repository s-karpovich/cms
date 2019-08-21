package com.highend.cms.web.controller;

import com.highend.cms.service.RoleService;
import com.highend.cms.service.UserAccountService;
import com.highend.cms.service.enums.Status;
import com.highend.cms.service.model.PageDTO;
import com.highend.cms.service.model.RoleDTO;
import com.highend.cms.service.model.UserAccountDTO;
import com.highend.cms.web.validator.EditUserAccountFormValidator;
import com.highend.cms.web.validator.NewUserAccountFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserAccountController {

    private static final Logger logger = LoggerFactory.getLogger(UserAccountController.class);
    private final UserAccountService userAccountService;
    private final RoleService roleService;
    private final NewUserAccountFormValidator newUserAccountFormValidator;
    private final EditUserAccountFormValidator editUserAccountFormValidator;

    public UserAccountController(UserAccountService userAccountService, RoleService roleService, NewUserAccountFormValidator newUserAccountFormValidator, EditUserAccountFormValidator editUserAccountFormValidator) {
        this.userAccountService = userAccountService;
        this.roleService = roleService;
        this.newUserAccountFormValidator = newUserAccountFormValidator;
        this.editUserAccountFormValidator = editUserAccountFormValidator;
    }

    @GetMapping("/user")
    public String getUsers(@RequestParam(name = "page", defaultValue = "1") Integer page, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserAccountDTO currentUserAccount = userAccountService.getByUsername(username);
        PageDTO<UserAccountDTO> userAccounts = userAccountService.getUserAccounts(page);
        List<RoleDTO> roles = roleService.getRoles();
        model.addAttribute("userAccounts", userAccounts.getList());
        model.addAttribute("pages", userAccounts.getCountOfPages());
        model.addAttribute("roles", roles);
        model.addAttribute("currentUser", currentUserAccount);

        return "user";
    }

    @GetMapping("/user/new")
    public String addUser(UserAccountDTO userAccountDTO, ModelMap modelMap) {
        modelMap.addAttribute(userAccountDTO);
        List<RoleDTO> rolesList = roleService.getRoles();
        modelMap.addAttribute("roles", rolesList);

        return "new";
    }

    @PostMapping("/user/new")
    public String addUser(
            @ModelAttribute UserAccountDTO userAccountDTO,
            Model modelMap,
            BindingResult bindingResult
    ) {
        newUserAccountFormValidator.validate(userAccountDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            List<RoleDTO> rolesList = roleService.getRoles();
            modelMap.addAttribute("roles", rolesList);
            logger.error("User has not been added due to error, (ID): {}", userAccountDTO.getId());
            return "new";
        }
        userAccountService.create(userAccountDTO);
        logger.info("User has been successfully created (ID): {}", userAccountDTO.getId());
        return "redirect:/success";
    }

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable(value = "id") Long id, ModelMap modelMap) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserAccountDTO currentUserAccount = userAccountService.getByUsername(username);
        UserAccountDTO userAccount = userAccountService.getUserById(id);
        modelMap.addAttribute("userAccount", userAccount);
        modelMap.addAttribute("currentUserAccount", currentUserAccount);

        return "view";
    }

    @PostMapping("/user/{id}")
    public String getUser(@PathVariable(value = "id") Long id,
                          @ModelAttribute Status status) {
        UserAccountDTO userAccountDTO = userAccountService.getUserById(id);
        userAccountDTO.setStatus(status);
        userAccountService.update(userAccountDTO);
        logger.info("User status updated (ID): {}", userAccountDTO.getId());

        return "redirect:/success";
    }

    @GetMapping("/user/{id}/edit")
    public String editUser(@PathVariable(value = "id") Long id,
                           UserAccountDTO userAccountDTO,
                           ModelMap modelMap) {

        UserAccountDTO userAccount = userAccountService.getUserById(id);
        List<RoleDTO> rolesList = roleService.getRoles();
        modelMap.addAttribute("userAccount", userAccount);
        modelMap.addAttribute("userAccountDTO", userAccountDTO);
        modelMap.addAttribute("roles", rolesList);

        return "edit";
    }

    @PostMapping("/user/{id}/edit")
    public String editUser(@PathVariable(value = "id") Long id,
                           UserAccountDTO userAccount,
                           @ModelAttribute UserAccountDTO userAccountDTO,
                           ModelMap model,
                           BindingResult bindingResult) {
        userAccountDTO.setId(id);
        editUserAccountFormValidator.validate(userAccountDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            List<RoleDTO> rolesList = roleService.getRoles();
            model.addAttribute("userAccount", userAccount);
            model.addAttribute("userAccountDTO", userAccountDTO);
            model.addAttribute("roles", rolesList);
            logger.error("User has not been updated due to error, (ID): {}", userAccountDTO.getId());

            return "edit";
        }
        userAccountDTO.setChangedAt(userAccountService.getUserById(id).getChangedAt());
        userAccountDTO.setStatus(userAccountService.getUserById(id).getStatus());
        userAccountService.update(userAccountDTO);
        logger.info("User updated (ID): {}", userAccountDTO.getId());

        return "redirect:/success";
    }
}

